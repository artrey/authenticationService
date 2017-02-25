package authenticationserver.swagger.api;


import authenticationserver.ao.*;
import authenticationserver.entities.Domain;
import authenticationserver.entities.Organization;
import authenticationserver.swagger.model.MDomain;
import authenticationserver.swagger.model.MOrganization;
import authenticationserver.swagger.model.UserRoles;
import authenticationserver.swagger.model.UserRolesPatch;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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


    public ResponseEntity<Void> authenticate(@ApiParam(value = "name of the organization",required=true ) @PathVariable("oName") String oName,
        @ApiParam(value = "name of the domain",required=true ) @PathVariable("dName") String dName,
        @ApiParam(value = "participant id",required=true ) @PathVariable("pId") Long pId,
        @ApiParam(value = "current timestamp in seconds encoded with participants private key") @RequestParam(value = "encodedTime", required = false) String encodedTime) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> changeDomainStatus(@ApiParam(value = "id of the organization",required=true ) @PathVariable("oId") Long oId,
        @ApiParam(value = "id of the domain",required=true ) @PathVariable("id") Long id,
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

    public ResponseEntity<Void> changeParticipantStatus(@ApiParam(value = "name of the organization",required=true ) @PathVariable("oName") String oName,
        @ApiParam(value = "name of the domain",required=true ) @PathVariable("dName") String dName,
        @ApiParam(value = "participant id",required=true ) @PathVariable("pId") Long pId,
        @ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "state", required = true) String state) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> createDomain(@ApiParam(value = "id of the organization in wich domain will be created",required=true ) @PathVariable("oId") Long oId,
        @ApiParam(value = "object that represents a new domain. Status should be null" ,required=true ) @RequestBody MDomain body,
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

        Organization organization = organizationAO.getById(oId);

        if (organization == null)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        if (organization.getStatus() == Organization.Statuses.INACTIVE || !organizationAO.isAdministrator(oId, uId))
        {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        try {
            domainAO.createDomain(oId, body.getName(), body.getDescription(), uId);
        } catch(DomainExistsException e) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> createOrganization(@ApiParam(value = "Object that represents a new organization. Status and domains property should be null" ,required=true ) @RequestBody MOrganization body,
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
            organizationAO.createOrganisation(body.getName(), body.getDescription(), uId);
        } catch(OrganizationExistsException e) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteDomainById(@ApiParam(value = "id of the organization",required=true ) @PathVariable("oId") Long oId,
        @ApiParam(value = "id of the domain",required=true ) @PathVariable("id") Long id,
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

    public ResponseEntity<Void> deleteParticipant(@ApiParam(value = "name of the organization",required=true ) @PathVariable("oName") String oName,
        @ApiParam(value = "name of the domain",required=true ) @PathVariable("dName") String dName,
        @ApiParam(value = "participant id",required=true ) @PathVariable("pId") Long pId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // TODO - здесь и еще в некоторых методах не нужно id организации
    public ResponseEntity<MDomain> getDomainById(@ApiParam(value = "id of the organization",required=true ) @PathVariable("oId") Long oId,
        @ApiParam(value = "id of the domain",required=true ) @PathVariable("id") Long id,
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

    public ResponseEntity<List<String>> getOrganizationUsers(@ApiParam(value = "name of the organization",required=true ) @PathVariable("name") String name) {
        // do some magic!
        return new ResponseEntity<List<String>>(HttpStatus.OK);
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

    public ResponseEntity<UserRoles> getUserRoles(@ApiParam(value = "name of the organization",required=true ) @PathVariable("name") String name,
                                                  @ApiParam(value = "user login",required=true ) @PathVariable("login") String login) {
        // do some magic!
        return new ResponseEntity<UserRoles>(HttpStatus.OK);
    }

    public ResponseEntity<Void> patchUserRoles(@ApiParam(value = "name of the organization",required=true ) @PathVariable("name") String name,
        @ApiParam(value = "user login",required=true ) @PathVariable("login") String login,
        @ApiParam(value = "roles description" ,required=true ) @RequestBody UserRolesPatch body) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> registerParticipant(@ApiParam(value = "name of the organization",required=true ) @PathVariable("oName") String oName,
        @ApiParam(value = "name of the domain",required=true ) @PathVariable("dName") String dName,
        @ApiParam(value = "if true - returns a qrCode image in body that encodes string organizationName|domainName|participantId|privateKey, default = false. Dispite of this parameter, participantId|privateKey returns in the header 'participant'") @RequestParam(value = "withQR", required = false) Boolean withQR,
        @ApiParam(value = "name of registered participant. If specified - must be unique") @RequestParam(value = "pName", required = false) String pName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> setUserRoles(@ApiParam(value = "name of the organization",required=true ) @PathVariable("name") String name,
        @ApiParam(value = "user login",required=true ) @PathVariable("login") String login,
        @ApiParam(value = "roles description" ,required=true ) @RequestBody UserRoles body) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
