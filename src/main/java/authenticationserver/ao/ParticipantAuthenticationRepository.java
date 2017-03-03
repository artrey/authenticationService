package authenticationserver.ao;

import authenticationserver.entities.ParticipantAuthentication;
import org.springframework.data.repository.Repository;

/**
 * Created by igor on 26.02.17.
 */
public interface ParticipantAuthenticationRepository extends Repository<ParticipantAuthentication, Long> {

    ParticipantAuthentication save(ParticipantAuthentication pa);

    ParticipantAuthentication findByValueAndErrorIsNull(long value);

}
