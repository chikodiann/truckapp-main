package com.ann.truckApp.domain.repository;

import com.ann.truckApp.domain.model.Ads;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdsRepository extends JpaRepository<Ads,Long> {
}
