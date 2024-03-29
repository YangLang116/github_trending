package com.xtu.plugin.github.entity;

public class TrendingItem {
    private String language;
    private String userName;
    private String repoName;
    private String repoDesc;
    private String starNum;
    private String forkNum;
    private String dailyStar;
    private String url;

    public String getUserName() {
        return userName == null || userName.isEmpty() ? "-" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRepoName() {
        return repoName == null || repoName.isEmpty() ? "-" : repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoDesc() {
        return repoDesc == null || repoDesc.isEmpty() ? "-" : repoDesc;
    }

    public void setRepoDesc(String repoDesc) {
        this.repoDesc = repoDesc;
    }

    public String getStarNum() {
        return starNum == null || starNum.isEmpty() ? "-" : starNum;
    }

    public void setStarNum(String starNum) {
        this.starNum = starNum;
    }

    public String getForkNum() {
        return forkNum == null || forkNum.isEmpty() ? "-" : forkNum;
    }

    public void setForkNum(String forkNum) {
        this.forkNum = forkNum;
    }

    public String getLanguage() {
        return language == null || language.isEmpty() ? "-" : language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDailyStar() {
        return dailyStar == null || dailyStar.isEmpty() ? "-" : dailyStar;
    }

    public void setDailyStar(String dailyStar) {
        this.dailyStar = dailyStar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
