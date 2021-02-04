package pl.edu.pjwstk.jaz.allezon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.jaz.allezon.entity.Auction;
import pl.edu.pjwstk.jaz.allezon.entity.Category;
import pl.edu.pjwstk.jaz.allezon.entity.Section;
import pl.edu.pjwstk.jaz.allezon.request.CategoryRequest;
import pl.edu.pjwstk.jaz.authorization.BadRequestExecption;
import pl.edu.pjwstk.jaz.authorizationjpa.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Transactional
@Service
public class AllezonAdminService {

    private final EntityManager em;

    public AllezonAdminService(EntityManager em) {

        this.em = em;
    }

    public void createSection(String sectionName) {

        var section = new Section();
        section.setName(sectionName);
        em.persist(section);

    }

    public void updateSection(String sectionName,String newName) {
        try{
            var section = findSectionByName(sectionName);
            section.setName(newName);
            em.merge(section);
        }
        catch (NoResultException exception) {
            throw new BadRequestExecption();
        }
    }

    public void createCategory(CategoryRequest category){

        var categoryEntity  = new Category();
        categoryEntity.setName(category.getCategoryName());
        categoryEntity.setSection(findSectionByName(category.getSectionName()));
        em.persist(categoryEntity);
    }

    public void updateCategory(CategoryRequest category,String categoryName) {
        try{
            var categoryEntity = findCategoryByName(categoryName);
            categoryEntity.setName(category.getCategoryName());
            categoryEntity.setSection(findSectionByName(category.getSectionName()));
            em.merge(categoryEntity);
        }catch (NoResultException exception) {
            throw new BadRequestExecption();
        }
    }

    public Category findCategoryByName(String categoryName) {
        return em.createQuery("SELECT category FROM Category category WHERE category.name =: categoryName", Category.class)
                .setParameter("categoryName", categoryName)
                .getSingleResult();
    }

    public Section findSectionByName(String sectionName) {
        return em.createQuery("SELECT section FROM Section section WHERE section.name =: sectionName", Section.class)
                .setParameter("sectionName", sectionName)
                .getSingleResult();
    }

    public List<Auction> getAuctionsByCreator(UserEntity userEntity){
        return em.createQuery("select auction FROM Auction auction WHERE auction.userEntity =:userEntity", Auction.class)
                .setParameter("userEntity",userEntity)
                .getResultList();
    }

}
