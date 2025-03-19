package cybercooker.scheduleservice.entity.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeekTemplate {
    private int id;
    private int spaceId;
    private String name;
    private TemplateSchedule data;
}


