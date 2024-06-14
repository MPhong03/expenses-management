package com.mphong.EMWebAPI.Interfaces;

import com.mphong.EMWebAPI.Models.Dtos.EmailDetail;

public interface EmailService {
    String sendMail(EmailDetail details);
}
