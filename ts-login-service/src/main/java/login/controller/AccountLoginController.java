package login.controller;

import login.domain.*;
import login.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import login.service.AccountLoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountLoginController {

    @Autowired
    private AccountLoginService accountService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts Login Service ] !";
    }

    public static int loginNumCache = 0;
    public static int logoutNumCache = 0;


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public LoginResult login(@RequestBody LoginInfo li, @CookieValue String YsbCaptcha, HttpServletResponse response, @RequestHeader HttpHeaders headers) {
        System.out.println(String.format("The headers in login service is %s", headers.toString()));
        System.out.println(String.format("The loginNumCache in login service is %s", loginNumCache + ""));
        loginNumCache = loginNumCache + 1;
        LoginResult loginResult = null;

        if (YsbCaptcha == null || YsbCaptcha.length() == 0 ||
                li.getEmail() == null || li.getEmail().length() == 0 ||
                li.getPassword() == null || li.getPassword().length() == 0 ||
                li.getVerificationCode() == null || li.getEmail().length() == 0) {
            LoginResult errorResult = new LoginResult();
            errorResult.setAccount(null);
            errorResult.setMessage("Verification Code or Email or Password format wrong."+"__"+loginNumCache);
            errorResult.setStatus(false);
            errorResult.setToken(null);
            return errorResult;
        }
        System.out.println("[Login Service][Login] Verification Code:" + li.getVerificationCode() +
                " VerifyCookie:" + YsbCaptcha);
        loginResult = accountService.login(li, YsbCaptcha, response, headers);
        loginResult.setMessage(loginResult.getMessage()+"__"+loginNumCache);
        return loginResult;
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public LogoutResult logout(@RequestBody LogoutInfo li, HttpServletRequest request, HttpServletResponse response, @RequestHeader HttpHeaders headers) {
        System.out.println("[Login Service][Logout] Logout ID:" + li.getId() + " Token:" + li.getToken());
        System.out.println(String.format("The loginNumCache in login service is %s", logoutNumCache + ""));
        logoutNumCache = logoutNumCache + 1;
        LogoutResult logoutResult = accountService.logout(li, request, response, headers);
        logoutResult.setMessage(logoutResult.getMessage()+ logoutNumCache);
        return logoutResult;
    }

}