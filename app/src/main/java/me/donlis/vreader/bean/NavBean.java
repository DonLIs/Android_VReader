package me.donlis.vreader.bean;

import java.io.Serializable;
import java.util.List;

public class NavBean implements Serializable {

    private boolean selected;
    private int cid;
    private String name;
    private List<ArticlesBean> articles;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

}
