package com.ann.truckApp.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private String lastName;
    private String email;
    private String from_city;
    private String from_province;
    private String from_neighborhood;
    private String to_city;
    private String to_province;
    private boolean status;
    private String to_neighborhood;
    private String typeVehicle;
    private String typeLoad;
    private LocalDateTime expiration;
    @OneToMany(mappedBy = "ads", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Notification> notifications;


}
