package com.example.sms.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolapiResponseDTO {

    private String groupId;
    private String messageId;
    private String statusCode;
    private String statusMessage;
    private String to;
    private String from;
    private String type;
    private String country;

    public boolean isSuccess() {
        return "2000".equals(statusCode);
    }
}
