package com.alioth4j.chatgpt3p5o.service.impl;

import cn.hutool.json.JSONUtil;
import com.alioth4j.chatgpt3p5o.pojo.domain.BucketAccessPolicy;
import com.alioth4j.chatgpt3p5o.service.MinIOService;
import io.minio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MinIOServiceImpl implements MinIOService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinIOServiceImpl.class);

    @Value("${minio.endpoint}")
    private String ENDPOINT;

    @Value("${minio.bucketName}")
    private String BUCKET_NAME;

    @Value("${minio.accessKey}")
    private String ACCESS_KEY;

    @Value("${minio.secretKey}")
    private String SECRET_KEY;


    @Override
    public String upload(MultipartFile file) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
                BucketAccessPolicy bucketAccessPolicy = createBucketAccessPolicy();
                SetBucketPolicyArgs setBucketPolicyArgs = SetBucketPolicyArgs.builder()
                        .bucket(BUCKET_NAME)
                        .config(JSONUtil.toJsonStr(bucketAccessPolicy))
                        .build();
                minioClient.setBucketPolicy(setBucketPolicyArgs);
            }
            // 设置存储对象名称
            String fileName = file.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String objectName = sdf.format(new Date()) + "/" + fileName;

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(objectName)
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), ObjectWriteArgs.MIN_MULTIPART_SIZE)
                    .build();
            minioClient.putObject(putObjectArgs);
            LOGGER.info("文件上传成功");
            return ENDPOINT + "/" + BUCKET_NAME + "/" + objectName;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("上传发生错误：{}", e.getMessage());
        }
        return "";
    }

    private BucketAccessPolicy createBucketAccessPolicy() {
        BucketAccessPolicy.Statement statement = BucketAccessPolicy.Statement.builder()
                .Effect("Allow")
                .Principal("*")
                .Action("s3:GetObject")
                .Resource("arn:aws:s3:::" + BUCKET_NAME + "/*.**")
                .build();
        return BucketAccessPolicy.builder()
                .Version("2012-10-17")
                .Statement(List.of(statement))
                .build();
    }
}
