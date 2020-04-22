package com.ss.dao;

import com.ss.entity.Video;
import com.ss.po.VideoPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDAO {

    //分页展示
    List<Video> showAllVideo(@Param("start")Integer start, @Param("rows")Integer rows);
    //总条数
    Integer conter();

    //添加
    void insertVideo(Video video);
    //修改
    void updateVideo(Video video);
    Video showVideo(String id);
    //删除
    void deleteVideo(String id);


    //前台首页展示全部视频
    List<VideoPo> queryByReleaseTime ();

}
