package com.ecommerceapp.flightreservationservice.kafka_listeners;

import com.ecommerceapp.flightreservationservice.repositories.UserRepository;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FlightReservationListener {


    @Autowired
    UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${KAFKA_TOPIC_2}")
    private String kafkaTopic2;

    @KafkaListener(topics="${KAFKA_TOPIC_1}")
    public void listen(String received_content_string) {
        System.out.println("----------RECEIVED----------");
        JSONObject received_content = new JSONObject(received_content_string);
        String message = received_content.getString("message");
        System.out.println("message: " + message);
        if (message.equals("Create FlightReservation: Checking User")) {
            ObjectId userId = new ObjectId(received_content.getString("userId"));
            ObjectId flightReservationId = new ObjectId(received_content.getString("flightReservationId"));
            System.out.println("flightReservationId: " + flightReservationId);
            System.out.println("userId: " + userId);
            JSONObject content_to_send = new JSONObject();
            String message_to_send;
            if(userRepository.existsById(userId) == true) {
                message_to_send = "UserExists";
            }
            else {
                message_to_send = "UserNotExists";
            }
            content_to_send.put("message", message_to_send);
            content_to_send.put("flightReservationId", flightReservationId);
            System.out.println("----------TO SEND----------");
            System.out.println("message: " + message_to_send);
            System.out.println("flightReservationId: " + flightReservationId);
            kafkaTemplate.send(kafkaTopic2, content_to_send.toString());
        }
    }

}
