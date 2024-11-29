package com.liraymond04.deepl;

import java.time.Instant;
import java.util.Random;

public class Hacks {

    public static long generateTimestamp(String[] sentences) {
        long now = Instant.now().toEpochMilli();
        int iCount = 1;
        for (String sentence : sentences) {
            iCount += sentence.length() - sentence.replace("i", "").length();
        }
        return now + (iCount - now % iCount);
    }

    public static int generateId() {
        return new Random().nextInt(100_000_000) + 1_000_000;
    }
}
