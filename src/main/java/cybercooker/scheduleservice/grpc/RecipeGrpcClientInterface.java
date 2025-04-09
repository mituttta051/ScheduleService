package cybercooker.scheduleservice.grpc;

import cybercooker.scheduleservice.entity.dto.RecipeDTO;
import cybercooker.scheduleservice.entity.filter.Filter;

import java.util.List;

public interface RecipeGrpcClientInterface {
    List<RecipeDTO> getRecipesByFilter(Filter filter, int spaceId);
}
