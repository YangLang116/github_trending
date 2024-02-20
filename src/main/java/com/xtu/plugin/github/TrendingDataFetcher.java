package com.xtu.plugin.github;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.xtu.plugin.github.entity.TrendingItem;
import com.xtu.plugin.github.utils.CloseUtils;
import com.xtu.plugin.github.utils.CollectionUtils;
import com.xtu.plugin.github.utils.LogUtils;
import com.xtu.plugin.github.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TrendingDataFetcher {

    private final TrendingDataParser parser;

    public TrendingDataFetcher() {
        this.parser = new TrendingDataParser();
    }

    public void fetch(@NotNull String url, OnResultListener listener) {
        final Application application = ApplicationManager.getApplication();
        application.executeOnPooledThread(() -> {
            String html = downloadHtml(url);
            if (StringUtils.isEmpty(html)) {
                application.invokeLater(() -> listener.onFail("Network is invalid"));
            } else {
                List<TrendingItem> trendingItems = parser.parseTrendingList(html);
                if (CollectionUtils.isEmpty(trendingItems)) {
                    application.invokeLater(() -> listener.onFail("Data parse fail"));
                } else {
                    application.invokeLater(() -> listener.onSuccess(trendingItems));
                }
            }
        });
    }

    private String downloadHtml(@NotNull String htmlUrl) {
        BufferedReader reader = null;
        try {
            URL url = new URL(htmlUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(20 * 1000);
            connection.setReadTimeout(20 * 1000);
            int code = connection.getResponseCode();
            if (code != 200) return null;
            String line;
            StringBuilder htmlContentSb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            while ((line = reader.readLine()) != null) {
                htmlContentSb.append(line);
            }
            return htmlContentSb.toString();
        } catch (Exception e) {
            LogUtils.error(TrendingDataFetcher.class.getSimpleName(), e);
            return null;
        } finally {
            CloseUtils.close(reader);
        }
    }

    public interface OnResultListener {

        void onSuccess(@NotNull List<TrendingItem> list);

        void onFail(@NotNull String error);
    }
}
