package com.example.traditionalmarket.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 s3client;
    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    public List<String> uploadImage(List<MultipartFile> files) throws IOException {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();

            // 파일명이 null인 경우 예외 처리
            if (filename == null) {
                throw new IllegalStateException("파일명이 null입니다.");
            }

            int dotIndex = filename.lastIndexOf(".");
            if (dotIndex == -1) {
                throw new IllegalArgumentException("파일에 적절한 확장자가 없습니다: " + filename);
            }

            String key = "boards/" + changedImageName(filename);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            s3client.putObject(bucketName, key, file.getInputStream(), metadata);

            String imageUrl = generateFileUrl(key);
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }

    public void deleteImage(String imageUrl) {
        String splitStr = ".com/";
        String key = imageUrl.substring(imageUrl.lastIndexOf(splitStr) + splitStr.length());
        s3client.deleteObject(new DeleteObjectRequest(bucketName, key));
    }

    // 랜덤 파일 이름 메서드
    private String changedImageName(String originName) {
        String uuid = UUID.randomUUID().toString();
        String extension = originName.substring(originName.lastIndexOf("."));
        return uuid + extension;
    }

    private String generateFileUrl(String key) {
        return s3client.getUrl(bucketName.trim(), key).toString();
    }
}

