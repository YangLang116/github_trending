package com.xtu.plugin.github.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.xtu.plugin.github.ui.TrendingDataView;
import com.xtu.plugin.github.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class LanguageAction extends AnAction {
    private static final String[] sLanguage = new String[]{
            "C", "C++", "C#",
            "Dart", "Go",
            "Java", "JavaScript",
            "Kotlin", "Python",
            "Php", "Ruby", "Swift"};
    private final TrendingDataView dataView;

    public LanguageAction(@NotNull TrendingDataView dataView) {
        super("Select Language", "Select language", AllIcons.Actions.ShortcutFilter);
        this.dataView = dataView;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        String select = Messages.showEditableChooseDialog("", "Select Language", null, sLanguage, sLanguage[0], null);
        if (StringUtils.isEmpty(select)) return;
        String language = select.toLowerCase(Locale.ROOT).replace("#", "%23");
        this.dataView.setLanguage(language);
    }
}
