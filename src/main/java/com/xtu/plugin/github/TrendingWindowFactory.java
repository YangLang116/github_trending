package com.xtu.plugin.github;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.xtu.plugin.github.action.LanguageAction;
import com.xtu.plugin.github.action.RefreshAction;
import com.xtu.plugin.github.ui.TrendingListView;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class TrendingWindowFactory implements ToolWindowFactory, DumbAware {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentManager contentManager = toolWindow.getContentManager();
        ContentFactory factory = contentManager.getFactory();
        TrendingListView trendingListView = new TrendingListView();
        Content content = factory.createContent(trendingListView, "", true);
        contentManager.addContent(content);
        toolWindow.setAutoHide(true);
        toolWindow.setTitleActions(createTitleActions(trendingListView));
        trendingListView.refreshData();
    }

    private List<AnAction> createTitleActions(@NotNull TrendingListView listView) {
        return Arrays.asList(new RefreshAction(listView), new LanguageAction(listView));
    }
}
