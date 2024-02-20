package com.xtu.plugin.github.ui;

import com.intellij.ide.BrowserUtil;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.xtu.plugin.github.TrendingDataFetcher;
import com.xtu.plugin.github.constant.UrlConstant;
import com.xtu.plugin.github.entity.TrendingItem;
import com.xtu.plugin.github.render.TrendingCellRender;
import com.xtu.plugin.github.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TrendingListView extends JPanel {

    private String language = "";
    private final TrendingDataFetcher fetcher;
    private final DefaultListModel<TrendingItem> listModel;
    private final JBList<TrendingItem> listView;

    public TrendingListView() {
        this.fetcher = new TrendingDataFetcher();
        this.setLayout(new BorderLayout());
        this.listView = new JBList<>();
        this.listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.listModel = new DefaultListModel<>();
        this.listView.setModel(this.listModel);
        this.listView.setCellRenderer(new TrendingCellRender());
        this.listView.addListSelectionListener(e -> {
            int index = this.listView.getSelectedIndex();
            if (index == -1) return;
            String url = this.listModel.get(index).getUrl();
            if (StringUtils.isEmpty(url)) return;
            BrowserUtil.open(url);
        });
        this.add(new JBScrollPane(this.listView), BorderLayout.CENTER);
    }

    public void setLanguage(String language) {
        if (StringUtils.equals(language, this.language)) return;
        this.language = language;
        refreshData();
    }

    public void refreshData() {
        this.fetcher.fetch(UrlConstant.TRENDING_URL + language, new TrendingDataFetcher.OnResultListener() {
            @Override
            public void onSuccess(@NotNull List<TrendingItem> list) {
                listModel.clear();
                listModel.addAll(list);
            }

            @Override
            public void onFail(@NotNull String error) {
                listModel.clear();
                listView.setEmptyText(error);
            }
        });
    }
}
