package cybercooker.scheduleservice.grpc;

import cybercooker.scheduleservice.entity.dto.RecipeDTO;
import cybercooker.scheduleservice.entity.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("recipeGrpcClientProxy")
public class RecipeGrpcClientProxy implements RecipeGrpcClientInterface {
    @Autowired
    private RecipeGrpcClient recipeGrpcClient;
    private final Map<Integer, Map<Filter, List<RecipeDTO>>> cache = new HashMap<>();

    @Override
    public List<RecipeDTO> getRecipesByFilter(Filter filter, int spaceId) {
        if (cache.containsKey(spaceId) && cache.get(spaceId).containsKey(filter)) {
            return cache.get(spaceId).get(filter);
        }
        List<RecipeDTO> recipeDTOList = recipeGrpcClient.getRecipesByFilter(filter, spaceId);
        cache.computeIfAbsent(spaceId, k -> new HashMap<>()).put(filter, recipeDTOList);

        return recipeDTOList;
    }
}
