package com.example.demo.domain;

import javax.persistence.*;

@Entity
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String shippingAddressName;
    private String shippingAddressStreet1;
    private String shippingAddressStreet2;
    private String shippingAddressCity;
    private String shippingAddressCountry;
    private String shippingAddressZipCode;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShippingAddressName() {
        return shippingAddressName;
    }

    public void setShippingAddressName(String shippingAddressName) {
        this.shippingAddressName = shippingAddressName;
    }

    public String getShippingAddressStreet1() {
        return shippingAddressStreet1;
    }

    public void setShippingAddressStreet1(String shippingAddressStreet1) {
        this.shippingAddressStreet1 = shippingAddressStreet1;
    }

    public String getShippingAddressStreet2() {
        return shippingAddressStreet2;
    }

    public void setShippingAddressStreet2(String shippingAddressStreet2) {
        this.shippingAddressStreet2 = shippingAddressStreet2;
    }

    public String getShippingAddressCity() {
        return shippingAddressCity;
    }

    public void setShippingAddressCity(String shippingAddressCity) {
        this.shippingAddressCity = shippingAddressCity;
    }

    public String getShippingAddressCountry() {
        return shippingAddressCountry;
    }

    public void setShippingAddressCountry(String shippingAddressCountry) {
        this.shippingAddressCountry = shippingAddressCountry;
    }

    public String getShippingAddressZipCode() {
        return shippingAddressZipCode;
    }

    public void setShippingAddressZipCode(String shippingAddressZipCode) {
        this.shippingAddressZipCode = shippingAddressZipCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}





















