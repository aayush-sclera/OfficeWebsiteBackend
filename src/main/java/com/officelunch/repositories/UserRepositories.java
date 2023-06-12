package com.officelunch.repositories;

import com.officelunch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositories extends JpaRepository<User, Integer> {

     @Query(value = "SELECT * FROM user WHERE username = :username",nativeQuery = true)
     Optional<User> getByUsername( String username);
     User findByUsername(String username);
     Boolean existsByUsername(String username);
     Boolean existsByEmail(String email);

}
