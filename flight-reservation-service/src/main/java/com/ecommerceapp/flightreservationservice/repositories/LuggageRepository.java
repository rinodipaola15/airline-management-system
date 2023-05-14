package com.ecommerceapp.flightreservationservice.repositories;

import com.ecommerceapp.flightreservationservice.models.Luggage;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface LuggageRepository extends CrudRepository<Luggage, ObjectId> {

}
