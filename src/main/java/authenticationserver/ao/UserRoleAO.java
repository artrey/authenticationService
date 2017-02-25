package authenticationserver.ao;

import authenticationserver.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by igor on 25.02.17.
 */
public class UserRoleAO {

    @Autowired
    private UserRoleRepository userRoleRepository;


    public List<UserRole> getUserRoles(long uId)
    {
        return userRoleRepository.findByUserIdAndDenyTimeIsNull(uId);
    }


    public List<UserRole> getOrganizationUserRoles(long organizationId, long uId)
    {
        return userRoleRepository.findByOrganizationIdAndUserIdAndDenyTimeIsNull(organizationId, uId);
    }


    public void grantRole(long organizationId, Long dId, long uId, UserRole.Roles role, long granterUserId)
    {
        Long domainId = role == UserRole.Roles.ADMINISTRATOR ? null : dId;

        List<UserRole> roles = userRoleRepository.findByOrganizationIdAndUserIdAndDenyTimeIsNull(organizationId, uId);

        // Администратору нельзя предоставить другие права
        if (roles.stream().anyMatch(r -> r.getRole() == UserRole.Roles.ADMINISTRATOR
                || Objects.equals(domainId, r.getDomainId()) && role == r.getRole())) return;

        // Если роль - администратор - закрываем все предыдущие роли
        Date currentDate = new Date();
        if (role == UserRole.Roles.ADMINISTRATOR)
        {
            roles.forEach(r -> {r.setDenyTime(currentDate); r.setDenierUserId(granterUserId); userRoleRepository.save(r);});
        }

        userRoleRepository.save(new UserRole(uId, organizationId, domainId, role, granterUserId));
    }


    public void denyRole(long organizationId, Long dId, long uId, UserRole.Roles role, long denierUserId)
    {
        Long domainId = role == UserRole.Roles.ADMINISTRATOR ? null : dId;

        if (role == UserRole.Roles.ADMINISTRATOR && uId == denierUserId)
        {
            throw new RuntimeException("Administrator cant deny his own role");
        }

        UserRole ur = userRoleRepository.findByOrganizationIdAndDomainIdAndUserIdAndRoleAndDenyTimeIsNull(organizationId, domainId, uId, role);
        if (ur == null)
        {
            return;
        }
        ur.setDenyTime(new Date());
        ur.setDenierUserId(denierUserId);

        userRoleRepository.save(ur);
    }

}
