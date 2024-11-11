package com.homework.mpay.account.adopter.in;

import com.homework.mpay.account.adopter.in.dto.CancelEarnPointRequest;
import com.homework.mpay.account.adopter.in.dto.EarnPointRequest;
import com.homework.mpay.account.adopter.in.dto.UsePointRequest;
import com.homework.mpay.account.application.port.in.EarnPointUseCase;
import com.homework.mpay.account.application.port.in.UsePointUseCase;
import com.homework.mpay.common.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "포인트", description = "포인트 거래 API")
public class PointController {
    private final EarnPointUseCase earnPointUseCase;
    private final UsePointUseCase usePointUseCase;

    @PostMapping(path = "homework/point/earn", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "포인트 적립", description = "특정 계좌에 포인트를 적립하는 API 입니다.")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "201", description = "적립 완료"),
                @ApiResponse(
                        responseCode = "400",
                        description = "유효하지 않은 요청 정보",
                        content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
                @ApiResponse(
                        responseCode = "500",
                        description = "알수 없는 오류 발생",
                        content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
            })
    ResponseEntity<Void> postEarnPoint(@Valid @RequestBody EarnPointRequest request) {
        earnPointUseCase.earnPoint(
                request.getUserId(), request.getAmount(), request.getPointTypeCode());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "homework/point/earn/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "포인트 적립 취소", description = "특정 계좌의 특정 포인트를 적립 취소하는 API 입니다.")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "적립 취소 완료"),
                @ApiResponse(
                        responseCode = "400",
                        description = "유효하지 않은 요청 정보",
                        content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
                @ApiResponse(
                        responseCode = "500",
                        description = "알수 없는 오류 발생",
                        content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
            })
    ResponseEntity<Void> deleteEarnPoint(@Valid @RequestBody CancelEarnPointRequest request) {
        earnPointUseCase.cancelEarnPoint(request.getUserId(), request.getPointId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "homework/point/use", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "포인트 사용", description = "주문에 의한 포인트 사용 API 입니다.")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "사용 완료"),
                @ApiResponse(
                        responseCode = "400",
                        description = "유효하지 않은 요청 정보",
                        content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
                @ApiResponse(
                        responseCode = "500",
                        description = "알수 없는 오류 발생",
                        content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
            })
    ResponseEntity<Void> postUsePoint(@Valid @RequestBody UsePointRequest request) {
        usePointUseCase.usePoint(request.getUserId(), request.getAmount(), request.getOrderId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "homework/point/use/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "포인트 사용 취소", description = "특정 주문에 대한 포인트 사용 취소 API 입니다.")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "사용 취소 완료"),
                @ApiResponse(
                        responseCode = "400",
                        description = "유효하지 않은 요청 정보",
                        content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
                @ApiResponse(
                        responseCode = "500",
                        description = "알수 없는 오류 발생",
                        content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
            })
    ResponseEntity<Void> deleteUsePoint(@Valid @RequestBody UsePointRequest request) {
        usePointUseCase.useCancelPoint(request.getUserId(), request.getAmount(), request.getOrderId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
