package com.nalstudio.springjms.domain;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "TACO_ORDER")
//
public class Order implements Serializable {
    private static final long serialVersionId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date placedAt;

    @ManyToOne
    private User user;

    @NotBlank(message = "Name is required")
    private String deliveryName;

    @NotBlank(message = "Street is required")
    private String deliveryStreet;

    @NotBlank(message = "City is required")
    private String deliveryCity;

    @NotBlank(message = "State is required")
    @Size(max = 2, message = "State must be up to 2 characters long")
    private String deliveryState;

    @NotBlank(message = "Zip code is required")
    private String deliveryZip;

    @CreditCardNumber(message = "Not a valid creadit card number")
    private String ccNumber;

    @Pattern(regexp = "^0[1-9]|1[0-2]([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV") // integer: 자릿수, fraction: 소수점 자리수
    private String ccCVV;

    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco design) {
        this.tacos.add(design);
    }

    @PrePersist
    void placeAt() {
        this.placedAt = new Date();
    }
}
