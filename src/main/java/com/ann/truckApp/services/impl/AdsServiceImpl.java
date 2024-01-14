package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.model.Ads;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.AdsRepository;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.request.AdsRequest;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AdsServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdsRepository adsRepository;

    public BaseResponse<?> addAds(AdsRequest adsRequest){
        Users users = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(()->new CustomerNotFoundException("Could not find"));
        Ads ads = new Ads();
        ads.setEmail(ads.getEmail());
        ads.setUser(users);
        ads.setTo_(adsRequest.getTo_());
        ads.setLastName(ads.getLastName());
        ads.setTypeVehicle(adsRequest.getTypeVehicle());
        adsRepository.save(ads);
        return new BaseResponse<>(ads);

    }

}
