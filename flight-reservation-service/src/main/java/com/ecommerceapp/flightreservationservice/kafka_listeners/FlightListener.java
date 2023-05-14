package com.ecommerceapp.flightreservationservice.kafka_listeners;

import com.ecommerceapp.flightreservationservice.models.FlightFare;
import com.ecommerceapp.flightreservationservice.models.FlightReservation;
import com.ecommerceapp.flightreservationservice.models.FlightReservationStatus;
import com.ecommerceapp.flightreservationservice.models.Luggage;
import com.ecommerceapp.flightreservationservice.repositories.FlightFareRepository;
import com.ecommerceapp.flightreservationservice.repositories.FlightReservationRepository;
import com.ecommerceapp.flightreservationservice.repositories.LuggageRepository;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightListener {

    @Autowired
    FlightReservationRepository flightReservationRepository;

    @Autowired
    LuggageRepository luggageRepository;

    @Autowired
    FlightFareRepository flightFareRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "${KAFKA_TOPIC_4}")
    public void listen(String received_content_string) {
        System.out.println("----------RECEIVED----------");
        JSONObject received_content = new JSONObject(received_content_string);
        String message = received_content.getString("message");
        System.out.println("message: " + message);
        ObjectId flightReservationId = new ObjectId(received_content.getString("flightReservationId"));
        System.out.println("flightReservationId: " + flightReservationId);
        String flightsPrice_string = received_content.getString("flightsPrice");
        List<String> flightsPrice_stringlist = new ArrayList<>(Arrays.asList(flightsPrice_string.split(",")));
        List<BigDecimal> flightsPrice = new ArrayList<>();
        for(String flightPrice : flightsPrice_stringlist) {
            flightsPrice.add(new BigDecimal(flightPrice));
        }
        System.out.println("flightsPrice:");
        for(BigDecimal flightPrice : flightsPrice) {
            System.out.println(flightPrice);
        }
        if(message.equals("FlightsExist")) {
            System.out.println("Check flightsId: OK");
            FlightReservation flightReservation = flightReservationRepository.findById(flightReservationId).get();
            BigDecimal amount = calculatePrice(flightReservation, flightsPrice);
            flightReservation.setAmount(amount);
            flightReservation.setStatus(FlightReservationStatus.CONFIRMED);
            flightReservationRepository.save(flightReservation);
        }
    }

    public BigDecimal calculatePrice(FlightReservation f, List<BigDecimal> flightsPrice) {
        BigDecimal amount = new BigDecimal(0.0);
        for(BigDecimal flightPrice : flightsPrice) {
            amount = amount.add(flightPrice.multiply(new BigDecimal(f.getPassengersNumber())));
        }
        for(int i=0; i<f.getFlightsReservationDetails().size(); i++) {
            for(int j=0; j<f.getPassengersNumber(); j++) {
                //flightFare
                FlightFare flightFare = flightFareRepository.findById(f.getFlightsReservationDetails().get(i).getPassengersData().get(j).getFlightFare()).get();
                amount = amount.add(flightFare.getPrice());
                //extraLuggage
                for(ObjectId key : f.getFlightsReservationDetails().get(i).getPassengersData().get(j).getExtraLuggage().keySet()) {
                    Luggage luggage = luggageRepository.findById(key).get();
                    amount = amount.add((luggage.getPrice().multiply(new BigDecimal(f.getFlightsReservationDetails().get(i).getPassengersData().get(j).getExtraLuggage().get(key))))); //luggagePrice * quantity
                }
                //travelInsurance
                if(f.getFlightsReservationDetails().get(i).getPassengersData().get(j).isTravelInsurance() == true) {
                    amount = amount.add(new BigDecimal("50"));
                }
                if(f.getFlightsReservationDetails().get(i).getPassengersData().get(j).isFastTrack() == true) {
                    amount = amount.add(new BigDecimal("20"));
                }
            }
        }
        return amount;
    }

}
