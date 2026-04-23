package com.restaurant.it210session14bai3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Getter;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    private int stock;

    @Version
    private int version;

    public void setStock(int i) {

    }
}