package ra.java_service_project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoursePagingRequest {
    private Integer page;
    private Integer itemPage;
    private String sortBy;
    private Boolean orderBy;
}
