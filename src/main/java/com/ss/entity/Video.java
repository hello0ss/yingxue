package com.ss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Video implements Serializable {

    private String id;
    private String title;
    private String brief;
    private String vipath;
    private String imgpath;
    private Date publishday;
    private String classid;
    private String groupid;
    private String userid;
    private Classes classes;

}
