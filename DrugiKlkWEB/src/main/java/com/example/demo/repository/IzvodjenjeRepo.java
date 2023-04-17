package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Izvodjenje;
import model.Predstava;
import model.Scena;

public interface IzvodjenjeRepo extends JpaRepository<Izvodjenje, Integer> {

	
	List<Izvodjenje> findByPredstava(Predstava p);
	
	List<Izvodjenje> findByScena(Scena s);
}
