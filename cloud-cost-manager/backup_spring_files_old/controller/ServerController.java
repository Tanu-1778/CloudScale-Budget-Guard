package com.cloud.controller;

import com.cloud.dto.ServerDTO;
import com.cloud.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Server operations (DISABLED - Using ServerServlet)
 */
// @RestController
// @RequestMapping("/api/servers")
public class ServerController {

    // @Autowired
    // private ServerService serverService;

    /**
     * GET /servers - Get all servers with optional department filter
     */
    @GetMapping
    public ResponseEntity<List<ServerDTO>> getAllServers(@RequestParam(required = false) Long departmentId) {
        return ResponseEntity.ok(serverService.getAllServers(departmentId));
    }

    /**
     * GET /servers/:id - Get server by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServerDTO> getServerById(@PathVariable Long id) {
        return ResponseEntity.ok(serverService.getServerById(id));
    }

    /**
     * POST /servers - Add new server
     */
    @PostMapping
    public ResponseEntity<ServerDTO> addServer(@RequestBody ServerDTO dto) {
        ServerDTO created = serverService.addServer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /servers/:id - Update server
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServerDTO> updateServer(@PathVariable Long id, @RequestBody ServerDTO dto) {
        ServerDTO updated = serverService.updateServer(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /servers/:id - Delete server
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServer(@PathVariable Long id) {
        serverService.deleteServer(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /servers/status/:status - Get servers by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ServerDTO>> getServersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(serverService.getServersByStatus(status));
    }
}
