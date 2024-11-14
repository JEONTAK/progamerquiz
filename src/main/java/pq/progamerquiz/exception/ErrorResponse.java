package pq.progamerquiz.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private List<StackTraceElement> stackTrace;
    private String message;
    private HttpStatus status;
}