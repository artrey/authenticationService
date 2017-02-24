package authenticationserver.swagger.api;

import authenticationserver.ao.SessionAO;
import authenticationserver.ao.UserAO;
import authenticationserver.ao.UserExistsException;
import authenticationserver.entities.ServerUser;
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


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-02-23T15:14:07.329Z")

@Transactional(isolation = Isolation.SERIALIZABLE)
@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    private UserAO userAO;

    @Autowired
    private SessionAO sessionAO;


    public ResponseEntity<Void> changeUserStatus(@ApiParam(value = "new status", required = true,
            allowableValues = "ACTIVE, INACTIVE") @RequestParam(value = "status", required = true) String status,
                                                 @CookieValue(SESSION_CODE_COOKIE) String sessionCode) {
        status = status.toUpperCase();

        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        ServerUser.Statuses uStatus;
        if (status.equals("ACTIVE"))
        {
            uStatus = ServerUser.Statuses.ACTIVE;
        }
        else if (status.equals("INACTIVE"))
        {
            uStatus = ServerUser.Statuses.INACTIVE;
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

    public ResponseEntity<Void> deleteUser(@CookieValue(SESSION_CODE_COOKIE) String sessionCode) {
        Long uId = sessionAO.getSessionUserId(sessionCode);
        if (uId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
        userAO.changeStatus(uId, ServerUser.Statuses.INACTIVE);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<MUser> getUserByLogin(@ApiParam(value = "login of the user to return",required=true ) @PathVariable("login") String login) {
        ServerUser serverUser = userAO.getByLogin(login);
        if (serverUser == null) {
            return new ResponseEntity<MUser>(HttpStatus.NOT_FOUND);
        }
        MUser mUser = new MUser();
        mUser.setLogin(serverUser.getLogin());
        mUser.setEmail(serverUser.getEmail());
        mUser.setStatus(serverUser.getStatus() == ServerUser.Statuses.ACTIVE ? MUser.StatusEnum.ACTIVE : MUser.StatusEnum.INACTIVE);
        return new ResponseEntity<MUser>(mUser, HttpStatus.OK);
    }

    public ResponseEntity<Void> login(@ApiParam(value = "user login",required=true ) @PathVariable("login") String login,
        @ApiParam(value = "user password", required = true) @RequestParam(value = "pass", required = true) String pass,
                                      HttpServletResponse response) {
        ServerUser serverUser = userAO.verify(login, pass);
        if (serverUser == null)
        {
            return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
        }
        response.addCookie(new Cookie(SESSION_CODE_COOKIE, sessionAO.create(serverUser.getId())));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> logout(@CookieValue(SESSION_CODE_COOKIE) String sessionCode) {

        Long userId = sessionAO.getSessionUserId(sessionCode);

        if (userId == null)
        {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        sessionAO.close(userId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
