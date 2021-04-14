<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/jsp/cmmn/incTagLib.jsp"/>
<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<%
    pageContext.setAttribute("crcn", "\r\n"); 
    pageContext.setAttribute("br", "<br>");
%> 
<script type="text/javascript" src="/publish/ma/js/board.js"></script>
<script type="text/javascript" src="/resource/js/lib.validate.js"></script>
<script type="text/javaScript">
$(document).ready(function(){
	/* fncDate('searchStartDate','searchEndDate'); */	
	fncPageBoard('addList','addList.do',1);
	$("#testTable").colspan(0)
	
});

</script>
<div class="content_box">
	<form:form commandName="searchVO" name="defaultFrm" id="defaultFrm" method="post">
		<form:hidden path="seq" id="seq"/>
		<form:hidden path="pageIndex" id="pageIndex"/> 
		<form:hidden path="atchFileId" id="atchFileId"/>
		<jsp:directive.include file="/WEB-INF/jsp/cmmn/inc/incSearchForm.jsp"/>
		<!-- tbl -->
		<div class="tbl_wrap">
			<table class="tbl_row_type01" id="testTable">
				<caption>내용(제목, 작성자, 작성일 등으로 구성)</caption>
				<colgroup>
					<col>
					<col>
					<col>
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row"><strong>dummyCol1</strong></th>
	                    <th scope="row"><strong>dummyCol2</strong></th>
	                    <th scope="row"><strong>dummyCol3</strong></th>
	                    <th scope="row"><strong>dummyCol4</strong></th>
					</tr> 
					<tr>
						<td>1</td>
						<td>1</td>
						<td>1</td>
						<td>1</td>
					</tr>
					<tr>
						<td>1</td>
						<td>2</td>
						<td>2</td>
						<td>2</td>
					</tr>
					<tr>
						<td>1</td>
						<td>2</td>
						<td>2</td>
						<td>2</td>
					</tr>
				</tbody>
			</table>
		</div>
		<input id="inputBox">
	</form:form>
</div>
