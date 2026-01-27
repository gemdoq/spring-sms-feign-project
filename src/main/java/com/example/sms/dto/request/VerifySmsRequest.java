package com.example.sms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "SMS 인증번호 검증 요청")
public class VerifySmsRequest {

    @NotBlank(message = "휴대폰 번호는 필수입니다")
    @Pattern(regexp = "^01[0-9]{8,9}$", message = "올바른 휴대폰 번호 형식이 아닙니다 (예: 01012345678)")
    @Schema(description = "휴대폰 번호 (하이픈 없이)", example = "01012345678")
    private String phoneNumber;

    @NotBlank(message = "인증번호는 필수입니다")
    @Size(min = 6, max = 6, message = "인증번호는 6자리입니다")
    @Schema(description = "6자리 인증번호", example = "123456")
    private String code;
}
