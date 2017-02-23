package io.swagger.api;

import io.swagger.model.User;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-02-23T15:14:07.329Z")

@Api(value = "users", description = "the users API")
public interface UsersApi {

    @ApiOperation(value = "Updates users status", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Wrong parameters. Status must be 'active' or 'inactive'", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation. Only user itself can change status", response = Void.class) })
    @RequestMapping(value = "/users",
        method = RequestMethod.PATCH)
    ResponseEntity<Void> changeUserStatus(@ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status);


    @ApiOperation(value = "Creates a new user", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 405, message = "Invalid input", response = Void.class),
        @ApiResponse(code = 409, message = "User with specified login already exists", response = Void.class) })
    @RequestMapping(value = "/users",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createUser(@ApiParam(value = "Object that represents a new user. Status property should be null" ,required=true ) @RequestBody User body);


    @ApiOperation(value = "Updates users status to 'inactive'", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden operation. Only user itself can change status", response = Void.class) })
    @RequestMapping(value = "/users",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteUser();


    @ApiOperation(value = "Finds user by login", notes = "Returns requested user information", response = User.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation. (Pass property is null)", response = User.class),
        @ApiResponse(code = 404, message = "User with specified login not found", response = User.class) })
    @RequestMapping(value = "/users/{login}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<User> getUserByLogin(@ApiParam(value = "login of the user to return",required=true ) @PathVariable("login") String login);


    @ApiOperation(value = "Logins user", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 405, message = "Wrong user login or password", response = Void.class) })
    @RequestMapping(value = "/users/{login}/login",
        method = RequestMethod.GET)
    ResponseEntity<Void> login(@ApiParam(value = "user login",required=true ) @PathVariable("login") String login,
        @ApiParam(value = "user password", required = true) @RequestParam(value = "pass", required = true) String pass);


    @ApiOperation(value = "Logouts user", notes = "", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class) })
    @RequestMapping(value = "/users/logout",
        method = RequestMethod.GET)
    ResponseEntity<Void> logout();

}
