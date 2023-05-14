package com.ecommerceapp.flightreservationservice.repositories;

import com.ecommerceapp.flightreservationservice.models.FlightReservation;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface FlightReservationRepository extends CrudRepository<FlightReservation, ObjectId> {

}
