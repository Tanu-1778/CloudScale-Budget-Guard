package com.cloud.controller;

import com.cloud.dto.OrganizationDTO;
import com.cloud.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Organization REST Controller (DISABLED - Using OrganizationServlet)
 */
// @RestController
// @RequestMapping("/api/organizations")
public class OrganizationController {

    // @Autowired
    // private OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationDTO> createOrganization(@RequestBody OrganizationDTO dto) {
        OrganizationDTO created = organizationService.createOrganization(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizationDTO> updateOrganization(@PathVariable Long id, @RequestBody OrganizationDTO dto) {
        OrganizationDTO updated = organizationService.updateOrganization(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> getOrganization(@PathVariable Long id) {
        OrganizationDTO org = organizationService.getOrganization(id);
        return ResponseEntity.ok(org);
    }

    @GetMapping
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations() {
        List<OrganizationDTO> orgs = organizationService.getAllOrganizations();
        return ResponseEntity.ok(orgs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.noContent().build();
    }
}
