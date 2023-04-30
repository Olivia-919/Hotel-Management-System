<%@page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<html>
<head>
    <meta charset="UTF-8">
  <title>欢迎页面-X-admin2.1</title>
    <meta name="renderer" content="webkit">
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">--%>
<%--    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />--%>
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
    <style>
        .laytable-cell-checkbox, .laytable-cell-numbers, .laytable-cell-radio, .laytable-cell-space {
            padding: 5px;
            text-align: center;
        }
    </style>
</head>

<body class="">
<div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">首页</a>
        <a href="">演示</a>
        <a>
          <cite>导航元素</cite></a>
      </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
</div>
<div class="x-body">
    <div class="demoTable">
        类型名称：
        <div class="layui-inline">
            <input class="layui-input" name="typeName" id="typeName" autocomplete="off">
        </div>
        <button class="layui-btn" data-type="reload">搜索</button>
    </div>
    <script type="text/html" id="toolbarDemo">
        <div class="layui-btn-container">
        <xblock>
            <button class="layui-btn layui-btn-danger" lay-event="getCheckData">批量删除</button>
            <button class="layui-btn" onclick="x_admin_show('添加房间类型','./TypeAdd.jsp',600,400)"><i class="layui-icon"></i>添加</button>
        </xblock>
        </div>
    </script>
    <table class="layui-table" lay-data="{url:'../typeInfoServlet',page:true,toolbar: '#toolbarDemo',id:'test'}" lay-filter="test">
        <thead>
        <tr>
            <th lay-data="{type:'checkbox'}">ID</th>
            <th lay-data="{field:'id', width:120, sort: true}">ID</th>
            <th lay-data="{field:'typeName', width:200, sort: true}">类型名称</th>
            <th lay-data="{field:'price',  width: 150}">价格</th>
            <th lay-data="{field:'createTime',templet: '#dateTpl'}">创建时间</th>
            <th lay-data="{field:'createAuthor', width: 150}">创建人</th>
            <th lay-data="{fixed: 'right', width:220, align:'center', toolbar: '#barDemo'}">操作</th>
        </tr>
        </thead>
    </table>

</div>

<script type="text/html" id="dateTpl">
   {{ layui.util.toDateString(d.createTime,"yyyy-MM-dd")}}
</script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/html" id="switchTpl">
    <!-- 这里的 checked 的状态只是演示 -->
    <input type="checkbox" name="sex" value="{{d.id}}" lay-skin="switch" lay-text="女|男" lay-filter="sexDemo" {{ d.id == 10003 ? 'checked' : '' }}>
</script>
<script>
    layui.use(['form', 'table'], function () {
        var form = layui.form,
            table = layui.table;

        var $ = layui.$, active = {
            reload: function(){
                var typeName = $('#typeName').val();
                debugger
                //执行重载
                table.reload('test', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    ,where: {
                       content: typeName
                    }
                }, 'data');
            }
        };

        $('.demoTable .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        /**
         * 根据数据获取选择记录的id集合
         */
        function getCheakIds(data){
            var arr=new Array();
            for(var i=0;i<data.length;i++){
                arr.push(data[i].id);
            }
            return arr.join(",");
        }

        /**
         * 根据id删除记录信息
         */
        function deleteByIds(ids,index){
            $.ajax({
                url:"../typeInfoServlet?flag=delete",
                type:"post",
                data: {ids:ids},
                success:function(result){
                    if(result.code==0){
                        layer.alert("删除成功", {icon: 6},function () {
                            parent.window.location.reload();

                        });
                    }else{
                        layer.msg("不好意思，删除失败了...")
                    }
                }
            })
        }

        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            debugger
            //console.log(obj)
            if(obj.event === 'del'){
                layer.confirm('真的删除行么?', function(index){
                    //这里调用删除功能即可
                    deleteByIds(data.id,index);
                    // layer.close(index);
                });
            } else if(obj.event === 'edit'){
                var index = layer.open({
                    title: '修改类型',
                    type: 2,
                    shade: 0.2,
                    shadeClose: true,
                    area: ['60%', '60%'],
                    content: 'TypeUpdate.jsp?id='+data.id,
                });
                $(window).on("resize", function () {
                    layer.full(index);
                });
            }
        });
        //头部监听事件
        //头工具栏事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            if(obj.event=='getCheckData'){
                var data = checkStatus.data;
                /**
                 * 如果没有选择提示选择，如果选择获取选中id集合
                 */
                debugger
                if(data.length==0){
                    layer.msg("请选择您要删除的记录信息...")
                }else{
                    //获取id集合，进行删除工作
                    var ids=getCheakIds(data);
                    layer.confirm('您确定要删除记录吗？',function(index){
                       //执行删除工作
                        deleteByIds(ids,index);
                    })
                }

                // layer.alert(JSON.stringify(data));
            }






        });

    });
</script>

</body>

</html>
