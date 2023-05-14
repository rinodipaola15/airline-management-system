package com.ecommerceapp.flightreservationservice.controllers;

import com.ecommerceapp.flightreservationservice.models.FlightFare;
import com.ecommerceapp.flightreservationservice.models.FlightReservation;
import com.ecommerceapp.flightreservationservice.models.FlightReservationStatus;
import com.ecommerceapp.flightreservationservice.models.TicketType;
import com.ecommerceapp.flightreservationservice.repositories.FlightFareRepository;
import com.ecommerceapp.flightreservationservice.repositories.FlightReservationRepository;
import com.ecommerceapp.flightreservationservice.repositories.LuggageRepository;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@RestController
@RequestMapping("flight_reservations")
public class FlightReservationController {

    @Autowired
    FlightReservationRepository flightReservationRepository;

    @Autowired
    FlightFareRepository flightFareRepository;

    @Autowired
    LuggageRepository luggageRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${KAFKA_TOPIC_1}")
    private String kafkaTopic1;

    private TicketType ticketType;

    @GetMapping(path="/getAllFlightReservations")
    public Iterable<FlightReservation> getFlightReservations() {
        return flightReservationRepository.findAll();
    }

    @GetMapping("/getFlightReservation/{id}")
    public FlightReservation getFlightReservation(@PathVariable("id") String id) {
        Optional<FlightReservation> f = flightReservationRepository.findById(new ObjectId(id));
        if (f.isPresent()) {
            return f.get();
        }
        return null;
    }

    @PostMapping(path="/addFlightReservation", consumes={"application/JSON"}, produces="application/json")
    public String createFlightReservation(@RequestBody FlightReservation f) {
        if(f.getFlightsReservationDetails().size() == 0) {
            return "You must enter at least one flight.";
        }
        if(f.getFlightsReservationDetails().size()==2) {
            if(f.getFlightsReservationDetails().get(0).get_flightId_string().equals(f.getFlightsReservationDetails().get(1).get_flightId_string()) == true) {
                return "Departure airport and destination airport must be different.";
            }
        }
        if(!((f.getTicketType().equals(ticketType.ONE_WAY) && f.getFlightsReservationDetails().size()==1) || (f.getTicketType().equals(ticketType.ROUND_TRIP) && f.getFlightsReservationDetails().size()==2))) {
            return "The number of flights entered is not compatible with the ticketType parameter.";
        }
        if(f.getPassengersNumber()<=0){
            return "The number of passengers must be > 0.";
        }
        for(int i=0; i<f.getFlightsReservationDetails().size(); i++) {
            if(f.getFlightsReservationDetails().get(i).getPassengersData().size() != f.getPassengersNumber()) {
                return "The number of passengers entered is not compatible with the passengersNumber parameter.";
            }
        }
        for(int j=0; j<f.getFlightsReservationDetails().size(); j++) {
            for(int k=0; k<f.getPassengersNumber(); k++) {
                for(ObjectId key : f.getFlightsReservationDetails().get(j).getPassengersData().get(k).getExtraLuggage().keySet()) {
                    if(luggageRepository.findById(key).equals(Optional.empty())) {
                        return "The luggage with id " + key + " doesn't exist.";
                    }
                    if(f.getFlightsReservationDetails().get(j).getPassengersData().get(k).getExtraLuggage().get(key) < 0) {
                        return "The entered quantity associated with the luggage with id " + key + " is negative.";
                    }
                }
                ObjectId flightFareId = f.getFlightsReservationDetails().get(j).getPassengersData().get(k).getFlightFare();
                if(flightFareRepository.findById(flightFareId).equals(Optional.empty())) {
                    return "The flightFare with id " + flightFareId.toString() + " doesn't exist.";
                }
            }
        }
        flightReservationRepository.save(f);
        JSONObject content = new JSONObject();
        content.put("message", "Create FlightReservation: Checking User");
        content.put("userId", f.get_userId_string());
        content.put("flightReservationId", f.get_id_string());
        System.out.println("----------TO SEND----------");
        System.out.println("message: Create FlightReservation: Checking User");
        System.out.println("userId: " + f.get_userId_string());
        System.out.println("flightReservationId: " + f.get_id_string());
        kafkaTemplate.send(kafkaTopic1, content.toString());
        return "Flight reservation created successfully.\n" + "Flight Reservation ID: " + f.get_id_string();
    }

    @DeleteMapping(path="/deleteAllFlightReservations")
    public void deleteFlightReservations() {
        flightReservationRepository.deleteAll();
    }

    @DeleteMapping(path="/deleteFlightReservation/{id}")
    public void deleteFlightReservation(@PathVariable("id") String id) {
        flightReservationRepository.deleteById(new ObjectId(id));
    }

}
