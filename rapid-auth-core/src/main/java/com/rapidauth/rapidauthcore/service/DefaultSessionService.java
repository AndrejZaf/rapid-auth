package com.rapidauth.rapidauthcore.service;

import com.rapidauth.rapidauthcore.factory.AuthSessionFactory;
import com.rapidauth.rapidauthcore.model.AuthSession;
import com.rapidauth.rapidauthcore.model.AuthUser;
import com.rapidauth.rapidauthcore.model.CreateSessionRequest;
import com.rapidauth.rapidauthcore.repository.AuthSessionRepository;
import com.rapidauth.rapidauthcore.util.SecureRandomGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class DefaultSessionService<S extends AuthSession> implements SessionService<S> {

    private final AuthSessionRepository<S> sessionRepository;
    private final AuthSessionFactory<S> sessionFactory;
    private final SecureRandomGenerator randomGenerator;

    @Value("${spring.auth.session.duration:7d}")
    private Duration sessionDuration;

    @Override
    public S createSession(AuthUser user, SessionContext context) {
        log.debug("Creating session for user: {}", user.getId());

        // Generate secure token
        String token = randomGenerator.generateToken();

        // Calculate expiration
        Instant expiresAt = Instant.now().plus(sessionDuration);

        // Create session using factory
        S session = sessionFactory.create(CreateSessionRequest.builder()
                .userId(user.getId())
                .token(token)
                .expiresAt(expiresAt)
                .ipAddress(context.getIpAddress())
                .userAgent(context.getUserAgent())
                .build());

        // Save session
        S savedSession = sessionRepository.save(session);

        log.info("Session created for user: {}, expires at: {}", user.getId(), expiresAt);

        return savedSession;
    }

    @Override
//    @Transactional(readOnly = true)
    public Optional<S> getSession(String token) {
        log.debug("Getting session by token");

        return sessionRepository.findByToken(token)
                .filter(session -> {
                    if (session.isExpired()) {
                        log.debug("Session expired: {}", session.getId());
                        return false;
                    }
                    return true;
                });
    }

    @Override
//    @Transactional(readOnly = true)
    public Optional<S> findById(String id) {
        log.debug("Finding session by id: {}", id);
        return sessionRepository.findById(id);
    }

    @Override
//    @Transactional(readOnly = true)
    public List<S> getUserSessions(String userId) {
        log.debug("Getting sessions for user: {}", userId);
        return sessionRepository.findByUserId(userId);
    }

    @Override
    public void invalidateSession(String token) {
        log.debug("Invalidating session by token");

        sessionRepository.findByToken(token)
                .ifPresent(session -> {
                    sessionRepository.deleteById(session.getId());
                    log.info("Session invalidated: {}", session.getId());
                });
    }

    @Override
    public void invalidateSessionById(String id) {
        log.debug("Invalidating session by id: {}", id);
        sessionRepository.deleteById(id);
        log.info("Session invalidated: {}", id);
    }

    @Override
    public void invalidateAllUserSessions(String userId) {
        log.debug("Invalidating all sessions for user: {}", userId);
        sessionRepository.deleteByUserId(userId);
        log.info("All sessions invalidated for user: {}", userId);
    }

    @Override
    public int cleanupExpiredSessions() {
        log.debug("Cleaning up expired sessions");
        int deleted = sessionRepository.deleteExpiredSessions();
        log.info("Cleaned up {} expired sessions", deleted);
        return deleted;
    }

    @Override
    public S refreshSession(String token) {
        log.debug("Refreshing session");

        S session = getSession(token)
                .orElseThrow(() -> new RuntimeException("Session not found or expired"));

        // Create new session with extended expiration
        // Note: This creates a new session with a new token
        // Developers can override this to update the existing session instead
        Instant newExpiresAt = Instant.now().plus(sessionDuration);

        S newSession = sessionFactory.create(CreateSessionRequest.builder()
                .userId(session.getUserId())
                .token(randomGenerator.generateToken())
                .expiresAt(newExpiresAt)
                .ipAddress(session.getIpAddress())
                .userAgent(session.getUserAgent())
                .build());

        // Delete old session
        sessionRepository.deleteById(session.getId());

        // Save new session
        S savedSession = sessionRepository.save(newSession);

        log.info("Session refreshed for user: {}", session.getUserId());

        return savedSession;
    }

    /**
     * Scheduled task to cleanup expired sessions.
     * Runs daily at 2 AM by default.
     * Can be configured via: spring.auth.session.cleanup-cron
     */
    @Scheduled(cron = "${spring.auth.session.cleanup-cron:0 0 2 * * ?}")
    public void scheduledCleanup() {
        log.info("Running scheduled session cleanup");
        cleanupExpiredSessions();
    }
}
