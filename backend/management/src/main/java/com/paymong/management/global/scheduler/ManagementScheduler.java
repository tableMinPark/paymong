package com.paymong.management.global.scheduler;

import org.springframework.scheduling.Trigger;

public interface ManagementScheduler {

//    public void stopScheduler(Long mongId){
//        LOGGER.info("{}의 {} scheduler를 중지합니다.", this.getClass().getSimpleName(), mongId);
//        schedulerMap.get(mongId).shutdown();
//    }
//
//    public void startScheduler(Long mongId){
//        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
////        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.initialize();
////      scheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
//
//        // 스케쥴러 시작
//        LOGGER.info("new {}를 추가합니다.", this.getClass().getSimpleName());
////        scheduler.schedule(getRunnable(mongId), getTrigger());
//        schedulerMap.put(mongId, scheduler);
//    }

//    public void startScheduler(Long mongId){
//        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
//        scheduler.initialize();
////        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
////      scheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
//        scheduler.setThreadNamePrefix("hello-" + mongId + "-");
//        // 스케쥴러 시작
//        LOGGER.info("new {}를 추가합니다.", this.getClass().getSimpleName());
//        scheduler.scheduleWithFixedDelay(getRunnable(mongId), Date.from(Instant.now().plusSeconds(30L)), 5L*1000L);
//        schedulerMap.put(mongId, scheduler);
//    }

//    public void addSchedulerByInstant(Long mongId){
//        LOGGER.info("new {}를 추가합니다.", this.getClass().getSimpleName());
//        scheduler.schedule(getRunnable(mongId), getInstant());
//    }

    void stopScheduler(Long mongId);

    void startScheduler(Long mongId);

    Runnable getRunnable(Long mongId);
    Trigger getTrigger();
    Long getDelay();

}
