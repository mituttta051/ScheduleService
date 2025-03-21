package cybercooker.scheduleservice.entity.template;

import jakarta.validation.constraints.Min;
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
    private int id;
    @NotNull
    @Min(value = 1, message = "Space id must not be null")
    private int spaceId;
    @NotNull
    private String name;
    @NotNull
    private TemplateSchedule data;
}


