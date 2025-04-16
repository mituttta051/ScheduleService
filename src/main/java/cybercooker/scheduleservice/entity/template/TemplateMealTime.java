package cybercooker.scheduleservice.entity.template;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class TemplateMealTime {
    @NotEmpty
    private String name;
    @NotNull
    private Boolean canCook;
    @NotNull
    @Valid
    List<TemplateMealSlot> mealSlots;
}
