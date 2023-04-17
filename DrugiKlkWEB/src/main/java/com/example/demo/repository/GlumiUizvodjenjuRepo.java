package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.GlumiUIzvodjenju;
import model.Izvodjenje;

public interface GlumiUizvodjenjuRepo extends JpaRepository<GlumiUIzvodjenju, Integer> {

	List<GlumiUIzvodjenju> findByIzvodjenje(Izvodjenje i);
	
	
}
