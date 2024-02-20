package com.xtu.plugin.github.utils;

import com.intellij.openapi.diagnostic.Logger;

public class LogUtils {

    private static final Logger LOG = Logger.getInstance("Github Trending -> ");

    public static void error(String entryPoint, Exception exception) {
        LOG.error(entryPoint + " : " + exception.getMessage());
    }
}
