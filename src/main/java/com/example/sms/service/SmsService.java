package com.example.sms.service;

import com.example.sms.client.SolapiClient;
import com.example.sms.dto.request.SolapiMessageDTO;
import com.example.sms.dto.request.SolapiRequestDTO;
import com.example.sms.dto.response.SmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SmsService {

    private final SolapiClient solapiClient;
    private final Map<String, VerificationInfo> verificationStore = new ConcurrentHashMap<>();
    private static final SecureRandom RANDOM = new SecureRandom();

    @Value("${solapi.sender-phone}")
    private String senderPhone;

    @Value("${sms.verification.expiry-minutes}")
    private int expiryMinutes;

    public SmsService(SolapiClient solapiClient) {
        this.solapiClient = solapiClient;
    }

    public SmsResponse sendVerificationCode(String phoneNumber) {
        log.info("인증번호 발송 요청: {}", phoneNumber);

        String code = generateVerificationCode();
        String message = "[SMS인증] 인증번호: " + code + " (" + expiryMinutes + "분간 유효)";

        try {
            SolapiMessageDTO smsMessage = SolapiMessageDTO.sms(phoneNumber, senderPhone, message);
            SolapiRequestDTO request = SolapiRequestDTO.of(smsMessage);

            Map<String, Object> response = solapiClient.sendMessage(request);
            log.debug("Solapi 응답: {}", response);

            verificationStore.put(phoneNumber, new VerificationInfo(code, LocalDateTime.now().plusMinutes(expiryMinutes)));
            log.info("인증번호 발송 성공: {}", phoneNumber);

            return SmsResponse.success("인증번호가 발송되었습니다");
        } catch (Exception e) {
            log.error("SMS 발송 실패: {}", e.getMessage());
            return SmsResponse.failure("SMS 발송에 실패했습니다: " + e.getMessage());
        }
    }

    public SmsResponse verifyCode(String phoneNumber, String code) {
        log.info("인증번호 검증 요청: {}", phoneNumber);

        VerificationInfo info = verificationStore.get(phoneNumber);

        if (info == null) {
            log.warn("인증번호 없음: {}", phoneNumber);
            return SmsResponse.failure("인증번호를 먼저 요청해주세요");
        }

        if (info.isExpired()) {
            log.warn("인증번호 만료: {}", phoneNumber);
            verificationStore.remove(phoneNumber);
            return SmsResponse.failure("인증번호가 만료되었습니다");
        }

        if (!info.code().equals(code)) {
            log.warn("인증번호 불일치: {}", phoneNumber);
            return SmsResponse.failure("인증번호가 일치하지 않습니다");
        }

        verificationStore.remove(phoneNumber);
        log.info("인증 성공: {}", phoneNumber);
        return SmsResponse.success("인증이 완료되었습니다");
    }

    private String generateVerificationCode() {
        int code = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(code);
    }

    private record VerificationInfo(String code, LocalDateTime expiryTime) {
        boolean isExpired() {
            return LocalDateTime.now().isAfter(expiryTime);
        }
    }
}
