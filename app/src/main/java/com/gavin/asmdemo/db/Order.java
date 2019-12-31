package com.gavin.asmdemo.db;

/**
 * @Describe:
 * @Author: wfy
 */
public class Order {
    private Integer id;
    private String desc;

    public Order() {
    }

    public Order(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                '}';
    }
}
