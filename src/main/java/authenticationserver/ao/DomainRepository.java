package authenticationserver.ao;

import authenticationserver.entities.Domain;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by igor on 24.02.17.
 */
public interface DomainRepository extends Repository<Domain, Long> {

    Domain save(Domain domain);

    Domain findById(long id);

    Domain findByOrganizationIdAndName(long organizationId, String name);

    List<Domain> findByOrganizationId(long organizationId);
}
