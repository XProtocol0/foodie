package com.github.foodie.services.impl;

import com.github.foodie.services.DistanceService;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
public class OSRMDistanceServiceImpl implements DistanceService {
    @Override
    public double calculateDistance(Point src, Point dest) {
        //call osrm 3rd party api
        return 0;
    }
}
