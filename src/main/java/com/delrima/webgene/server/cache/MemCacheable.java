package com.delrima.webgene.server.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MemCacheable {

    /**
     * If specified, group name is used as a part of caching key instead of method signature.
     */
    String group() default "";

    /**
     * Determines cache provider. If not specified, group name will be used for cache provider lookup.
     */
    String cache() default "";

    /**
     * Defines expiration for some seconds in the future. Specified value overrides expiration time set by cache provider.
     */
    int expirationSeconds() default 0;
}