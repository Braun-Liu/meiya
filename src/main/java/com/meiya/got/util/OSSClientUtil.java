package com.meiya.got.util;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

//import com.fndsoft.bcis.exception.ImgException;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.meiya.got.Config.AliyunOSSConfig;
import com.meiya.got.exception.ImgException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aliyun.oss.OSSClient;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

/**
 * 阿里云 OSS文件类
 *
 * @author YuanDuDu
 */
@Component
public class OSSClientUtil {

    Log log = LogFactory.getLog(OSSClientUtil.class);
    // endpoint以杭州为例，其它region请按实际情况填写
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // accessKey
    private static String accessKeyId = "LTAIFYG39lX3ivGF";
    private static String accessKeySecret = "DFU4ap92yEO6IGVnkFdAvanJdNafPt";
    //空间
    private static String bucketName = "myxly-images";
    //文件存储目录
    private static String fileHost = "storephoto/";
//    @Autowired
//    private static AliyunOSSConfig aliyunOSSConfig;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OSSClientUtil.class);

    private static String FILE_URL;
//    private static String bucketName = aliyunOSSConfig.getBucketname();
//    private  static String endpoint = aliyunOSSConfig.getEndpoint();
//    private static String accessKeyId = aliyunOSSConfig.getKeyid();
//    private static String accessKeySecret = aliyunOSSConfig.getKeysecret();
//    private static String fileHost = aliyunOSSConfig.getFilehost();

    /**
     * 上传文件。
     *
     * @param file 需要上传的文件路径
     * @return 如果上传的文件是图片的话，会返回图片的"URL"，如果非图片的话会返回"非图片，不可预览。文件路径为：+文件路径"
     */
    public static String upLoad(File file) {
        // 默认值为：true
        boolean isImage = true;
        // 判断所要上传的图片是否是图片，图片可以预览，其他文件不提供通过URL预览
        try {
            Image image = ImageIO.read(file);
            isImage = image == null ? false : true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("------OSS文件上传开始--------" + file.getName());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());

        // 判断文件
        if (file == null) {
            return null;
        }
        // 创建OSSClient实例。
        OSSClient ossClient = (OSSClient) new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 判断容器是否存在,不存在就创建
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            // 设置文件路径和名称
            String fileUrl = fileHost + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());
            if (isImage) {//如果是图片，则图片的URL为：....
                FILE_URL = "https://" + bucketName + "." + endpoint + "/" + fileUrl;
            } else {
                FILE_URL = "非图片，不可预览。文件路径为：" + fileUrl;
            }

            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName,fileUrl,file));
            // 设置权限(公开读)
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            if (result != null) {
                logger.info("------OSS文件上传成功------" + fileUrl);
            }
        } catch (OSSException oe) {
            logger.error(oe.getMessage());
        } catch (ClientException ce) {
            logger.error(ce.getErrorMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return FILE_URL;
    }


    /**
     * 通过文件名下载文件
     *
     * @param objectName    要下载的文件名
     * @param localFileName 本地要创建的文件名
     */
    public static void downloadFile(String objectName, String localFileName) {

        // 创建OSSClient实例。
        OSSClient ossClient = (OSSClient) new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFileName));
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 列举 test 文件下所有的文件
     */
    public static void listFile() {
        // 创建OSSClient实例。
        OSSClient ossClient = (OSSClient) new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

        // 设置prefix参数来获取fun目录下的所有文件。
        listObjectsRequest.setPrefix("test/");
        // 列出文件。
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有文件。
        System.out.println("Objects:");
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            System.out.println(objectSummary.getKey());
        }
        // 遍历所有commonPrefix。
        System.out.println("CommonPrefixes:");
        for (String commonPrefix : listing.getCommonPrefixes()) {
            System.out.println(commonPrefix);
        }
        // 关闭OSSClient。
        ossClient.shutdown();
    }

/*    private OSSClient ossClient;

//    public OSSClientUtil() {
//        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//    }

    *//**
     * 初始化
     *//*
    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    *//**
     * 销毁
     *//*
    public void destory() {
        ossClient.shutdown();
    }*/

  /*  *//**
     * 上传图片
     *
     * @param url
     *//*
  *//*  public void uploadImg2Oss(String url) throws ImgException {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            System.out.println("开始上传图片---------");
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            System.out.println("上传完毕---------");
            this.uploadFile2OSS(fin, split[split.length - 1]);
        } catch (FileNotFoundException e) {
            throw new ImgException("图片上传失败");
        }
    }*/

    public static String getFileUrl(MultipartFile fileupload) throws OSSException, ClientException, IOException {
        // 获取文件后缀名
        String suffix =getSuffix(fileupload);
        // 填自己的帐号信息
//        String endpoint = "oss-cn-shenzhen.aliyuncs.com";
//        String accessKeyId = "LTAIATBxxxxxxxx4R";
//        String accessKeySecret = "VuCGomJW0O5xxxxxxxxx9QKR";
        // 创建OSSClient实例
        OSSClient ossClient= (OSSClient) new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
   //     OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 文件桶
        String bucketName = "myxly-images";
        // 文件名格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        // 该桶中的文件key
        String dateString = sdf.format(new Date()) + "."+suffix; // 20180322010634.jpg
        // 上传文件

        ossClient.putObject(bucketName, fileHost+dateString, new ByteArrayInputStream(fileupload.getBytes()));
        // 设置URL过期时间为100年，默认这里是int型，转换为long型即可
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 100);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, fileHost+dateString, expiration);
        return url.toString();
    }
    // 获取文 MultipartFile 文件后缀名工具
    public static String getSuffix(MultipartFile fileupload){
        String originalFilename = fileupload.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        System.out.println(suffix);
        return suffix;
    }
}

/*
    public String uploadImg2Oss(MultipartFile file) throws ImgException {
        if (file.getSize() > 1024 * 1024) {
            throw new ImgException("上传图片大小不能超过1M！");
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            throw new ImgException("图片上传失败");
        }
    }*/

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     *//*
    public String getImgUrl(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(this.filedir + split[split.length - 1]);
        }
        return null;
    }

    *//**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     *//*
    public String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    *//**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     *//*
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    *//**
     * 获得url链接
     *
     * @param key
     * @return
     *//*
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }*/

