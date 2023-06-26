package com.test.test.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidationService {

    private final String MAIL_REGEX = "^(.+)@(\\S+)$";
    private final Pattern mailPattern = Pattern.compile(MAIL_REGEX);
    public boolean isValidEmail(String email) {
        return mailPattern.matcher(email).matches();
    }

}
