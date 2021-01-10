package com.fh;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class OssUtils {

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    public static final  String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    public final static String accessKeyId = "LTAI4GFEkaHix7G2KxAfhFvZ";
    public final static String accessKeySecret = "1AsDmATgr5X4HqsxvtdbyxEwe05TtG";
    public final static String bucketName = "2005plus";
    public final static String photoUrl = "https://test-bucket-by.oss-cn-beijing.aliyuncs.com";


    public static String uploadPhoto(String mkdir, MultipartFile file) throws IOException {


        String oldFilename = file.getOriginalFilename();
        String suffix = oldFilename.substring(oldFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + suffix;
        String path = mkdir + "/" + newFilename;
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ossClient.putObject(bucketName, path, file.getInputStream());

// 关闭OSSClient。
        ossClient.shutdown();
        return photoUrl + "/" + path;
    }

}
