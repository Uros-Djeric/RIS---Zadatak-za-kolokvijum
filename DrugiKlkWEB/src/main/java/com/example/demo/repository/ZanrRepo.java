package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Zanr;

public interface ZanrRepo extends JpaRepository<Zanr, Integer> {

	@Query("select z from Zanr z inner join z.zanrPredstaves zp where zp.predstava.idPredstava = :idP")
	List<Zanr> findByPredstava(@Param("idP")Integer idP);
}
