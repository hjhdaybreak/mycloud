<%--
  Created by IntelliJ IDEA.
  User: 19235
  Date: 2020/10/21
  Time: 21:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>无标题文档</title>
    <%--    不用加../ 因为是服务端直接请求这下   req.getRequestDispatcher("usermanager/viewUser.jsp").forward(req,resp);;--%>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
<%--    <script>--%>
<%--        &lt;%&ndash;function butgo() {&ndash;%&gt;--%>
<%--        &lt;%&ndash;    var xhr = new XMLHttpRequest();&ndash;%&gt;--%>
<%--        &lt;%&ndash;    xhr.open("post", "downloadOrView.do?operate=download&typeFlag=document&fileName=${filename}");&ndash;%&gt;--%>
<%--        &lt;%&ndash;    xhr.send();&ndash;%&gt;--%>
<%--        &lt;%&ndash;}&ndash;%&gt;--%>

<%--        function butstop() {--%>
<%--            var xhr = new XMLHttpRequest();--%>
<%--            xhr.open("get", "testbreak.do?flag=stop");--%>
<%--            xhr.send();--%>
<%--        }--%>

<%--    </script>--%>


</head>


<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">云盘管理</a></li>
        <li><a href="#">云盘资源</a></li>
        <li><a href="#">文档</a></li>
    </ul>
</div>

<div class="rightinfo">
    <div class="formtitle1"><span>文档列表</span></div>
    <table class="tablelist">
        <thead>
        <tr>
            <th>序号</th>
            <th>文档名</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${documentList}" var="filename" varStatus="status">
            <tr>
                <td>${status.count}</td>
                <td>${filename}</td>
                <td><a href="downloadOrView.do?operate=download&typeFlag=document&fileName=${filename}"
                       class="tablelink">下载</a>&nbsp;&nbsp;<a
                        href="downloadOrView.do?operate=preView&typeFlag=document&fileName=${filename}"
                        class="tablelinkclick">预览</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%--    <td><a href="testbreak.do?flag=go" class="tablelink">继续</a></td>--%>
    <%--    <td><a href="testbreak.do?flag=stop" class="tablelink">暂停</a></td>--%>
<%--    <input type="button" value="继续" onclick="butgo()"/>--%>
    <a href="testbreak.do?flag=stop">下载全部暂停</a>

</div>
</body>
</html>
