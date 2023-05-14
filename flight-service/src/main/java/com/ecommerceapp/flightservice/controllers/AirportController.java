package com.ecommerceapp.flightservice.controllers;

import com.ecommerceapp.flightservice.models.Airport;
import com.ecommerceapp.flightservice.repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("airports")
public class AirportController {

    @Autowired
    AirportRepository airportRepository;

    @GetMapping(path="/getAllAirports")
    public Iterable<Airport> getAirports() {
        return airportRepository.findAll();
    }

    @PostMapping(path="/addAirport", consumes={"application/JSON"}, produces="application/json")
    public Airport createAirport(@RequestBody Airport a) {
        return airportRepository.save(a);
    }

}