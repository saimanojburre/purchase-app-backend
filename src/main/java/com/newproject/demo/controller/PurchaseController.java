package com.newproject.demo.controller;

import com.newproject.demo.entity.Purchase;
import com.newproject.demo.service.PurchaseService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@CrossOrigin(origins = "*") // allow Angular
public class PurchaseController {

	private final PurchaseService service;

	public PurchaseController(PurchaseService service) {
		this.service = service;
	}

	@PostMapping
	public Purchase createPurchase(@RequestBody Purchase purchase) {
		return service.savePurchase(purchase);
	}

	@GetMapping
	public List<Purchase> getAllPurchases() {
		return service.getAllPurchases();
	}

    @PutMapping("/{id}")
    public ResponseEntity<Purchase> update(@PathVariable Long id, @RequestBody Purchase purchase){
        return  ResponseEntity.ok(service.update(id, purchase));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok("Deleted");
    }

	// 🔹 API 1 → Date filter
	@GetMapping("/by-date")
	public List<Purchase> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,

			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return service.getByDate(startDate, endDate);
	}

	// 🔹 API 2 → Name filter
	@GetMapping("/by-name")
	public List<Purchase> getByName(@RequestParam String customerName) {

		if (customerName == null || customerName.trim().isEmpty()) {
			throw new RuntimeException("Customer name is required");
		}

		return service.getByName(customerName);
	}
}