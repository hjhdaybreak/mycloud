<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 19235
  Date: 2020/11/28
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--    <title>Title</title>--%>
    <%--    <script language="javascript">--%>
    <%--        function butstop() {--%>
    <%--            var xhr = new XMLHttpRequest();--%>
    <%--            xhr.open("get", "testbreak.do?flag=stop");--%>
    <%--            xhr.send();--%>
    <%--        }--%>

    <%--    </script>--%>
</head>
<body>
<%--<p> ${fileList}下载暂停了</p>--%>

<%--<div class="rightinfo">--%>

<%--    <td>--%>
<%--        <a href="downloadOrView.do?operate=download&typeFlag=document&fileName=${fileList}"--%>
<%--           class="tablelink">下载</a>--%>
<%--    </td>--%>

<%--</div>--%>


<div class="rightinfo">
    <div class="formtitle1"><span>下载未完成文档列表</span></div>
    <table class="tablelist">
        <thead>
        <tr>
            <th>序号</th>
            <th>文档名</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${fileList}" var="filename" varStatus="status">
            <tr>
                <td>${status.count}</td>
                <td>${filename}</td>
                <td><a href="downloadOrView.do?operate=download&typeFlag=document&fileName=${filename}"
                       class="tablelink">继续下载</a>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%--    <td><a href="testbreak.do?flag=go" class="tablelink">继续</a></td>--%>
    <%--    <td><a href="testbreak.do?flag=stop" class="tablelink">暂停</a></td>--%>
    <%--    <input type="button" value="继续" onclick="butgo()"/>--%>
    <%--    <a href="testbreak.do?flag=stop">下载全部暂停</a>--%>

</div>


</body>
</html>
