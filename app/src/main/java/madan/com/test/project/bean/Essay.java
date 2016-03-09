package madan.com.test.project.bean;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 山东娃 on 2016/3/5.
 */
public class Essay extends BmobObject implements Serializable {
    private static final long serialVersionUID = 8975823590589202L;

    private User author;
    private String content;
    private List<String> imageUrls;
    private int love;
    private int hate;
    private int share;
    private int comment;
    private boolean myFav;//收藏
    private boolean myLove;
    private boolean isPass;
    private BmobRelation relation;

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setIsPass(boolean isPass) {
        this.isPass = isPass;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public int getHate() {
        return hate;
    }

    public void setHate(int hate) {
        this.hate = hate;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public boolean isMyFav() {
        return myFav;
    }

    public void setMyFav(boolean myFav) {
        this.myFav = myFav;
    }

    public boolean isMyLove() {
        return myLove;
    }

    public void setMyLove(boolean myLove) {
        this.myLove = myLove;
    }

    public BmobRelation getRelation() {
        return relation;
    }

    public void setRelation(BmobRelation relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "Essay{" +
                "author=" + author +
                ", content='" + content + '\'' +
                ", imageUrls=" + imageUrls +
                ", love=" + love +
                ", hate=" + hate +
                ", share=" + share +
                ", comment=" + comment +
                ", myFav=" + myFav +
                ", myLove=" + myLove +
                ", isPass=" + isPass +
                ", relation=" + relation +
                '}';
    }
}
