package com.beewear.api.application.ports.outbound.cache;

import java.util.Set;

public interface RecentProductsCachePort {
    void addProduct(String productId, Long createdAt);
    Set<String> getRecentProducts(int limit);
    Set<String> getRecentProducts(int limit, Long lastTimestamp);
}
