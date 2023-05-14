package com.ecommerceapp.flightreservationservice.kafka_listeners;

import com.ecommerceapp.flightreservationservice.models.FlightReservation;
import com.ecommerceapp.flightreservationservice.repositories.FlightReservationRepository;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserListener {

    @Autowired
    FlightReservationRepository flightReservationRepository;


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${KAFKA_TOPIC_3}")
    private String kafkaTopic3;

    @KafkaListener(topics = "${KAFKA_TOPIC_2}")
    public void listen(String received_content_string) {
        System.out.println("----------RECEIVED----------");
        JSONObject received_content = new JSONObject(received_content_string);
        String message = received_content.getString("message");
        System.out.println("message: " + message);
        ObjectId flightReservationId = new ObjectId(received_content.getString("flightReservationId"));
        System.out.println("flightReservationId: " + flightReservationId);
        if (message.equals("UserExists")) {
                System.out.println("Check userId: OK");
                FlightReservation flightReservation = flightReservationRepository.findById(flightReservationId).get();
                JSONObject content = new JSONObject();
                content.put("message", "Create FlightReservation: Checking Flights");
                List<String> flightsId = new ArrayList<>();
                for(int i=0; i<flightReservation.getFlightsReservationDetails().size(); i++) {
                    flightsId.add(flightReservation.getFlightsReservationDetails().get(i).get_flightId_string());
                }
                String message_to_send = "Create FlightReservation: Checking Flights";
                content.put("message", message_to_send);
                content.put("flightsId", String.join(",", flightsId));
                content.put("flightReservationId", flightReservation.get_id_string());
                System.out.println("----------TO SEND----------");
                System.out.println("message: " + message_to_send);
                System.out.println("flightsId:");
                for(int j=0; j<flightReservation.getFlightsReservationDetails().size(); j++) {
                    System.out.println(flightReservation.getFlightsReservationDetails().get(j).get_flightId_string());
                }
                System.out.println("flightReservationId: " + flightReservation.get_id_string());
                kafkaTemplate.send(kafkaTopic3, content.toString());
        }
        else {
            flightReservationRepository.deleteById(flightReservationId);
            System.out.println("The entered userId does not exist.");
        }
    }

}