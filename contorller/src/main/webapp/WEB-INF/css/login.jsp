<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/10/29 0029
  Time: 上午 9:59
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    //http://localhost:8080/01_sxtoa_war_exploded/
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
    <script>

    </script>
</head>
<body>
<%

    String uname = "";
    String pwd = "";
    String checked = "";
    Cookie[] cookies = request.getCookies();
    if(cookies != null){
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("uname")){
                uname = cookie.getValue();
            }
            if(cookie.getName().equals("pwd")){
                pwd = cookie.getValue();
            }
            if(cookie.getName().equals("remeber")){
                checked = cookie.getValue();
            }
        }
    }
%>
<h3>欢迎访问S111班级银行转账系统</h3>
<hr>
<font color="red" size="20">${requestScope.msg}</font>
<form action="userLogin" method="post">
    用户名 : <input type="text" name="uname" value="<%=uname%>"><br>
    密码: <input type="password" name="pwd" value="<%=pwd%>"><br>
    <input type="checkbox" name="remeber" value="rp" checked="<%=checked%>">记住密码
    <input type="submit" value="登录">
</form>
</body>
</html>
