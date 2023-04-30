package com.yanzhen.controller;

import com.alibaba.fastjson.JSON;
import com.yanzhen.model.DataCount;
import com.yanzhen.model.RoomInfo;
import com.yanzhen.model.RoomType;
import com.yanzhen.service.RoomInfoService;
import com.yanzhen.service.impl.RoomInfoServiceImpl;
import com.yanzhen.util.JsonObject;
import com.yanzhen.util.PageBean;
import com.yanzhen.util.R;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/roomServlet")
public class RoomInfoServlet extends HttpServlet {
    RoomInfoService service=new RoomInfoServiceImpl();
    @Override
    protected void service(HttpServletRequest req,
                           HttpServletResponse resp) throws ServletException, IOException {
       //接收前端传过来的功能标识
        String flag=req.getParameter("flag")==null?"":req.getParameter("flag");
        switch (flag){
            case "insert":
                RoomInfo info=getValues(req);
                service.saveInfo(info);
                resp.setContentType("application/json;chartset=utf-8");
                resp.getWriter().print(JSON.toJSONString(R.ok()));
                break;
            case "upload":
                try {
                    uploadFile(req,resp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "queryRoomListByStatus":
                List<RoomInfo> lists= service.queryListByStatus(0);
                resp.setContentType("application/json;chartset=utf-8");
                resp.getWriter().print(JSON.toJSONString(lists));
                break;
            case "delete":
                String ids=req.getParameter("ids");
                //把接收到的字符串转成集合
                List<String> idList= Arrays.asList(ids.split(","));
                for(String id:idList){
                    service.deleteById(Integer.parseInt(id));
                }
                resp.setContentType("application/json;chartset=utf-8");
                resp.getWriter().print(JSON.toJSONString(R.ok()));
                break;
            case "queryId":
                String id=req.getParameter("id");
                RoomInfo info1= null;
                try {
                    info1 = service.queryByID(Integer.parseInt(id));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                req.setAttribute("info",info1);
                resp.setContentType("text/html;chartset=utf-8");
                req.getRequestDispatcher("admin/room/update.jsp").forward(req,resp);
                break;
            case "update":
                RoomInfo roomInfo=getValues(req);
                boolean bs=service.updateInfo(roomInfo);
                resp.setContentType("application/json;chartset=utf-8");
                String jsons2=JSON.toJSONString(R.fail("修改失败了..."));
                if(bs){
                    jsons2=JSON.toJSONString(R.ok());
                }
                resp.getWriter().print(jsons2);
                break;
            case "tongji":
                try {
                    List<DataCount> dataCountList= service.queryTjList();
                    //存放类型名称
                    List <String> listName=new ArrayList<>();
                    List <Integer> listData=new ArrayList<>();
                    for(DataCount dataCount:dataCountList){
                        listName.add("'"+dataCount.getName()+"'");
                        listData.add(dataCount.getCounts());
                    }
                    req.setAttribute("listName",listName);
                    req.setAttribute("listData",listData);
                    req.getRequestDispatcher("admin/tongji/index.jsp").forward(req,resp);
                } catch (Exception throwables) {
                    throwables.printStackTrace();
                }
                break;
            default:
                query(req,resp);
                break;
        }

    }

    /**
     * 上传图片接口
     */
    public void uploadFile(HttpServletRequest req,
                           HttpServletResponse resp) throws Exception {
        //文件上传处理器
        FileItemFactory factory=new DiskFileItemFactory();
        ServletFileUpload upload=new ServletFileUpload(factory);
        //解析请求信息
        List items=upload.parseRequest(req);
        Iterator it=items.iterator();
        String fileName="";
        if(it.hasNext()){
           FileItem item= (FileItem) it.next();
            fileName=item.getName();
           //获取文件的后缀名
           String suffName= fileName.substring(fileName.lastIndexOf("."));
           //随机生成文件名称
           fileName= UUID.randomUUID()+suffName;
           req.setAttribute("fileName",fileName);
           //真实上传到的文件地址  注意文件夹存在
           String basePath="D:\\images";
           File f=new File(basePath);
           if(!f.exists()){
               f.mkdirs();
           }
           File file=new File(basePath,fileName);
           item.write(file);
        }
        resp.setContentType("application/json;chartset=utf-8");
        String json=JSON.toJSONString(R.ok(fileName,null));
        resp.getWriter().print(json);
    }



        /**
         * 根据前端传值获取对应的值信息，并把值信息封装到RoomInfo 对象中
         * @param req
         * @return
         */
    public RoomInfo getValues(HttpServletRequest req){
        //根据id判断是修改还新增，如修改id不为空
        RoomInfo info=new RoomInfo();
        String id=req.getParameter("id")==null?null:req.getParameter("id");
        if(id!=null){
           info.setId(Integer.parseInt(id));
        }
        String number=req.getParameter("number");
        String area=req.getParameter("area");
        String roomId=req.getParameter("roomId");
        String counts=req.getParameter("counts");
        String img=req.getParameter("img");
        String status=req.getParameter("status");
        String price=req.getParameter("price");
        String remark=req.getParameter("remark");
        info.setArea(Double.parseDouble(area));
        info.setNumber(number);
        info.setRoom_id(Integer.parseInt(roomId));
        info.setCounts(Integer.parseInt(counts));
        info.setImg(img);
        info.setStatus(Integer.parseInt(status));
        info.setPrice(Double.parseDouble(price));
        info.setRemark(remark);
        return info;
    }
    /**
     * 查询
     */
    public void query(HttpServletRequest req,
                      HttpServletResponse resp) throws ServletException, IOException {

        //接收前端的传值  当前页 每页显示记录数
        String currPage=req.getParameter("page");
        String limit=req.getParameter("limit");
        String content=req.getParameter("content")==null?null:req.getParameter("content");
        //判断
        if(currPage==null || "".equals(currPage.trim())){
            currPage="1";
        }

        if(limit==null || "".equals(limit.trim())){
            limit="10";
        }

        //通过service层接口拿到分页对象
        PageBean<RoomInfo> pageBean=null;
        try {
            pageBean = service.getPage(currPage,limit,content);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //转到前端
        JsonObject object=new JsonObject();
        object.setCode(0);
        object.setMsg("");
        object.setCount(pageBean.getTotalCount());
        object.setData(pageBean.getPageData());

        resp.setContentType("application/json;chartset=utf-8");
        String jsons= JSON.toJSONString(object);
        resp.getWriter().print(jsons);
    }
}
