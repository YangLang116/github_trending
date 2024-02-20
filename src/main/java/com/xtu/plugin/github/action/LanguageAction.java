package com.xtu.plugin.github.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.messages.MessagesService;
import com.xtu.plugin.github.ui.TrendingDataView;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LanguageAction extends AnAction {
    private final TrendingDataView dataView;
    private final Map<String, String> languageMap = new HashMap<>();

    public LanguageAction(@NotNull TrendingDataView dataView) {
        super("Select Language", "Select language", AllIcons.Actions.ShortcutFilter);
        this.dataView = dataView;
        this.languageMap.put("Python", "python");
        this.languageMap.put("C", "c");
        this.languageMap.put("C++", "c++");
        this.languageMap.put("C#", "c%23");
        this.languageMap.put("JavaScript", "javascript");
        this.languageMap.put("Swift", "swift");
        this.languageMap.put("Ruby", "ruby");
        this.languageMap.put("Go", "go");
        this.languageMap.put("PHP", "php");
        this.languageMap.put("kotlin", "kotlin");
        this.languageMap.put("Dart", "dart");
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        String[] keyList = this.languageMap.keySet().toArray(new String[0]);
        MessagesService messagesService = MessagesService.getInstance();
        int selectIndex = messagesService.showChooseDialog(project, this.dataView,
                "", "Select Language",
                keyList, keyList[0], null);
        if (selectIndex == -1) return;
        String selectKey = keyList[selectIndex];
        this.dataView.setLanguage(this.languageMap.get(selectKey));
    }
}
