package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Izvodjenje;
import model.Karta;

public interface KartaRepo extends JpaRepository<Karta, Integer> {

	List<Karta> findByIzvodjenje(Izvodjenje i);
	
}
