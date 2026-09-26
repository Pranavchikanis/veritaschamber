package com.veritaschambers.security;

import com.veritaschambers.exception.RateLimitExceededException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A minimal, in-memory rate limiter to prevent abuse of the AI Chat API.
 * Uses a fixed-window approach.
 */
@Component
public class ChatRateLimiter {

    // Maximum allowed AI requests per IP per minute
    private static final int MAX_REQUESTS_PER_MINUTE = 5;

    private final ConcurrentHashMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();

    /**
     * Checks if the given IP has exceeded the rate limit.
     * Throws an exception if exceeded.
     */
    public void checkLimit(String ipAddress) {
        AtomicInteger count = requestCounts.computeIfAbsent(ipAddress, k -> new AtomicInteger(0));
        
        if (count.incrementAndGet() > MAX_REQUESTS_PER_MINUTE) {
            throw new RateLimitExceededException("Too many chat requests. Please try again later.");
        }
    }

    /**
     * Resets the counts every minute.
     * Note: This requires @EnableScheduling on the application class or configuration.
     */
    @Scheduled(fixedRate = 60000)
    public void resetCounts() {
        requestCounts.clear();
    }
}
