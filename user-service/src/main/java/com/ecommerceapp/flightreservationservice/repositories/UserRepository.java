package com.ecommerceapp.flightreservationservice.repositories;

import com.ecommerceapp.flightreservationservice.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, ObjectId> {

}
