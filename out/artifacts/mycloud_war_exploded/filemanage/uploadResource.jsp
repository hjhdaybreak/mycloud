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

</body>
</html>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<!--上传文件都是用post  post 可以支持字符流application/x-www-form-urlencoded和字节流multipart/form-data -->
<form action="../uploadfile.do" method="post" enctype="multipart/form-data">
    选择上传文件: <input type="file" name="file">
    <input type="submit" value="OK">
</form>
</body>
</html>