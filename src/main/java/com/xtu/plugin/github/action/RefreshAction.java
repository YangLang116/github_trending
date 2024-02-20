package com.xtu.plugin.github.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.xtu.plugin.github.ui.TrendingListView;
import org.jetbrains.annotations.NotNull;

public class RefreshAction extends AnAction {

    private final TrendingListView listView;

    public RefreshAction(@NotNull TrendingListView listView) {
        super(AllIcons.Actions.Refresh);
        this.listView = listView;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        this.listView.refreshData();
    }
}
