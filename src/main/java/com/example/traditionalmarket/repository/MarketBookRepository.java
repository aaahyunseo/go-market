package com.example.traditionalmarket.repository;

import com.example.traditionalmarket.entity.MarketBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MarketBookRepository extends JpaRepository<MarketBook, UUID> {
}
