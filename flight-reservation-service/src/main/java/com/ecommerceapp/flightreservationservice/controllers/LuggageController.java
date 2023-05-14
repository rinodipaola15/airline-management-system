package com.ecommerceapp.flightreservationservice.controllers;

import com.ecommerceapp.flightreservationservice.models.Luggage;
import com.ecommerceapp.flightreservationservice.repositories.LuggageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("luggage")
public class LuggageController {

    @Autowired
    LuggageRepository luggageRepository;

    @GetMapping(path="/getAllLuggage")
    public Iterable<Luggage> getLuggage() {
        return luggageRepository.findAll();
    }

    @PostMapping(path="/addLuggage", consumes={"application/JSON"}, produces="application/json")
    public Luggage createLuggage(@RequestBody Luggage l) {
        return luggageRepository.save(l);
    }

}
