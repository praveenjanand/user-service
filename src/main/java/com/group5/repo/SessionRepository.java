package com.group5.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.group5.dto.Session;

@Component
@Repository
public interface SessionRepository extends MongoRepository<Session, String> {
	Session findByUsername(String username);
}
