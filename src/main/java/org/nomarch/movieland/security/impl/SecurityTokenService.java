package org.nomarch.movieland.security.impl;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.jdbc.JdbcUserDao;
import org.nomarch.movieland.dto.UserUUID;
import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.exception.IncorrectCredentialsException;
import org.nomarch.movieland.security.SecurityService;
import org.nomarch.movieland.security.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@PropertySource(value = "classpath:application.properties")
public class SecurityTokenService implements SecurityService {
    @Autowired
    private JdbcUserDao jdbcUserDao;
    @Value("${uuid.lifetime.in.seconds}")
    private int uuidLifeTimeInSeconds;
    private final Map<String, Session> uuidSessionCacheMap = new HashMap<>();

    @Override
    public UserUUID login(@NonNull String email, @NonNull String password) {
        User user = jdbcUserDao.login(email, password);

        UUID uuid = UUID.randomUUID();
        Session session = Session.builder()
                .user(user)
                .uuidToken(uuid)
                .expiryTime(LocalDateTime.now().plusSeconds(uuidLifeTimeInSeconds))
                .build();

        uuidSessionCacheMap.put(uuid.toString(), session);

        return UserUUID.builder().uuid(uuid.toString()).nickname(user.getFullName()).build();
    }

    @Override
    public void logout(@NonNull String uuidToken) {
        Session session = uuidSessionCacheMap.remove(uuidToken);
        if (session == null) {
            log.debug("UUID token {} is not found or outdated", uuidToken);
        }
    }

    @Override
    public long findUserIdByUUIDToken(@NonNull String uuidToken) {
        Session session = uuidSessionCacheMap.get(uuidToken);

        if (session == null || LocalDateTime.now().isAfter(session.getExpiryTime())) {
            uuidSessionCacheMap.remove(uuidToken);
            throw new IncorrectCredentialsException("UUID token is invalid or expired: " + uuidToken);
        }

        return session.getUser().getId();
    }

    @Scheduled(fixedRateString = "${user.uuid.cache.clear.interval}")
    private void clearExpiredSessionsCache() {
        for (Map.Entry<String, Session> entry : uuidSessionCacheMap.entrySet()) {
            if (LocalDateTime.now().isAfter(entry.getValue().getExpiryTime())) {
                uuidSessionCacheMap.remove(entry.getKey());
            }
        }
    }
}
