package com.xtu.plugin.github.utils;

import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtils {

    public static void close(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                //ignore
            }
        }
    }
}
