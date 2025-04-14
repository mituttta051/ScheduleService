package cybercooker.scheduleservice.mapper;

import cybercooker.recipeservice.grpc.filter.*;
import cybercooker.recipeservice.grpc.recipe.GetRecipesByFilterGrpc;
import cybercooker.scheduleservice.entity.filter.*;

import java.util.stream.Collectors;

public class FilterMapper {
    public static GetRecipesByFilterGrpc toGetRecipesByFilterGrpc(Filter filter, int spaceId) {
        GetRecipesByFilterGrpc.Builder request = GetRecipesByFilterGrpc.newBuilder();

        request.setSpaceId(spaceId);
        request.setFilter(toFilterGrpc(filter));

        return request.build();
    }

    private static FilterGrpc toFilterGrpc(Filter filter) {
        return switch (filter) {
            case ServingsNumberFilter servingsNumberFilter -> toServingsNumberFilterGrpc(servingsNumberFilter);
            case CookTimeFilter cookTimeFilter -> toCookTimeFilterGrpc(cookTimeFilter);
            case ContainsTagFilter containsTagFilter -> toContainsTagFilterGrpc(containsTagFilter);
            case AndFilter andFilter -> toAndFilterGrpc(andFilter);
            case OrFilter orFilter -> toOrFilterGrpc(orFilter);
            default -> throw new IllegalArgumentException("Unknown filter type: " + filter.getClass().getName());
        };
    }

    private static FilterGrpc toOrFilterGrpc(OrFilter orFilter) {
        OrFilterGrpc orFilterGrpc = OrFilterGrpc.newBuilder()
                .addAllFilters(orFilter.getFilters().stream()
                        .map(FilterMapper::toFilterGrpc)
                        .collect(Collectors.toList()))
                .build();
        return FilterGrpc.newBuilder().setOr(orFilterGrpc).build();
    }

    private static FilterGrpc toAndFilterGrpc(AndFilter andFilter) {
        AndFilterGrpc andFilterGrpc = AndFilterGrpc.newBuilder()
                .addAllFilters(andFilter.getFilters().stream()
                        .map(FilterMapper::toFilterGrpc)
                        .collect(Collectors.toList()))
                .build();
        return FilterGrpc.newBuilder().setAnd(andFilterGrpc).build();
    }

    private static FilterGrpc toContainsTagFilterGrpc(ContainsTagFilter containsTagFilter) {
        ContainsTagFilterGrpc containsTagFilterGrpc = ContainsTagFilterGrpc.newBuilder()
                .setTagId(containsTagFilter.getTagId())
                .setTagValue(containsTagFilter.getTagValue())
                .build();
        return FilterGrpc.newBuilder().setContainsTag(containsTagFilterGrpc).build();
    }

    private static FilterGrpc toCookTimeFilterGrpc(CookTimeFilter cookTimeFilter) {
        CookTimeFilterGrpc cookTimeFilterGrpc = CookTimeFilterGrpc.newBuilder()
                .setMin(cookTimeFilter.getMinCookTime())
                .setMax(cookTimeFilter.getMaxCookTime())
                .build();
        return FilterGrpc.newBuilder().setCookTime(cookTimeFilterGrpc).build();
    }

    private static FilterGrpc toServingsNumberFilterGrpc(ServingsNumberFilter servingsNumberFilter) {
        ServingsNumberFilterGrpc servingsNumberFilterGrpc = ServingsNumberFilterGrpc.newBuilder()
                .setMin(servingsNumberFilter.getMinServingsNumber())
                .setMax(servingsNumberFilter.getMaxServingsNumber())
                .build();
        return FilterGrpc.newBuilder().setServingsNumber(servingsNumberFilterGrpc).build();
    }
}
