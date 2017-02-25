package authenticationserver.swagger.api;

import authenticationserver.swagger.model.MDomain;
import authenticationserver.swagger.model.MOrganization;

import authenticationserver.swagger.model.UserRoles;
import authenticationserver.swagger.model.UserRolesPatch;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-02-24T18:13:23.197Z")

@Api(value = "organizations", description = "the organizations API")
public interface OrganizationsApi {

    @ApiOperation(value = "Authenticates participant", notes = "Authenticates participant. If participant is inactive - he wont be authenticated", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Authentication error", response = Void.class) })
    @RequestMapping(value = "/organizations/{oName}/domains/{dName}/participants/{pId}/authentication",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Void> authenticate(@ApiParam(value = "name of the organization", required = true) @PathVariable("oName") String oName,
                                      @ApiParam(value = "name of the domain", required = true) @PathVariable("dName") String dName,
                                      @ApiParam(value = "participant id", required = true) @PathVariable("pId") Long pId,
                                      @ApiParam(value = "current timestamp in seconds encoded with participants private key") @RequestParam(value = "encodedTime", required = false) String encodedTime);


    @ApiOperation(value = "Changes domains status", notes = "Changes domains status. If domain becomes inactive, its new participants cant be creates. MDomain status can be changes only if organization is active", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Wrong parameters. Status must be 'active' or 'inactive'", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation", response = Void.class),
        @ApiResponse(code = 404, message = "MOrganization or domain with specified name not found", response = Void.class) })
    @RequestMapping(value = "/organizations/{oId}/domains/{id}",
        method = RequestMethod.PATCH)
    ResponseEntity<Void> changeDomainStatus(@ApiParam(value = "id of the organization", required = true) @PathVariable("oId") Long oId,
                                            @ApiParam(value = "id of the domain", required = true) @PathVariable("id") Long id,
                                            @ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status,
                                            @CookieValue(UsersApi.SESSION_CODE_COOKIE) String sessionCode);


    @ApiOperation(value = "Changes organizations status", notes = "Changes organizations status. If status becomes inactive, users cant create/modify its domains and participants", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Wrong parameters. Status must be 'active' or 'inactive'", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation", response = Void.class),
        @ApiResponse(code = 404, message = "MOrganization with specified id not found", response = Void.class) })
    @RequestMapping(value = "/organizations/{id}",
        method = RequestMethod.PATCH)
    ResponseEntity<Void> changeOrganizationStatus(@ApiParam(value = "id of the organization", required = true) @PathVariable("id") Long id,
                                                  @ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status,
                                                  @CookieValue(UsersApi.SESSION_CODE_COOKIE) String sessionCode);


    @ApiOperation(value = "Changes participants status", notes = "Changes participants status. Participants status can be changed only if organization and domain status is active", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Wrong parameters. Status must be 'active' or 'inactive'", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation", response = Void.class),
        @ApiResponse(code = 404, message = "MOrganization or domain or participant not found", response = Void.class) })
    @RequestMapping(value = "/organizations/{oName}/domains/{dName}/participants/{pId}",
        method = RequestMethod.PATCH)
    ResponseEntity<Void> changeParticipantStatus(@ApiParam(value = "name of the organization", required = true) @PathVariable("oName") String oName,
                                                 @ApiParam(value = "name of the domain", required = true) @PathVariable("dName") String dName,
                                                 @ApiParam(value = "participant id", required = true) @PathVariable("pId") Long pId,
                                                 @ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "state", required = true) String state);


    @ApiOperation(value = "Creates a new domain", notes = "Creates a new domain. A new domain cant be created if the organization is inactive", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation", response = Void.class),
        @ApiResponse(code = 404, message = "MOrganization not found", response = Void.class),
        @ApiResponse(code = 409, message = "MDomain with specified name already exists", response = Void.class) })
    @RequestMapping(value = "/organizations/{oId}/domains",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createDomain(@ApiParam(value = "id of the organization in wich domain will be created", required = true) @PathVariable("oId") Long oId,
                                      @ApiParam(value = "object that represents a new domain. Status should be null", required = true) @RequestBody MDomain body,
                                      @CookieValue(UsersApi.SESSION_CODE_COOKIE) String sessionCode);


    @ApiOperation(value = "Creates a new organization", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 409, message = "MOrganization with specified name already exists", response = Void.class) })
    @RequestMapping(value = "/organizations",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createOrganization(@ApiParam(value = "Object that represents a new organization. Status and domains property should be null", required = true) @RequestBody MOrganization body,
                                            @CookieValue(UsersApi.SESSION_CODE_COOKIE) String sessionCode);


    @ApiOperation(value = "Changes domains status to 'inactive'", notes = "Changes domains status to 'inactive'. If domain is inactive, its new participants cant be creates. MDomain status can be changes only if organization is active", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation", response = Void.class),
        @ApiResponse(code = 404, message = "MOrganization or domain with specified name not found", response = Void.class) })
    @RequestMapping(value = "/organizations/{oId}/domains/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteDomainById(@ApiParam(value = "id of the organization", required = true) @PathVariable("oId") Long oId,
                                          @ApiParam(value = "id of the domain", required = true) @PathVariable("id") Long id,
                                          @CookieValue(UsersApi.SESSION_CODE_COOKIE) String sessionCode);


    @ApiOperation(value = "Changes organizations status to 'inactive'", notes = "Changes organizations status to 'inactive'. If organization is inactive users cant modify its domains and create participants", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation. Only administrator allowed", response = Void.class),
        @ApiResponse(code = 404, message = "MOrganization with specified id not found", response = Void.class) })
    @RequestMapping(value = "/organizations/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteOrganizationById(@ApiParam(value = "id of the organization", required = true) @PathVariable("id") Long id,
                                                @CookieValue(UsersApi.SESSION_CODE_COOKIE) String sessionCode);


    @ApiOperation(value = "Sets participants status to 'inactive'", notes = "Sets participants status to 'inactive'. Participants status can be changed only if organization and domain status is active", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation", response = Void.class),
        @ApiResponse(code = 404, message = "MOrganization or domain or participant not found", response = Void.class) })
    @RequestMapping(value = "/organizations/{oName}/domains/{dName}/participants/{pId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteParticipant(@ApiParam(value = "name of the organization", required = true) @PathVariable("oName") String oName,
                                           @ApiParam(value = "name of the domain", required = true) @PathVariable("dName") String dName,
                                           @ApiParam(value = "participant id", required = true) @PathVariable("pId") Long pId);


    @ApiOperation(value = "Finds domain by id", notes = "Returns requested domain information", response = MDomain.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = MDomain.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = MDomain.class),
        @ApiResponse(code = 403, message = "Forbidden operation. Only organization administrators and users with the domain roles allowed", response = MDomain.class),
        @ApiResponse(code = 404, message = "MOrganization or domain with specified name not found", response = MDomain.class) })
    @RequestMapping(value = "/organizations/{oId}/domains/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<MDomain> getDomainById(@ApiParam(value = "id of the organization", required = true) @PathVariable("oId") Long oId,
                                         @ApiParam(value = "id of the domain", required = true) @PathVariable("id") Long id,
                                          @CookieValue(UsersApi.SESSION_CODE_COOKIE) String sessionCode);


    @ApiOperation(value = "Finds organization by id", notes = "Returns requested organization information with its domains", response = MOrganization.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = MOrganization.class),
        @ApiResponse(code = 404, message = "MOrganization with specified id not found", response = MOrganization.class) })
    @RequestMapping(value = "/organizations/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<MOrganization> getOrganizationById(@ApiParam(value = "id of organization to return", required = true) @PathVariable("id") Long id,
                                                      @CookieValue(UsersApi.SESSION_CODE_COOKIE) String sessionCode);


    @ApiOperation(value = "Finds organization users", notes = "Returns a list of users names that partisipate in the organization", response = String.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = String.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = String.class),
        @ApiResponse(code = 403, message = "Forbidden operation. Only administrator allowed", response = String.class),
        @ApiResponse(code = 404, message = "MOrganization with specified name not found", response = String.class) })
    @RequestMapping(value = "/organizations/{name}/users",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<String>> getOrganizationUsers(@ApiParam(value = "name of the organization", required = true) @PathVariable("name") String name);


    @ApiOperation(value = "Returns all organizations with their domains in which authenticated user participates (has roles)", notes = "", response = MOrganization.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = MOrganization.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = MOrganization.class) })
    @RequestMapping(value = "/organizations",
        method = RequestMethod.GET)
    ResponseEntity<List<MOrganization>> getUserOrganizations(@CookieValue(UsersApi.SESSION_CODE_COOKIE) String sessionCode);


    @ApiOperation(value = "Finds users roles", notes = "Returns specified user roles in the organization", response = UserRoles.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = UserRoles.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = UserRoles.class),
        @ApiResponse(code = 403, message = "Forbidden operation. Only administrator and user itslef allowed", response = UserRoles.class),
        @ApiResponse(code = 404, message = "MOrganization with specified name not found", response = UserRoles.class) })
    @RequestMapping(value = "/organizations/{name}/users/{login}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<UserRoles> getUserRoles(@ApiParam(value = "name of the organization", required = true) @PathVariable("name") String name,
                                           @ApiParam(value = "user login", required = true) @PathVariable("login") String login);


    @ApiOperation(value = "Modifies specified user roles (add or delete)", notes = "Modifies specified user roles (add or delete). Users roles can be changed only if organization and domain (if role is domain specific) is active. Administrator cant change his own role", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Wrong parameters. Status must be 'active' or 'inactive'", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation", response = Void.class),
        @ApiResponse(code = 404, message = "MOrganization with specified name not found", response = Void.class) })
    @RequestMapping(value = "/organizations/{name}/users/{login}",
        consumes = { "application/json" },
        method = RequestMethod.PATCH)
    ResponseEntity<Void> patchUserRoles(@ApiParam(value = "name of the organization", required = true) @PathVariable("name") String name,
                                        @ApiParam(value = "user login", required = true) @PathVariable("login") String login,
                                        @ApiParam(value = "roles description", required = true) @RequestBody UserRolesPatch body);


    @ApiOperation(value = "Registers a new participant", notes = "Registers a new participant. A new participants can be registered only if organization and domain is active", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation", response = Void.class),
        @ApiResponse(code = 404, message = "MOrganization or domain with specified name not found", response = Void.class),
        @ApiResponse(code = 409, message = "Participant with specified name already exists", response = Void.class) })
    @RequestMapping(value = "/organizations/{oName}/domains/{dName}/participants",
        produces = { "image/jpeg" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> registerParticipant(@ApiParam(value = "name of the organization", required = true) @PathVariable("oName") String oName,
                                             @ApiParam(value = "name of the domain", required = true) @PathVariable("dName") String dName,
                                             @ApiParam(value = "if true - returns a qrCode image in body that encodes string organizationName|domainName|participantId|privateKey, default = false. Dispite of this parameter, participantId|privateKey returns in the header 'participant'") @RequestParam(value = "withQR", required = false) Boolean withQR,
                                             @ApiParam(value = "name of registered participant. If specified - must be unique") @RequestParam(value = "pName", required = false) String pName);


    @ApiOperation(value = "Sets user roles", notes = "Sets user roles. Users roles can be changed only if organization and domain (if role is domain specific) is active. Administrator cant change his own role", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation.", response = Void.class),
        @ApiResponse(code = 404, message = "MOrganization with specified name not found", response = Void.class) })
    @RequestMapping(value = "/organizations/{name}/users/{login}",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> setUserRoles(@ApiParam(value = "name of the organization", required = true) @PathVariable("name") String name,
                                      @ApiParam(value = "user login", required = true) @PathVariable("login") String login,
                                      @ApiParam(value = "roles description", required = true) @RequestBody UserRoles body);

}
