package com.example.sms.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "SMS API 응답")
public class SmsResponse {

    @Schema(description = "성공 여부", example = "true")
    private boolean success;

    @Schema(description = "응답 메시지", example = "인증번호가 발송되었습니다")
    private String message;

    public static SmsResponse success(String message) {
        return new SmsResponse(true, message);
    }

    public static SmsResponse failure(String message) {
        return new SmsResponse(false, message);
    }
}
