package com.ss.controller;

import com.ss.entity.Video;
import com.ss.entity.Videoss;
import com.ss.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("video")
public class VideoController {

    @Resource
    VideoService videoService;

    @ResponseBody
    @RequestMapping("queryAllVideoMethod")
    public HashMap<String,Object> queryAllVideoMethod (Integer page,Integer rows) {
        System.out.println("---------------视频图片分页展示--------------");

        return videoService.queryAllVideo(page,rows);
    }

    @ResponseBody
    @RequestMapping("VideoMethod")
    public Object VideoMethod (Video video,String oper) {
        System.out.println(video);
        String ss = null;
        if ("add".equals(oper)) {
            System.out.println("--------------视频图片添加--------------");
            return videoService.insertVideo(video);
        } else if ("edit".equals(oper)) {
            System.out.println("--------------视频图片修改--------------");
            videoService.updateVideo(video);
        } else if ("del".equals(oper)) {
            System.out.println("--------------视频图片删除--------------");
            return videoService.deleteVideo(video.getId());
        }
        return "";
    }

    @ResponseBody
    @RequestMapping("viuploadVideoMethod")
    public void viuploadVideoMethod (MultipartFile vipath,String id) {
        System.out.println("--------------视频图片文件上传--------------");
        System.out.println(id);
        videoService.uploadImgVideo(vipath, id);
    }

    @ResponseBody
    @RequestMapping("querySearchVideoMethod")
    public List<Videoss> querySearchVideoMethod (String content) {
        System.out.println("--------------视频(图片)模糊查询--------------");

        return videoService.querySearch(content);
    }


}
