package com.homework.mpay.account.adopter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
@Schema(description = "계좌 정보 응답 모델")
public class GetAccountResponse {
    @Schema(description = "사용자 아이디")
    private final String userId;

    @Schema(description = "보유 포인트 목록")
    private final List<PointResponse> points;
}
