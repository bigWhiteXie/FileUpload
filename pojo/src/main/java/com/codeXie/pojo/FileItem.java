package com.codeXie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileItem implements Serializable {
    private Integer id;
    private String fileName;
    private String position;
    private String uploadTime;
    private String uname;
}
