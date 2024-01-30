package com.ann.truckApp.domain.model;

import com.ann.truckApp.domain.enums.TIER;
import jakarta.persistence.*;
import lombok.*;


@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorValue("Admin")
@ToString
@Entity
public class Admin extends Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username = "ADMIN";
    private String password = "Admin@123456";



    public void setSubscriptionTier(TIER tier) {

    }
}

