package com.xtu.plugin.github;

import com.xtu.plugin.github.constant.UrlConstant;
import com.xtu.plugin.github.entity.TrendingItem;
import com.xtu.plugin.github.utils.CollectionUtils;
import com.xtu.plugin.github.utils.LogUtils;
import com.xtu.plugin.github.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class TrendingDataParser {

    @Nullable
    public List<TrendingItem> parseTrendingList(@NotNull String html) {
        try {
            Document document = Jsoup.parse(html);
            Elements articleNodeList = document.getElementsByTag("article");
            if (articleNodeList == null || articleNodeList.isEmpty()) return null;
            List<TrendingItem> resultList = new ArrayList<>();
            for (Element articleNode : articleNodeList) {
                TrendingItem item = parseItem(articleNode);
                resultList.add(item);
            }
            return resultList;
        } catch (Exception e) {
            LogUtils.error(TrendingDataParser.class.getSimpleName(), e);
            return null;
        }
    }

    @NotNull
    private TrendingItem parseItem(@NotNull Element articleNode) {
        TrendingItem item = new TrendingItem();
        item.setUserName(parseUserName(articleNode));
        item.setRepoName(parseRepoName(articleNode));
        item.setRepoDesc(parseRepoDesc(articleNode));
        item.setLanguage(parseRepoLanguage(articleNode));
        item.setStarNum(parseRepoLabelNum(articleNode, 0));
        item.setForkNum(parseRepoLabelNum(articleNode, 1));
        item.setDailyStar(parseDailyStar(articleNode));
        item.setUrl(parseUrl(articleNode));
        return item;
    }

    @Nullable
    private String parseUserName(@NotNull Element articleNode) {
        Elements selectNodeList = articleNode.select("h2 > a > span");
        if (selectNodeList == null || selectNodeList.isEmpty()) return null;
        return selectNodeList.first().text().replace("/", "").trim();

    }

    @Nullable
    private String parseRepoName(@NotNull Element articleNode) {
        Elements selectNodeList = articleNode.select("h2 > a");
        if (selectNodeList == null || selectNodeList.isEmpty()) return null;
        List<TextNode> textNodes = selectNodeList.first().textNodes();
        if (CollectionUtils.isEmpty(textNodes)) return null;
        int size = textNodes.size();
        return textNodes.get(size - 1).text().trim();
    }

    @Nullable
    private String parseRepoLanguage(@NotNull Element articleNode) {
        Elements divNodeList = articleNode.getElementsByTag("div");
        if (divNodeList == null || divNodeList.isEmpty()) return null;
        return divNodeList.last().children().first().children().last().text().trim();
    }

    @Nullable
    private String parseRepoDesc(@NotNull Element articleNode) {
        Elements selectNodeList = articleNode.getElementsByTag("p");
        if (selectNodeList == null || selectNodeList.isEmpty()) return null;
        return selectNodeList.first().text().trim();
    }

    @Nullable
    private String parseRepoLabelNum(@NotNull Element articleNode, int index) {
        Elements divNodeList = articleNode.getElementsByTag("div");
        if (divNodeList == null || divNodeList.isEmpty()) return null;
        Elements aNodeList = divNodeList.last().getElementsByTag("a");
        if (aNodeList == null || aNodeList.isEmpty()) return null;
        return aNodeList.get(index).text().trim();
    }

    @Nullable
    private String parseDailyStar(@NotNull Element articleNode) {
        Elements divNodeList = articleNode.getElementsByTag("div");
        if (divNodeList == null || divNodeList.isEmpty()) return null;
        Elements spanList = divNodeList.last().getElementsByTag("span");
        if (spanList == null || spanList.isEmpty()) return null;
        return spanList.last().text().trim();
    }

    @Nullable
    private String parseUrl(@NotNull Element articleNode) {
        Elements selectNodeList = articleNode.select("h2 > a");
        if (selectNodeList == null || selectNodeList.isEmpty()) return null;
        String href = selectNodeList.first().attr("href");
        if (StringUtils.isEmpty(href)) return null;
        return UrlConstant.HOST_URL + href;
    }
}
