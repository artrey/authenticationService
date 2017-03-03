package authenticationserver.swagger.api;

import authenticationserver.swagger.model.MOrganization;
import authenticationserver.swagger.model.MUser;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-02-25T17:17:00.655Z")

@Api(value = "users", description = "the users API")
public interface UsersApi {

    public static final String SESSION_CODE_COOKIE = "sessionCode";

    @ApiOperation(value = "Adds new organization in a white list", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 404, message = "Organization is not found", response = Void.class)})
    @RequestMapping(value = "/users/whitelist/{oId}",
            method = RequestMethod.POST)
    ResponseEntity<Void> addToWhiteList(@ApiParam(value = "id of the organization",required=true ) @PathVariable("oId") Long oId,
                                        @CookieValue(value = SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Updates users status", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Wrong parameters. Status must be 'active' or 'inactive'", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class) })
    @RequestMapping(value = "/users",
        method = RequestMethod.PATCH)
    ResponseEntity<Void> changeUserStatus(@ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status,
                                          @CookieValue(SESSION_CODE_COOKIE) String sessionCode);


    @ApiOperation(value = "Creates a new user", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 405, message = "Invalid input", response = Void.class),
        @ApiResponse(code = 409, message = "MUser with specified login already exists", response = Void.class) })
    @RequestMapping(value = "/users",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createUser(@ApiParam(value = "Object that represents a new user. Status property should be null" ,required=true ) @RequestBody MUser body);


    @ApiOperation(value = "Deletes organization from the white list", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class) })
    @RequestMapping(value = "/users/whitelist/{oId}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteFromWhiteList(@ApiParam(value = "id of the organization",required=true ) @PathVariable("oId") Long oId,
                                             @CookieValue(value = SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Updates users status to 'inactive'", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class) })
    @RequestMapping(value = "/users",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteUser(@CookieValue(SESSION_CODE_COOKIE) String sessionCode);


    @ApiOperation(value = "Finds user by login", notes = "Returns requested user information", response = MUser.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation. (Pass property is null)", response = MUser.class),
        @ApiResponse(code = 404, message = "MUser with specified login not found", response = MUser.class) })
    @RequestMapping(value = "/users/{login}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<MUser> getUserByLogin(@ApiParam(value = "login of the user to return",required=true ) @PathVariable("login") String login);


    @ApiOperation(value = "Prints organizations that are in the white list", notes = "Other users can add rights to their organizations only it the user has their organizations in the white list", response = MOrganization.class, responseContainer = "List", tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = MOrganization.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = MOrganization.class) })
    @RequestMapping(value = "/users/whitelist",
            method = RequestMethod.GET)
    ResponseEntity<List<MOrganization>> getWhiteList(@CookieValue(value = SESSION_CODE_COOKIE, required = false) String sessionCode);


    @ApiOperation(value = "Logins user", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 405, message = "Wrong user login or password", response = Void.class) })
    @RequestMapping(value = "/users/{login}/login",
        method = RequestMethod.GET)
    ResponseEntity<Void> login(@ApiParam(value = "user login",required=true ) @PathVariable("login") String login,
        @ApiParam(value = "user password", required = true) @RequestParam(value = "pass", required = true) String pass,
                               HttpServletResponse response);


    @ApiOperation(value = "Logouts user", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class) })
    @RequestMapping(value = "/users/logout",
        method = RequestMethod.GET)
    ResponseEntity<Void> logout(@CookieValue(SESSION_CODE_COOKIE) String sessionCode);

}
