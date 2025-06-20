package com.example.traditionalmarket.repository;

import com.example.traditionalmarket.entity.VisitedMarket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VisitedMarketRepository extends JpaRepository<VisitedMarket, UUID> {
}
