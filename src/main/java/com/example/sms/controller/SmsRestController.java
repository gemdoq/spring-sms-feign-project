package com.example.sms.controller;

import com.example.sms.dto.request.SendSmsRequest;
import com.example.sms.dto.request.VerifySmsRequest;
import com.example.sms.dto.response.SmsResponse;
import com.example.sms.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
@Tag(name = "SMS 인증 API", description = "SMS 인증번호 발송 및 검증 API")
public class SmsRestController {

    private final SmsService smsService;

    @PostMapping("/send")
    @Operation(summary = "인증번호 발송", description = "입력한 휴대폰 번호로 6자리 인증번호를 발송합니다")
    public ResponseEntity<SmsResponse> sendVerificationCode(
            @Valid @RequestBody SendSmsRequest request) {
        SmsResponse response = smsService.sendVerificationCode(request.getPhoneNumber());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    @Operation(summary = "인증번호 검증", description = "발송된 인증번호가 일치하는지 검증합니다")
    public ResponseEntity<SmsResponse> verifyCode(
            @Valid @RequestBody VerifySmsRequest request) {
        SmsResponse response = smsService.verifyCode(request.getPhoneNumber(), request.getCode());
        return ResponseEntity.ok(response);
    }
}
