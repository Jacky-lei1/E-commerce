<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.js"></script>

    <script type="text/javascript">
        function sendAjax(){
           /* $.post("http://localhost:7070/order/loadOrderList02","uid=1234",function(data){

                alert(data);
            });*/
            //jQuery对JsonP的支持，相当于这个函数产生的是一个虚拟的函数，jQuery会自动通过时间戳产生一个虚拟的函数名称
            $.getJSON("http://localhost:7070/order/loadOrderList03?callback=?","uid=1234",function(data){
                //将json对象转化为字符串
                var str = JSON.stringify(data);
                alert(str);
            });

        }
        //因为控制层中加入了@ResponseBody标签，所以响应回来的数据是一个json对象
        function doCallback(data) {
            //将json对象转化为字符串
            var str = JSON.stringify(data);
            alert(str);
        }

    </script>
</head>
<body>
<a href="javascript:sendAjax()">sendAjax</a>

<%--执行这个脚本，相当于服务器响应回一个字符串：String result = callback+"("+ JSON.toJSONString(list) +")"
    相当于在当前的<body>标签中添加一个方法调用的代码：doCallback(JSON.toJSONString(list))
--%>
<script src="http://localhost:7070/order/loadOrderList03?uid=999999&callback=doCallback"></script>
</body>
</html>