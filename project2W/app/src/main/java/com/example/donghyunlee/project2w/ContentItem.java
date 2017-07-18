package com.example.donghyunlee.project2w;

import java.util.Date;

/**
 * Created by DONGHYUNLEE on 2017-07-11.
 */

/*
    컨텐츠에 들어간 Item
 */
public class ContentItem {

    private int storeImg;
    private String storeName;
    private String storeContent;
    private double dist;
    private double popular;
    private Date recent;
    private int checkbutton;
    private String storeAddress;

    public ContentItem() {

    }
    public ContentItem(String storeAddress, int storeImg, String storeName, String storeContent, double dist, double popular, Date recent,int checkbutton) {
        this.storeAddress = storeAddress;
        this.storeImg = storeImg;
        this.storeContent = storeContent;
        this.storeName = storeName;
        this.dist = dist;
        this.popular = popular;
        this.recent = recent;
        this.checkbutton = checkbutton;
    }
    /*
        Getter
     */
    public String getStoreAddress(){ return storeAddress;}
    public int getStoreImg() { return storeImg;}
    public int getCheckbutton() { return checkbutton;}
    public String getStoreContent() {
        return storeContent;
    }
    public String getStoreName() { return storeName; }
    public double getDist() { return dist;}
    public double getPopular() { return popular; }
    public Date getRecent() { return recent; }
   /*
        Setter
   */
   public void setStoreAddress(String storeAddress){ this.storeAddress = storeAddress;}
   public void setCheckbutton(int checkbutton) { this.checkbutton = checkbutton; }
    public void setStoreImg(int storeImg) { this.storeImg = storeImg;}
    public void setStoreContent(String storeContent) {
        this.storeContent = storeContent;
    }
    public void setStoreName(String storeName) { this.storeName = storeName;}
    public void setDist(double dist) { this.dist = dist;}
    public void setPopular(double popular) { this.popular = popular; }
    public void setRecent(Date recent) { this.recent = recent; }
}
