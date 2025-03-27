package cybercooker.scheduleservice.entity.week;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private LocalDate startDate;
    @NotNull
    private Integer spaceId;
    @NotNull
    @Valid
    private Schedule data;

}
