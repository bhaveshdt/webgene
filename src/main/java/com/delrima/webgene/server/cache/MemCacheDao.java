package com.delrima.webgene.server.cache;

/*
 * import java.lang.reflect.Method;
 * 
 * import org.aopalliance.intercept.MethodInvocation;
 * 
 * import com.delrima.webgene.server.dao.IFamilyTreeDAO; import com.google.appengine.api.memcache.Expiration; import
 * com.google.appengine.api.memcache.MemcacheService; import com.google.appengine.api.memcache.MemcacheServiceFactory;
 * 
 * public class MemCacheDao implements IFamilyTreeDAO { private MemcacheService memcache;
 * 
 * public MemmCacheDao() { memcache = MemcacheServiceFactory.getMemcacheService(); }
 * 
 * private Object handleMemCacheable(MethodInvocation invocation, MemCacheable options) throws Throwable { Object key =
 * generateKey(invocation.getThis(), invocation.getMethod(), options.group(), invocation.getArguments()); Object result =
 * memcache.get(key); if (result != null) return result; result = invocation.proceed(); putToCache(key, result, options); return
 * result; }
 * 
 * protected boolean putToCache(Object key, Object value, MemCacheable options) { try { if (value == null) return false;
 * Expiration expires = null; if (options.expirationSeconds() > 0) expires =
 * Expiration.byDeltaSeconds(options.expirationSeconds()); MemcacheService.SetPolicy setPolicy =
 * MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT; setPolicy = MemcacheService.SetPolicy.SET_ALWAYS; memcache.put(key, value,
 * expires, setPolicy); return true; } catch (Throwable t) { return false; } }
 * 
 * protected Object generateKey() { // generate key using hash codes of the target method, method arguments, // etc. } }
 */