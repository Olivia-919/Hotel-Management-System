<%@page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%
    String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String basePath2=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>欢迎页面-X-admin2.1</title>
    <meta name="renderer" content="webkit">
    <%--    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">--%>
    <%--    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />--%>
    <link rel="stylesheet" href="<%=basePath%>admin/css/font.css">
    <link rel="stylesheet" href="<%=basePath%>admin/css/xadmin.css">
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>admin/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath%>admin/js/xadmin.js"></script>
    <script type="text/javascript" src="<%=basePath%>admin/js/cookie.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="x-body">
    <form class="layui-form">
        <input type="hidden" id="img" name="img" >
        <div class="layui-form-item">
            <label for="number" class="layui-form-label">
                <span class="x-red">*</span>房间编号
            </label>
            <div class="layui-input-inline">
                <input type="text" id="number" name="number" required=""
                       autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">
                <span class="x-red">*</span>名称建议唯一
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">房间类型</label>
            <div class="layui-input-inline">
                <select name="roomId" id="typeId" lay-verify="required" lay-search="">
                    <option value="">请选择</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label for="area" class="layui-form-label">
                <span class="x-red">*</span>面积
            </label>
            <div class="layui-input-inline">
                <input type="text" id="area" name="area" required=""
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <!--上传图片开始-->
        <div class="layui-form-item">
            <label  class="layui-form-label">
                <span class="x-red">*</span>房间图片
            </label>
            <div class="layui-input-inline">
                <div class="layui-upload-drag" id="test10">
                    <i class="layui-icon"></i>
                    <p>点击上传，或将文件拖拽到此处</p>
                    <div class="layui-hide" id="uploadDemoView">
                        <hr>
                        <img src="" alt="上传成功后渲染" style="max-width: 196px">
                    </div>
                </div>
            </div>
        </div>
        <!--上传图片结束-->
        <div class="layui-form-item">
            <label for="price" class="layui-form-label">
                <span class="x-red">*</span>价格
            </label>
            <div class="layui-input-inline">
                <input type="text" id="price" name="price" required=""
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label for="counts" class="layui-form-label">
                <span class="x-red">*</span>人数
            </label>
            <div class="layui-input-inline">
                <input type="text" id="counts" name="counts" required=""
                       autocomplete="off" class="layui-input">
            </div>
        </div>


        <div class="layui-form-item">
            <label class="layui-form-label">状态</label>
            <div class="layui-input-block">
                <input type="checkbox" checked="" name="status" lay-skin="switch" lay-filter="switchTest" lay-text="ON|OFF">
            </div>
        </div>

        <div class="layui-form-item">
            <label for="L_pass" class="layui-form-label">
                <span class="x-red">*</span>备注信息
            </label>
            <div class="layui-input-inline">
                <input type="text" id="L_pass" name="remark" required=""
                       autocomplete="off" class="layui-input">
            </div>

        </div>

        <div class="layui-form-item">
            <label  class="layui-form-label">
            </label>
            <button  class="layui-btn" lay-filter="add" lay-submit="">
                添加房间
            </button>
        </div>
    </form>
</div>
<script>
    layui.use(['upload','form','layer'], function(){
        $ = layui.jquery;
        var upload = layui.upload;
        var form = layui.form
            ,layer = layui.layer;

        //通过ajax请求向后端获取类型名称数据并渲染到指定select位置
        $.get("<%=basePath%>typeInfoServlet?flag=queryTypeList",{},function (data) {
            var list=data;
            var select=document.getElementById("typeId");
            if(list!=null ){
                for(var c in list){
                    var option=document.createElement("option");
                    option.setAttribute("value",list[c].id);
                    option.innerText=list[c].typeName;
                    select.appendChild(option);
                }
            }
            form.render("select");
        },"json")


        //拖拽上传
        upload.render({
            elem: '#test10'
            ,url: '<%=basePath%>roomServlet?flag=upload' //此处用的是第三方的 http 请求演示，实际使用时改成您自己的上传接口即可。
            ,done: function(res){
                debugger
                layer.msg('上传成功');
                layui.$('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', "<%=basePath2%>/images/"+res.msg);
                //给隐藏域赋值
                $("#img").val("images/"+res.msg);
                console.log(res)
            }
        });


        //监听提交
        form.on('submit(add)', function(data){
            /*
               1、获取输入框的值信息
               2、通过ajax请求向后端发送请求
               3、根据响应反馈信息提示，并刷新主页
             */
            var data=data.field;

            if(data.status=='ON'){
                data.status=1;
            }else{
                data.status=0;
            }
           debugger
            $.ajax({
                url:"<%=basePath%>roomServlet?flag=insert",
                type:"post",
                data:data,
                success:function(result){
                    if(result.code==0){
                        layer.alert("增加成功", {icon: 6},function () {
                            //关闭当前frame
                            x_admin_close();
                            // 可以对父窗口进行刷新
                            x_admin_father_reload();
                        });
                    }else{
                        layer.msg("不好意思，添加失败了...")
                    }
                }
            })
            // console.log(data);
            // //发异步，把数据提交给php

            return false;
        });


    });
</script>

</body>

</html>