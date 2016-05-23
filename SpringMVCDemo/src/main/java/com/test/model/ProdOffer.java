package com.test.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by tanzepeng on 2016/3/22.
 */
public class ProdOffer implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long prodOfferId;
    private String offerType;
    private String prodOfferName;
    private String statusCd;
    private Date statudDate;
    private Date effDate;
    private Date expDate;
    private String offerNbr;

    public Long getProdOfferId() {
        return prodOfferId;
    }

    public void setProdOfferId(Long prodOfferId) {
        this.prodOfferId = prodOfferId;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getProdOfferName() {
        return prodOfferName;
    }

    public void setProdOfferName(String prodOfferName) {
        this.prodOfferName = prodOfferName;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public Date getStatudDate() {
        return statudDate;
    }

    public void setStatudDate(Date statudDate) {
        this.statudDate = statudDate;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getOfferNbr() {
        return offerNbr;
    }

    public void setOfferNbr(String offerNbr) {
        this.offerNbr = offerNbr;
    }
}
