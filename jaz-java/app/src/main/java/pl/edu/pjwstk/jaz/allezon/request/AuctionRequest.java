package pl.edu.pjwstk.jaz.allezon.request;

import pl.edu.pjwstk.jaz.allezon.entity.Parameter;
import pl.edu.pjwstk.jaz.allezon.entity.Photo;

import java.util.List;

public class AuctionRequest {

    private int price;
    private String auctionTitle;
    private String auctionDescription;
    private String categoryName;
    private String userName;
    private Long version;
    private List<PhotoRequest> photoList;
    private List<ParameterRequest> parameterList;

    public AuctionRequest() {
    }

    public AuctionRequest(int price, String auctionTitle, String auctionDescription, String categoryName, String userName, Long version, List<PhotoRequest> photoList, List<ParameterRequest> parameterList) {
        this.price = price;
        this.auctionTitle = auctionTitle;
        this.auctionDescription = auctionDescription;
        this.categoryName = categoryName;
        this.userName = userName;
        this.version = version;
        this.photoList = photoList;
        this.parameterList = parameterList;
    }
    public AuctionRequest(int price, String auctionTitle, String auctionDescription, String categoryName, List<PhotoRequest> photoRequestList, List<ParameterRequest> parameterRequestList) {
        this.price = price;
        this.auctionTitle = auctionTitle;
        this.auctionDescription = auctionDescription;
        this.categoryName = categoryName;
        this.photoList = photoRequestList;
        this.parameterList = parameterRequestList;
    }

    public int getPrice() {
        return price;
    }

    public String getAuctionTitle() {
        return auctionTitle;
    }

    public String getAuctionDescription() {
        return auctionDescription;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getUserName() {
        return userName;
    }

    public Long getVersion() {
        return version;
    }

    public List<PhotoRequest> getPhotoList() {
        return photoList;
    }

    public List<ParameterRequest> getParameterList() {
        return parameterList;
    }
}

