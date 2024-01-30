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
    private String firstname;
    private String lastname;
    private String phone;
    private String truck_type;
    private String email;
    private String from_city;
    private String from_province;
    private String from_neighborhood;
    private String to_city;
    private String to_province;
    private String to_neighborhood;
    private String type_of_load;
    private boolean status;
    private LocalDateTime expiration;
    @OneToMany(mappedBy = "ads", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Notification> notifications;


}
