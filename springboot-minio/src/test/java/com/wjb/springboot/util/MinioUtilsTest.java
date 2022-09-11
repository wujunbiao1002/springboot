package com.wjb.springboot.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <b><code>MinioUtilsTest</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/9/12 1:04.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Slf4j
@SpringBootTest
class MinioUtilsTest {
    @Autowired
    private MinioUtils minioUtils;

    @SneakyThrows
    @Test
    void removeObject() {
        minioUtils.removeObject("minio-test","hosts");
    }

    @Test
    void doesObjectExist(){
        log.info("{}",minioUtils.doesObjectExist("minio-test","2ef03b15-6f62-49ec-b6d5-d0ed93f97969"));
    }

    @SneakyThrows
    @Test
    void getPresignedObjectUrl(){
        log.info("{}",minioUtils.getPresignedObjectUrl("minio-test","2ef03b15-6f62-49ec-b6d5-d0ed93f97969"));
    }

    @SneakyThrows
    @Test
    void downloadObject(){
        minioUtils.downloadObject("minio-test","2ef03b15-6f62-49ec-b6d5-d0ed93f97969");
    }
}