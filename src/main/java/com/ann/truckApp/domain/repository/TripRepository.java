package com.ann.truckApp.domain.repository;

import com.ann.truckApp.domain.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository  extends JpaRepository<Trip,Long> {

}
