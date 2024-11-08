package com.homework.mpay.account.adopter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
@Schema(description = "포인트 응답 모델")
public class PointResponse {
    @Schema(description = "포인트 아이디")
    private final String pointId;

    @Schema(description = "적립 금액")
    private final int earnedAmount;

    @Schema(description = "가용 금액")
    private final int availableAmount;

    @Schema(description = "만료 된 금액")
    private final int expiredAmount;

    @Schema(description = "만료 일자")
    private final LocalDate expireDate;

    @Schema(description = "포인트 유형 코드")
    private final String pointTypeCode;

    @Schema(description = "포인트 유형 이름")
    private final String pointTypeName;

    @Schema(description = "적립 일시")
    private final LocalDateTime earnedAt;

    @Schema(description = "변경 일시")
    private final LocalDateTime modifiedAt;
}
