package com.group5.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.group5.dto.*;

@Component
@Repository
public interface UserRepository extends MongoRepository<User, String> {
	User findByUsername(String username);
    User findByEmail(String email);
}
