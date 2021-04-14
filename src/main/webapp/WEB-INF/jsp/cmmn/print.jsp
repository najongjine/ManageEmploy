<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/jsp/cmmn/incTagLib.jsp"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<!-- <meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Pragma" content="no-store"/>-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>한국전파진흥협회</title>
<link rel="stylesheet" type="text/css" href="/publish/ft/css/reset.css"> 
<link rel="stylesheet" type="text/css" href="/publish/ft/css/print.css"> 
<script type="text/javascript" src="/publish/ft/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
var myAOS;
$(document).ready(function(){
	var openContHtml = opener.$("#content").html();
	var newText = openContHtml.replace(/<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi,"");
	console.log(newText);
	$("#content").html(newText);
	window.print();
});

</script>
</head>

<body>
	<div id="content" class="page">
	</div>
</body>
</html>
