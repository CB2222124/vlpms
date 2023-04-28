package com.github.cb2222124.vlpms.backend;

import java.io.*;
import java.util.Random;

/**
 * Development test utility class for building various types of mock registrations. Dateless, Northern Ireland and
 * diplomatic plates have not been implemented.
 * NOTE: This implementation is vulnerable to generating duplicate registrations.
 */
@SuppressWarnings("unused")
public class MockRegistrationBuilder {

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
                String registration = String.valueOf(char1) + char2 + num1 + num2 + char3 + char4 + char5;
                int price = random.nextInt(10000, 100000);
                bw.append("SELECT list_new_registration('")
                        .append(registration).append("',")
                        .append("'Current'").append(",")
                        .append(String.valueOf(price))
                        .append(");");
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
                String registration = char1 + nums + char2 + char3 + char4;
                int price = random.nextInt(10000, 100000);
                bw.append("SELECT list_new_registration('")
                        .append(registration).append("',")
                        .append("'Prefix'").append(",")
                        .append(String.valueOf(price))
                        .append(");");
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
                String registration = String.valueOf(char1) + char2 + char3 + nums + char4;
                int price = random.nextInt(10000, 100000);
                bw.append("SELECT list_new_registration('")
                        .append(registration).append("',")
                        .append("'Suffix'").append(",")
                        .append(String.valueOf(price))
                        .append(");");
            }
        }
    }
}
