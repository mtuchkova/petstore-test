package utils;

import org.apache.commons.lang3.RandomStringUtils;

public class PetStoreUtil {

    public static int getRandomNumber() {
        int min = 1;
        int max = 1000;
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public static String getRandomString() {
        int maxLength = 11;
        return RandomStringUtils.randomAlphabetic(maxLength);
    }
}
