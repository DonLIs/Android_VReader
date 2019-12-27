package me.donlis.vreader.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class CompItem implements MultiItemEntity {

    public CompItem(){

    }

    public CompItem(String content,int type){
        this.content = content;
        this.type = type;
    }

    public final static int TEXT = 0;

    public final static int IMG = 1;

    private String content;

    private int type;

    @Override
    public int getItemType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
