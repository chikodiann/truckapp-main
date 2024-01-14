package com.ann.truckApp.domain.model;

import com.ann.truckApp.domain.enums.TIER;
import com.ann.truckApp.domain.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status;

    private String phoneNumber;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private TIER subscriptionTier;
    @Enumerated(EnumType.STRING)
    private Type type;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Trip> trips;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ads> ads;
}
