package com.mphong.EMWebAPI.Models.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTPValidiation {
    private String email;
    private String otp;
}
