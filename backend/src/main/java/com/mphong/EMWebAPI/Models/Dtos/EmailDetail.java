package com.mphong.EMWebAPI.Models.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetail {
    private String recipient;
    private String subject;
    private String msgBody;
}
