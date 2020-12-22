<%--
  Created by IntelliJ IDEA.
  User: 19235
  Date: 2020/10/21
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>注册页面</title>
    <link href="../css/style.css" rel="stylesheet" type="text/css"/>
    <link href="../css/style.css" rel="stylesheet" type="text/css"/>
    <link href="../css/select.css" rel="stylesheet" type="text/css"/>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">用户管理</a></li>
        <li><a href="#">添加用户</a></li>
    </ul>
</div>

<div class="formbody">

    <div class="formtitle"><span>基本信息</span></div>
    <form action="../register.do" method="post">
        <input type="hidden" name="flag" value="addUser">

        <ul class="forminfo">
            <li>
                <label>用户名</label>
                <input name="username" type="text" class="dfinput"/></li>
            <li>
            <li>
                <label>用户密码</label>
                <input name="userpwd" type="text" class="dfinput"/><i></i>
            </li>
            <li>
                <label>是否购买VIP</label><cite>
                <input name="vip" type="radio" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="vip" type="radio" value="0"/>否
            </cite>
            </li>
            <li>
                <label>&nbsp</label>
                <input type="submit" class="btn" value="确认保存"/>
            </li>
        </ul>
    </form>
</div>
</body>
</html>
