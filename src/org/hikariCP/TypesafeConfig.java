package org.hikariCP;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.Config;

public class TypesafeConfig {
    private static final Logger log = LoggerFactory.getLogger("TypesafeConfig");

    public static void main(String[] args) {
        // {{start:resource}}
        Config defaultConfig = ConfigFactory.parseResources("defaults.conf");
        // {{end:resource}}

        // {{start:fallback}}
        Config fallbackConfig = ConfigFactory.parseResources("overrides.conf")
                                             .withFallback(defaultConfig)
                                             .resolve();
        // {{end:fallback}}

        // {{start:text}}
        log.info("name: {}", defaultConfig.getString("conf.name"));
        log.info("name: {}", fallbackConfig.getString("conf.name"));
        log.info("title: {}", defaultConfig.getString("conf.title"));
        log.info("title: {}", fallbackConfig.getString("conf.title"));
        // {{end:text}}

        // {{start:resolved}}
        log.info("combined: {}", fallbackConfig.getString("conf.combined"));
        // {{end:resolved}}

        // {{start:durations}}
        //log.info("redis.ttl minutes: {}", fallbackConfig.( TimeUnit.MINUTES)); 
        //log.info("redis.ttl seconds: {}", fallbackConfig.getDuration("redis.ttl", TimeUnit.SECONDS));
        // {{end:durations}}

        // {{start:memorySize}}
        // Any path in the configuration can be treated as a separate Config object.
        Config uploadService = fallbackConfig.getConfig("uploadService");
        log.info("maxChunkSize bytes: {}", uploadService.getBytes("maxChunkSize").byteValue());
        log.info("maxFileSize bytes: {}", uploadService.getBytes("maxChunkSize").byteValue());
        // {{end:memorySize}}

        // {{start:whitelist}}
        List<Integer> whiteList = fallbackConfig.getIntList("conf.nested.whitelistIds");
        log.info("whitelist: {}", whiteList);
        List<String> whiteListStrings = fallbackConfig.getStringList("conf.nested.whitelistIds");
        log.info("whitelist as Strings: {}", whiteListStrings);
        // {{end:whitelist}}


        // {{start:booleans}}
        log.info("yes: {}", fallbackConfig.getBoolean("featureFlags.featureA"));
        log.info("true: {}", fallbackConfig.getBoolean("featureFlags.featureB"));
        // {{end:booleans}}
    }
}