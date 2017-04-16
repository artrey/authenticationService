package authenticationserver.swagger.api;

import authenticationserver.ao.*;
import authenticationserver.entities.SUser;

import authenticationserver.swagger.model.MOrganization;
import authenticationserver.swagger.model.MUser;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-02-25T17:17:00.655Z")

@Transactional(isolation = Isolation.SERIALIZABLE)
@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    private UserAO userAO;

    @Autowired
    private WhiteOrganizationAO whiteOrganizationAO;

    @Autowired
    private OrganizationAO organizationAO;

    @Autowired
    private SessionAO sessionAO;


    public ResponseEntity<Void> addToWhiteList(@ApiParam(value = "id of the organization",required=true ) @PathVariable("oId") Long oId,
                                               @CookieValue(value = SESSION_CODE_COOKIE, required = false) String sessionCode) {

        Long userId = sessionAO.getSessionUserId(sessionCode);

        if (userId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        if (organizationAO.getById(oId) == null)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        whiteOrganizationAO.addToWhileList(userId, oId);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> changeUserStatus(@ApiParam(value = "new status", required = true,
            allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status,
                                                 @CookieValue(value = SESSION_CODE_COOKIE, required = false) String sessionCode) {
        status = status.toUpperCase();

        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        SUser.Statuses uStatus;
        if (status.equals("ACTIVE"))
        {
            uStatus = SUser.Statuses.ACTIVE;
        }
        else if (status.equals("INACTIVE"))
        {
            uStatus = SUser.Statuses.INACTIVE;
        }
        else
        {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        userAO.changeStatus(uId, uStatus);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> createUser(@ApiParam(value = "Object that represents a new user. Status property should be null" ,required=true ) @RequestBody MUser body) {

        if (StringUtils.isEmpty(body.getLogin()) || StringUtils.isEmpty(body.getEmail())
                || StringUtils.isEmpty(body.getPass())) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        try {
            userAO.create(body.getLogin(), body.getEmail(), body.getPass());
        } catch(UserExistsException e) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteFromWhiteList(@ApiParam(value = "id of the organization",required=true ) @PathVariable("oId") Long oId,
                                                    @CookieValue(value = SESSION_CODE_COOKIE, required = false) String sessionCode) {

        Long userId = sessionAO.getSessionUserId(sessionCode);

        if (userId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        whiteOrganizationAO.remFromWhiteList(userId, oId);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteUser(@CookieValue(value = SESSION_CODE_COOKIE, required = false) String sessionCode) {
        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
        userAO.changeStatus(uId, SUser.Statuses.INACTIVE);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<MUser> getUserByLogin(@ApiParam(value = "login of the user to return",required=true ) @PathVariable("login") String login) {
        SUser sUser = userAO.getByLogin(login);
        if (sUser == null) {
            return new ResponseEntity<MUser>(HttpStatus.NOT_FOUND);
        }
        MUser mUser = new MUser();
        mUser.setLogin(sUser.getLogin());
        mUser.setEmail(sUser.getEmail());
        mUser.setStatus(sUser.getStatus() == SUser.Statuses.ACTIVE ? MUser.StatusEnum.ACTIVE : MUser.StatusEnum.INACTIVE);
        return new ResponseEntity<MUser>(mUser, HttpStatus.OK);
    }

    public ResponseEntity<List<MOrganization>> getWhiteList(@CookieValue(value = SESSION_CODE_COOKIE, required = false) String sessionCode) {

        Long userId = sessionAO.getSessionUserId(sessionCode);

        if (userId == null)
        {
            return new ResponseEntity<List<MOrganization>>(HttpStatus.UNAUTHORIZED);
        }

        List<MOrganization> whiteList = whiteOrganizationAO.getWhiteOrganizations(userId).stream()
                .map(o -> o.toMOrganization()).collect(Collectors.toList());

        return new ResponseEntity<List<MOrganization>>(whiteList, HttpStatus.OK);
    }

    public ResponseEntity<Void> login(@ApiParam(value = "user login",required=true ) @PathVariable("login") String login,
        @ApiParam(value = "user password", required = true) @RequestParam(value = "pass", required = true) String pass,
                                      HttpServletResponse response) {
        SUser sUser = userAO.verify(login, pass);
        if (sUser == null)
        {
            return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        Cookie cookie = new Cookie(SESSION_CODE_COOKIE, sessionAO.create(sUser.getId()));
        cookie.setPath("/");
        response.addCookie(cookie);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> logout(@CookieValue(value = SESSION_CODE_COOKIE, required = false) String sessionCode) {

        Long userId = sessionAO.getSessionUserId(sessionCode);

        if (userId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        sessionAO.close(userId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
