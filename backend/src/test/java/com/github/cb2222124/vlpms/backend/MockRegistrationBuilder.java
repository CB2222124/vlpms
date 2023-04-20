package com.github.cb2222124.vlpms.backend;

import java.io.*;
import java.util.Random;

/**
 * Development test utility class for building various types of mock registrations. Dateless, Northern Ireland and
 * diplomatic plates have not been implemented.
 */
public class MockRegistrationBuilder {

    public static void main(String[] args) throws IOException {
        try (FileOutputStream current = new FileOutputStream("mock-registrations.txt", true);
             FileOutputStream prefix = new FileOutputStream("mock-registrations.txt", true);
             FileOutputStream suffix = new FileOutputStream("mock-registrations.txt", true)) {
            MockRegistrationBuilder mrb = new MockRegistrationBuilder();
            mrb.writeCurrent(current, 1000000);
            mrb.writePrefix(prefix, 1000000);
            mrb.writeSuffix(suffix, 1000000);
        }
    }

    /**
     * Writes mock registrations to an output stream following the current pattern.
     * 2 letters, 2 numbers, 3 letters.
     * ^[A-Z]{2}[0-9]{2}[A-Z]{3}$
     *
     * @param out   Output stream.
     * @param count Number of registrations to be generated.
     * @throws IOException I/O error.
     */
    public void writeCurrent(OutputStream out, int count) throws IOException {
        try (OutputStreamWriter osw = new OutputStreamWriter(out);
             BufferedWriter bw = new BufferedWriter(osw)) {
            Random random = new Random();
            for (int i = 0; i < count; i++) {
                char char1 = (char) (random.nextInt(26) + 'A');
                char char2 = (char) (random.nextInt(26) + 'A');
                char char3 = (char) (random.nextInt(26) + 'A');
                char char4 = (char) (random.nextInt(26) + 'A');
                char char5 = (char) (random.nextInt(26) + 'A');
                String num1 = String.valueOf(random.nextInt(10));
                String num2 = String.valueOf(random.nextInt(10));
                bw.append(char1).append(char2).append(num1).append(num2).append(char3).append(char4).append(char5).append("\n");
            }
        }
    }

    /**
     * Writes mock registrations to an output stream following the prefix pattern.
     * 1 letter, 1 to 3 numbers, 3 letters.
     * ^[A-Z][0-9]{1,3}[A-Z]{3}$
     *
     * @param out   Output stream.
     * @param count Number of registrations to be generated.
     * @throws IOException I/O error.
     */
    public void writePrefix(OutputStream out, int count) throws IOException {
        try (OutputStreamWriter osw = new OutputStreamWriter(out);
             BufferedWriter bw = new BufferedWriter(osw)) {
            Random random = new Random();
            for (int i = 0; i < count; i++) {
                char char1 = (char) (random.nextInt(26) + 'A');
                char char2 = (char) (random.nextInt(26) + 'A');
                char char3 = (char) (random.nextInt(26) + 'A');
                char char4 = (char) (random.nextInt(26) + 'A');
                int numberLength = random.nextInt(1, 4); //Produce more even spread of 1/2/3-digit numbers.
                String nums = String.valueOf(random.nextInt((int) Math.pow(10, numberLength)));
                bw.append(char1).append(nums).append(char2).append(char3).append(char4).append("\n");
            }
        }
    }

    /**
     * Writes mock registrations to an output stream following the suffix pattern.
     * 3 letters, 1 to 3 numbers, 1 letter.
     * ^[A-Z]{3}[0-9]{1,3}[A-Z]$
     *
     * @param out   Output stream.
     * @param count Number of registrations to be generated.
     * @throws IOException I/O error.
     */
    public void writeSuffix(OutputStream out, int count) throws IOException {
        try (OutputStreamWriter osw = new OutputStreamWriter(out);
             BufferedWriter bw = new BufferedWriter(osw)) {
            Random random = new Random();
            for (int i = 0; i < count; i++) {
                char char1 = (char) (random.nextInt(26) + 'A');
                char char2 = (char) (random.nextInt(26) + 'A');
                char char3 = (char) (random.nextInt(26) + 'A');
                char char4 = (char) (random.nextInt(26) + 'A');
                int numberLength = random.nextInt(1, 4); //Produce more even spread of 1/2/3-digit numbers.
                String nums = String.valueOf(random.nextInt((int) Math.pow(10, numberLength)));
                bw.append(char1).append(char2).append(char3).append(nums).append(char4).append("\n");
            }
        }
    }
}
