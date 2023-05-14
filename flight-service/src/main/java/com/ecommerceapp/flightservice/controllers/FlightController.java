package com.ecommerceapp.flightservice.controllers;

import com.ecommerceapp.flightservice.models.Flight;
import com.ecommerceapp.flightservice.repositories.AirportRepository;
import com.ecommerceapp.flightservice.repositories.FlightRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("flights")
public class FlightController {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    AirportRepository airportRepository;

    @GetMapping(path="/getAllFlights")
    public Iterable<Flight> getFlights() {
        return flightRepository.findAll();
    }

    @GetMapping("/getFlight/{id}")
    public Flight getFlight(@PathVariable("id") String id) {
        Optional<Flight> f = flightRepository.findById(new ObjectId(id));
        if (f.isPresent()) {
            return f.get();
        }
        return null;
    }

    @PostMapping(path="/addFlight", consumes={"application/JSON"}, produces="application/json")
    public Object createFlight(@RequestBody Flight f) {
        if(f.getDepartureAirportId().toString().equals(f.getDestinationAirportId().toString())) {
            return "Departure airport and destination airport must be different.";
        } else {
            if (airportRepository.existsById(f.getDepartureAirportId()) == false) {
                return "Departure airport not found.";
            } else if (airportRepository.existsById(f.getDestinationAirportId()) == false) {
                return "Destination airport not found.";
            } else {
                return flightRepository.save(f);
            }
        }
    }

    @DeleteMapping(path="/deleteAllFlights")
    public void deleteFlights() {
        flightRepository.deleteAll();
    }

    @DeleteMapping(path="/deleteFlight/{id}")
    public void deleteFlight(@PathVariable("id") String id) {
        flightRepository.deleteById(new ObjectId(id));
    }

}
