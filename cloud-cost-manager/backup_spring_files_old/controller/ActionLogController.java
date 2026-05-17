package com.cloud.controller;

import com.cloud.dto.ActionLogDTO;
import com.cloud.service.ActionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for ActionLog operations (DISABLED - Using Servlets instead)
 */
// @RestController
// @RequestMapping({"/api/action-logs", "/api/logs"})
public class ActionLogController {

    // @Autowired
    // private ActionLogService actionLogService;

    /**
     * GET /logs - Get all logs
     */
    @GetMapping
    public ResponseEntity<List<ActionLogDTO>> getAllLogs() {
        return ResponseEntity.ok(actionLogService.getAllLogs());
    }

    /**
     * GET /logs/latest - Get latest logs (default 50)
     */
    @GetMapping("/latest")
    public ResponseEntity<List<ActionLogDTO>> getLatestLogs(
            @RequestParam(defaultValue = "50") int limit) {
        return ResponseEntity.ok(actionLogService.getLatestLogs(limit));
    }

    /**
     * POST /logs - Add new log entry
     */
    @PostMapping
    public ResponseEntity<ActionLogDTO> addLog(@RequestBody ActionLogDTO dto) {
        ActionLogDTO created = actionLogService.addLog(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
