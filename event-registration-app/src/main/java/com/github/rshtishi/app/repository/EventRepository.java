package com.github.rshtishi.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.rshtishi.app.entity.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
	
	public List<Event> findAll();
	
	public Event findById(int id);

}
