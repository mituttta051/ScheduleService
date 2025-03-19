package cybercooker.scheduleservice.entity.template;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeekTemplate {
    private int id;
    private int spaceId;
    private String name;
    private TemplateSchedule data;
}


