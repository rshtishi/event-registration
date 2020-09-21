package com.github.rshtishi.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.rshtishi.app.entity.Attendee;

@Repository
public interface AttendeeRepository extends CrudRepository<Attendee, Integer> {

	public List<Attendee> findAll();

	public Attendee findById(int id);

}
