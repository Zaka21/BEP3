package com.example.ginos.core.application.command;

import com.example.ginos.core.domain.Pizza;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class AddToCart {

    private String customerName;
    private Pizza selectedPizza;
    private int quantity;
    private boolean finishedOrder;

    public AddToCart(String customerName, Pizza selectedPizza, int quantity, boolean finishedOrder) {
        this.customerName = customerName;
        this.selectedPizza = selectedPizza;
        this.quantity = quantity;
        this.finishedOrder = finishedOrder;
    }
}
