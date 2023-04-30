<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
    String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="description" content="Sona Template">
    <meta name="keywords" content="Sona, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sona |模板</title>

    <!-- Google Font -->
<%--    <link href="https://fonts.googleapis.com/css?family=Lora:400,700&display=swap" rel="stylesheet">--%>
<%--    <link href="https://fonts.googleapis.com/css?family=Cabin:400,500,600,700&display=swap" rel="stylesheet">--%>

    <!-- Css Styles -->
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="css/flaticon.css" type="text/css">
    <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
    <link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <!-- 引入 layui.css -->
    <%--    <link rel="stylesheet" href="//unpkg.com/layui@2.6.8/dist/css/layui.css">--%>
    <link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.6.8/css/layui.css" type="text/css">
    <!-- 引入 layui.js -->
    <%--    <script src="//unpkg.com/layui@2.6.8/dist/layui.js"></script>--%>
    <script src="https://www.layuicdn.com/layui-v2.6.8/layui.js"></script>
</head>

<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>


<!-- Header Section Begin -->
<jsp:include page="header.jsp" flush="true" />
<!-- Header End -->

<!-- Contact Section Begin -->
<section class="contact-section spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-4">
                <div class="contact-text">
                    <h2>联系方式</h2>
                    <p>酒店坐落于上海浦东新区南洋泾路，距新国际博览中心约2.5公里，地铁9号线（芳甸路站3号口）下地铁，约50米即到；靠近地铁2号线科技馆站。
                        自酒店出发驱车前往世博馆、陆家嘴商圈约15分钟车程，临近的大拇指广场，联洋广场，金桥国际广场，嘉里中心等休闲购物广场是您忙碌之余的好去处；世纪公园，东方艺术中心，上海科技馆步行即达，让您享受别样的海派艺术氛围。</p>
                    <table>
                        <tbody>
                        <tr>
                            <td class="c-o">地址:</td>
                            <td>上海浦东新区南洋泾路</td>
                        </tr>
                        <tr>
                            <td class="c-o">电话:</td>
                            <td>010-111000000</td>
                        </tr>
                        <tr>
                            <td class="c-o">Email:</td>
                            <td>jiudian1101@126.com</td>
                        </tr>
                        <tr>
                            <td class="c-o">Fax:</td>
                            <td>+(12) 345 67890</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>


            <div class="col-lg-7 offset-lg-1">
                <form class="layui-form contact-form">
                    <div class="row">
                        <div class="col-lg-6">
                            <input type="text" name="username" placeholder="Name*">
                        </div>
                        <div class="col-lg-6">
                            <input type="text" name="email" placeholder="Email*">
                        </div>
                        <div class="col-lg-12">
                            <textarea name="remark" placeholder="Your Review"></textarea>
                            <button  lay-filter="add" lay-submit="">现在提交</button>
                        </div>
                    </div>
                </form>


            </div>
        </div>

    </div>
</section>
<!-- Contact Section End -->

<!-- Footer Section Begin -->
<jsp:include page="foot.jsp" flush="true" />
<!-- Footer Section End -->
<script>
    layui.use(['form','layer'], function(){
        $ = layui.jquery;
        var form = layui.form
            ,layer = layui.layer;
        //监听提交
        form.on('submit(add)', function(data){
            var data=data.field;
            $.ajax({
                url:"reserveWebServlet?flag=saveComment",
                type:"post",
                data:data,
                success:function(result){
                    if(result.code==0){
                        layer.msg("留言成功...")
                    }else{
                        //如果失败提示信息，不进行任何跳转
                        layer.msg("不好意思，留言失败...")
                    }
                }
            })
            return false;
        });
    });
</script>


<!-- Js Plugins -->
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.magnific-popup.min.js"></script>
<script src="js/jquery.nice-select.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/jquery.slicknav.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/main.js"></script>
</body>

</html>