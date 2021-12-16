package com.codeXie.controller;

import com.codeXie.mapper.FilesMapper;
import com.codeXie.pojo.*;
import com.codeXie.service.UserService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @RequestMapping("userLogin")
    public String login(String uname, String pwd, String remeber, HttpSession session, HttpServletResponse response){
        User user = userService.loginService(uname, pwd);
        if(user != null){
            Cookie cookie1 = new Cookie("uname", uname);
            Cookie cookie2 = new Cookie("pwd", pwd);
            Cookie cookie3 = new Cookie("remeber", remeber);
            cookie1.setPath("/fileUp/");
            cookie2.setPath("/fileUp/");
            cookie3.setPath("/fileUp/");
            if("checked".equals(remeber)){
                cookie1.setMaxAge(60*10*10);
                cookie2.setMaxAge(60*10*10);
                cookie3.setMaxAge(60*10*10);
            }else{
                cookie1.setMaxAge(0);
                cookie2.setMaxAge(0);
                cookie3.setMaxAge(0);
            }
            response.addCookie(cookie1);
            response.addCookie(cookie2);
            response.addCookie(cookie3);
            session.setAttribute("user",user);
            return "redirect:/fileList.html";
        }else{
            return "forward:/index.jsp";
        }
    }
    @ResponseBody
    @RequestMapping("queryFile")
    public PageBean<FileItem> getList(Integer index, Integer size, FileItem item){
        //初始化查询条件condition
        Condition condition = new Condition();
        condition.setIndex(index);
        condition.setSize(size);
        condition.setUname(item.getUname());
        condition.setFileName(item.getFileName());
        condition.setUploadTime(item.getUploadTime());
        return userService.pageService(condition);
    }
    @ResponseBody
    @RequestMapping("uploadFile")
    public ReturnBody uploadFile(MultipartFile file, HttpSession session, HttpServletRequest request) throws IOException {
        //1.得到文件名及后缀
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        //2.将文件上传到文件夹中
        String path = request.getRealPath("/upload");
        File dir = new File(path);
        //如果文件夹不存在则创建文件夹
        if(!dir.exists()){
            dir.mkdirs();
        }
        //得到文件名
        String newName =  UUID.randomUUID()+""+suffix;
        String position = "upload/"+ newName;
        file.transferTo(new File(dir,newName));

        //初始化fileitem
        User user = (User) session.getAttribute("user");
        LocalDateTime time = LocalDateTime.now();
        String uploadTime = time.format(formatter);

        FileItem item = new FileItem();
        item.setUname(user.getUname());
        item.setPosition(position);
        item.setUploadTime(uploadTime);
        item.setFileName(fileName);
        //3.将文件插入数据库
        int i = userService.uploadFile(item);
        ReturnBody body = new ReturnBody();
        if(i>0){
            body.setStatus("ok");
        }else{
            body.setStatus("error");
        }
        return body;
    }

    @RequestMapping("downloadFile")
    public void downloadFile(HttpServletResponse response,String position,String fileName,HttpServletRequest request) throws IOException {
        //得到该文件
        ServletContext sc = request.getServletContext();
        String path = sc.getRealPath(position);
        File file = new File(path);
        System.out.println(path+position);

        //设置响应头
        response.setHeader("Content-Disposition", "attachment;fileName="+ fileName);
        byte[] bytes = FileUtils.readFileToByteArray(file);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.close();

    }

    @ResponseBody
    @RequestMapping("delFile")
    public ReturnBody delFile(String position,HttpServletRequest request){
        int i = userService.delFileService(position);
        ServletContext sc = request.getServletContext();
        String path = sc.getRealPath(position);
        File file = new File(path);
        boolean flag = file.delete();
        ReturnBody body = new ReturnBody();
        if(i>0 && flag){
            body.setStatus("ok");
        }else{
            body.setStatus("error");
        }
        return body;
    }
}
