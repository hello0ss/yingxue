package com.ss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.ss.dao.*;
import com.ss.entity.Log;
import com.ss.entity.Video;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YingxueApplicationTests {

    @Resource
    AdminDAO adminDAO;
    @Resource
    UsersDAO usersDAO;
    @Resource
    ClassesDAO classesDAO;
    @Resource
    VideoDAO videoDAO;
    @Resource
    LogDAO logDAO;


    // Endpoint以杭州为例，其它Region请按实际情况填写。
    String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    String accessKeyId = "";
    String accessKeySecret = "";

    @Test
    public void contextLoads() {

        System.out.println(usersDAO.queryCityUsers("女"));

        System.out.println(usersDAO.queryAllUsers());

        /*logDAO.insertLog(new Log("1","aa","aa","aa","aa"));
        List<Log> logs = logDAO.showAllLog(0, 9);
        System.out.println(logs);*/
        /*List<Video> list = videoDAO.showAllVideo(0, 4);
        for (Video ss : list) {
            System.out.println(ss);
        }*/
        /*videoDAO.updateVideo(new Video().setId("1").setVipath("aaaaa"));*/
        /*System.out.println(usersDAO.showAllUsers(0,1));

        System.out.println(adminDAO.queryByName("admin"));
        System.out.println(ImageCodeUtil.getNumber(5));*/
    }

    //创建阿里云存储空间
    @Test
    public void createBucket () {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "";
        String accessKeySecret = "";
        String bucketName = "";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建存储空间。
        ossClient.createBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //文件上传(1)
    @Test
    public void uploadFile () {
        //存储空间名
        String bucketName = "ss-yingxue";
        //制定上传文件名称  (可以指定上传目录)
        String fileName = "7.jpeg";
        //指定本地文件路径
        String localFile = "C:\\Users\\86184\\Pictures\\Saved Pictures\\啊啊的壁纸\\7.jpeg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //创建PutObjectRequest对象
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,fileName,new File(localFile));

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传文件
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //文件上传(2)流
    @Test
    public void aa () throws IOException {
        //存储空间名
        String bucketName = "ss-yingxue";
        //制定上传文件名称  (可以指定上传目录)
        String fileName = "8.jpg";
        //指定本地文件路径
        String localFile = "C:\\Users\\86184\\Pictures\\Saved Pictures\\啊啊的壁纸\\8.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        InputStream inputStream = new FileInputStream(localFile);

        // 上传文件
        ossClient.putObject(bucketName,fileName,inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //文件上传(2)Byte数组
    @Test
    public void bb () throws IOException {
        //存储空间名
        String bucketName = "ss-yingxue";
        //制定上传文件名称  (可以指定上传目录)
        String fileName = "8.jpg";
        //指定本地文件路径
        String localFile = "C:\\Users\\86184\\Pictures\\Saved Pictures\\啊啊的壁纸\\8.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //上传Byte数组
        byte[] content = "".getBytes();
        // 上传文件
        ossClient.putObject(bucketName,fileName,new ByteArrayInputStream(content));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //文件下载
    @Test
    public void downLoad () {
        //存储空间名
        String bucketName = "ss-yingxue";
        //指定下载文件名称  (可以指定上传目录)
        String objectName = "7.jpeg";
        //指定下载本地文件路径  (可自定义文件名称)
        String localFile = "C:\\Users\\86184\\Pictures\\Saved Pictures\\图片\\aa.jpeg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName,objectName),new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //删除文件
    @Test
    public void delete(){
        //存储空间名
        String bucketName = "ss-yingxue";
        //指定删除文件名称
        String objectName = "7.jpeg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

}
