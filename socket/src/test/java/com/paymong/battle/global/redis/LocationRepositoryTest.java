package com.paymong.battle.global.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    @Transactional
    void saveTest() {
        locationRepository.save(1L, 128.853664D, 35.094998D);
        locationRepository.save(2L, 128.8539094D, 35.0963382D);
    }

    @Test
    @Transactional
    void geoRadiusByMemberTest(){
        locationRepository.findById(1L).forEach((location) -> {
            System.out.println("location.toString() = " + location.toString());
        });
    }
}