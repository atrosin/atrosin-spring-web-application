package com.example.sprdeploy.repository;

import com.example.sprdeploy.entities.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message,Long> {
    List<Message> findByTag(String tag);
}
