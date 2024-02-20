package com.xtu.plugin.github.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class StringUtils {

    public static boolean equals(@Nullable String str1, @Nullable String str2) {
        return Objects.equals(str1, str2);
    }

    public static boolean isEmpty(@Nullable String str) {
        return str == null || str.isEmpty();
    }

    public static String fix(@Nullable String txt, @NotNull String defaultTxt) {
        if (isEmpty(txt)) return defaultTxt;
        return txt;
    }
}
