package authenticationserver.ao;

import authenticationserver.entities.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import java.util.UUID;

/**
 * Created by igor on 23.02.17.
 */
public class SessionAO {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private EntityManager em;

    @Value("${sessionTTL:1000000}")
    private long sessionTTLMin;


    public static String generateSessionCode()
    {
        return UUID.randomUUID().toString();
    }


    /**
     * Создает новую сессию, закрывая при этом старую, если она была.
     *
     * @param userId
     * @return хэш сессии для доступа
     */
    public String create(long userId)
    {
        UserSession oldSession = sessionRepository.findByUserId(userId);
        if (oldSession != null)
        {
            sessionRepository.delete(oldSession);
            em.flush();
        }
        return sessionRepository.save(new UserSession(generateSessionCode(), userId)).getSessionCode();
    }

    /**
     * Закрывает сессию пользоваеля.
     *
     * @param userId
     */
    public void close(long userId)
    {
        UserSession session = sessionRepository.findByUserId(userId);
        sessionRepository.delete(session);
    }

    /**
     * Возвращает идентификатор пользователя, которому принадлежит сессия, или null, если данная сессия устарели или
     * не существует
     *
     * @param sessionCode
     * @return
     */
    public Long getSessionUserId(String sessionCode)
    {
        UserSession session = sessionRepository.findBySessionCode(sessionCode);
        return session == null
                || System.currentTimeMillis() - session.getCreationTime().getTime() > sessionTTLMin*60_000
                ? null : session.getUserId();
    }
}
