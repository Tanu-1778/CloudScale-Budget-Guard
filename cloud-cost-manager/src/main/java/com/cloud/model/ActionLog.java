package com.cloud.model;

/**
 * ActionLog Entity - Records system actions and alerts
 */
public class ActionLog {

    private Long id;

    private String type;

    private String message;

    private String timestamp;

    private Long createdAt = System.currentTimeMillis();

    public ActionLog() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }
}
