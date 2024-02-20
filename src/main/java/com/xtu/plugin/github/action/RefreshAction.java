package com.xtu.plugin.github.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.xtu.plugin.github.ui.TrendingDataView;
import org.jetbrains.annotations.NotNull;

public class RefreshAction extends AnAction {

    private final TrendingDataView dataView;

    public RefreshAction(@NotNull TrendingDataView dataView) {
        super("Refresh", "Refresh", AllIcons.Actions.Refresh);
        this.dataView = dataView;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        this.dataView.refreshData();
    }
}
