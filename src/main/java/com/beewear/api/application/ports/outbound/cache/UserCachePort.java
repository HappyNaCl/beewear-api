package com.beewear.api.application.ports.outbound.cache;

import com.beewear.api.domain.entities.User;

public interface UserCachePort {
    void invalidate(String userId);
    void setUser(User user);
    User getUser(String userId);
}