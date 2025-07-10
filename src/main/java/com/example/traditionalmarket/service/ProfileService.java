package com.example.traditionalmarket.service;

import com.example.traditionalmarket.entity.Profile;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final S3Service s3Service;

    // 프로필 이미지 업로드
    @Transactional
    public void uploadImages(User user, MultipartFile image) throws IOException {
        String imageUrl = s3Service.profileUploadImage(image);

        Profile profile = user.getProfile();

        if (profile == null) {
            profile = Profile.builder()
                    .imageUrl(imageUrl)
                    .user(user)
                    .build();
        } else {
            // 기존 이미지 삭제
            s3Service.deleteImage(profile.getImageUrl());
            profile.setImageUrl(imageUrl);
        }

        profileRepository.save(profile);
    }

    // 이미지 삭제
    @Transactional
    public void deleteImages(User user) {
        Profile profile = user.getProfile();
        if (profile != null) {
            s3Service.deleteImage(profile.getImageUrl());
            profileRepository.delete(profile);
            user.setProfile(null);
        }
    }

}
