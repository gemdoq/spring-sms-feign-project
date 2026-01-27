package com.example.sms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SolapiMessageDTO {

    private String to;
    private String from;
    private String text;
    private String type;

    public static SolapiMessageDTO sms(String to, String from, String text) {
        return new SolapiMessageDTO(to, from, text, "SMS");
    }
}
