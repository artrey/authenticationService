package authenticationserver;

import authenticationserver.ao.SessionAO;
import authenticationserver.swagger.api.UsersApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by igor on 19.03.17.
 */
@Controller
public class WebPageController {

    @Autowired
    private SessionAO sessionAO;

    @RequestMapping(value = "/")
    public String index(@CookieValue(value = UsersApi.SESSION_CODE_COOKIE, required = false) String sessionCode) {
        return "index";
    }

}
