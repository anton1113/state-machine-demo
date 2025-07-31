package com.arash.edu.statemachinedemo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnakeCaseUtil {

    private static final String WORD_REGEX = "[A-Z][a-z]+|[A-Z]+(?=[A-Z][a-z])|[A-Z]+|[a-z]+|[0-9]+";

    public static String toSnakeCase(@NonNull String text) {
        Pattern regex = Pattern.compile(WORD_REGEX);
        Matcher matcher = regex.matcher(text);

        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group().toLowerCase());
        }

        return String.join("_", words);
    }
}
