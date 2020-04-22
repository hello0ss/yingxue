package com.ss.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class VideoPo {

    private String id;
    private String title;
    private String brief;
    private String imgpath;
    private String vipath;
    private Date publishday;

    private String classname;

    private String img;
}
