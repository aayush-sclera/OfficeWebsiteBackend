package com.officelunch.repositories;

import com.officelunch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositories extends JpaRepository<User, Integer> {

     User findByUsername(String username);

}
