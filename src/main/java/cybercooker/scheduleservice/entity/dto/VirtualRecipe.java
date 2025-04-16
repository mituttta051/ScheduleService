package cybercooker.scheduleservice.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VirtualRecipe {
    private int id;
    private int portionsLeft;
    private int daysTillExpiry;
}
