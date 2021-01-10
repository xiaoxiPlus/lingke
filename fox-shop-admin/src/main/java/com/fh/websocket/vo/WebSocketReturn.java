package com.fh.websocket.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class WebSocketReturn {
    private Integer code;

    private String msg;
}
