package pl.edu.pjwstk.jaz.allezon.entity;


import pl.edu.pjwstk.jaz.authorizationjpa.UserEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "auction")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;

    private String title;

    private String description;

    private Long version;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_id")
    private Set<Photo> photoSet;

    @OneToMany(mappedBy = "auction",cascade = CascadeType.ALL)
    private Set<AuctionParameter> auctionParameters;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Set<Photo> getPhotosSet() {
        return photoSet;
    }

    public void setPhotosSet(Set<Photo> photoSet) {
        this.photoSet = photoSet;
    }

    public Set<AuctionParameter> getAuctionParameters() {
        return auctionParameters;
    }

    public void setAuctionParameters(Set<AuctionParameter> auctionParameters) {
        this.auctionParameters = auctionParameters;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}

