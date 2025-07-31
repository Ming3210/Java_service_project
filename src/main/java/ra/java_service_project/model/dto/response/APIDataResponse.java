package ra.java_service_project.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIDataResponse <T>{
    private Boolean success;
    private String message;
    private T data;
    private HttpStatus status;
}
