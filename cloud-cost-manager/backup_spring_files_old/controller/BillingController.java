package com.cloud.controller;

import com.cloud.dto.BillingDTO;
import com.cloud.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Billing operations (DISABLED - Using BillingServlet)
 */
// @RestController
// @RequestMapping("/api/billing")
public class BillingController {

    // @Autowired
    // private BillingService billingService;

    /**
     * GET /billing - Get all billing records with optional department filtering
     */
    @GetMapping
    public ResponseEntity<List<BillingDTO>> getAllBilling(@RequestParam(required = false) Long departmentId) {
        return ResponseEntity.ok(billingService.getAllBilling(departmentId));
    }

    /**
     * GET /billing/:id - Get billing by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<BillingDTO> getBillingById(@PathVariable Long id) {
        return ResponseEntity.ok(billingService.getBillingById(id));
    }

    /**
     * POST /billing - Add new billing record
     */
    @PostMapping
    public ResponseEntity<BillingDTO> addBilling(@RequestBody BillingDTO dto) {
        BillingDTO created = billingService.addBilling(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
