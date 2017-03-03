package authenticationserver.swagger.api;


import authenticationserver.ao.*;
import authenticationserver.entities.*;
import authenticationserver.swagger.model.*;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-02-24T18:13:23.197Z")

@Transactional(isolation = Isolation.SERIALIZABLE)
@Controller
public class OrganizationsApiController implements OrganizationsApi {

    @Autowired
    private SessionAO sessionAO;

    @Autowired
    private OrganizationAO organizationAO;

    @Autowired
    private DomainAO domainAO;

    @Autowired
    private UserRoleAO userRoleAP;

    @Autowired
    private UserAO userAO;

    @Autowired
    private DomainParticipantAO domainParticipantAO;

    @Autowired
    private WhiteOrganizationAO whiteOrganizationAO;


    @Override
    public ResponseEntity<Void> authenticate(@ApiParam(value = "id of the domain",required=true ) @PathVariable("id") Long id,
                                             @ApiParam(value = "participant id",required=true ) @PathVariable("pId") Long pId,
                                             @ApiParam(value = "current timestamp in seconds encoded with participants private key") @RequestBody String encodedTime,
                                             @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {   // do some magic!
        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        Domain domain = domainAO.getById(id);
        DomainParticipant participant = domainParticipantAO.getById(pId);
        if (domain == null || participant == null)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        Organization organization = organizationAO.getById(domain.getOrganizationId());

        if (!organizationAO.isAdministrator(domain.getOrganizationId(), uId)
                && !domainAO.hasRole(id, uId, UserRole.Roles.AUTHENTICATOR) ||
                organization.getStatus() == Organization.Statuses.INACTIVE ||
                domain.getStatus() == Domain.Statuses.INACTIVE ||
                participant.getStatus() == DomainParticipant.Statuses.INACTIVE)
        {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        if (null != domainParticipantAO.authenticate(pId, encodedTime, uId)) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> changeDomainStatus(@ApiParam(value = "id of the domain",required=true ) @PathVariable("id") Long id,
                                                   @ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status,
                                                   @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {
        status = status.toUpperCase();

        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        Domain domain = domainAO.getById(id);
        if (domain == null)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        if (!organizationAO.isAdministrator(domain.getOrganizationId(), uId))
        {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        Domain.Statuses dStatus;
        if (status.equals("ACTIVE"))
        {
            dStatus = Domain.Statuses.ACTIVE;
        }
        else if (status.equals("INACTIVE"))
        {
            dStatus = Domain.Statuses.INACTIVE;
        }
        else
        {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        domainAO.changeStatus(id, dStatus);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> changeOrganizationStatus(@ApiParam(value = "id of the organization",required=true ) @PathVariable("id") Long id,
                                                         @ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status,
                                                         @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {
        status = status.toUpperCase();

        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        if (organizationAO.getById(id) == null)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        if (!organizationAO.isAdministrator(id, uId))
        {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        Organization.Statuses oStatus;
        if (status.equals("ACTIVE"))
        {
            oStatus = Organization.Statuses.ACTIVE;
        }
        else if (status.equals("INACTIVE"))
        {
            oStatus = Organization.Statuses.INACTIVE;
        }
        else
        {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        organizationAO.changeStatus(id, oStatus);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> changeParticipantStatus(@ApiParam(value = "id of the domain", required = true) @PathVariable("id") Long id,
                                                        @ApiParam(value = "participant id", required = true) @PathVariable("pId") Long pId,
                                                        @ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status,
                                                        @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {
        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        Domain domain = domainAO.getById(id);
        if (domain == null)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        if (!organizationAO.isAdministrator(domain.getOrganizationId(), uId)
                && !domainAO.hasRole(id, uId, UserRole.Roles.DISTRIBUTOR))
        {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        if (null == domainParticipantAO.getById(pId))
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        DomainParticipant.Statuses pStatus;
        if (status.equals("ACTIVE"))
        {
            pStatus = DomainParticipant.Statuses.ACTIVE;
        }
        else if (status.equals("INACTIVE"))
        {
            pStatus = DomainParticipant.Statuses.INACTIVE;
        }
        else
        {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        domainParticipantAO.changeStatus(pId, pStatus);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> createDomain(@ApiParam(value = "id of the organization in wich domain will be created",required=true ) @PathVariable("id") Long id,
                                             @ApiParam(value = "object that represents a new domain. Status and id should be null" ,required=true ) @RequestBody MDomain body,
                                             @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {

        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        if (body.getName() == null)
        {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        Organization organization = organizationAO.getById(id);

        if (organization == null)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        if (organization.getStatus() == Organization.Statuses.INACTIVE || !organizationAO.isAdministrator(id, uId))
        {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        try {
            domainAO.createDomain(id, body.getName(), body.getDescription(), uId);
        } catch(DomainExistsException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> createOrganization(@ApiParam(value = "Object that represents a new organization. Id, status and domains properties should be null" ,required=true ) @RequestBody MOrganization body,
                                                   @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {
        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        if (body.getName() == null)
        {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        try {
            Organization o = organizationAO.createOrganisation(body.getName(), body.getDescription(), uId);
            whiteOrganizationAO.addToWhileList(uId, o.getId());
        } catch(OrganizationExistsException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteDomainById(@ApiParam(value = "id of the domain",required=true ) @PathVariable("id") Long id,
                                                 @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {
        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        Domain domain = domainAO.getById(id);
        if (domain == null)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        if (!organizationAO.isAdministrator(domain.getOrganizationId(), uId))
        {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        domainAO.changeStatus(id, Domain.Statuses.INACTIVE);

        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    public ResponseEntity<Void> deleteOrganizationById(@ApiParam(value = "id of the organization",required=true ) @PathVariable("id") Long id,
                                                       @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {
        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        if (organizationAO.getById(id) == null)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        if (!organizationAO.isAdministrator(id, uId))
        {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        organizationAO.changeStatus(id, Organization.Statuses.INACTIVE);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteParticipant(@ApiParam(value = "id of the domain", required = true) @PathVariable("id") Long id,
                                                  @ApiParam(value = "participant id", required = true) @PathVariable("pId") Long pId,
                                                  @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {
        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        Domain domain = domainAO.getById(id);
        if (domain == null)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        if (!organizationAO.isAdministrator(domain.getOrganizationId(), uId)
                && !domainAO.hasRole(id, uId, UserRole.Roles.DISTRIBUTOR))
        {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        if (null == domainParticipantAO.getById(pId))
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        domainParticipantAO.changeStatus(pId, DomainParticipant.Statuses.INACTIVE);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MDomain> getDomainById(@ApiParam(value = "id of the domain",required=true ) @PathVariable("id") Long id,
                                                 @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {
        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<MDomain>(HttpStatus.UNAUTHORIZED);
        }

        Domain domain = domainAO.getById(id);
        if (domain == null)
        {
            return new ResponseEntity<MDomain>(HttpStatus.NOT_FOUND);
        }

        if (!domainAO.hasRights(id, uId))
        {
            return new ResponseEntity<MDomain>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<MDomain>(domain.toMDomain(), HttpStatus.OK);
    }

    public ResponseEntity<MOrganization> getOrganizationById(@ApiParam(value = "id of organization to return",required=true ) @PathVariable("id") Long id,
                                                             @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {
        Long uId = sessionAO.getSessionUserId(sessionCode);

        Organization organization = organizationAO.getById(id);

        if (organization == null)
        {
            return new ResponseEntity<MOrganization>(HttpStatus.NOT_FOUND);
        }

        MOrganization mOrg = organization.toMOrganization();
        if (uId != null) mOrg.setMDomains(domainAO.getUserDomains(id, uId).stream().map(d -> d.toMDomain()).collect(Collectors.toList()));

        return new ResponseEntity<MOrganization>(mOrg, HttpStatus.OK);
    }

    public ResponseEntity<List<String>> getOrganizationUsers(@ApiParam(value = "id of the organization",required=true ) @PathVariable("id") Long id,
                                                             @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {
        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<List<String>>(HttpStatus.UNAUTHORIZED);
        }

        if (organizationAO.getById(id) == null)
        {
            return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
        }

        if (!organizationAO.isAdministrator(id, uId))
        {
            return new ResponseEntity<List<String>>(HttpStatus.FORBIDDEN);
        }

        List<String> usersLogins = organizationAO.getOrganizationUsers(id).stream()
                .map(userId -> userAO.getById(userId).getLogin()).collect(Collectors.toList());

        return new ResponseEntity<List<String>>(usersLogins, HttpStatus.OK);
    }

    public ResponseEntity<List<MOrganization>> getUserOrganizations(@CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {

        Long uId = sessionAO.getSessionUserId(sessionCode);

        if (uId == null)
        {
            return new ResponseEntity<List<MOrganization>>(HttpStatus.UNAUTHORIZED);
        }

        List<MOrganization> mOrg = organizationAO.getUsersOrganizations(uId).stream().map(o -> o.toMOrganization()).collect(Collectors.toList());
        mOrg.forEach(o -> o.setMDomains(domainAO.getUserDomains(o.getId(), uId).stream().map(d -> d.toMDomain()).collect(Collectors.toList())));

        return new ResponseEntity<List<MOrganization>>(mOrg, HttpStatus.OK);
    }

    public ResponseEntity<UserRoles> getUserRoles(@ApiParam(value = "id of the organization",required=true ) @PathVariable("id") Long id,
                                                  @ApiParam(value = "user login",required=true ) @PathVariable("login") String login,
                                                  @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {
        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<UserRoles>(HttpStatus.UNAUTHORIZED);
        }

        SUser user = userAO.getByLogin(login);
        if(user == null || organizationAO.getById(id) == null)
        {
            return new ResponseEntity<UserRoles>(HttpStatus.NOT_FOUND);
        }

        if (uId != user.getId() && !organizationAO.isAdministrator(id, uId))
        {
            return new ResponseEntity<UserRoles>(HttpStatus.FORBIDDEN);
        }

        List<UserRole> roles = userRoleAP.getOrganizationUserRoles(id, user.getId());
        UserRoles userRoles = new UserRoles();
        userRoles.setIsAdministrator(roles.stream().anyMatch(r -> r.getRole() == UserRole.Roles.ADMINISTRATOR));
        if (!userRoles.getIsAdministrator())
        {
            userRoles.setRoles(roles.stream().map(r -> {
                DomainRole dR = new DomainRole();
                dR.setDomainId(r.getDomainId());
                if (r.getRole() == UserRole.Roles.DISTRIBUTOR)
                {
                    dR.setRole(DomainRole.RoleEnum.DISTRIBUTOR);
                }
                else if(r.getRole() == UserRole.Roles.AUTHENTICATOR)
                {
                    dR.setRole(DomainRole.RoleEnum.AUTHENTICATOR);
                }
                return dR;
            }).collect(Collectors.toList()));
        }

        return new ResponseEntity<UserRoles>(userRoles, HttpStatus.OK);
    }

    public ResponseEntity<Void> patchUserRoles(@ApiParam(value = "id of the organization",required=true ) @PathVariable("id") Long id,
                                               @ApiParam(value = "user login",required=true ) @PathVariable("login") String login,
                                               @ApiParam(value = "roles description" ,required=true ) @RequestBody UserRolesPatch body,
                                               @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {
        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        // Если некорректно заданы параметры
        if (body.getRoles() == null || body.getAction() == null || body.getRoles().getIsAdministrator() == null
                || body.getRoles().getRoles().stream().anyMatch(r -> r.getRole() == null || r.getDomainId() == null))
        {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        SUser user = userAO.getByLogin(login);
        if(user == null || organizationAO.getById(id) == null)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        if (!organizationAO.isAdministrator(id, uId))
        {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        try {

            if (body.getAction() == UserRolesPatch.ActionEnum.ADD) {

                if (!whiteOrganizationAO.isWhiteOrganization(user.getId(), id))
                {
                    return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
                }

                // Добавляем роли

                if (body.getRoles().getIsAdministrator()) {
                    userRoleAP.grantRole(id, null, user.getId(), UserRole.Roles.ADMINISTRATOR, uId);
                } else {
                    body.getRoles().getRoles().forEach(r -> userRoleAP.grantRole(id, r.getDomainId(), user.getId(),
                            r.getRole() == DomainRole.RoleEnum.DISTRIBUTOR ? UserRole.Roles.DISTRIBUTOR
                                    : UserRole.Roles.AUTHENTICATOR, uId));
                }
            }
            else
            {
                // Удаляем роли
                if (body.getRoles().getIsAdministrator()) {
                    userRoleAP.denyRole(id, null, user.getId(), UserRole.Roles.ADMINISTRATOR, uId);
                } else {
                    body.getRoles().getRoles().forEach(ur -> userRoleAP.denyRole(id, ur.getDomainId(), user.getId(),
                            ur.getRole() == DomainRole.RoleEnum.DISTRIBUTOR ? UserRole.Roles.DISTRIBUTOR
                                    : UserRole.Roles.AUTHENTICATOR, uId));
                }

            }
        } catch (UserRoleChangeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> registerParticipant(@ApiParam(value = "id of the domain",required=true ) @PathVariable("id") Long id,
                                                    @ApiParam(value = "if true - returns a qrCode image in body that encodes string organizationName|domainName|participantId|privateKey, default = false. Dispite of this parameter, participantId|privateKey returns in the header 'participant'") @RequestParam(value = "withQR", required = false) Boolean withQR,
                                                    @ApiParam(value = "name of registered participant. If specified then must be unique") @RequestParam(value = "pName", required = false) String pName,
                                                    @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode,
                                                    HttpServletResponse response) {
        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        Domain domain = domainAO.getById(id);
        if (domain == null)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        Organization organization = organizationAO.getById(domain.getOrganizationId());

        if (!organizationAO.isAdministrator(domain.getOrganizationId(), uId)
                && !domainAO.hasRole(id, uId, UserRole.Roles.DISTRIBUTOR) ||
                organization.getStatus() == Organization.Statuses.INACTIVE ||
                domain.getStatus() == Domain.Statuses.INACTIVE)
        {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        DomainParticipantAO.ParticipantIdAndPrivateKey idAndPK = domainParticipantAO.createDomainParticipant(id, pName, uId);

        response.addHeader("participant", idAndPK.toString());

        if (withQR != null && withQR)
        {
            // Отправляем картинку
            try {
                ImageIO.write(QRCodeGenerator.getQRCode(organization.getName() + "|"
                        + domain.getName() + "|" + idAndPK.toString()), "png", response.getOutputStream());
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> setUserRoles(@ApiParam(value = "id of the organization",required=true ) @PathVariable("id") Long id,
                                             @ApiParam(value = "user login",required=true ) @PathVariable("login") String login,
                                             @ApiParam(value = "roles description" ,required=true ) @RequestBody UserRoles body,
                                             @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {
        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        // Если некорректно заданы параметры
        if (body.getIsAdministrator() == null
                || body.getRoles().stream().anyMatch(r -> r.getRole() == null || r.getDomainId() == null))
        {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        SUser user = userAO.getByLogin(login);
        if(user == null || organizationAO.getById(id) == null || body.getRoles().stream()
                .anyMatch(r -> domainAO.getById(r.getDomainId()) == null))
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        if (!organizationAO.isAdministrator(id, uId))
        {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        if ((body.getIsAdministrator() || body.getRoles() != null && !body.getRoles().isEmpty())
                && !whiteOrganizationAO.isWhiteOrganization(user.getId(), id))
        {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        try {
            // Удаляем старые роли
            userRoleAP.getOrganizationUserRoles(id, user.getId())
                    .forEach(ur -> userRoleAP.denyRole(id, ur.getDomainId(), user.getId(), ur.getRole(), uId));


            // Назначаем новые роли

            if (body.getIsAdministrator()) {
                userRoleAP.grantRole(id, null, user.getId(), UserRole.Roles.ADMINISTRATOR, uId);
            } else {
                body.getRoles().forEach(r -> userRoleAP.grantRole(id, r.getDomainId(), user.getId(),
                        r.getRole() == DomainRole.RoleEnum.DISTRIBUTOR ? UserRole.Roles.DISTRIBUTOR
                                : UserRole.Roles.AUTHENTICATOR, uId));
            }

        } catch (UserRoleChangeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
