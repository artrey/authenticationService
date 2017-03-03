package authenticationserver.ao;

import authenticationserver.entities.Organization;
import authenticationserver.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by igor on 24.02.17.
 */
public class OrganizationAO {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserRoleAO userRoleAO;

    /**
     * Создает организацию.
     *
     * @param name
     * @param description
     */
    public Organization createOrganisation(String name, String description, long creatorUserId)
    {
        if (organizationRepository.findByName(name) != null)
        {
            throw new OrganizationExistsException();
        }

        Organization o = organizationRepository.save(new Organization(name, description, creatorUserId));
        userRoleAO.grantRole(o.getId(), null, creatorUserId, UserRole.Roles.ADMINISTRATOR, creatorUserId);
        return o;
    }


    /**
     * Изменяет статус организации.
     *
     * @param organizationId
     * @param status
     */
    public void changeStatus(long organizationId, Organization.Statuses status)
    {
        Organization organization = organizationRepository.findById(organizationId);
        if (organization == null)
        {
            throw new RuntimeException(String.format("Organization with id %d does not exists", organizationId));
        }

        if (organization.getStatus() == status) return;

        organization.setStatus(status);
        organizationRepository.save(organization);
    }


    /**
     * Возвращает организацию без доменов.
     *
     * @param organizationId
     * @return
     */
    public Organization getById(long organizationId)
    {
        return organizationRepository.findById(organizationId);
    }


    /**
     * Возвращает список организаций в которых у пользователя есть права.
     *
     * @param userId
     * @return
     */
    public List<Organization> getUsersOrganizations(long userId)
    {
        return userRoleAO.getUserRoles(userId).stream().map(UserRole::getOrganizationId).distinct()
                .map(oId -> organizationRepository.findById(oId)).collect(Collectors.toList());
    }


    /**
     * Возвращает список идентификаторов пользователей, учавствующих в организации.
     *
     * @param oId
     * @return
     */
    public List<Long> getOrganizationUsers(long oId)
    {
        return userRoleAO.getOrganizationUsersRoles(oId).stream().map(UserRole::getUserId).distinct()
                .collect(Collectors.toList());
    }


    /**
     * Возвращает true, если указанный пользователь явдяется администратором указанной организации.
     *
     * @param organizationId
     * @param userId
     * @return
     */
    public boolean isAdministrator(long organizationId, long userId)
    {
        return userRoleAO.getOrganizationUserRoles(organizationId, userId).stream()
                .anyMatch(ur -> ur.getRole() == UserRole.Roles.ADMINISTRATOR);
    }

}
