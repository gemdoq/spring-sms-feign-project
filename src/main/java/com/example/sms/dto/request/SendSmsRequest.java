package com.example.sms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "SMS 인증번호 발송 요청")
public class SendSmsRequest {

    @NotBlank(message = "휴대폰 번호는 필수입니다")
    @Pattern(regexp = "^01[0-9]{8,9}$", message = "올바른 휴대폰 번호 형식이 아닙니다 (예: 01012345678)")
    @Schema(description = "수신자 휴대폰 번호 (하이픈 없이)", example = "01012345678")
    private String phoneNumber;
}
