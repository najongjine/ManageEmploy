package com.open.cmmn.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.open.cmmn.dao.CmmnDAO;
import com.open.cmmn.service.CmmnService;
import com.open.vo.EmployVO;
import com.open.vo.ManageVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * <pre>
 * Class Name : CmmnServiceImpl.java
 * Description: CmmnServiceImpl.
 * Modification Information
 *
 *    수정일         수정자         수정내용
 *    -------        -------     -------------------
 *    2017. 1. 19.	 User		최초생성
 *
 * </pre>
 *
 * @author User
 * @since 2017. 1. 19.
 * @version 1.0
 * @see <pre>
 *
 * </pre>
 */
@Service("cmmnManageService")
public class CmmnManageServiceImpl extends EgovAbstractServiceImpl {

	/** CmmnDAO . */
	@Autowired
	private CmmnDAO cmmnDAO;

	
	public List<?> selectList(final Object paramVO, final String queryId) throws Exception {
		return cmmnDAO.selectList(paramVO, queryId);
	}

	
	public Map<?, ?> selectMap(final Object paramVO, final String queryId, final String mapKey) throws Exception {
		return cmmnDAO.selectMap(paramVO, queryId, mapKey);
	}

	
	public int selectCount(final Object paramVO, final String queryId) throws Exception {
		return cmmnDAO.selectCount(paramVO, queryId);
	}

	
	public Object selectContents(final Object paramVO, final String queryId) throws Exception {
		return cmmnDAO.selectContents(paramVO, queryId);
	}

	
	public void insertContents(final Object paramVO, final String queryId) throws Exception {
		cmmnDAO.insertContents(paramVO, queryId);
	}

	
	public Object insertSelectKey(final Object pParamVO, final String pQueryId) throws Exception {
		return cmmnDAO.insertSelectKey(pParamVO, pQueryId);
	}

	
	public void updateContents(final Object paramVO, final String queryId) throws Exception {
		cmmnDAO.updateContents(paramVO, queryId);
	}
	
	public void updateManageContents(final Object paramVO, final String queryId) throws Exception {
		cmmnDAO.updateContents(paramVO, queryId);
		cmmnDAO.deleteContents(paramVO, queryId+".deleteEmpList");
		List<EmployVO> empList=((ManageVO)paramVO).getEmployList();
		cmmnDAO.insertContents(empList, queryId + ".insertListContents");
	}

	
	public void deleteContents(final Object paramVO, final String queryId) throws Exception {
		cmmnDAO.deleteContents(paramVO, queryId);
	}
	
	
	public void insertAllContents(final List<?> paramList, final String queryId) throws Exception {
		cmmnDAO.insertAllContents(paramList, queryId);
	}
	

}
