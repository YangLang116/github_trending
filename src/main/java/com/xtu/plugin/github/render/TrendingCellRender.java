package com.xtu.plugin.github.render;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import com.xtu.plugin.github.entity.TrendingItem;
import com.xtu.plugin.github.ui.TrendingItemView;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TrendingCellRender extends TrendingItemView implements ListCellRenderer<TrendingItem> {

    private final Border defaultBorder = JBUI.Borders.compound(
            JBUI.Borders.customLineBottom(JBColor.GRAY),
            JBUI.Borders.empty(16));
    private final Border selectBorder = JBUI.Borders.compound(
            JBUI.Borders.customLine(JBColor.BLUE),
            JBUI.Borders.empty(16));

    @Override
    public Component getListCellRendererComponent(JList<? extends TrendingItem> list, TrendingItem item, int index, boolean isSelected, boolean cellHasFocus) {
        this.setBorder(isSelected ? selectBorder : defaultBorder);
        this.bindData(item);
        return this;
    }
}
