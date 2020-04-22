package com.ss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = "yingxue",type = "ss")
public class Videoss implements Serializable {

    @Id
    private String id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String brief;
    @Field(type = FieldType.Keyword)
    private String vipath;
    @Field(type = FieldType.Keyword)
    private String imgpath;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Field(type = FieldType.Date)
    private Date publishday;
    @Field(type = FieldType.Keyword)
    private String classid;
    @Field(type = FieldType.Keyword)
    private String groupid;
    @Field(type = FieldType.Keyword)
    private String userid;

}
