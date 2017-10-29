package me.codetalk.cache.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisCallback;

/**
 * 
 * @author guobxu
 *
 */
public interface ICacheService {

    public boolean exists(String key);

    public boolean set(byte[] key, byte[] value, long activeTime);

    public boolean set(String key, Object obj, long activeTime);

    public boolean set(String key, Object obj);

    public long delete(String key);

    public Object get(String key);
    
    // hash
    public Object hGet(String hash, String key);
    
    public List<Object> hMGet(String hash, List<String> keys);
    
    public List<Object> hMGet(String hash, String[] keys);
  
    // 返回值仅用于判断是新增加键值对, 或是更新了已有键值对
    public boolean hSet(String hash, String key, Object obj);

    public Object doCallback(RedisCallback callback);
    
    public Set<String> hKeys(String hash);
    
    public void hDel(String hash, String key);

}