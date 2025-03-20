package cybercooker.scheduleservice.request;

import cybercooker.scheduleservice.entity.template.TemplateSchedule;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeekTemplateCreateRequest {
    @NotNull
    private int spaceId;
    @NotNull
    private String name;
    @NotNull
    private TemplateSchedule data;
}