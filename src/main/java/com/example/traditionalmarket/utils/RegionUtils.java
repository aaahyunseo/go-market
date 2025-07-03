package com.example.traditionalmarket.utils;

public class RegionUtils {
    public static String extractRegionFromAddress(String address) {
        if (address == null || address.isBlank()) return "기타";

        String prefix = address.split(" ")[0];

        if (prefix.startsWith("서울")) return "서울";
        if (prefix.startsWith("경기")) return "경기도";
        if (prefix.startsWith("부산")) return "부산";
        if (prefix.startsWith("대구")) return "대구";
        if (prefix.startsWith("광주")) return "광주";
        if (prefix.startsWith("대전")) return "대전";
        if (prefix.startsWith("울산")) return "울산";
        if (prefix.startsWith("세종")) return "세종";
        if (prefix.startsWith("인천")) return "인천";
        if (prefix.startsWith("강원")) return "강원";
        if (prefix.startsWith("충북")) return "충청북도";
        if (prefix.startsWith("충남")) return "충청남도";
        if (prefix.startsWith("전북")) return "전라북도";
        if (prefix.startsWith("전남")) return "전라남도";
        if (prefix.startsWith("경북")) return "경상북도";
        if (prefix.startsWith("경남")) return "경상남도";
        if (prefix.startsWith("제주")) return "제주도";
        return "기타";
    }

    public static String normalizeRegion(String inputRegion) {
        if (inputRegion == null || inputRegion.isBlank()) return "기타";

        return extractRegionFromAddress(inputRegion);
    }
}
