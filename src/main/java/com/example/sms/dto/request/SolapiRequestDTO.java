package com.example.sms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SolapiRequestDTO {

    private List<SolapiMessageDTO> messages;

    public static SolapiRequestDTO of(SolapiMessageDTO message) {
        return new SolapiRequestDTO(Collections.singletonList(message));
    }
}
