package com.github.foodie.utils;

import java.util.Random;

public class OtpUtil {

    public static String getRandomOtp() {
        Random random = new Random();
        int otpInt = random.nextInt(10000);  //0 to 9999
        return String.format("%04d", otpInt);
    }
}
