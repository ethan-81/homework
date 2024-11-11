package com.homework.mpay.account.adopter.in.dto;

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
public class UsePointRequest {
    @Schema(description = "사용자 아이디")
    @NotNull
    private final String userId;

    @Schema(description = "주문 아이디")
    @NotNull
    private final String orderId;

    @Schema(description = "사용 금액")
    @Min(value = 0)
    private final int amount;
}
