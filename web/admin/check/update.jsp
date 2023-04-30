<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html  class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>管理系统</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <!--    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />-->
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="stylesheet" href="<%=basePath%>admin/css/font.css">
    <link rel="stylesheet" href="<%=basePath%>admin/css/xadmin.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript"src="https://cdn.bootcss.com/blueimp-md5/2.10.0/js/md5.min.js"></script>
    <script src="<%=basePath%>admin/lib/layui/layui.js" charset="utf-8"></script>

    <script type="text/javascript" src="<%=basePath%>admin/js/xadmin.js"></script>
    <script type="text/javascript" src="<%=basePath%>admin/js/cookie.js"></script>
    <script>
        // 是否开启刷新记忆tab功能
        // var is_remember = false;
    </script>
</head>

<body>
<div class="x-body">
    <form class="layui-form" lay-filter="update" >
        <input type="hidden" name="id" value="${info.id}">
        <input type="hidden" id="roomId"  value="${info.room_id}">
        <div class="layui-form-item">
            <label  class="layui-form-label">
                <span class="x-red">*</span>身份证号
            </label>
            <div class="layui-input-block">
                <input type="text" id="id_card" name="idCard" value="${info.id_card}"
                       autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label  class="layui-form-label">
                <span class="x-red">*</span> 联系电话
            </label>
            <div class="layui-input-block">
                <input type="text" id="tel" name="tel" required="" value="${info.tel}"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label  class="layui-form-label">
                <span class="x-red">*</span>房间编号
            </label>
            <div class="layui-input-block">
                <select name="roomId" id="typeId" lay-verify="required">
                    <!--
                       这里查询状态因都是未入住的，所以当前修改的找不到，后端可以加入方法实现

                    -->
                    <option value="">请选择</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">入住时间</label>
            <div class="layui-input-block">
                <input type="text" name="startTime" value="<fmt:formatDate value="${info.start_time}" pattern="yyyy-MM-dd HH:mm:ss" />" class="layui-input" id="test" placeholder="yyyy-MM-dd HH:mm:ss">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">预计离开时间</label>
            <div class="layui-input-block">
                <input type="text"  name="endTime" class="layui-input" id="test2"
                       placeholder="yyyy-MM-dd HH:mm:ss"
                       value="<fmt:formatDate value="${info.end_time}" pattern="yyyy-MM-dd HH:mm:ss"/> ">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">是否入住</label>
            <div class="layui-input-block">
                <input type="checkbox" <c:if test="${info.state==1}">checked </c:if> name="state" lay-skin="switch" lay-filter="switchTest" lay-text="入住|预定">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">是否付费</label>
            <div class="layui-input-block">
                <input type="checkbox" checked="" <c:if test="${info.is_pay==1}">checked </c:if> name="isPay" lay-skin="switch" lay-filter="switchTest" lay-text="付费|未付费">
            </div>
        </div>
        <div class="layui-form-item">
            <label  class="layui-form-label">
            </label>
            <button  class="layui-btn" lay-filter="update" lay-submit="">
                修改预定信息
            </button>
        </div>
    </form>
</div>
<script>
    layui.use(['form','layer', 'layedit','laydate'], function(){
        $ = layui.jquery;
        var form = layui.form
            ,layer = layui.layer;
        laydate=layui.laydate;
        var form=layui.form,
            layedit=layui.layedit;
        var index1 = layedit.build('LAY_demo1', {
            tool: ['strong', 'italic', 'underline', 'del', '|', 'left', 'center', 'right'],
            height: 150
        });

        //时间选择器
        laydate.render({
            elem: '#test'
            ,type: 'datetime'
        });
        //时间选择器
        laydate.render({
            elem: '#test2'
            ,type: 'datetime'
        });


        //动态获取图书类型的数据
        var values=document.getElementById("roomId").value;
        debugger
        $.get("<%=basePath%>roomServlet?flag=queryRoomListByStatus",{},function (data) {
            var list=data;
            var select=document.getElementById("typeId");
            if(list!=null|| list.size()>0){
                for(var c in list){
                    var option=document.createElement("option");
                    option.setAttribute("value",list[c].id);
                    option.innerText=list[c].number;
                    select.appendChild(option);
                    if(list[c].id==values){
                        option.setAttribute("selected","selected");
                        layui.form.render('select');
                    }
                }
            }
            form.render('select');
        },"json")




        //监听提交
        form.on('submit(update)', function(data){
            console.log(data);
            //发异步，把数据提交给php
            var data=data.field;
            if(data.state=='ON'){
                data.state=1;
            }else{
                data.state=0;
            }
            if(data.isPay=='ON'){
                data.isPay=1;
            }else{
                data.isPay=0;
            }

            $.ajax({
                url:"<%=basePath%>reserveServlet?flag=update",
                type:"post",
                data:data,
                success:function (result) {
                    if(result.code==0){
                        layer.alert("修改成功", {icon: 6},function () {
                            //关闭当前frame
                            x_admin_close();
                            // 可以对父窗口进行刷新
                            x_admin_father_reload();
                        });
                    }else{
                        layer.msg("添加失败")
                    }
                }
            });
            return false;
        });
    });
</script>

</body>

</html>