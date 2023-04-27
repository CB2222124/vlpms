package com.github.cb2222124.vlpms.backend;

import java.io.*;
import java.util.*;

public class RegistrationSearcher {

    private record Match(String line, int distance) {
    }

    public static void main(String[] args) throws IOException {
        List<String> matches = new RegistrationSearcher().getMatches("DAVID", 10, new FileInputStream("mock-registrations.txt"));
        System.out.println(matches);
    }

    /**
     * Creates an ordered list of registrations based on approximate matches using a modified levenshtein
     * distance algorithm which TODO: considers weighted number substitutions for letters.
     *
     * @param inputString Input to be matched, truncated to seven characters (Maximum registration length).
     * @param numMatches  The number of registration matches to fetch.
     * @param inputStream Input stream for existing registrations.
     * @return List of ordered matched strings.
     */
    public List<String> getMatches(String inputString, int numMatches, InputStream inputStream) throws IOException {
        List<Match> matches = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int distance = levenshteinDistance(inputString.toCharArray(), line.toCharArray());
                if (matches.size() == 0) matches.add(new Match(line, distance));
                for (int i = 0; i < matches.size(); i++) {
                    if (distance < matches.get(i).distance()) {
                        matches.add(i, new Match(line, distance));
                        if (matches.size() > numMatches) matches.remove(matches.size() - 1);
                        break;
                    }
                }
            }
        }
        List<String> result = new ArrayList<>();
        for (Match match : matches) {
            result.add(match.line());
        }
        return result;
    }

    private int levenshteinDistance(char[] s1, char[] s2) {
        int[][] distance = new int[s1.length + 1][s2.length + 1];
        for (int i = 0; i < s1.length + 1; i++) distance[i][0] = i;
        for (int j = 0; j < s2.length + 1; j++) distance[0][j] = j;

        for (int i = 1; i < s1.length + 1; i++) {
            for (int j = 1; j < s2.length + 1; j++) {
                int distanceDel = distance[i - 1][j] + 1;
                int distanceIns = distance[i][j - 1] + 1;
                int distanceSub = distance[i - 1][j - 1];
                if (s1[i - 1] != s2[j - 1]) distanceSub += 1;
                distance[i][j] = Math.min(Math.min(distanceDel, distanceIns), distanceSub);
            }
        }
        return distance[s1.length][s2.length];
    }
}
