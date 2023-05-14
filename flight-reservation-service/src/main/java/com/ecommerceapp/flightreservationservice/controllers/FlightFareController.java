package com.ecommerceapp.flightreservationservice.controllers;

import com.ecommerceapp.flightreservationservice.models.FlightFare;
import com.ecommerceapp.flightreservationservice.repositories.FlightFareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("flight_fares")
public class FlightFareController {

    @Autowired
    FlightFareRepository flightFareRepository;

    @GetMapping(path="/getAllFlightFares")
    public Iterable<FlightFare> getFlightFares() {
        return flightFareRepository.findAll();
    }

    @PostMapping(path="/addFlightFare", consumes={"application/JSON"}, produces="application/json")
    public FlightFare createFlightFare(@RequestBody FlightFare f) {
        return flightFareRepository.save(f);
    }

}