package com.example.traditionalmarket.utils;

public class RegionUtils {
    public static String extractRegionFromAddress(String address) {
        if (address == null) return "기타";
        if (address.startsWith("서울")) return "서울";
        if (address.startsWith("경기")) return "경기도";
        if (address.startsWith("부산")) return "부산";
        if (address.startsWith("대구")) return "대구";
        if (address.startsWith("광주")) return "광주";
        if (address.startsWith("대전")) return "대전";
        if (address.startsWith("울산")) return "울산";
        if (address.startsWith("세종")) return "세종";
        if (address.startsWith("인천")) return "인천";
        if (address.startsWith("강원")) return "강원";
        if (address.startsWith("충북")) return "충청북도";
        if (address.startsWith("충남")) return "충청남도";
        if (address.startsWith("전북")) return "전라북도";
        if (address.startsWith("전남")) return "전라남도";
        if (address.startsWith("경북")) return "경상북도";
        if (address.startsWith("경남")) return "경상남도";
        if (address.startsWith("제주")) return "제주";
        return "기타";
    }
}
