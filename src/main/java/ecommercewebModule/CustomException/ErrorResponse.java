package ecommercewebModule.CustomException;



import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse {

    private String message;
    private HttpStatus status;
    private LocalDateTime dateAndTime;
    private boolean success;
}
