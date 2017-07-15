package com.example.donghyunlee.project2w;

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
    private String dist;
    private String popular;
    private String recent;
    private int checkbutton;
    private String storeAddress;

    public ContentItem()
    {

    }
    public ContentItem(String storeAddress, int storeImg, String storeName, String storeContent, String dist, String popular, String recent,int checkbutton)
    {
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
    public String getDist() { return dist; }
    public String getPopular() { return popular; }
    public String getRecent() { return recent; }
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
    public void setDist(String dist) { this.dist = dist;}
    public void setPopular(String popular) { this.popular = popular; }
    public void setRecent(String recent) { this.recent = recent; }
}
