package com.officelunch.repositories;

import com.officelunch.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepo extends JpaRepository<Availability, Integer> {

}
