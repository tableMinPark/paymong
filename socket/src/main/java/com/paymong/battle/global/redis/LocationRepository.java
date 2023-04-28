package com.paymong.battle.global.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
public class LocationRepository {
    @Value("${battle.max_distance}")
    private Integer MAX_DISTANCE;
    @Value("${battle.max_search}")
    private Integer MAX_SEARCH;
    private RedisTemplate redisTemplate;

    public LocationRepository( @Qualifier("locationRedisTemplate") RedisTemplate redisTemplate ){
        this.redisTemplate = redisTemplate;
    }

    public void save(Long characterId, Double latitude, Double longitude) {
        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo();
        geoOperations.add("characterLocation", new Point(longitude, latitude), characterId.toString());
    }

    public List<String> findById(Long characterId) {
        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo();

        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResultList
                = geoOperations.radius("characterLocation", characterId.toString(),
                        new Distance(MAX_DISTANCE, RedisGeoCommands.DistanceUnit.METERS),
                            RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                                    .limit(MAX_SEARCH)
                                    .includeDistance()
                                    .sortAscending());

        return geoResultList.getContent().stream()
                            .map(geoLocationGeoResult -> geoLocationGeoResult.getContent().getName())
                            .collect(Collectors.toList());
    }

    public void remove(Long characterId) {
        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo();
        geoOperations.remove("characterLocation", characterId.toString());
    }

    public void removeAll() {
        redisTemplate.expire("characterLocation", -1, TimeUnit.MILLISECONDS);
    }
}
