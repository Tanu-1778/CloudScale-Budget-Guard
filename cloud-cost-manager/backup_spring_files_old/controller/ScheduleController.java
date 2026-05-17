package com.cloud.controller;

import com.cloud.dto.ScheduleDTO;
import com.cloud.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Schedule operations (DISABLED - Using ScheduleServlet)
 */
// @RestController
// @RequestMapping({"/api/schedules", "/api/schedule"})
public class ScheduleController {

    // @Autowired
    // private ScheduleService scheduleService;

    /**
     * GET /schedule - Get current schedule
     */
    @GetMapping
    public ResponseEntity<ScheduleDTO> getSchedule() {
        return ResponseEntity.ok(scheduleService.getSchedule());
    }

    /**
     * PUT /schedule - Update schedule
     */
    @PutMapping
    public ResponseEntity<ScheduleDTO> updateSchedule(@RequestBody ScheduleDTO dto) {
        ScheduleDTO updated = scheduleService.updateSchedule(dto);
        return ResponseEntity.ok(updated);
    }
}
