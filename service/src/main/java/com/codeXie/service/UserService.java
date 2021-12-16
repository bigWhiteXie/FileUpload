package com.codeXie.service;

import com.codeXie.pojo.Condition;
import com.codeXie.pojo.FileItem;
import com.codeXie.pojo.PageBean;
import com.codeXie.pojo.User;

public interface UserService {
    User loginService(String uname,String pwd);

    public PageBean<FileItem> pageService(Condition condition);

    public int uploadFile(FileItem item);

    int delFileService(String position);
}
