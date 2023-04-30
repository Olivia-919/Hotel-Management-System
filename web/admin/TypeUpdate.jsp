<%--
  Created by IntelliJ IDEA.
  User: zhouk
  Date: 2022/1/3
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="x-admin-sm">

<head>
    <meta charset="UTF-8">
    <title>欢迎页面-X-admin2.1</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="stylesheet" href="./css/font.css">
    <link rel="stylesheet" href="./css/xadmin.css">
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="./lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="./js/xadmin.js"></script>
    <script type="text/javascript" src="./js/cookie.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="x-body">
    <form class="layui-form">
        <input type="hidden" id="id" name="id">
        <div class="layui-form-item">
            <label  class="layui-form-label">
                <span class="x-red">*</span>类型名称
            </label>
            <div class="layui-input-inline">
                <input type="text" id="typeName"  name="typeName" required=""
                       autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">
                <span class="x-red">*</span>名称建议唯一
            </div>
        </div>
        <div class="layui-form-item">
            <label  class="layui-form-label">
                <span class="x-red">*</span>价格
            </label>
            <div class="layui-input-inline">
                <input type="text" id="price" name="price" required=""
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">
                <span class="x-red">*</span>备注信息
            </label>
            <div class="layui-input-inline">
                <input type="text" id="remark" name="remark" required=""
                       autocomplete="off" class="layui-input">
            </div>

        </div>

        <div class="layui-form-item">
            <label  class="layui-form-label">
            </label>
            <button  class="layui-btn" lay-filter="update" lay-submit="">
                修改房间类型
            </button>
        </div>
    </form>
</div>
<script>
    layui.use(['form','layer'], function(){
        $ = layui.jquery;
        var form = layui.form
            ,layer = layui.layer;

       <%
          String id=request.getParameter("id");
       %>
        //通过ajax请求像后端获取对象信息
        $.ajax({
            url:"../typeInfoServlet?flag=queryByID",
            type:"post",
            data:{id:<%=id%>},
            success:function(result){
                 $("#id").val(result.id);
                $("#price").val(result.price);
                $("#typeName").val(result.typeName);
                $("#remark").val(result.remark);
            }
        })



        //监听提交
        form.on('submit(update)', function(data){
            /*
               1、获取输入框的值信息
               2、通过ajax请求向后端发送请求
               3、根据响应反馈信息提示，并刷新主页
             */
            var data=data.field;
            $.ajax({
                url:"../typeInfoServlet?flag=updateSubmit",
                type:"post",
                data:data,
                success:function(result){
                    if(result.code==0){
                        layer.alert("修改成功", {icon: 6},function () {
                            // //关闭当前frame
                            // x_admin_close();
                            // // 可以对父窗口进行刷新
                            // x_admin_father_reload();
                            parent.window.location.reload();
                        });
                    }else{
                        layer.msg("不好意思，修改失败了...")
                    }
                }
            })

            return false;
        });


    });
</script>

</body>

</html>