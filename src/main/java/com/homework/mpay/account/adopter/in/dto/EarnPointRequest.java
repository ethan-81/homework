package com.homework.mpay.account.adopter.in.dto;

import com.homework.mpay.common.constant.PointTypeCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
@Schema(description = "포인트 적립 요청 모델")
public class EarnPointRequest {
    @Schema(description = "사용자 아이디")
    @NotNull
    private final String userId;

    @Schema(description = "적립 금액")
    @Min(value = 0)
    private final int amount;

    @Schema(description = "적립 포인트 유형 코드, FREE_POINT: 무료 포인트, MANUAL_POINT: 수기 지급 포인트")
    @NotNull
    private final PointTypeCode pointTypeCode;
}
