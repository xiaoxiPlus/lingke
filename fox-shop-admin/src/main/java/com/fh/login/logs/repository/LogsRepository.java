package com.fh.login.logs.repository;

import com.fh.login.logs.entity.Logs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepository extends MongoRepository<Logs,String> {
}
