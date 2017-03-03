package authenticationserver.ao;

import authenticationserver.entities.SUser;
import org.springframework.data.repository.Repository;

/**
 * Created by igor on 24.02.17.
 */
public interface UserRepository extends Repository<SUser, String> {

    SUser save(SUser sUser);

    SUser findByLogin(String login);

    SUser findById(long id);
}
