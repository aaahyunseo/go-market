package com.example.traditionalmarket.service;

import com.example.traditionalmarket.entity.Board;
import com.example.traditionalmarket.entity.ImageFile;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.repository.ImageFileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageFileService {

    private final ImageFileRepository imageFileRepository;
    private final S3Service s3Service;

    // 이미지 업로드
    @Transactional
    public void uploadImages(User user, Board board, List<MultipartFile> images) throws IOException {
        List<String> imageUrls = s3Service.uploadImage(images);

        for (String imageUrl : imageUrls) {
            ImageFile newImage = ImageFile.builder()
                    .imageUrl(imageUrl)
                    .board(board)
                    .user(user)
                    .build();
            imageFileRepository.save(newImage);
        }
    }

    // 이미지 삭제
    @Transactional
    public void deleteImages(Board board) {
        List<ImageFile> filesToDelete = new ArrayList<>(board.getImageFiles());
        if (!filesToDelete.isEmpty()) {
            filesToDelete.forEach(img -> s3Service.deleteImage(img.getImageUrl()));
            imageFileRepository.deleteAll(filesToDelete);
            board.getImageFiles().clear();
        }
    }

    // 특정 ID 이미지 삭제
    @Transactional
    public void deleteImagesByImageIds(List<UUID> imageIds) {
        if (imageIds != null && !imageIds.isEmpty()) {
            List<ImageFile> filesToDelete = imageFileRepository.findAllById(imageIds);
            filesToDelete.forEach(img -> s3Service.deleteImage(img.getImageUrl()));
            imageFileRepository.deleteAllInBatch(filesToDelete);
        }
    }
}
