package com.example.sms.client;

import com.example.sms.dto.request.SolapiRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        name = "solapiClient",
        url = "${solapi.api-url}",
        configuration = SolapiAuthInterceptor.class
)
public interface SolapiClient {

    @PostMapping("/messages/v4/send")
    Map<String, Object> sendMessage(@RequestBody SolapiRequestDTO request);
}
