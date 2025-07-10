package com.example.traditionalmarket.repository;

import com.example.traditionalmarket.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageFileRepository extends JpaRepository<ImageFile, UUID> {
}
