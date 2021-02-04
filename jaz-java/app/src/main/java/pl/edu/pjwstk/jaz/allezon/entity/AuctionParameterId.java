package pl.edu.pjwstk.jaz.allezon.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AuctionParameterId implements Serializable {

    private Long auction_id;
    private Long parameter_id;

    public AuctionParameterId(Long auction_id, Long parameter_id) {
        this.auction_id = auction_id;
        this.parameter_id = parameter_id;
    }

    public AuctionParameterId() {
    }

    public Long getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(Long auction_id) {
        this.auction_id = auction_id;
    }

    public Long getParameter_id() {
        return parameter_id;
    }

    public void setParameter_id(Long parameter_id) {
        this.parameter_id = parameter_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuctionParameterId)) return false;
        AuctionParameterId that = (AuctionParameterId) o;
        return Objects.equals(getAuction_id(), that.getAuction_id()) && Objects.equals(getParameter_id(), that.getParameter_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuction_id(), getParameter_id());
    }

}
