package com.test.test.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodingSHA256 {

    private static final String SECRET_KEY = "SoftClub in secret";

    public static byte[] getSHA(String password) throws NoSuchAlgorithmException {
        MessageDigest encrypt = MessageDigest.getInstance("SHA-256");
        encrypt.update(SECRET_KEY.getBytes());
        return encrypt.digest(password.getBytes(StandardCharsets.UTF_8));
    }

    public static String encodePassword(String password) throws NoSuchAlgorithmException {
        BigInteger bigInteger = new BigInteger(1, EncodingSHA256.getSHA(password));

        StringBuilder encoded = new StringBuilder(bigInteger.toString(16));

        while (encoded.length() < 32) {
            encoded.insert(0, '0');
        }

        return encoded.toString();
    }
}
