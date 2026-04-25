package com.newproject.demo.repository;

import com.newproject.demo.entity.Purchase;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	@Query("""
			    SELECT p FROM Purchase p
			    WHERE p.date BETWEEN :startDate AND :endDate
			""")
	List<Purchase> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	@Query("""
			    SELECT p FROM Purchase p
			    WHERE LOWER(p.customerName) LIKE LOWER(CONCAT('%', :customerName, '%'))
			""")
	List<Purchase> findByCustomerName(@Param("customerName") String customerName);

}