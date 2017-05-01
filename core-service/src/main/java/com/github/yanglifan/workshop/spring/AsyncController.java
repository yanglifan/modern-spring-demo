package com.github.yanglifan.workshop.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RequestMapping("async")
@RestController
public class AsyncController {

    private static final String DEFERRED_RESULT = "deferred-result-";
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private AtomicInteger count = new AtomicInteger();

    @GetMapping("simpleDeferredResult")
    public DeferredResult<String> simpleDeferredResult() {
        final DeferredResult<String> simpleDeferredResult = new DeferredResult<>();
        scheduler.schedule(
                () -> simpleDeferredResult.setResult(DEFERRED_RESULT + count.incrementAndGet()),
                30, TimeUnit.SECONDS
        );
        return simpleDeferredResult;
    }
}
