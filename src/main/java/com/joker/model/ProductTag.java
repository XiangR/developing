package com.joker.model;

import java.io.Serializable;

/**
 * :
 * <p/>
 * author：xiaye create：2019-06-02
 */

public class ProductTag implements Serializable {

    private String name;

    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
