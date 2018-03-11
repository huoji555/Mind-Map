<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//String courseName = (String)session.getAttribute("courseName");
//Integer coursebh = (Integer)session.getAttribute("coursebh");
//String chapterName = (String)session.getAttribute("chapterName");
//Integer chapterbh = (Integer)session.getAttribute("chpterbh");

String zlid =request.getParameter("zlid");
String filename = request.getParameter("filename");
System.out.println("f:"+filename);
filename=new String(filename.getBytes("ISO-8859-1"),"utf-8");

String path1=request.getParameter("path");
//String path1 ="upload/previewFile/videos/wzh/2016051708492527525.flv";
//String path1 ="upload/previewFile/texts/wzh/2016051708492677530.swf";
System.out.println(zlid+" "+filename+" "+path1);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>电子笔记</title>
    <link href="css/wjh_main.css" rel="stylesheet" />
	<link href="css/maintop.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" type="text/css" href="js/ui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/ui/themes/icon.css">
	<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="js/ui/jquery.easyui.min.js"></script>
	<script type="text/javascript">
		function closeNs() {
			 if(navigator.appName.indexOf("Microsoft") != -1){
                 document.getElementById('video').closeNs();
           	 } else {
                 document.embeds['video'].closeNs();
           	 }
		}
	</script>
  </head>
  
  <body class="easyui-layout">
  	<table id="test_tables"></table>
	  	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
					id="video" width="100%" height="99%"
					codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
				<param name="src" value="/studentswf/viewModule.swf" />
				<param name="quality" value="high" />
				<param name="bgcolor" value="#869ca7" />
				<param name="allowFullScreen" value="true" />
				<param name="FlashVars" value="zlmc=<%=filename %>&zlid=<%=zlid %>&path=<%=path1 %>
						"/>
				<param name="allowScriptAccess" value="sameDomain" />
				<embed src="/swf/viewModule.swf" quality="high" bgcolor="#869ca7"
						flashVars="zlmc=<%=filename %>&zlid=<%=zlid %>&path=<%=path1 %>
							"
						width="100%" height ="99%" name="FBVideo" align="middle"
						play="true"
						loop="false"
						quality="high"
						allowScriptAccess="sameDomain"
						allowFullScreen="true"
						type="application/x-shockwave-flash"
						pluginspage="http://www.adobe.com/go/getflashplayer">
				</embed>
		</object>
  </body>
</html>
