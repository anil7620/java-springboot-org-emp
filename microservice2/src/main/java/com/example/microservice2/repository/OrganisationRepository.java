package com.example.microservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservice2.model.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation, Integer>{

}
