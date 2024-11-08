package com.homework.mpay.account.adopter.in;

import com.homework.mpay.account.adopter.in.dto.AccountDtoMapper;
import com.homework.mpay.account.adopter.in.dto.GetAccountResponse;
import com.homework.mpay.account.application.port.in.InquiryAccountUseCase;
import com.homework.mpay.account.domain.Account;
import com.homework.mpay.common.exception.ErrorConstant;
import com.homework.mpay.common.exception.ErrorResponse;
import com.homework.mpay.common.exception.InvalidParameterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "계좌", description = "계좌 조회 API")
public class AccountController {
    private final InquiryAccountUseCase inquiryAccountUseCase;
    private final AccountDtoMapper accountDtoMapper;

    @GetMapping(path = "homework/account/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "계좌 정보 조회", description = "특정 사용자의 계좌 정보를 조회 합니다.")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "성공"),
                @ApiResponse(
                        responseCode = "400",
                        description = "유효하지 않은 요청 정보",
                        content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
                @ApiResponse(
                        responseCode = "500",
                        description = "알수 없는 오류 발생",
                        content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
            })
    ResponseEntity<GetAccountResponse> getAccount(@PathVariable String userId) {
        if (!StringUtils.hasText(userId)) {
            throw new InvalidParameterException(ErrorConstant.INVALID_REQUEST_DATA);
        }

        Account account = inquiryAccountUseCase.inquiryAccount(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(accountDtoMapper.mapToGetAccountResponse(account));
    }
}
