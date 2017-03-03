package authenticationserver.ao;

import authenticationserver.entities.DomainParticipant;
import org.springframework.data.repository.Repository;

/**
 * Created by igor on 26.02.17.
 */
public interface DomainParticipantRepository extends Repository<DomainParticipant, Long> {

    DomainParticipant save(DomainParticipant domainParticipant);

    DomainParticipant findById(long id);
}
