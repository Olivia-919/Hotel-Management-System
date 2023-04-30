<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="description" content="Sona Template">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sona |模板</title>

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
</head>

<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>

<!-- Header Section Begin -->
<jsp:include page="header.jsp" flush="true" />
<!-- Header End -->

<!-- Breadcrumb Section Begin -->
<div class="breadcrumb-section">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb-text">
                    <h2>我们的房间</h2>
                    <div class="bt-option">
                        <a href="./home.html">首页</a>
                        <span>房间</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Breadcrumb Section End -->

<!-- Rooms Section Begin -->
<section class="rooms-section spad">
    <div class="container">
        <div class="row">

      <c:forEach items="${list}" var="room">
            <div class="col-lg-4 col-md-6">
                <div class="room-item">
                    <img src="<%=basePath%>${room.img}" width="430px" height="270px" alt="">
                    <div class="ri-text">
                        <h4>${room.type_name}</h4>
                        <h3>${room.price}$<span>/每晚</span></h3>
                        <table>
                            <tbody>
                            <tr>
                                <td class="r-o">大小:</td>
                                <td>${room.area} 英尺</td>
                            </tr>
                            <tr>
                                <td class="r-o">容量:</td>
                                <td>最大${room.counts}人</td>
                            </tr>
                            <tr>
                                <td class="r-o">床:</td>
                                <td>${room.bed}</td>
                            </tr>
                            <tr>
                                <td class="r-o">备注:</td>
                                <td>
                                        ${fn:substring(room.remark, 0, 10)}...
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <a href="reserveWebServlet?flag=queryById&id=${room.id}" class="primary-btn">更多细节</a>
                    </div>
                </div>
            </div>
      </c:forEach>



        </div>
    </div>
</section>
<!-- Rooms Section End -->

<!-- Footer Section Begin -->
<jsp:include page="foot.jsp" flush="true" />
<!-- Footer Section End -->

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
