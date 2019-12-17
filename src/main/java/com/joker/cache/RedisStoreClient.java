package com.joker.cache;

import java.util.List;
import java.util.Map;

public interface RedisStoreClient {

    public <T> Boolean multiSet(Map<StoreKey, T> map, int expireInSeconds);

    public <T> Boolean multiSet(Map<StoreKey, T> map);

    public <T> Map<StoreKey, T> multiGet(List<StoreKey> keys);

    public <T> Map<StoreKey, T> multiGet(List<StoreKey> keys, long timeout);

    Boolean setnx(StoreKey key, Object value, int expireInSeconds);

    Long multiDelete(List<StoreKey> keys);

    Long delete(StoreKey key);
}
