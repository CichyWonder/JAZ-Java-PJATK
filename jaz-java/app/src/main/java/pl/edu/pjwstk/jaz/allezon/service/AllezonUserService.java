package pl.edu.pjwstk.jaz.allezon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.jaz.allezon.entity.*;
import pl.edu.pjwstk.jaz.allezon.request.AuctionRequest;
import pl.edu.pjwstk.jaz.allezon.request.ParameterRequest;
import pl.edu.pjwstk.jaz.allezon.request.PhotoRequest;
import pl.edu.pjwstk.jaz.authorization.BadRequestExecption;
import pl.edu.pjwstk.jaz.authorization.UnauthorizedExecption;
import pl.edu.pjwstk.jaz.authorizationjpa.LoginControllerJpa;
import pl.edu.pjwstk.jaz.authorizationjpa.UserEntity;
import pl.edu.pjwstk.jaz.authorizationjpa.UserService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class AllezonUserService {

    private final LoginControllerJpa loginControllerJpa;
    private final UserService userService;
    private final EntityManager entityManager;

    public AllezonUserService(LoginControllerJpa loginControllerJpa, EntityManager entityManager, UserService userService) {
        this.loginControllerJpa = loginControllerJpa;
        this.entityManager = entityManager;
        this.userService = userService;
    }


    public void createAuction(AuctionRequest auction) {
        var auctionEntity = new Auction();

        auctionEntity.setTitle(auction.getAuctionTitle());
        auctionEntity.setDescription(auction.getAuctionDescription());
        auctionEntity.setPrice(auction.getPrice());
        auctionEntity.setVersion(1L);
        auctionEntity.setUserEntity(userService.findUserByUsername(loginControllerJpa.getUsername()));
        auctionEntity.setCategory(findCategoryByName(auction.getCategoryName()));
        auctionEntity.setAuctionParameters(setAuctionParameters(auction.getParameterList(), auctionEntity));
        auctionEntity.setPhotosSet(setAuctionPhotos(auction.getPhotoList(), auctionEntity));
        entityManager.persist(auctionEntity);

    }

    public void editAuction(AuctionRequest auctionRequest, Long auction_id) {

        var auction = findAuctionById(auction_id);

        if (auction.getUserEntity().getUsername().equals(loginControllerJpa.getUsername())) {

            if (auctionRequest.getAuctionTitle() != null) {
                auction.setTitle(auctionRequest.getAuctionTitle());
            }
            if (auctionRequest.getAuctionDescription() != null) {
                auction.setDescription(auctionRequest.getAuctionDescription());
            }
            if (auctionRequest.getPrice() != 0) {
                auction.setPrice(auctionRequest.getPrice());
            }
            if (auctionRequest.getCategoryName() != null) {
                try {
                    if (findCategoryByName(auctionRequest.getCategoryName()) != null) {
                        auction.setCategory(findCategoryByName(auctionRequest.getCategoryName()));
                    }
                } catch (NoResultException exception) {
                    throw new BadRequestExecption();
                }
            }
            if (auctionRequest.getPhotoList() != null) {
                for (Photo p : auction.getPhotosSet()){
                    deletePhoto(p);
                }
                for (Photo p : setAuctionPhotos(auctionRequest.getPhotoList(), auction)){
                    entityManager.persist(p);
                }
            }
            if (auctionRequest.getParameterList() != null) {
                for(Parameter auctionParameter : getAllParametersForAuction(auction_id)){
                    deleteParameter(auctionParameter);
                }
                auction.setAuctionParameters(setAuctionParameters(auctionRequest.getParameterList(), auction));
            }
            if (auctionRequest.getVersion().equals(auction.getVersion())) {
                auction.setVersion(auction.getVersion() + 1);

                entityManager.merge(auction);
            } else {
                throw new BadRequestExecption();
            }

        } else {

            throw new UnauthorizedExecption();
        }
    }


    public Set<AuctionParameter> setAuctionParameters(List<ParameterRequest> parameters, Auction auction) {

        Set<AuctionParameter> auctionParameters = new HashSet<>();

        for (ParameterRequest parameterRequest : parameters) {
            var parameter = new Parameter();
            parameter.setKey(parameterRequest.getParameterKey());

            var auctionParameter = new AuctionParameter();
            auctionParameter.setAuction(auction);
            auctionParameter.setParameter(parameter);
            auctionParameter.setValue(parameterRequest.getParameterValue());
            auctionParameters.add(auctionParameter);
        }
        return auctionParameters;
    }

    public Set<Photo> setAuctionPhotos(List<PhotoRequest> auctionPhotos, Auction auction) {

        Set<Photo> auctionPhotosSet = new HashSet<>();

        for (PhotoRequest photo : auctionPhotos) {
            var auctionPhoto = new Photo();
            auctionPhoto.setName(photo.getPhotoName());
            auctionPhoto.setPosition(photo.getPhotoPosition());
            auctionPhoto.setAuction(auction);
            auctionPhotosSet.add(auctionPhoto);
        }
        return auctionPhotosSet;

    }

    public Category findCategoryByName(String name) {
        return entityManager.createQuery("SELECT category FROM Category category WHERE category.name =: name", Category.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Auction findAuctionById(Long auction_id) {
        return entityManager.createQuery("SELECT auction FROM Auction auction WHERE auction.id =: auction_id", Auction.class)
                .setParameter("auction_id", auction_id)
                .getSingleResult();
    }
    public void deletePhoto(Photo photo){
        entityManager.createQuery("DELETE FROM Photo photo where photo=:photo")
                .setParameter("photo",photo)
                .executeUpdate();
    }
    public void deleteParameter(Parameter parameter){
        entityManager.createQuery("DELETE FROM AuctionParameter auctionparameter  where auctionparameter.parameter =:parameter")
                .setParameter("parameter",parameter)
                .executeUpdate();
        entityManager.createQuery("DELETE FROM Parameter parameter where parameter=:parameter")
                .setParameter("parameter",parameter)
                .executeUpdate();
    }
    public List<Parameter> getAllParametersForAuction(Long auction){
        return entityManager.createQuery("SELECT auctionparameter.parameter FROM AuctionParameter auctionparameter WHERE " +
                "auctionparameter.auction.id =: auction",Parameter.class)
                .setParameter("auction",auction)
                .getResultList();
    }

}

