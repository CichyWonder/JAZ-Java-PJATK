package pl.edu.pjwstk.jaz.allezon.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.jaz.allezon.entity.Auction;
import pl.edu.pjwstk.jaz.allezon.entity.AuctionParameter;
import pl.edu.pjwstk.jaz.allezon.entity.Photo;
import pl.edu.pjwstk.jaz.allezon.request.ParameterRequest;
import pl.edu.pjwstk.jaz.allezon.request.PhotoRequest;
import pl.edu.pjwstk.jaz.authorization.BadRequestExecption;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class ViewAuctionService {

    private final EntityManager entityManager;
    private AllezonUserService allezonUserService;

    public ViewAuctionService(EntityManager entityManager, AllezonUserService allezonUserService) {
        this.entityManager = entityManager;
        this.allezonUserService = allezonUserService;
    }

    public ViewAuction getAuction(Long auctionId) {

        ViewAuction viewAuction = new ViewAuction();

        try {
            var auction = allezonUserService.findAuctionById(auctionId);

            viewAuction.setAuctionId(auctionId);
            viewAuction.setPrice(auction.getPrice());
            viewAuction.setAuctionTitle(auction.getTitle());
            viewAuction.setAuctionDescription(auction.getDescription());
            viewAuction.setCategoryName(auction.getCategory().getName());
            viewAuction.setUserName(auction.getUserEntity().getUsername());

            Set<PhotoRequest> photoRequests = new HashSet<>();
            for (Photo photo : auction.getPhotosSet()) {
                var auctionPhoto = new PhotoRequest(photo.getName(), photo.getPosition());
                photoRequests.add(auctionPhoto);
            }
            viewAuction.setPhotoList(photoRequests);

            Set<ParameterRequest> auctionParameters = new HashSet<>();
            for (AuctionParameter auctionParameter : auction.getAuctionParameters()) {
                var parameter = new ParameterRequest(auctionParameter.getParameter().getKey(), auctionParameter.getValue());
                auctionParameters.add(parameter);
            }
            viewAuction.setParameterList(auctionParameters);

            viewAuction.setVersion(auction.getVersion());
        } catch (NoResultException exception) {
            throw new BadRequestExecption();
        }

        return viewAuction;
    }

    public List<ViewAuctionList> getAuctionList(){
        List<Auction> auctions = getAllAuctions();
        List<ViewAuctionList> auctionViews = new ArrayList<>();
        for (Auction auction : auctions){
            var auctionView = new ViewAuctionList();

             auctionView.setMiniature(getMiniature(auction.getId(),1));
             auctionView.setTitle(auction.getTitle());
             auctionView.setCategory(auction.getCategory().getName());
             auctionView.setPrice(auction.getPrice());


            auctionViews.add(auctionView);

        }

        return auctionViews;
    }


    public String getMiniature(Long auction_id,int positionPhoto){
        return (String) entityManager.createQuery("SELECT photo.name FROM Photo photo WHERE photo.auction.id =: auction_id AND photo.position =: positionPhoto")
                .setParameter("positionPhoto",positionPhoto)
                .setParameter("auction_id", auction_id)
                .getSingleResult();
    }
    public List<Auction> getAllAuctions(){
        return entityManager.createQuery("select auction FROM Auction auction",Auction.class)
                .getResultList();
    }
}
