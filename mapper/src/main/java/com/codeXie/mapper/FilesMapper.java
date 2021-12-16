package com.codeXie.mapper;

import com.codeXie.pojo.Condition;
import com.codeXie.pojo.FileItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FilesMapper {

    List<FileItem> selQueryMapper(Condition condition);

    int selCountMapper(Condition condition);


    @Delete("delete from files where position=#{position}")
    int delFileMapper(@Param("position") String position);

    @Insert("insert into files values(default,#{fileName},#{position},#{uploadTime},#{uname})")
    int addFileMapper(FileItem item);
}
