<%--
  Created by IntelliJ IDEA.
  User: 19235
  Date: 2020/10/19
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>欢迎登录后台管理系统</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    <script language="JavaScript" src="js/jquery.js"></script>
    <script src="js/cloud.js" type="text/javascript"></script>

<%--    解决frameset 导致窗口嵌套   --%>
    <script language="javascript">
        if(window.parent.length>0){
            //有父窗口 让父窗口 发送请求跳转到login.jsp
            window.parent.location="login.jsp"
        }
        $(function(){
            $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
            $(window).resize(function(){
                $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
            })
        });
        // function change(){
        //     //点击的时候 请求validateCode.do  ?+Math.random() 请求生成图片 url 一样会从缓冲中读取
        //     // $(selector).attr(attribute,value) attribute	规定属性的名称。 value	规定属性的值。
        //     $("#code").attr("src","validateCode.do?"+Math.random());
        // }
    </script>

</head>

<%--<body style="background-color:#1c77ac; background-image:url(images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">--%>



<div id="mainBody">
    <div id="cloud1" class="cloud"></div>
    <div id="cloud2" class="cloud"></div>
</div>


<div class="logintop">
    <ul>
        <li><a href="#">回首页</a></li>
        <li><a href="#">帮助</a></li>
        <li><a href="#">关于</a></li>
    </ul>
</div>

<div class="loginbody">
${msg}

    <div class="loginbox loginbox2">
      <form action="login.do" method="post">
          <ul>
              <li><input name="username" type="text" class="loginuser" value="admin" onclick="JavaScript:this.value=''"/></li>
              <li><input name="userpwd" type="text" class="loginpwd" value="密码" onclick="JavaScript:this.value=''"/></li>
              <li><input name="" type="submit" class="loginbtn" value="登录"  onclick=""/></li>
              <a href="usermanager/addUser.jsp" >注册</a>
          </ul>
      </form>
    </div>
</div>
</body>
</html>
