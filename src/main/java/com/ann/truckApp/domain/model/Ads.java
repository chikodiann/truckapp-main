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

    @Override
    public String toString() {
        return "Ads{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", truck_type='" + truck_type + '\'' +
                ", email='" + email + '\'' +
                ", from_city='" + from_city + '\'' +
                ", from_province='" + from_province + '\'' +
                ", from_neighborhood='" + from_neighborhood + '\'' +
                ", to_city='" + to_city + '\'' +
                ", to_province='" + to_province + '\'' +
                ", to_neighborhood='" + to_neighborhood + '\'' +
                ", type_of_load='" + type_of_load + '\'' +
                ", status=" + status +
                ", expiration=" + expiration +
                ", creationTimestamp=" + creationTimestamp +
                ", notifications=" + notifications +
                '}';
    }

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
    @Column(name = "expiration")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiration;

    @Column(name = "creation_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationTimestamp;

    @OneToMany(mappedBy = "ads", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Notification> notifications;

    public boolean getStatus() {
        return status;
    }
}
