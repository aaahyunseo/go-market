package com.example.traditionalmarket.repository;

import com.example.traditionalmarket.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SurveyRepository extends JpaRepository<Survey, UUID> {
}
