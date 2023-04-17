package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Predstava;
import model.Reziser;

public interface PredstavaRepo extends JpaRepository<Predstava, Integer> {

	Predstava findByNaziv(String naziv);
	
	List<Predstava> findByReziser(Reziser r);
}
