package com.mutantsparade.mutantdetected.web.rest.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class MutantDetectedAsyncConfig extends AsyncConfigurerSupport {

    private static final Logger log = LoggerFactory.getLogger(MutantDetectedAsyncConfig.class);

    /**
     * Build the threads pool used by the async method executions.
     *
     * NOTE that pool sizes should be optimized based on load and performance testing.
     *
     * @return The thread pool for async executions.
     */
    @Override
    public Executor getAsyncExecutor() {
        super.getAsyncExecutor();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1000);
        executor.setMaxPoolSize(1000);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }

    /**
     * This method handle uncaught exceptions thrown be async method executions.
     *
     *  This implementation only logs the error, other implementations might execute
     *  another compensation actions.
     *
     * @return the exception handler used to manage async executions uncaught exceptions.
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {

        return (throwable, method, obj)->{
            log.error("Exception Caught in Thread - " + Thread.currentThread().getName());
            log.error("Exception message - " + throwable.getMessage());
            log.error("Method name - " + method.getName());
            for (Object param : obj) {
                log.error("Parameter value - " + param);
            }
        };

    }

}
