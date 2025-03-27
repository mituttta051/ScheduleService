package cybercooker.scheduleservice.entity.template;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeekTemplate {
    @NotNull
    private Integer id;
    @NotNull
    private Integer spaceId;
    @NotNull
    private String name;
    @NotNull
    @Valid
    private TemplateSchedule data;
}


