package com.officelunch.repositories;

import com.officelunch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface UserRepositories extends JpaRepository<User, Integer> {

     @Query(value = "SELECT * FROM user WHERE username = :username",nativeQuery = true)
     Optional<User> getByUsername( String username);
     User findByUsername(String username);
     Boolean existsByUsername(String username);
     Boolean existsByEmail(String email);

     @Query(value = "SELECT u.username, u.email FROM user u WHERE u.is_employee = 0", nativeQuery = true)
     List<Map<Objects,String>> getAllEmployee();

     @Transactional
     @Modifying
     @Query(value = "UPDATE user u set u.is_employee = 1 where u.email = ?1", nativeQuery = true)
     void deactivateEmployee(String email);

}
