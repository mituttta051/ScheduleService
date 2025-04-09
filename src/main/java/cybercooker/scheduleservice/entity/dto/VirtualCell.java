package cybercooker.scheduleservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VirtualCell {
    int depth;
    RecipeDTO recipe;
    List<RecipeDTO> candidates;
    VirtualCell parent;
    boolean isCookedHere;
    boolean locked; //not considering
}
