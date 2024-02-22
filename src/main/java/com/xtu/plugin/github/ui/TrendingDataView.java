package com.xtu.plugin.github.ui;

import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBUI;
import com.xtu.plugin.github.TrendingDataFetcher;
import com.xtu.plugin.github.constant.UrlConstant;
import com.xtu.plugin.github.entity.TrendingItem;
import com.xtu.plugin.github.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TrendingDataView extends JPanel {

    private String language = "";
    private final TrendingDataFetcher fetcher;
    private final Box listView;
    private final JLabel tipView;

    public TrendingDataView() {
        this.setLayout(new BorderLayout());
        this.fetcher = new TrendingDataFetcher();
        this.listView = Box.createVerticalBox();
        this.tipView = new JLabel();
        this.tipView.setHorizontalAlignment(SwingConstants.CENTER);
        this.tipView.setFont(new Font(null, Font.PLAIN, JBUI.scaleFontSize(15f)));
    }

    public void setLanguage(String language) {
        if (StringUtils.equals(language, this.language)) return;
        this.language = language;
        refreshData();
    }

    public void refreshData() {
        showTipView("Loading Data...");
        this.fetcher.fetch(UrlConstant.TRENDING_URL + language, new TrendingDataFetcher.OnResultListener() {
            @Override
            public void onSuccess(@NotNull List<TrendingItem> list) {
                showListView(list);
            }

            @Override
            public void onFail(@NotNull String error) {
                showTipView(error);
            }
        });
    }

    private void showListView(@NotNull List<TrendingItem> list) {
        removeAll();
        this.listView.removeAll();
        for (TrendingItem item : list) {
            TrendingItemView itemView = new TrendingItemView();
            itemView.bindData(item);
            listView.add(itemView);
        }
        add(new JBScrollPane(this.listView), BorderLayout.CENTER);
    }

    private void showTipView(@NotNull String error) {
        removeAll();
        this.tipView.setText(error);
        add(this.tipView, BorderLayout.CENTER);
    }
}
