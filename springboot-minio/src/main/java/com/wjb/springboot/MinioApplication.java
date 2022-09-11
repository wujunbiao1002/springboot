package com.wjb.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <b><code>${NAME}</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> ${DATE} ${TIME}.
 *
 * @author Arjun
 * @version 1.0.0
 * @since ${PROJECT_NAME} ${PROJECT_VERSION}
 */
@SpringBootApplication
public class MinioApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinioApplication.class, args);
    }
}