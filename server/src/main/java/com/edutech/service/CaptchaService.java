package com.edutech.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.edutech.dto.CaptchaResponse;

@Service
public class CaptchaService {

    private static class CaptchaData {

        private String answer;
        private LocalDateTime expiry;

        CaptchaData(String answer, LocalDateTime expiry) {
            this.answer = answer;
            this.expiry = expiry;
        }
    }

    private final Map<String, CaptchaData> captchaStore = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public CaptchaResponse generateCaptcha() {

        int firstNumber = random.nextInt(9) + 1;
        int secondNumber = random.nextInt(9) + 1;

        String captchaId = UUID.randomUUID().toString();
        String question = firstNumber + " + " + secondNumber + " = ?";
        String answer = String.valueOf(firstNumber + secondNumber);

        captchaStore.put(
                captchaId,
                new CaptchaData(answer, LocalDateTime.now().plusMinutes(5))
        );

        return new CaptchaResponse(captchaId, question);
    }

    public boolean validateCaptcha(String captchaId, String captchaAnswer) {

        if (captchaId == null || captchaAnswer == null) {
            return false;
        }

        CaptchaData captchaData = captchaStore.get(captchaId);

        if (captchaData == null) {
            return false;
        }

        if (captchaData.expiry.isBefore(LocalDateTime.now())) {
            captchaStore.remove(captchaId);
            return false;
        }

        boolean valid = captchaData.answer.equals(captchaAnswer.trim());

        captchaStore.remove(captchaId);

        return valid;
    }
}