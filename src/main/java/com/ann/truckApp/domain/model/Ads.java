package com.ann.truckApp.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String from_city;

    @Column(nullable = false)
    private String from_province;

    @Column(nullable = false)
    private String from_neighborhood;

    @Column(nullable = false)
    private String to_city;

    @Column(nullable = false)
    private String to_province;

    @Column(nullable = false)
    private String to_neighborhood;

    @Column(nullable = false)
    private String typeVehicle;

    @Column(nullable = false)
    private String typeLoad;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToMany(mappedBy = "ads", cascade = CascadeType.ALL)
    private List<Notification> notifications;



}
