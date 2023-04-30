<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
    String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<html >

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
<!-- Room Details Section Begin -->
<section class="room-details-section spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-8">
                <div class="room-details-item">
                    <img src="<%=basePath%>${info.img}" width="700px" height="450px" alt="">
                    <div class="rd-text">
                        <div class="rd-title">
                            <h3>${info.type_name}</h3>
                           <%-- <div class="rdt-right">
                                <a href="#">现在预订</a>
                            </div>--%>
                        </div>
                        <h2>${info.price}$<span>/每晚</span></h2>
                        <table>
                            <tbody>
                            <tr>
                                <td class="r-o">大小:</td>
                                <td>${info.area} 英尺</td>
                            </tr>
                            <tr>
                                <td class="r-o">容量:</td>
                                <td>最大${info.counts}人</td>
                            </tr>
                            <tr>
                                <td class="r-o">床:</td>
                                <td>${info.bed}</td>
                            </tr>
                            <tr>
                                <td class="r-o">服务:</td>
                                <td>${info.remark}</td>
                            </tr>
                            </tbody>
                        </table>
                        <p class="f-para">${info.remark}</p>

                    </div>
                </div>


                <div class="rd-reviews" id="view">


                </div>




                <script id="demo" type="text/html">
                    <h4>点评</h4>
                    <ul>
                        {{#  layui.each(d.list, function(index, item){ }}
                        <div class="review-item">
                            <div class="ri-pic">
                                <img src="img/tou.png" alt="">
                            </div>
                            <div class="ri-text">
                                <span>{{ item.username }}</span>
                                <div class="rating">

                                    {{layui.util.toDateString(new Date(item.commentTime).getTime(), "yyyy-MM-dd HH:mm:ss") }}

                                </div>
                                <h5>   </h5>
                                <p>{{ item.remark }}</p>
                            </div>
                        </div>
                        {{#  }); }}
                        {{#  if(d.list.length === 0){ }}
                        无数据
                        {{#  } }}
                    </ul>
                </script>



                <div class="review-add">
                    <h4>添加评论</h4>
                    <form class="layui-form ra-form" >
                        <input name="roomId" value="${info.id}" type="hidden">
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


            <div class="col-lg-4">
                <div class="room-booking">
                    <h3>在线订房</h3>
                    <form class="layui-form">
                        <input name="room_id" type="hidden" value="${info.id}">
                        <div class="check-date">
                            <label >入住时间</label>
                            <input type="text"   name="startTime" class="layui-input" id="test"
                                   placeholder="yyyy-MM-dd">
                        </div>
                        <div class="check-date">
                            <label>离开时间:</label>
                            <input type="text"  name="endTime" class="layui-input" id="test2"
                                   placeholder="yyyy-MM-dd">
                        </div>
                        <div class="select-option">
                            <label for="id_card">身份号码:</label>
                            <input type="text" id="id_card" name="idCard" required=""
                                   autocomplete="off" class="layui-input">
                        </div>
                        <div class="select-option">
                            <label for="tel">联系方式:</label>
                            <input type="text" id="tel" name="tel" required=""
                                   autocomplete="off" class="layui-input">
                        </div>
                        <button class="layui-btn" lay-filter="query" lay-submit="">提交预定</button>
                    </form>
                </div>
            </div>



        </div>
    </div>
</section>
<!-- Room Details Section End -->

<!-- Footer Section Begin -->
<jsp:include page="foot.jsp" flush="true" />
<!-- Footer Section End -->



<script>
    layui.use(['laytpl','form','layer','laydate'], function(){
        $ = layui.jquery;
        var form = layui.form
            ,layer = layui.layer;
        var laydate=layui.laydate;
        var laytpl = layui.laytpl;
        //时间选择器
        laydate.render({
            elem: '#test'
            ,type: 'date'
        });
        //时间选择器
        laydate.render({
            elem: '#test2'
            ,type: 'date'
        });
        //监听提交
        form.on('submit(query)', function(data){
            var data=data.field;
            debugger
            $.ajax({
                url:"reserveWebServlet?flag=saveInfo",
                type:"post",
                data:data,
                success:function(result){
                    if(result.code==0){
                        location.href="index.jsp";
                    }else{
                        //如果失败提示信息，不进行任何跳转
                        layer.msg("不好意思，预定失败...")
                    }
                }
            })
            return false;
        });

        //第三步：渲染模版

        $.get("reserveWebServlet?flag=commentList&roomId=${info.id}",{},function (data) {
            var list=data;
            debugger
            var getTpl = demo.innerHTML
                ,view = document.getElementById('view');
            laytpl(getTpl).render(list, function(html){
                view.innerHTML = html;
            });
        })



        //监听提交
        form.on('submit(add)', function(data){
            var data=data.field;
            debugger

            $.ajax({
                url:"reserveWebServlet?flag=saveComment",
                type:"post",
                data:data,
                success:function(result){
                    //重新刷新评论区的div布局
                    var data=result;
                    var getTpl = demo.innerHTML
                        ,view = document.getElementById('view');
                    laytpl(getTpl).render(data, function(html){
                        view.innerHTML = html;
                    });
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
