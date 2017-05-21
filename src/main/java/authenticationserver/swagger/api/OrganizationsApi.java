package authenticationserver.swagger.api;

import authenticationserver.swagger.model.MDomain;
import authenticationserver.swagger.model.MOrganization;

import authenticationserver.swagger.model.UserRoles;
import authenticationserver.swagger.model.UserRolesPatch;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-02-25T17:17:00.655Z")

@Api(value = "organizations", description = "the organizations API")
public interface OrganizationsApi {

    @ApiOperation(value = "Authenticates participant", notes = "Authenticates participant. If participant is inactive - he wont be authenticated", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Authentication error", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation. Only authenticators and administrators can authenticate participants", response = Void.class) })
    @RequestMapping(value = "/organizations/domains/{id}/participants/{pId}/authentication",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> authenticate(@ApiParam(value = "id of the domain", required = true) @PathVariable("id") Long id,
                                      @ApiParam(value = "participant id", required = true) @PathVariable("pId") Long pId,
                                      @ApiParam(value = "current timestamp in seconds encoded with participants private key") @RequestBody String encodedTime,
                                      @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Changes domains status", notes = "Changes domains status. If domain becomes inactive, its new participants cant be creates. Domain status can be changes only if organization is active", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Wrong parameters. Status must be 'active' or 'inactive'", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation", response = Void.class),
        @ApiResponse(code = 404, message = "Domain with specified id not found", response = Void.class) })
    @RequestMapping(value = "/organizations/domains/{id}",
        method = RequestMethod.PATCH)
    ResponseEntity<Void> changeDomainStatus(@ApiParam(value = "id of the domain", required = true) @PathVariable("id") Long id,
                                            @ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status,
                                            @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Changes organizations status", notes = "Changes organizations status. If status becomes inactive, users cant create/modify its domains and participants", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Wrong parameters. Status must be 'active' or 'inactive'", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation", response = Void.class),
        @ApiResponse(code = 404, message = "Organization with specified id not found", response = Void.class) })
    @RequestMapping(value = "/organizations/{id}",
        method = RequestMethod.PATCH)
    ResponseEntity<Void> changeOrganizationStatus(@ApiParam(value = "id of the organization", required = true) @PathVariable("id") Long id,
                                                  @ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status,
                                                  @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Creates a new domain", notes = "Creates a new domain. A new domain cant be created if the organization is inactive", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation", response = Void.class),
        @ApiResponse(code = 404, message = "Organization not found", response = Void.class),
        @ApiResponse(code = 409, message = "Domain with specified name already exists", response = Void.class) })
    @RequestMapping(value = "/organizations/{id}/domains",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createDomain(@ApiParam(value = "id of the organization in wich domain will be created", required = true) @PathVariable("id") Long id,
                                      @ApiParam(value = "object that represents a new domain. Status and id should be null", required = true) @RequestBody MDomain body,
                                      @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Creates a new organization", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Organization name must be specified", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 409, message = "Organization with specified name already exists", response = Void.class) })
    @RequestMapping(value = "/organizations",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createOrganization(@ApiParam(value = "Object that represents a new organization. Id, status and domains properties should be null", required = true) @RequestBody MOrganization body,
                                            @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Changes domains status to 'inactive'", notes = "Changes domains status to 'inactive'. If domain is inactive, its new participants cant be creates. Domain status can be changes only if organization is active", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation", response = Void.class),
        @ApiResponse(code = 404, message = "Domain with specified id not found", response = Void.class) })
    @RequestMapping(value = "/organizations/domains/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteDomainById(@ApiParam(value = "id of the domain", required = true) @PathVariable("id") Long id,
                                          @CookieValue(UsersApi.SESSION_CODE_COOKIE) String sessionCode);


    @ApiOperation(value = "Changes organizations status to 'inactive'", notes = "Changes organizations status to 'inactive'. If organization is inactive users cant modify its domains and create participants", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation. Only administrator allowed", response = Void.class),
        @ApiResponse(code = 404, message = "Organization with specified id not found", response = Void.class) })
    @RequestMapping(value = "/organizations/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteOrganizationById(@ApiParam(value = "id of the organization", required = true) @PathVariable("id") Long id,
                                                @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Finds domain by id", notes = "Returns requested domain information", response = MDomain.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = MDomain.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = MDomain.class),
        @ApiResponse(code = 403, message = "Forbidden operation. Only organization administrators and users with the domain roles allowed", response = MDomain.class),
        @ApiResponse(code = 404, message = "Domain with specified id not found", response = MDomain.class) })
    @RequestMapping(value = "/organizations/domains/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<MDomain> getDomainById(@ApiParam(value = "id of the domain", required = true) @PathVariable("id") Long id,
                                          @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Finds organization by id", notes = "Returns requested organization information with its domains", response = MOrganization.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = MOrganization.class),
        @ApiResponse(code = 404, message = "Organization with specified id not found", response = MOrganization.class) })
    @RequestMapping(value = "/organizations/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<MOrganization> getOrganizationById(@ApiParam(value = "id of organization to return", required = true) @PathVariable("id") Long id,
                                                      @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Finds organization users", notes = "Returns a list of users names that partisipate in the organization", response = String.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = String.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = String.class),
        @ApiResponse(code = 403, message = "Forbidden operation. Only administrator allowed", response = String.class),
        @ApiResponse(code = 404, message = "Organization with specified id not found", response = String.class) })
    @RequestMapping(value = "/organizations/{id}/users",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<String>> getOrganizationUsers(@ApiParam(value = "id of the organization", required = true) @PathVariable("id") Long id,
                                                      @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Returns all organizations with their domains in which authenticated user participates (has roles)", notes = "", response = MOrganization.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = MOrganization.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = MOrganization.class) })
    @RequestMapping(value = "/organizations",
        method = RequestMethod.GET)
    ResponseEntity<List<MOrganization>> getUserOrganizations(@CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Finds users roles", notes = "Returns specified user roles in the organization", response = UserRoles.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = UserRoles.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = UserRoles.class),
        @ApiResponse(code = 403, message = "Forbidden operation. Only administrator and user itslef allowed", response = UserRoles.class),
        @ApiResponse(code = 404, message = "Organization with specified id not found", response = UserRoles.class) })
    @RequestMapping(value = "/organizations/{id}/users/{login}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<UserRoles> getUserRoles(@ApiParam(value = "id of the organization", required = true) @PathVariable("id") Long id,
                                           @ApiParam(value = "user login", required = true) @PathVariable("login") String login,
                                           @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Modifies specified user roles (add or delete)", notes = "Modifies specified user roles (add or delete). Users roles can be changed only if organization and domain (if role is domain specific) is active. Administrator cant change his own role", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Wrong parameters. Status must be 'active' or 'inactive'", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation", response = Void.class),
        @ApiResponse(code = 404, message = "Organization with specified id not found", response = Void.class) })
    @RequestMapping(value = "/organizations/{id}/users/{login}",
        consumes = { "application/json" },
        method = RequestMethod.PATCH)
    ResponseEntity<Void> patchUserRoles(@ApiParam(value = "id of the organization", required = true) @PathVariable("id") Long id,
                                        @ApiParam(value = "user login", required = true) @PathVariable("login") String login,
                                        @ApiParam(value = "roles description", required = true) @RequestBody UserRolesPatch body,
                                        @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Registers a new participant", notes = "Registers a new participant. A new participants can be registered only if organization and domain is active", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation. Only distributors and administrators can create participants", response = Void.class),
        @ApiResponse(code = 404, message = "Domain with specified id not found", response = Void.class),
        @ApiResponse(code = 409, message = "Participant with specified name already exists", response = Void.class) })
    @RequestMapping(value = "/organizations/domains/{id}/participants",
        produces = { "image/jpeg" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> registerParticipant(@ApiParam(value = "id of the domain", required = true) @PathVariable("id") Long id,
                                             @ApiParam(value = "if true - returns a qrCode image in body that encodes string organizationName|domainName|participantId|privateKey, default = false. Dispite of this parameter, participantId|privateKey returns in the header 'participant'") @RequestParam(value = "withQR", required = false) Boolean withQR,
                                             @ApiParam(value = "name of registered participant. If specified then must be unique") @RequestParam(value = "pName", required = false) String pName,
                                             @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode,
                                             HttpServletResponse response);


    @ApiOperation(value = "Sets user roles", notes = "Sets user roles. Users roles can be changed only if organization and domain (if role is domain specific) is active. Administrator cant change his own role", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation.", response = Void.class),
        @ApiResponse(code = 404, message = "Organization with specified id not found", response = Void.class) })
    @RequestMapping(value = "/organizations/{id}/users/{login}",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> setUserRoles(@ApiParam(value = "id of the organization", required = true) @PathVariable("id") Long id,
                                      @ApiParam(value = "user login", required = true) @PathVariable("login") String login,
                                      @ApiParam(value = "roles description", required = true) @RequestBody UserRoles body,
                                      @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);

    @ApiOperation(value = "Changes participants status", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
            @ApiResponse(code = 400, message = "Wrong parameters. Status must be 'active' or 'inactive'", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden operation. Only administrators and distributors can change participants status", response = Void.class),
            @ApiResponse(code = 404, message = "Domain or participant is not found", response = Void.class) })
    @RequestMapping(value = "/organizations/domains/{id}/participants/{pId}",
            method = RequestMethod.PATCH)
    ResponseEntity<Void> changeParticipantStatus(@ApiParam(value = "id of the domain", required = true) @PathVariable("id") Long id,
                                                 @ApiParam(value = "participant id", required = true) @PathVariable("pId") Long pId,
                                                 @ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status,
                                                 @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Sets participants status to 'inactive'", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden operation. Only administrators and distributors can change participants status", response = Void.class),
            @ApiResponse(code = 404, message = "Domain or participant is not found", response = Void.class) })
    @RequestMapping(value = "/organizations/domains/{id}/participants/{pId}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteParticipant(@ApiParam(value = "id of the domain", required = true) @PathVariable("id") Long id,
                                           @ApiParam(value = "participant id", required = true) @PathVariable("pId") Long pId,
                                           @CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode);
}
