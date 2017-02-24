package authenticationserver.ao;

import authenticationserver.entities.UserSession;
import org.springframework.data.repository.Repository;

/**
 * Created by igor on 24.02.17.
 */
public interface SessionRepository extends Repository<UserSession, String> {

    UserSession findBySessionCode(String sessionCode);

    UserSession findByUserId(long userId);

    void delete(UserSession userSession);

    UserSession save(UserSession userSession);
}
