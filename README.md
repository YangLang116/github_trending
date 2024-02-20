<!-- Plugin description -->
Trending repositories on GitHub today ~~~~~
<!-- Plugin description end -->

## ScreenShot
![Display](https://github.com/YangLang116/github_trending/raw/main/screenshots/github-trending.png)

---

## 项目说明

### 1、使用JSoup解析html
Java对xml解析方式主流分为Dom、SAX、JDom、Dom4J，但是面对如下两种标签时，会出现xml解析失败：

- 不存在闭合标签
```html
<meta charset="utf-8">
```

- 属性不以key=value的形式出现
```html
<link rel="preconnect" href="https://github.githubassets.com" crossorigin>
```

### 2、使用Box.createVerticalBox代替JBList
每个条目中的文案长度不一致，导致cell height不相同，由于JBList中的ListUI机制(BasicListUI#updateLayoutState)，无法保证JBList+JTextArea支持动态Item高度。

JBList:
```java
JBList listView = new JBList<>();
//设置单选
listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//绑定数据源
listModel = new DefaultListModel<>();
listView.setModel(this.listModel);
//设置UI样式
listView.setCellRenderer(new TrendingCellRender());
//item 点击
listView.addListSelectionListener(e -> {
    int index = this.listView.getSelectedIndex();
    ...
});
```

Box:
```java
Box listView = Box.createVerticalBox();
listView.add(itemView);
```

### 3、文本内容多行显示
JLabel 无法多行显示，了解 JLabel、JTextArea、JTextField、JEditorPane、JTextComponent 继承关系以及区别
```java
JTextArea descView = new JTextArea();
//设置自动换行
descView.setLineWrap(true);
descView.setWrapStyleWord(true);
//设置透明背景色
descView.setBackground(new Color(0, 0, 0, 0));
```