package authenticationserver.ao;

import authenticationserver.entities.ServerUser;
import org.springframework.data.repository.Repository;

/**
 * Created by igor on 24.02.17.
 */
public interface UserRepository extends Repository<ServerUser, String> {

    ServerUser save(ServerUser serverUser);

    ServerUser findByLogin(String login);

    ServerUser findById(long id);
}
