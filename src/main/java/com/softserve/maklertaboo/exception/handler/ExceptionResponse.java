package com.softserve.maklertaboo.exception.handler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * ExceptionResponse class with information of occurred exception.
 *
 * @author Roman Blavatskyi
 */
@Data
@NoArgsConstructor
public class ExceptionResponse {

    private String message;

    @JsonIgnore
    private String timeStamp;

    @JsonIgnore
    private String trace;

    @JsonIgnore
    private String path;

    /**
     * Constructor with parameters of {@link ExceptionResponse} entity.
     *
     * @author Roman Blavatskyi
     */
    public ExceptionResponse(Map<String, Object> errorAttributes) {
        this.setMessage((String) errorAttributes.get("message"));
        this.setTimeStamp(errorAttributes.get("timestamp").toString());
        this.setTrace((String) errorAttributes.get("trace"));
        this.setPath((String) errorAttributes.get("path"));
    }
}
