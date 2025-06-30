package com.example.traditionalmarket.dto.request.marketbook;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarketBookRequestDto {
    @NotBlank(message = "시장 이름을 입력해주세요.")
    private String marketName;
}
