package com.mphong.EMWebAPI.Models.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPassword {
    private String email;
    private String newPassword;
}
