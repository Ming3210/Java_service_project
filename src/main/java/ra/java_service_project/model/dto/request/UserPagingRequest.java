package ra.java_service_project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.java_service_project.utils.RoleName;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPagingRequest {
    private Integer page;
    private Integer itemPage;
    private String sortBy;
    private Boolean orderBy;
}
