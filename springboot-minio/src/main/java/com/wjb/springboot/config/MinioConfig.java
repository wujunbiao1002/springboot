package com.wjb.springboot.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <b><code>MinioClientConfig</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/9/11 15:46.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Configuration
@Slf4j
public class MinioConfig {

    @Autowired
    private MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient = MinioClient.builder().endpoint(minioProperties.getEndpoint()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getDefaultBucketName()).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getDefaultBucketName()).build());
            }
        } catch (ErrorResponseException | XmlParserException | ServerException | NoSuchAlgorithmException |
                 IOException | InvalidResponseException | InvalidKeyException | InternalException |
                 InsufficientDataException e) {
            log.error("Exception information={}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return minioClient;
    }
}
