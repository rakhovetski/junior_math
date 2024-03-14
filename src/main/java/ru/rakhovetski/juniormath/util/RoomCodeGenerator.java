package ru.rakhovetski.juniormath.util;

import java.util.Random;

public class RoomCodeGenerator {

    private static final String CODE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Integer CODE_LENGTH = 6;


    public static String generateRoomCode() {
        StringBuilder code = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CODE_CHARS.length());
            code.append(CODE_CHARS.charAt(index));
        }

        return code.toString();
    }
}
