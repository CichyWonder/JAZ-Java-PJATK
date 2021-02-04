package pl.edu.pjwstk.jaz.allezon.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.allezon.request.AuctionRequest;
import pl.edu.pjwstk.jaz.allezon.request.SectionRequest;
import pl.edu.pjwstk.jaz.allezon.service.AllezonUserService;
import pl.edu.pjwstk.jaz.allezon.service.ViewAuction;
import pl.edu.pjwstk.jaz.allezon.service.ViewAuctionList;
import pl.edu.pjwstk.jaz.allezon.service.ViewAuctionService;

import java.util.List;

@RestController
public class AuctionController {

    private final AllezonUserService allezonUserService;
    private final ViewAuctionService viewAuctionService;

    public AuctionController(AllezonUserService allezonUserService, ViewAuctionService viewAuctionService) {
        this.allezonUserService = allezonUserService;
        this.viewAuctionService = viewAuctionService;
    }

    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/addAuction")
    public void creteAuction(@RequestBody @Validated AuctionRequest auction){
        allezonUserService.createAuction(auction);
    }

    @PreAuthorize("hasAuthority('user')")
    @PutMapping("/editAuction/{auction_id}")
    public void editAuction(@RequestBody @Validated AuctionRequest auction, @PathVariable("auction_id") Long auction_id){
        allezonUserService.editAuction(auction,auction_id);
    }

    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/auctions/{auctionId}")
    public ViewAuction getAuction(@PathVariable("auctionId") Long auctionId){

        return viewAuctionService.getAuction(auctionId);
    }

    @PreAuthorize("hasAnyAuthority('user')")
    @GetMapping("/auctions")
    public List<ViewAuctionList> getAuctions(){
        return viewAuctionService.getAuctionList();

    }

}
