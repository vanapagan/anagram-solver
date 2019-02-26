package com.palo.kristo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        final String fileName = args[0];
        final String keyword = args[1].trim().toLowerCase();

        final int keywordLength = keyword.length();
        final String keywordSorted = sortCharsInString(keyword);

        Predicate<String> isAnagram = s -> (s.length() == keywordLength && sortCharsInString(s).equals(keywordSorted)
                && !s.toLowerCase().equals(keyword));

        String result = "";
        try (Stream<String> stream = Files.lines(Paths.get(fileName), StandardCharsets.ISO_8859_1)) {
            result = stream
                    .filter(isAnagram)
                    .collect(Collectors.joining(","));
        } catch (IOException e) {
            e.printStackTrace();
        }

        long timeInMicroseconds = (System.nanoTime() - startTime) / 1000;
        System.out.println(timeInMicroseconds + "," + (!result.isEmpty() ? result : "Could not find any anagrams for the keyword \'" + keyword + "\'"));
    }

    private static String sortCharsInString(String s) {
        char[] contentArray = s.trim().toLowerCase().toCharArray();
        Arrays.sort(contentArray);
        return new String(contentArray);
    }

}