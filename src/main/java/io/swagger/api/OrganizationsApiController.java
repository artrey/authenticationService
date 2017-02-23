package io.swagger.api;

import io.swagger.model.Domain;
import io.swagger.model.Organization;
import io.swagger.model.UserRoles;
import io.swagger.model.UserRolesPatch;

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
public class OrganizationsApiController implements OrganizationsApi {

    public ResponseEntity<Void> authenticate(@ApiParam(value = "name of the organization",required=true ) @PathVariable("oName") String oName,
        @ApiParam(value = "name of the domain",required=true ) @PathVariable("dName") String dName,
        @ApiParam(value = "participant id",required=true ) @PathVariable("pId") Long pId,
        @ApiParam(value = "current timestamp in seconds encoded with participants private key") @RequestParam(value = "encodedTime", required = false) String encodedTime) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> changeDomainStatus(@ApiParam(value = "name of the organization",required=true ) @PathVariable("oName") String oName,
        @ApiParam(value = "name of the domain",required=true ) @PathVariable("name") String name,
        @ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> changeOrganizationStatus(@ApiParam(value = "name of the organization",required=true ) @PathVariable("name") String name,
        @ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> changeParticipantStatus(@ApiParam(value = "name of the organization",required=true ) @PathVariable("oName") String oName,
        @ApiParam(value = "name of the domain",required=true ) @PathVariable("dName") String dName,
        @ApiParam(value = "participant id",required=true ) @PathVariable("pId") Long pId,
        @ApiParam(value = "new status", required = true, allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "state", required = true) String state) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> createDomain(@ApiParam(value = "name of the organization in wich domain will be created",required=true ) @PathVariable("oName") String oName,
        @ApiParam(value = "object that represents a new domain. Status should be null" ,required=true ) @RequestBody Domain body) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> createOrganization(@ApiParam(value = "Object that represents a new organization. Status property should be null" ,required=true ) @RequestBody Organization body) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteDomainByName(@ApiParam(value = "name of the organization",required=true ) @PathVariable("oName") String oName,
        @ApiParam(value = "name of the domain",required=true ) @PathVariable("name") String name) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteOrganizationByName(@ApiParam(value = "name of the organization",required=true ) @PathVariable("name") String name) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteParticipant(@ApiParam(value = "name of the organization",required=true ) @PathVariable("oName") String oName,
        @ApiParam(value = "name of the domain",required=true ) @PathVariable("dName") String dName,
        @ApiParam(value = "participant id",required=true ) @PathVariable("pId") Long pId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Domain> getDomainByName(@ApiParam(value = "name of the organization",required=true ) @PathVariable("oName") String oName,
        @ApiParam(value = "name of the domain",required=true ) @PathVariable("name") String name) {
        // do some magic!
        return new ResponseEntity<Domain>(HttpStatus.OK);
    }

    public ResponseEntity<Organization> getOrganizationByName(@ApiParam(value = "name of organization to return",required=true ) @PathVariable("name") String name) {
        // do some magic!
        return new ResponseEntity<Organization>(HttpStatus.OK);
    }

    public ResponseEntity<List<String>> getOrganizationUsers(@ApiParam(value = "name of the organization",required=true ) @PathVariable("name") String name) {
        // do some magic!
        return new ResponseEntity<List<String>>(HttpStatus.OK);
    }

    public ResponseEntity<List<String>> getUserDomains(@ApiParam(value = "name of organization in wich domain will be created",required=true ) @PathVariable("oName") String oName) {
        // do some magic!
        return new ResponseEntity<List<String>>(HttpStatus.OK);
    }

    public ResponseEntity<List<String>> getUserOrganizations() {
        // do some magic!
        return new ResponseEntity<List<String>>(HttpStatus.OK);
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
