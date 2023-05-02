package com.paymong.management.global.config;

import com.paymong.management.poop.scheduler.PoopScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Component
//@RequiredArgsConstructor
////  implements AsyncConfigurer, SchedulingConfigurer
//public class SchedulerConfig {
//
//    private final PoopScheduler poopScheduler;
//
//    @PostConstruct
//    public void startScheduler(){
//        poopScheduler.startScheduler();
//    }
//
////    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
//////        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
//////        scheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
//////        scheduler.setThreadNamePrefix("PAY-MONG-");
//////        scheduler.initialize();
////        return managementScheduler.startScheduler();
////    }
////
////    @Override
////    public Executor getAsyncExecutor(){
////        System.out.println("Async");
////        return this.threadPoolTaskScheduler();
////    }
////    @Override
////    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
////        System.out.println("config");
////        taskRegistrar.setTaskScheduler(this.threadPoolTaskScheduler());
////    }
//}
