package com.newproject.demo.service;

import com.newproject.demo.entity.Purchase;
import com.newproject.demo.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseService {

	private final PurchaseRepository repository;

	public PurchaseService(PurchaseRepository repository) {
		this.repository = repository;
	}

	public Purchase savePurchase(Purchase purchase) {
		purchase.setAmount(purchase.getQuantity() * purchase.getPrice());
		purchase.setDate(LocalDate.now());
		return repository.save(purchase);
	}

	public List<Purchase> getAllPurchases() {
		return repository.findAll();
	}

	// API 1
	public List<Purchase> getByDate(LocalDate startDate, LocalDate endDate) {
		return repository.findByDateRange(startDate, endDate);
	}

	// API 2
	public List<Purchase> getByName(String customerName) {
		return repository.findByCustomerName(customerName);
	}
}