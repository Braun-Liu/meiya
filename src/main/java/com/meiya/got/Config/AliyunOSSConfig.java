package com.meiya.got.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:application-oss.properties")
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSConfig {
    private static String bucketname;
    private static String endpoint;
    private static String keyid;
    private static String keysecret;
    private static String filehost;

    public static String getBucketname() {
        return bucketname;
    }

    public static void setBucketname(String bucketname) {
        AliyunOSSConfig.bucketname = bucketname;
    }

    public static String getEndpoint() {
        return endpoint;
    }

    public static void setEndpoint(String endpoint) {
        AliyunOSSConfig.endpoint = endpoint;
    }

    public static String getKeyid() {
        return keyid;
    }

    public static void setKeyid(String keyid) {
        AliyunOSSConfig.keyid = keyid;
    }

    public static String getKeysecret() {
        return keysecret;
    }

    public static void setKeysecret(String keysecret) {
        AliyunOSSConfig.keysecret = keysecret;
    }

    public static String getFilehost() {
        return filehost;
    }

    public static void setFilehost(String filehost) {
        AliyunOSSConfig.filehost = filehost;
    }

}
