package com.ann.truckApp.domain.model;

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
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long driverId;
    private boolean status;

    private String country;
    private String postalCode;
    private String vehicle;
    private String fromCity;
    private String toCity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Notification> notifications;



}
