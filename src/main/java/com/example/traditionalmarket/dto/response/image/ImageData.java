package com.example.traditionalmarket.dto.response.image;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ImageData {
    private UUID imageId;
    private String imageUrl;
}
