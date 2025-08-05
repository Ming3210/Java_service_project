package ra.java_service_project.model.dto.response;

import lombok.*;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentProgressResponse {
    private Integer courseId;
    private String courseTitle;
    private BigDecimal progressPercentage;
}
