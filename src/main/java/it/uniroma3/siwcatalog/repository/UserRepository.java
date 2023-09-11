package it.uniroma3.siwcatalog.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwcatalog.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}