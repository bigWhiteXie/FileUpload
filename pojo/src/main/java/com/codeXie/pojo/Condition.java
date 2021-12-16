package com.codeXie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Condition {
    private  Integer index;
    private Integer start;
    private Integer size;
    private String uname;
    private String fileName;
    private String uploadTime;
}
