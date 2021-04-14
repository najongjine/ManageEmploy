package com.open.ma.sys.mn;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.open.cmmn.model.CmmnDefaultVO;
import com.open.cmmn.service.CmmnService;
import com.open.cmmn.service.FileMngService;
import com.open.cmmn.util.SessionUtil;
import com.open.cmmn.util.StringUtil;
import com.open.ma.sys.mn.service.MnVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/** 공지사항 게시판을 관리하는 컨트롤러 클래스를 정의한다.
 */
@Controller
public class MnController {

	@Resource(name = "cmmnService")
    protected CmmnService cmmnService;
	
	@Resource(name = "FileMngService")
    private FileMngService fileMngService;
    
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** fileUploadProperties */
	@Resource(name = "fileUploadProperties")
	Properties fileUploadProperties;
	
	/**
	 * MappingJackson2JsonView.
	 */
	@Resource
	private MappingJackson2JsonView ajaxView;
	
	
    /** Program ID **/
    private final static String PROGRAM_ID = "Mn";

    /** folderPath **/
    private final static String folderPath = "/ma/sys/mn/";

	//@Resource(name = "beanValidator")
	//protected DefaultBeanValidator beanValidator;
	
    @Autowired
	private EhCacheCacheManager cacheManager;
    
	Logger log = Logger.getLogger(this.getClass());

	/**
	 * 메뉴권한 목록화면
	 * @param searchVO
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(folderPath + "list.do")
	public String list(@ModelAttribute("searchVO") CmmnDefaultVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {

		return ".mLayout:"+ folderPath + "list";
	}
	
	/**
	 * 메뉴권한 목록화면
	 * @param searchVO
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(folderPath + "addList.do")
	public String addList(@ModelAttribute("searchVO") CmmnDefaultVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		

		/**Cache sample */
		Ehcache cache = cacheManager.getCacheManager().getCache("properties");
		Element pageUnit = cache.get("pageUnit");
		Element pageSize = cache.get("pageSize");		 	

		
		if (pageUnit != null && pageSize != null) {
			searchVO.setPageUnit(Integer.parseInt(pageUnit.getValue().toString()));
			searchVO.setPageSize(Integer.parseInt(pageSize.getValue().toString()));
		} else {
			/** EgovPropertyService.sample */
			searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
			searchVO.setPageSize(propertiesService.getInt("pageSize"));
			/** cache에 입력 */
			cache.put(new Element("pageUnit", propertiesService.getInt("pageUnit")));
			cache.put(new Element("pageSize", propertiesService.getInt("pageSize")));
		}


		/** pageing setting */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		searchVO.setSchEtc01("1");
		
		int totCnt = cmmnService.selectCount(searchVO, PROGRAM_ID );
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		

		@SuppressWarnings("unchecked")
		List<MnVO> resultList = (List<MnVO>) cmmnService.selectList(searchVO, PROGRAM_ID );
		model.addAttribute("resultList", resultList);
		

		
		return ""+ folderPath + "addList";
	}



	/**
	 * 메뉴권한 상세화면
	 * @param searchVO
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(folderPath +"view.do")
	public String view(@ModelAttribute("searchVO") MnVO searchVO, Model model, HttpServletRequest request) throws Exception {
		
		System.out.println("searchVO :: "+searchVO.getLvl());
		
		/* 게시판 상세정보 */
		MnVO mnVO = new MnVO();
		mnVO = (MnVO) cmmnService.selectContents(searchVO, PROGRAM_ID );
		model.addAttribute("mnVO", mnVO);
		
		MnVO menuList = new MnVO();
		menuList.setLvl((Integer.parseInt(mnVO.getLvl())+1)+"");
		menuList.setMenuCl(mnVO.getMenuCl());
		menuList.setMenuSeq(mnVO.getMenuSeq());
		menuList.setAuthCode("1");
		List<MnVO> menuList2 = (List<MnVO>) cmmnService.selectList(menuList, PROGRAM_ID + ".menuSelectList");
		model.addAttribute("menuList2", menuList2);

		
		return ".mLayout:"+ folderPath + "view";
	}

	/**
	 * 메뉴권한 등록화면
	 * @param searchVO
	 * @param model
	 * @param procType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(folderPath + "{procType}Form.do")
	public String form(@ModelAttribute("searchVO") MnVO searchVO, Model model,@PathVariable String procType, HttpServletRequest request) throws Exception {
		
		MnVO mnVO = new MnVO();
		if (procType.equals("update") || procType.equals("subUpdate")) {
			mnVO = (MnVO) cmmnService.selectContents(searchVO, PROGRAM_ID);
			if(!StringUtil.nullString(SessionUtil.getUserDetails().getAuthCode()).equals("1") && !StringUtil.nullString(mnVO.getRgstId()).equals(StringUtil.nullString(SessionUtil.getUserDetails().getLoginSeq()))){
				model.addAttribute("message", "비정상적인 접근입니다.");
				model.addAttribute("cmmnScript", "list.do");
				return "cmmn/execute";
			}
		}else{
			mnVO.setMenuSeq(searchVO.getMenuSeq());
			mnVO.setMenuGroupSeq(searchVO.getMenuGroupSeq());
			mnVO.setLvl(searchVO.getLvl());
		}

		searchVO.setProcType(procType);
		mnVO.setSearchVO(searchVO);
		model.addAttribute("mnVO", mnVO);

		return ".mLayout:"+ folderPath + "form";
	}

	/**
	 * 메뉴권한 처리
	 * @param searchVO
	 * @param model
	 * @param status
	 * @param procType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = folderPath + "{procType}Proc.do", method = RequestMethod.POST)
	public String proc(@ModelAttribute("searchVO") MnVO searchVO, Model model, SessionStatus status,@PathVariable String procType, HttpServletRequest request) throws Exception {
		
		
		if(procType != null){
			
			if (procType.equals("insert")) {
				
				cmmnService.insertContents(searchVO, PROGRAM_ID);
				
			} else if (procType.equals("update") ) {
				
				cmmnService.updateContents(searchVO, PROGRAM_ID);
				
			} else if (procType.equals("delete")) {
				
				cmmnService.deleteContents(searchVO, PROGRAM_ID);
				
			}  else if (procType.equals("subInsert")) {
				
				cmmnService.insertContents(searchVO, PROGRAM_ID+".subInsertContents");
				
			} else if (procType.equals("subUpdate") ) {
				
				cmmnService.updateContents(searchVO, PROGRAM_ID);
				
			} else if (procType.equals("subDelete") ) {
				
				cmmnService.deleteContents(searchVO, PROGRAM_ID);
				
			}
			
			status.setComplete(); // 중복 Submit 방지 : 세션에 저장된 model 을 삭제한다.
			
			if(procType.equals("update")){
				model.addAttribute("message", "수정되었습니다.");
				model.addAttribute("cmmnScript", "view.do?menuSeq="+searchVO.getMenuSeq());
				return "cmmn/execute";
	    	}else if(procType.equals("insert")){
				model.addAttribute("message", "등록되었습니다.");
				model.addAttribute("cmmnScript", "list.do");
				return "cmmn/execute";
	    	}else if(procType.equals("delete") ){
				model.addAttribute("message", "삭제되었습니다..");
				model.addAttribute("cmmnScript", "list.do");
				return "cmmn/execute";
	    	}else if(procType.equals("subInsert")){
				model.addAttribute("message", "등록되었습니다.");
				model.addAttribute("cmmnScript", "view.do?menuSeq="+searchVO.getMenuGroupSeq());
				return "cmmn/execute";
	    	}else if(procType.equals("subUpdate")){
				model.addAttribute("message", "수정되었습니다.");
				model.addAttribute("cmmnScript", "view.do?menuSeq="+searchVO.getMenuGroupSeq());
				return "cmmn/execute";
	    	}else if(procType.equals("subDelete") ){
				model.addAttribute("message", "삭제되었습니다..");
				model.addAttribute("cmmnScript", "view.do?menuSeq="+searchVO.getMenuGroupSeq());
				return "cmmn/execute";
	    	}else{
	    		return "redirect:list.do";
	    	}
		}

		return "redirect:list.do";

	}
	
	/**
	 * 메뉴코드 중복체크
	 * @param searchVO
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = folderPath + "menuCodeOverlap.do")
	public ModelAndView menuCodeOverlap(@ModelAttribute("searchVO") MnVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		
		int overCnt = cmmnService.selectCount(searchVO, PROGRAM_ID + ".overlapSelectCount") ;
		
		model.addAttribute("overCnt", overCnt);
		
		return new ModelAndView(ajaxView,model);
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/menuSel.do")
	public String menuSel( @ModelAttribute("searchVO") MnVO searchVO,  ModelMap model,HttpServletRequest request) throws Exception {
		
		String gubun = StringUtil.nullString(request.getParameter("gubun"));
		String selVal = StringUtil.nullString(request.getParameter("selVal"));
		
		List<MnVO> menuList = (List<MnVO>) cmmnService.selectList(searchVO, PROGRAM_ID + ".menuSelSelectList");
		model.addAttribute("menuList", menuList);
		model.addAttribute("gubun", gubun);
		model.addAttribute("selVal", selVal);
		
		return folderPath+"menuSel";
	}

	
		

}
