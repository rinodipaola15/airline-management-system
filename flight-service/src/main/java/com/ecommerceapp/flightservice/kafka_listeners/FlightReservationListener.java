package com.ecommerceapp.flightservice.kafka_listeners;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.ecommerceapp.flightservice.repositories.FlightRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FlightReservationListener {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${KAFKA_TOPIC_4}")
    private String kafkaTopic4;

    @KafkaListener(topics = "${KAFKA_TOPIC_3}")
    public void listen(String received_content_string) {
        System.out.println("----------RECEIVED----------");
        JSONObject received_content = new JSONObject(received_content_string);
        String message = received_content.getString("message");
        System.out.println("message: " + message);
        if (message.equals("Create FlightReservation: Checking Flights")) {
            JSONObject content_to_send = new JSONObject();
            ObjectId flightReservationId = new ObjectId(received_content.getString("flightReservationId"));
            System.out.println("flightReservationId: " + flightReservationId);
            System.out.println("flightsId:");
            String flightsId_string = received_content.getString("flightsId");
            List<String> flightsId = new ArrayList<>(Arrays.asList(flightsId_string.split(",")));
            for(int i=0; i<flightsId.size(); i++) {
                System.out.println(flightsId.get(i));
            }
            boolean flightsExist = true;
            for(int j=0; j<flightsId.size(); j++) {
                if(flightRepository.existsById(new ObjectId(flightsId.get(j))) == false) {
                    flightsExist = false;
                }
            }
            String message_to_send;
            List<BigDecimal> flightsPrice = new ArrayList<>();
            if(flightsExist == true) {
                message_to_send = "FlightsExist";
                for(int k=0; k<flightsId.size(); k++) {
                    flightsPrice.add(flightRepository.findById(new ObjectId(flightsId.get(k))).get().getPrice());
                }
            }
            else {
                message_to_send = "FlightsNotExist";
            }
            content_to_send.put("message", message_to_send);
            content_to_send.put("flightReservationId", flightReservationId);
            List<String> flightsPrice_string = new ArrayList<>();
            for(BigDecimal flight_price : flightsPrice) {
                flightsPrice_string.add(flight_price.toString());
            }
            content_to_send.put("flightsPrice", String.join(",", flightsPrice_string));
            System.out.println("----------TO SEND----------");
            System.out.println("message: " + message_to_send);
            System.out.println("flightReservationId: " + flightReservationId);
            System.out.println("flightsPrice:");
            for(int p=0; p<flightsPrice.size(); p++) {
                System.out.println(flightsPrice.get(p));
            }
            kafkaTemplate.send(kafkaTopic4, content_to_send.toString());
        }
    }

}
