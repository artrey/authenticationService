package authenticationserver.ao;

import authenticationserver.entities.Domain;
import authenticationserver.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by igor on 24.02.17.
 */
public class DomainAO {

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private UserRoleAO userRoleAO;


    public void createDomain(long organizationId, String name, String description, long creatorUserId)
    {
        Domain domain = domainRepository.findByOrganizationIdAndName(organizationId, name);
        if (domain != null) throw new DomainExistsException();
        domainRepository.save(new Domain(organizationId, name, description, creatorUserId));
    }

    /**
     * Возвращает домен по идентификатору.
     *
     * @param id
     * @return
     */
    public Domain getById(long id)
    {
        return domainRepository.findById(id);
    }


    /**
     * Возвращает true, если у пользователя есть права в рамках этого домена (у администратора есть права в рамках всех
     * доменов организации).
     *
     * @param dId
     * @param uId
     * @return
     */
    public boolean hasRights(long dId, long uId)
    {
        Domain d = getById(dId);
        if (d == null) return false;

        return userRoleAO.getOrganizationUserRoles(d.getOrganizationId(), uId).stream()
                .anyMatch(ur -> ur.getDomainId() == null || ur.getDomainId() == dId);
    }


    public boolean hasRole(long dId, long uId, UserRole.Roles role)
    {
        Domain d = getById(dId);
        if (d == null) return false;

        return userRoleAO.getOrganizationUserRoles(d.getOrganizationId(), uId).stream()
                .anyMatch(ur -> ur.getDomainId() == dId && ur.getRole() == role);
    }


    public void changeStatus(long domainId, Domain.Statuses status)
    {
        Domain domain = domainRepository.findById(domainId);
        if (domain == null)
        {
            throw new RuntimeException(String.format("Domain with id %d does not exists", domainId));
        }

        if (domain.getStatus() == status) return;

        domain.setStatus(status);
        domainRepository.save(domain);
    }

    /**
     * Возвращает домены организации, на которые у пользователя есть права.
     *
     * @param organizationId
     * @param uId
     * @return
     */
    public List<Domain> getUserDomains(long organizationId, long uId)
    {
        final Long ADMIN = -1L;
        Set<Long> dIds = userRoleAO.getOrganizationUserRoles(organizationId, uId).stream()
                .map(ur -> ur.getDomainId() == null ? ADMIN : ur.getDomainId()).collect(Collectors.toSet());
        return domainRepository.findByOrganizationId(organizationId).stream()
                .filter(d -> dIds.contains(ADMIN) || dIds.contains(d.getId())).collect(Collectors.toList());
    }

}
