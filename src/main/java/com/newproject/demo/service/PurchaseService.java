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

    public Purchase update(Long id, Purchase updateData){
        Purchase existing = repository.findById(id).orElseThrow(()-> new RuntimeException("Record Not Found"));
        existing.setCustomerName(updateData.getCustomerName());
        existing.setProductName(updateData.getProductName());
        existing.setQuantity(updateData.getQuantity());
        existing.setPrice(updateData.getPrice());
        existing.setAmount(updateData.getAmount());
        existing.setDate(updateData.getDate());

        return repository.save(existing);
    }

    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Record Not Found");

        }
        repository.deleteById(id);
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