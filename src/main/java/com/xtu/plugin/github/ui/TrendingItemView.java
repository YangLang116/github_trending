package com.xtu.plugin.github.ui;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import com.xtu.plugin.github.entity.TrendingItem;
import com.xtu.plugin.github.utils.StringUtils;
import icons.PluginIcons;

import javax.swing.*;
import java.awt.*;

public class TrendingItemView extends JComponent {

    private static final int sIconTextGap = 5;
    private static final int sFootItemMargin = 15;
    private static final float sCommonTextSize = 12f;
    private static final float sHeaderTextSize = 14f;

    private JLabel userNameView;
    private JLabel repoNameView;
    private JTextArea descView;
    private JLabel languageView;
    private JLabel starView;
    private JLabel forkView;
    private JLabel dailyStarView;

    public TrendingItemView() {
        setLayout(new BorderLayout());
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(createHeadView());
        verticalBox.add(createDescView());
        verticalBox.add(createFootView());
        add(verticalBox, BorderLayout.CENTER);
    }

    private JComponent createHeadView() {
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        this.userNameView = new JLabel();
        this.userNameView.setIcon(PluginIcons.book);
        this.userNameView.setIconTextGap(sIconTextGap);
        this.userNameView.setForeground(JBColor.BLUE);
        this.userNameView.setFont(new Font(null, Font.PLAIN, JBUI.scaleFontSize(sCommonTextSize)));
        horizontalBox.add(this.userNameView);
        this.repoNameView = new JLabel();
        this.repoNameView.setForeground(JBColor.BLUE);
        this.repoNameView.setFont(new Font(null, Font.BOLD, JBUI.scaleFontSize(sHeaderTextSize)));
        horizontalBox.add(this.repoNameView);
        return horizontalBox;
    }

    private JComponent createDescView() {
        this.descView = new JTextArea();
        this.descView.setEditable(false);
        this.descView.setLineWrap(true);
        this.descView.setWrapStyleWord(true);
        this.descView.setBackground(new Color(0, 0, 0, 0));
        this.descView.setBorder(JBUI.Borders.empty(10, 21, 10, 10));
        this.descView.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        this.descView.setFont(new Font(null, Font.PLAIN, JBUI.scaleFontSize(sCommonTextSize)));
        return this.descView;
    }

    private JComponent createFootView() {
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        horizontalBox.add(this.languageView = createFootItemView(PluginIcons.language));
        horizontalBox.add(Box.createHorizontalStrut(sFootItemMargin));
        horizontalBox.add(this.starView = createFootItemView(PluginIcons.star));
        horizontalBox.add(Box.createHorizontalStrut(sFootItemMargin));
        horizontalBox.add(this.forkView = createFootItemView(PluginIcons.fork));
        horizontalBox.add(Box.createHorizontalGlue());
        horizontalBox.add(this.dailyStarView = createFootItemView(PluginIcons.star));
        return horizontalBox;
    }

    private JLabel createFootItemView(Icon icon) {
        JLabel label = new JLabel();
        label.setIcon(icon);
        label.setIconTextGap(sIconTextGap);
        label.setFont(new Font(null, Font.PLAIN, JBUI.scaleFontSize(sCommonTextSize)));
        return label;
    }

    public void bindData(TrendingItem item) {
        this.userNameView.setText(item.getUserName() + " / ");
        this.repoNameView.setText(item.getRepoName());
        this.descView.setText(StringUtils.fix(item.getRepoDesc(), "-"));
        this.languageView.setText(item.getLanguage());
        this.starView.setText(item.getStarNum());
        this.forkView.setText(item.getForkNum());
        this.dailyStarView.setText(item.getDailyStar());
    }
}
