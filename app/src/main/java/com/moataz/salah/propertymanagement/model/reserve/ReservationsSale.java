package com.moataz.salah.propertymanagement.model.reserve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservationsSale {
    @SerializedName("reservation_sale_id")
    @Expose
    private Integer reservationSaleId;
    @SerializedName("reservation_id")
    @Expose
    private Integer reservationId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("created_user_id")
    @Expose
    private Integer createdUserId;
    @SerializedName("updated_user_id")
    @Expose
    private Integer updatedUserId;
    @SerializedName("price")
    @Expose
    private float price;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("total_price_without_discout")
    @Expose
    private Integer totalPriceWithoutDiscout;
    @SerializedName("total_price_with_discout")
    @Expose
    private Double totalPriceWithDiscout;

    public Integer getReservationSaleId() {
        return reservationSaleId;
    }

    public void setReservationSaleId(Integer reservationSaleId) {
        this.reservationSaleId = reservationSaleId;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Integer getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Integer updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getTotalPriceWithoutDiscout() {
        return totalPriceWithoutDiscout;
    }

    public void setTotalPriceWithoutDiscout(Integer totalPriceWithoutDiscout) {
        this.totalPriceWithoutDiscout = totalPriceWithoutDiscout;
    }

    public Double getTotalPriceWithDiscout() {
        return totalPriceWithDiscout;
    }

    public void setTotalPriceWithDiscout(Double totalPriceWithDiscout) {
        this.totalPriceWithDiscout = totalPriceWithDiscout;
    }
}
