package io.swagger.api;

import io.swagger.model.User;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-02-23T15:14:07.329Z")

@Controller
public class UsersApiController implements UsersApi {

    public ResponseEntity<Void> changeUserStatus(@ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> createUser(@ApiParam(value = "Object that represents a new user. Status property should be null" ,required=true ) @RequestBody User body) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteUser() {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<User> getUserByLogin(@ApiParam(value = "login of the user to return",required=true ) @PathVariable("login") String login) {
        // do some magic!
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    public ResponseEntity<Void> login(@ApiParam(value = "user login",required=true ) @PathVariable("login") String login,
        @ApiParam(value = "user password", required = true) @RequestParam(value = "pass", required = true) String pass) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> logout() {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
