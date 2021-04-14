<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/jsp/cmmn/incTagLib.jsp"/>
<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<script type="text/javascript" src="/publish/ma/js/board.js"></script>
<script type="text/javascript" src="/resource/editor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/babel">
var oEditors = [];
var index='${index}' > 0 ? '${index}' : 1
$(document).ready(function(){	
	<%-- 에디터 --%>
	nhn.husky.EZCreator.createInIFrame({
	    oAppRef: oEditors,
	    elPlaceHolder: "cont",
	    sSkinURI: "/resource/editor/SmartEditor2Skin.html",
	    fCreator: "createSEditor2"
	}); 
	$("#btn_submit").bind("click", function(){
		
		if($("#title").val() == "" || $("#title").val() == null) {
			alert("제목을 입력해주세요");
			$("#title").focus();
			return false;
		}
			oEditors.getById["cont"].exec("UPDATE_CONTENTS_FIELD", []);/* 에디터 */
			
		if($("#cont").val()=='<p>&nbsp;</p>') {
			alert("내용을 입력해주세요");
			oEditors.getById["cont"].exec("FOCUS"); /* 에디터 */
			return false;
		}		
		var empnames=$(".empname")
		var empdepts=$(".empdept")
		for(var i = 0; i < empnames.length; i++){
			var inputStr=$(empnames[i]).val().trim()
			if(!inputStr){
				alert("이름을 입력해주세요")
				return false				
			}
		}
		for(var i = 0; i < empdepts.length; i++){
			var inputStr=$(empdepts[i]).val().trim()
			if(!inputStr){
				alert("부서를 입력해주세요")
				return false				
			}
		}
		
		fncPageBoard('write','${searchVO.procType}Proc.do');
		return false;
	});
	
	$("#btn_returnView").click(function(){
		$("#boardSeq").val($("#boardGrpSeq").val());
		fncPageBoard('view','view.do');
	});
	
	$(document).on("click",".addInput",function(){
		let seq='${resultVO.seq}'
		let inputHtml=`
			<tr id="inputTr_\${index}">
			<td>
			<input type="hidden" name="employList[\${index}].subSeq" id="subSeq\${index}" value=\${seq}>
			<input name="employList[\${index}].empname" id="empname\${index}" class="empname">
			</td>
			<td>
				<input name="employList[\${index}].empdept" id="empdept\${index}" class="empdept">
			</td>
			<td>
				<input name="employList[\${index}].emprank" id="emprank\${index}"  >
			</td>
			<td>
				<input type="number" name="employList[\${index}].hp" id="hp\${index}">
			</td>
			<td>
				<button class="addInput" type="button">+</button>
			</td>
			<td>
				<button type="button" id="removeTr_\${index}" class="removeInput">-</button>
			</td>
			</tr>
		`
		$("#manageTable").append(inputHtml)
		index++
		return false
	})
	
	$(document).on("click",".removeInput",function(){
		var inputBoxs=$(".removeInput")
		if(inputBoxs.length < 2){
			return false
		}
		var idArray=$(this).attr('id').split("_")
		var id=idArray[idArray.length-1]
		$("#inputTr_"+id).remove();
		return false
	})
});
</script>
<div class="content_box">
	<form:form commandName="resultVO" name="defaultFrm" id="defaultFrm" method="post">
		<form:hidden path="seq" id="seq"/>
		<form:hidden path="pageIndex" id="pageIndex"/> 
		<form:hidden path="atchFileId" id="atchFileId"/>
		<jsp:directive.include file="/WEB-INF/jsp/cmmn/inc/incSearchForm.jsp"/>
		<div class="tbl_wrap">
			<table class="tbl_row_type01"> 
				<caption>내용(제목, 작성자, 작성일 등으로 구성)</caption>
				<colgroup>
					<col style="width:20%;">
					<col style="width:30%;">
					<col style="width:20%;">
					<col style="width:30%;">
				</colgroup> 
				<tbody>
					<tr>
						<th scope="row"><strong class="th_tit">제목</strong></th>
						<td colspan="3">
							<input type="text" name="title" id="title" class="text w100p"  required="required"  maxlength="50" value="${util:unEscape(resultVO.title) }" />
							<form:errors path="title" cssClass="error" cssStyle="color:#ff0000" /> 
						</td>
					</tr>
					<tr>
						<th scope="row" ><strong class="th_tit">업체관리</strong></th>
					</tr>
					<tr>
						<th scope="row"><strong class="th_tit">내용</strong></th>
						<td colspan="3">
							<textarea name="cont" id="cont" class="txt_area w_100p" >${util:unEscape(resultVO.cont)}</textarea>
						</td> 
					</tr>
					<tr>
						<th scope="row"><strong>첨부파일</strong></th>
						<td colspan="3">
							<iframe name="atchFileIdFrame" id="atchFileIdFrame" src="/atch/fileUpload.do?atchFileId=${resultVO.atchFileId }&fileCnt=5&atchFileIdNm=atchFileId&updateType=upload" style="width: 100%;" height="50" frameborder="0" title="파일 업로드 폼"></iframe>
						</td>
					</tr> 
				</tbody>
			</table>
		</div>
		
		<div class="tbl_wrap">
			<table class="tbl_row_type01" id="manageTable">
				<caption>업체관리</caption>
				<colgroup>
					<col >
					<col >
					<col >
					<col >
					<col style="width:5%;">
					<col style="width:5%;">
				</colgroup> 
				<tbody>
					<tr>
						<th scope="row" colspan="6" style="text-align: center;"><strong>업체정보</strong></th>
					</tr>
					<tr>
						<th >이름</th>
						<th >부서</th>
						<th >직급</th>
						<th >전번</th>
						<th >입력추가</th>
						<th >입력란제거</th>
					</tr>
					<c:if test="${empty resultVO.employList}">
						<tr id="inputTr_0">
							<td>
								<input type="hidden" name="employList[0].subSeq" id="subSeq0" value="${resultVO.seq }">
								<input name="employList[0].empname" id="empname0" value="${result.empname}">
							</td>
							<td>
								<input name="employList[0].empdept" id="empdept0" value="${result.empdept}">
							</td>
							<td>
								<input name="employList[0].emprank" id="emprank0" value="${result.emprank}">
							</td>
							<td>
								<input type="number" name="employList[0].hp" id="hp0" value="${result.hp}">
							</td>
							<td >
								<button class="addInput" type="button">+</button>
							</td>
							<td >
								<button type="button" id="removeTr_0" class="removeInput">-</button>
							</td>
						</tr>
					</c:if>
					<c:forEach var="result" items="${resultVO.employList}" varStatus="status">
						<tr id="inputTr_${status.index}">
							<td>
								<input type="hidden" name="employList[${status.index}].subSeq" id="subSeq0" value="${resultVO.seq }">
								<input name="employList[${status.index}].empname" id="empname_${status.index}" value="${result.empname}" class="empname">
							</td>
							<td>
								<input name="employList[${status.index}].empdept" id="empdept_${status.index}" value="${result.empdept}" class="empdept">
							</td>
							<td>
								<input name="employList[${status.index}].emprank" id="emprank_${status.index}" value="${result.emprank}">
							</td>
							<td>
								<input type="number" name="employList[${status.index}].hp" id="hp_${status.index}" value="${result.hp}">
							</td>
							<td>
								<button class="addInput" type="button">+</button>
							</td>
							<td>
								<button type="button" id="removeTr_${status.index}" class="removeInput">-</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div class="btn_area">
			<a href="#" class="btn btn_mdl btn_${searchVO.procType eq 'update'? 'rewrite':'save'}" id="btn_submit">${searchVO.procType eq  'update' ? '수정' : '등록'}</a>
			<c:if test="${searchVO.procType ==  'update'}">
				<a href="#" class="btn btn_mdl btn_cancel" id="btn_returnView">취소</a>
			</c:if>
			<c:if test="${searchVO.procType ne  'update'}">
				<a href="#" class="btn btn_mdl btn_cancel" id="btn_list">취소</a>
			</c:if>
		</div>
	</form:form>
</div>