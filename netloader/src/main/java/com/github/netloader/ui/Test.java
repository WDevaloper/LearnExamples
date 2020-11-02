package com.github.netloader.ui;

import com.google.gson.Gson;

import java.math.BigDecimal;

public class Test {

    public static void main(String[] args) {
        Gson gson = new Gson();

        TestPrice testPrice = gson.fromJson(" { \"price\": 0.080988}", TestPrice.class);
        System.out.println("testPrice = " + testPrice);
    }

    class TestPrice {
        private BigDecimal price = BigDecimal.ZERO;

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "TestPrice{" +
                    "price=" + price +
                    '}';
        }
    }
}
