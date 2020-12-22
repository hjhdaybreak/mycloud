<%--
  Created by IntelliJ IDEA.
  User: 19235
  Date: 2020/11/7
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div >
    <form action="../viewFile.do" method="post">
        <input type="hidden" name="flag" value="document">
        <ul class="prosearch">
            <a>
                <input  type="submit" class="sure" value="文档" />
            </a>
        </ul>
    </form>
</div>

<div>
    <form action="../viewFile.do" method="post">
        <input type="hidden" name="flag" value="picture">
        <ul class="prosearch">
            <a>
                <input  type="submit" class="sure" value="图片" />
            </a>
        </ul>
    </form>

</div>
</body>
</html>
