<%--
  Created by IntelliJ IDEA.
  User: 19235
  Date: 2020/11/13
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文本预览</title>
    <style>
        div {
            white-space: pre-line;
        }
    </style>
</head>
<body>
<p>
    ${documentContent}
</p>

<div>
    <form action="../viewFile.do" method="post">
        <input type="hidden" name="flag" value="document">
        <a>
            <input type="submit" class="sure" value="点击返回文档界面"/>
        </a>
    </form>
</div>
</body>
</html>
