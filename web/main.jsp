<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Mycloud</title>
    <script language="javascript">
        if(window.parent.length>0){
            //有父窗口 让父窗口 发送请求跳转到login.jsp
            window.parent.location="main.jsp"
        }
    </script>
</head>
<frameset rows="*,31" cols="*" frameborder="no" border="0" framespacing="0">
    <frameset cols="187,*" frameborder="no" border="0" framespacing="0">
        <frame src="left.jsp" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" title="leftFrame"/>
        <frame src="index.jsp" name="rightFrame" id="rightFrame" title="rightFrame"/>
    </frameset>
    <frame src="footer.jsp" name="bottomFrame" scrolling="No" noresize="noresize" id="bottomFrame" title="bottomFrame"/>
</frameset>
<noframes>
    <body>
    </body>
</noframes>
</html>
</html>
