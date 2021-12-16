package com.codeXie.service.Impl;

import com.codeXie.mapper.FilesMapper;
import com.codeXie.mapper.UserMapper;
import com.codeXie.pojo.Condition;
import com.codeXie.pojo.FileItem;
import com.codeXie.pojo.PageBean;
import com.codeXie.pojo.User;
import com.codeXie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    FilesMapper filesMapper;
    @Override
    public User loginService(String uname, String pwd) {
      return userMapper.selUserMapper(uname,pwd);
    }

    public PageBean<FileItem> pageService(Condition condition){
        //创建pageBean对象
        PageBean pageBean = new PageBean();
        //设置pagebean的index和size
        pageBean.setIndex(condition.getIndex());
        pageBean.setSize(condition.getSize());
        //获取符合条件的记录数量
        int count = filesMapper.selCountMapper(condition);
        //设置符合记录的记录数量
        pageBean.setTotalCount(count);
        //为condition设置开始的行
        condition.setStart(pageBean.getStartRow());
        //按照条件查询表
        List<FileItem> list = filesMapper.selQueryMapper(condition);

        //返回pageBean
        pageBean.setList(list);
        return pageBean;
    }

    @Override
    public int uploadFile(FileItem item) {
        return filesMapper.addFileMapper(item);
    }

    @Override
    public int delFileService(String position) {
        return filesMapper.delFileMapper(position);
    }
}
