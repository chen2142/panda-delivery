package com.clx.utils;

import java.util.Random;

/**
 * Randomly generate a captcha tool class
 */
public class ValidateCodeUtils {
    /**
     * Randomly generate a verification code
     * @param length The length can be 4 or 6 digits
     * @return
     */
    public static Integer generateValidateCode(int length){
        Integer code =null;
        if(length == 4){
            code = new Random().nextInt(9999);//Generate random numbers, up to 9999
            if(code < 1000){
                code = code + 1000;//The random number is guaranteed to be 4 digits
            }
        }else if(length == 6){
            code = new Random().nextInt(999999);//Generate random numbers, up to 999999
            if(code < 100000){
                code = code + 100000;//The random number is guaranteed to be 6 digits
            }
        }else{
            throw new RuntimeException("Only 4-digit or 6-digit verification codes can be generated");
        }
        return code;
    }

    /**
     * A verification code of a string of specified length is randomly generated
     * @param length length
     * @return
     */
    public static String generateValidateCode4String(int length){
        Random rdm = new Random();
        String hash1 = Integer.toHexString(rdm.nextInt());
        String capstr = hash1.substring(0, length);
        return capstr;
    }
}
