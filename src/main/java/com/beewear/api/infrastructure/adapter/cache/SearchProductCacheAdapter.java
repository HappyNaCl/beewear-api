package com.beewear.api.infrastructure.adapter.cache;

import com.beewear.api.application.ports.outbound.cache.SearchProductCachePort;
import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class SearchProductCacheAdapter implements SearchProductCachePort {

    private final String CACHE_KEY = "search_products";
    private final RedisTemplate<String, String> redisTemplate;

    private String buildKey(String query, Double minPrice, Double maxPrice,
                            Gender gender, ProductCategory category,
                            Integer size, Integer page) {
        return CACHE_KEY + ":" +
                (query != null ? "query:" + normalizeQuery(query) : "") + ":" +
                (minPrice != null ? "minPrice:" + minPrice : "null") + ":" +
                (maxPrice != null ? "maxPrice:" + maxPrice : "null") + ":" +
                (gender != null ? "gender:" + gender : "null") + ":" +
                (category != null ? "category:" + category : "null") + ":" +
                (size != null ? "size:" + size : "null") + ":" +
                (page != null ? "page:" + page : "null");
    }

    private SetOperations<String, String> setOps() {
        return redisTemplate.opsForSet();
    }


    private String normalizeQuery(String query) {
        return query.replace(' ', '_').toLowerCase().trim();
    }


    @Override
    public void setProducts(String query, Double minPrice, Double maxPrice, Gender gender, ProductCategory productCategory,
                            Integer size, Integer page, List<UUID> productIds) {
        String cacheKey = buildKey(query, minPrice, maxPrice, gender, productCategory, size, page);
        String[] productIdStrings = productIds.stream().map(UUID::toString).toArray(String[]::new);
        setOps().add(cacheKey, productIdStrings);
        redisTemplate.expire(cacheKey, 1, TimeUnit.MINUTES);
    }

    @Override
    public List<UUID> getProducts(String query, Double minPrice, Double maxPrice, Gender gender, ProductCategory productCategory, Integer size, Integer page) {
        String cacheKey = buildKey(query, minPrice, maxPrice, gender, productCategory, size, page);
        Set<String> cachedProductIds = setOps().members(cacheKey);

        if (cachedProductIds == null || cachedProductIds.isEmpty()) {
            return List.of();
        }

        return cachedProductIds.stream().map(UUID::fromString).toList();
    }

    @Override
    public void invalidate(String query, Double minPrice, Double maxPrice, Gender gender, ProductCategory productCategory, Integer size, Integer page) {

    }
}
