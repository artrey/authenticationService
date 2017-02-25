package authenticationserver.ao;

import authenticationserver.entities.Organization;
import org.springframework.data.repository.Repository;

/**
 * Created by igor on 24.02.17.
 */
public interface OrganizationRepository extends Repository<Organization, Long> {

    Organization save(Organization organization);

    Organization findById(long id);

    Organization findByName(String name);
}
