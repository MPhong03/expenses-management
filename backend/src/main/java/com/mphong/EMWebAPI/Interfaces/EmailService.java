package com.mphong.EMWebAPI.Interfaces;

import com.mphong.EMWebAPI.Models.EmailDetail;

public interface EmailService {
    String sendMail(EmailDetail details);
}
