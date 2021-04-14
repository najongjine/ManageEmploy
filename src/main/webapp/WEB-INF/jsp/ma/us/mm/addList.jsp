<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/jsp/cmmn/incTagLib.jsp"/>
<script>
<%--$("#fixTable").tableHeadFixer();--%>
</script>
<%-- tbl --%>
<div class="tbl_top">
	<div class="tbl_left"><i class="i_all"></i> <span> 전체 : <strong>${paginationInfo.totalRecordCount}</strong> 건(${searchVO.pageIndex}/${paginationInfo.totalPageCount} Page) </span></div>
	<div class="tbl_right"></div>
</div>
<div class="tbl_wrap">
	<table class="tbl_col_type01" id="fixTable">
		<caption>목록(번호,제목,첨부,작성자,작성일,조회 로 구성)</caption>
		<colgroup>
			<col style="width: 4%;">
			<col style="width: 12%;">
			<col style="width: 8%;">
			<col style="width: 12%;">	
			<col style="width: 12%;">								
            <col style="width: 12%;">
            <col style="width: 12%;">
            <col style="width: 10%;">
            <col style="width: 5%;">
		</colgroup>
		<thead>
			<tr>
				<th scope="col">번호</th>
				<th scope="col">부서</th>
				<th scope="col">성명</th>
				<th scope="col">ID</th>
				<th scope="col">이메일</th>
                <th scope="col">내선번호</th>
				<th scope="col">최종접속일</th>
				<th scope="col">권한</th>
				<th scope="col">사용여부</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${fn:length(resultList) > 0}">
					<c:forEach var="result" items="${resultList}" varStatus="status">
						<tr class="cursor">
							<td onclick="fncPageBoard('update','updateForm.do','${result.seq}','seq')">${paginationInfo.totalRecordCount+1 - ((searchVO.pageIndex-1) * searchVO.pageSize + status.count)}<!-- <span class="tag_notice">공지</span> --></td>
							<td onclick="fncPageBoard('update','updateForm.do','${result.seq}','seq')">${result.dprt }</td>
							<td onclick="fncPageBoard('update','updateForm.do','${result.seq}','seq')">${result.name }</td>
							<td onclick="fncPageBoard('update','updateForm.do','${result.seq}','seq')">${result.id }</td>
							<td onclick="fncPageBoard('update','updateForm.do','${result.seq}','seq')">${result.email }</td>
							<td onclick="fncPageBoard('update','updateForm.do','${result.seq}','seq')">${util:strToHp(result.extnsNmbr) }</td>
							<td onclick="fncPageBoard('update','updateForm.do','${result.seq}','seq')">${result.lastDate }</td>
							<td onclick="fncPageBoard('update','updateForm.do','${result.seq}','seq')">${result.authCode }</td>
							<td onclick="fncPageBoard('update','updateForm.do','${result.seq}','seq')">${result.useYn eq "Y" ? "사용" : "미사용"}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="7" class="no_data">데이터가 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>
<%-- //tbl --%>
<%-- paging --%>
<div class="paging_wrap">	
	 <div class="paging">
        <div class="paging"><ui:pagination paginationInfo="${paginationInfo}" type="manage" jsFunction="fncPageBoard" /></div>
    </div>
    <div class="btn_right">
    	<c:if test="${loginMgrAuthCode eq '1'}">
			<a href="#" class="btn btn_mdl btn_save" onclick="fncPageBoard('write','insertForm.do');">등록</a>
		</c:if>
	</div>
</div>
<%-- //paging --%>

