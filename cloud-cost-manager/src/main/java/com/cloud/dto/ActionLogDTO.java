package com.cloud.dto;

/**
 * ActionLog Data Transfer Object
 */
public class ActionLogDTO {
    private Long id;
    private String type;
    private String msg;
    private String time;

    public ActionLogDTO() {}

    public ActionLogDTO(Long id, String type, String msg, String time) {
        this.id = id;
        this.type = type;
        this.msg = msg;
        this.time = time;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}
