package com.ss.app;


import com.ss.dao.VideoDAO;
import com.ss.entity.Common;
import com.ss.entity.Video;
import com.ss.service.VideoService;
import com.ss.util.AliyunSendPhoneUtil;
import com.ss.vo.VideoVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("app")
public class AppInterfaceController {

    @Resource
    VideoService videoService;

    /*发送验证码的接口*/
    @RequestMapping("getPhoneCode")
    public Common getPhoneCodes(String phone){
        //生成随机数
        String random = AliyunSendPhoneUtil.getRandom(6);
        System.out.println("存储验证码："+random);
        //发送验证码
        String message = AliyunSendPhoneUtil.sendCode(phone, random);
        System.out.println("验证码发送："+message);
        if (message.equals("发送成功")){
            return new Common("100","发送成功",phone);
        }else{
            return new Common("104","发送失败:"+message,null);
        }
    }

    /*
     * 首页查询视频信息接口
     * */
    @RequestMapping("queryByReleaseTime")
    public Common queryByReleaseTime(){
        try {
            //查询数据
            List<VideoVo> list = videoService.queryByReleaseTime();
            return new Common("100","查询成功",list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Common("104","查询失败",null);
        }
    }
}
