package cybercooker.scheduleservice.entity.week;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Week {
    private LocalDate startDate;
    private Integer spaceId;
    private Schedule data;

}
