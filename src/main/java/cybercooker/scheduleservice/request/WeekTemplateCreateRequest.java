package cybercooker.scheduleservice.request;

import cybercooker.scheduleservice.entity.template.TemplateSchedule;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeekTemplateCreateRequest {
    @NotNull
    private Integer spaceId;
    @NotNull
    private String name;
    @NotNull
    @Valid
    private TemplateSchedule data;
}