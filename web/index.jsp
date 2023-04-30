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
    <title>梦想酒店</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css?family=Lora:400,700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Cabin:400,500,600,700&display=swap" rel="stylesheet">

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

<!-- Hero Section Begin -->
<section class="hero-section">
    <div class="container">
        <div class="row">
            <div class="col-lg-6">
                <div class="hero-text">
                    <h1>豪华酒店</h1>
                    <p>我们酒店是当地最好的酒店，希望大家都来住宿.....</p>
                    <a href="#" class="primary-btn">现在</a>
                </div>
            </div>
            <div class="col-xl-4 col-lg-5 offset-xl-2 offset-lg-1">
                <div class="booking-form">
                    <h3>查询预定酒店信息</h3>
                    <form class="layui-form">
                        <div class="check-date">
                            <label >身份证号码：</label>
                            <input type="text" name="idCard">

                        </div>
                        <div class="check-date">
                            <label >联系电话</label>
                            <input type="text" name="tel" >

                        </div>
                        <button class="layui-btn" lay-filter="query" lay-submit="">预定查询</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="hero-slider owl-carousel">
        <div class="hs-item set-bg" data-setbg="img/hero/hero-1.jpg"></div>
        <div class="hs-item set-bg" data-setbg="img/hero/hero-2.jpg"></div>
        <div class="hs-item set-bg" data-setbg="img/hero/hero-3.jpg"></div>
    </div>
</section>
<!-- Hero Section End -->

<!-- Services Section End -->
<section class="services-section spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="section-title">
                    <span>我们的服务</span>
                    <h2>发现我们的服务</h2>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-4 col-sm-6">
                <div class="service-item">
                    <i class="flaticon-036-parking"></i>
                    <h4>旅行计划</h4>
                    <p><strong>我的同僚们都不愿意坐在我的位子上，因为我的精英们都是临时的
                        劳工和多洛尔麦格纳。</strong></p>
                </div>
            </div>
            <div class="col-lg-4 col-sm-6">
                <div class="service-item">
                    <i class="flaticon-033-dinner"></i>
                    <h4>宴会服务处</h4>
                    <p>我的同僚们都不愿意坐在我的位子上，因为我的精英们都是临时的
                        劳工和多洛尔麦格纳。</p>
                </div>
            </div>
            <div class="col-lg-4 col-sm-6">
                <div class="service-item">
                    <i class="flaticon-026-bed"></i>
                    <h4>当临时保姆</h4>
                    <p>我的同僚们都不愿意坐在我的位子上，因为我的精英们都是临时的
                        劳工和多洛尔麦格纳。.</p>
                </div>
            </div>
            <div class="col-lg-4 col-sm-6">
                <div class="service-item">
                    <i class="flaticon-024-towel"></i>
                    <h4>洗衣店</h4>
                    <p>我的同僚们都不愿意坐在我的位子上，因为我的精英们都是临时的
                        劳工和多洛尔麦格纳。.</p>
                </div>
            </div>
            <div class="col-lg-4 col-sm-6">
                <div class="service-item">
                    <i class="flaticon-044-clock-1"></i>
                    <h4>雇用司机</h4>
                    <p>我的同僚们都不愿意坐在我的位子上，因为我的精英们都是临时的
                        劳工和多洛尔麦格纳。</p>
                </div>
            </div>
            <div class="col-lg-4 col-sm-6">
                <div class="service-item">
                    <i class="flaticon-012-cocktail"></i>
                    <h4>酒和酒吧</h4>
                    <p>我的同僚们都不愿意坐在我的位子上，因为我的精英们都是临时的
                        劳工和多洛尔麦格纳。</p>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Services Section End -->

<!-- Home Room Section Begin -->
<section class="hp-room-section">
    <div class="container-fluid">
        <div class="hp-room-items">
            <div class="row">

                <c:forEach items="${list}" var="room">
                <div class="col-lg-3 col-md-6">
                    <div class="hp-room-item set-bg" data-setbg="<%=basePath%>${room.img}">
                        <div class="hr-text">
                            <h3>${room.type_name}</h3>
                            <h2>${room.price}$<span>/每晚</span></h2>
                            <table>
                                <tbody>
                                <tr>
                                    <td class="r-o">大小:</td>
                                    <td>${room.area} 英尺</td>
                                </tr>
                                <tr>
                                    <td class="r-o">容量:</td>
                                    <td>最大人数 ${room.counts}</td>
                                </tr>
                                <tr>
                                    <td class="r-o">床:</td>
                                    <td>${room.bed}</td>
                                </tr>
                                </tbody>
                            </table>
                            <a href="#" class="primary-btn">在线预定</a>
                        </div>
                    </div>
                </div>
                </c:forEach>


            </div>
        </div>
    </div>
</section>
<!-- Home Room Section End -->

<!-- Testimonial Section Begin -->



<!-- Footer Section Begin -->
<jsp:include page="foot.jsp" flush="true" />
<!-- Footer Section End -->

<script>
    layui.use(['form','layer'], function(){
        $ = layui.jquery;
        var form = layui.form
            ,layer = layui.layer;
        //监听提交
        form.on('submit(query)', function(data){
            var data=data.field;
            $.ajax({
                url:"indexServlet?flag=query",
                type:"post",
                data:data,
                success:function(result){
                    console.log(result, result.code == 0);
                    if(result.code==0){
                        layer.msg("有预定记录，您预定的房间号为"+result.msg)
                    }else{
                        layer.msg("不好意思，没有预定信息...")
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