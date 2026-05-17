package com.cloud.controller;

import com.cloud.dto.DepartmentDTO;
import com.cloud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Department REST Controller (DISABLED - Using DepartmentServlet)
 */
// @RestController
// @RequestMapping("/api/departments")
public class DepartmentController {

    // @Autowired
    // private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO dto) {
        DepartmentDTO created = departmentService.createDepartment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO dto) {
        DepartmentDTO updated = departmentService.updateDepartment(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id) {
        DepartmentDTO dept = departmentService.getDepartment(id);
        return ResponseEntity.ok(dept);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentsByOrganization(@PathVariable Long organizationId) {
        List<DepartmentDTO> depts = departmentService.getDepartmentsByOrganization(organizationId);
        return ResponseEntity.ok(depts);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> depts = departmentService.getAllDepartments();
        return ResponseEntity.ok(depts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
