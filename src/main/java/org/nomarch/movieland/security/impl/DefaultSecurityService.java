package org.nomarch.movieland.security.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.UserDao;
import org.nomarch.movieland.dto.LoginInfo;
import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.exception.IncorrectCredentialsException;
import org.nomarch.movieland.security.SecurityService;
import org.nomarch.movieland.security.session.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultSecurityService implements SecurityService {
    private final UserDao userDao;
    private final Map<String, Session> uuidSessionCacheMap = new HashMap<>();
    @Value("${user.uuid.lifetime.in.seconds}")
    private int uuidLifeTimeInSeconds;

    @Override
    public LoginInfo login(@NonNull String email, @NonNull String password) {
        User user = userDao.login(email, password);

        UUID uuid = UUID.randomUUID();
        Session session = Session.builder()
                .user(user)
                .uuidToken(uuid)
                .expiryTime(LocalDateTime.now().plusSeconds(uuidLifeTimeInSeconds))
                .build();

        uuidSessionCacheMap.put(uuid.toString(), session);

        return LoginInfo.builder().uuid(uuid.toString()).nickname(user.getNickname()).build();
    }

    @Override
    public void logout(@NonNull String uuidToken) {
        Session session = uuidSessionCacheMap.remove(uuidToken);
        if (session == null) {
            log.debug("UUID token {} is not found or outdated", uuidToken);
        }
    }

    @Override
    public User findUserByUUIDToken(@NonNull String uuidToken) {
        Session session = uuidSessionCacheMap.get(uuidToken);

        if (session == null || LocalDateTime.now().isAfter(session.getExpiryTime())) {
            uuidSessionCacheMap.remove(uuidToken);
            throw new IncorrectCredentialsException("UUID token is invalid or expired: " + uuidToken);
        }

        return session.getUser();
    }

    @Scheduled(fixedRateString = "${user.uuid.cache.clear.interval}")
    private void clearExpiredSessionsCache() {
        uuidSessionCacheMap.entrySet().removeIf(entry ->
                LocalDateTime.now().isAfter(entry.getValue().getExpiryTime()));
    }
}
