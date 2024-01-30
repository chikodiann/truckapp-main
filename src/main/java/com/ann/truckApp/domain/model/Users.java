package com.ann.truckApp.domain.model;

import com.ann.truckApp.domain.enums.TIER;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@Builder
@Getter
@Setter
@ToString
public  class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status;
    private String phoneNumber;
    private String email;
    private String password;
    private  String firstName;
    private  String lastName;
    @Enumerated(EnumType.STRING)
    private TIER subscriptionTier;
    private String otp;

}
