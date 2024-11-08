package com.homework.mpay.account.adopter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
@Schema(description = "포인트 적립 취소 요청 모델")
public class CancelEarnPointRequest {
    @Schema(description = "사용자 아이디")
    @NotNull
    private final String userId;

    @Schema(description = "포인트 아이디")
    @NotNull
    private final String pointId;
}
