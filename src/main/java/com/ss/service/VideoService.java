package com.ss.service;

import com.ss.entity.Video;
import com.ss.entity.Videoss;
import com.ss.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface VideoService {

    //分页展示
    HashMap<String,Object> queryAllVideo(Integer page, Integer rows);

    //添加
    String insertVideo(Video video);
    //文件上传
    void uploadImgVideo(MultipartFile vipath, String id);
    //修改
    void updateVideo(Video video);
    //删除
    HashMap<String, Object> deleteVideo(String id);

    //检索
    List<Videoss> querySearch(String content);




    //前台首页展示全部视频
    List<VideoVo> queryByReleaseTime ();
}
