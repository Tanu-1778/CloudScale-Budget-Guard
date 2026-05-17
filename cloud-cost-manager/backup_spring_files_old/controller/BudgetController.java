package com.cloud.controller;

import com.cloud.dto.BudgetDTO;
import com.cloud.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Budget operations (DISABLED - Using BudgetServlet)
 */
// @RestController
// @RequestMapping("/api/budget")
public class BudgetController {

    // @Autowired
    // private BudgetService budgetService;

    /**
     * GET /budget - Get budget based on role, email, and optional entity IDs
     */
    @GetMapping
    public ResponseEntity<BudgetDTO> getBudget(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long organizationId,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Long employeeId) {
        
        // Prioritize specific IDs if provided
        if (departmentId != null || employeeId != null || organizationId != null) {
            return ResponseEntity.ok(budgetService.getBudgetForEntity(role, organizationId, departmentId, employeeId));
        }
        
        if (role != null && email != null) {
            return ResponseEntity.ok(budgetService.getBudgetForRole(role, email));
        }
        return ResponseEntity.ok(budgetService.getBudget());
    }

    /**
     * POST /budget - Update budget total (optionally for role/entity)
     */
    @PostMapping
    public ResponseEntity<BudgetDTO> setBudget(@RequestBody BudgetDTO dto) {
        if (dto.getOrganizationId() != null || dto.getDepartmentId() != null || dto.getEmployeeId() != null) {
            String role = dto.getOrganizationId() != null ? "admin" : (dto.getDepartmentId() != null ? "manager" : "viewer");
            BudgetDTO updated = budgetService.setBudgetForRole(role, dto.getOrganizationId(), dto.getDepartmentId(), dto.getEmployeeId(), dto.getTotal());
            return ResponseEntity.ok(updated);
        }
        BudgetDTO updated = budgetService.setBudget(dto.getTotal());
        return ResponseEntity.ok(updated);
    }

    /**
     * PUT /budget - Full update (total, spent, thresholds, strategy, entity)
     */
    @PutMapping
    public ResponseEntity<BudgetDTO> updateBudget(@RequestBody BudgetDTO dto) {
        if (dto.getOrganizationId() != null || dto.getDepartmentId() != null || dto.getEmployeeId() != null) {
            BudgetDTO updated = budgetService.updateBudgetFullForRole(dto);
            return ResponseEntity.ok(updated);
        }
        BudgetDTO updated = budgetService.updateBudgetFull(dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * PUT /budget/spent - Update spent amount
     */
    @PutMapping("/spent")
    public ResponseEntity<BudgetDTO> updateSpent(@RequestBody BudgetDTO dto) {
        BudgetDTO updated = budgetService.updateSpent(dto.getSpent());
        return ResponseEntity.ok(updated);
    }
}
