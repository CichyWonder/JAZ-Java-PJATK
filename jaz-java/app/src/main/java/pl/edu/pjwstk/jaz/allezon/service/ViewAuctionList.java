package pl.edu.pjwstk.jaz.allezon.service;

public class ViewAuctionList {

    private String miniature;
    private String title;
    private String category;
    private int price;


    public void setPrice(int price) {
        this.price = price;
    }

    public void setMiniature(String miniature) {
        this.miniature = miniature;
    }

    public String getMiniature() {
        return miniature;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
