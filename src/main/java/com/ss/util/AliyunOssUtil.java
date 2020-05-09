package com.ss.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class AliyunOssUtil {

    //上传文件至阿里云
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    private static String accessKeyId = "";
    private static String accessKeySecret = "";

    // 创建OSSClient实例。
    private static OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

    //上传本地文件
    public static void upLoadFile(String bucket,String filename,String localFilePath) {

        //存储空间名
        String bucketName = bucket;
        //制定上传文件名称
        String fileName = filename;
        //指定本地文件路径
        String localFile = localFilePath;

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

    //上传字节数组
    public static void upLoadFileBytes(String bucket, String filename, MultipartFile img) {

        //存储空间名
        String bucketName = bucket;
        //制定上传文件名称
        String fileName = filename;
        //转换字节数组
        byte[] bytes = null;
        try {
            bytes = img.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 上传Byte数组
        ossClient.putObject(bucketName,fileName,new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //删除阿里云文件
    public static void delete (String bucket,String fileName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucket,fileName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }




}
