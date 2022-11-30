package com.wjb.springboot.minio.controller;

import com.wjb.springboot.minio.util.MinioUtils;
import io.minio.ObjectWriteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * <b><code>MinioController</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/9/11 4:43.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@RestController
@Slf4j
public class MinioController {
    @Autowired
    private MinioUtils minioUtils;

    @Value("${minio.bucketName}")
    private String bucketName;

    @PostMapping("/upload")
    public Object upload(MultipartFile file) {
        try {
            ObjectWriteResponse response = minioUtils.putObject(bucketName, file, UUID.randomUUID() + "_" + file.getOriginalFilename());
            log.info("{}==={}", response.etag(), response.versionId());
        } catch (Exception e) {
            log.error("Exception information={}", e.getMessage(), e);
            return "失败";
        }
        return "成功";
    }

    @GetMapping("/download/{fileName}")
    public String download(@PathVariable String fileName, HttpServletResponse response) {
        InputStream data = null;
        try {
            data = minioUtils.getObject(bucketName, fileName);
        } catch (Exception e) {
            log.error("Exception information={}", e.getMessage(), e);
        }
        assert data != null;

        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));

        try {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = data.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
            return "下载成功";
        } catch (IOException e) {
            log.error("Exception information={}", e.getMessage(), e);
            return "下载失败";
        }
    }
}

