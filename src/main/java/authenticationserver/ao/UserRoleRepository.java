package authenticationserver.ao;

import authenticationserver.entities.UserRole;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by igor on 25.02.17.
 */
public interface UserRoleRepository extends Repository<UserRole, Long> {

    void save(UserRole role);

    List<UserRole> findByUserIdAndDenyTimeIsNull(long uId);

    List<UserRole> findByOrganizationIdAndUserIdAndDenyTimeIsNull(long organizationId, long uId);

    UserRole findByOrganizationIdAndDomainIdAndUserIdAndRoleAndDenyTimeIsNull(long organizationId, Long domainId,
                                                                              long userId, UserRole.Roles role);
}
