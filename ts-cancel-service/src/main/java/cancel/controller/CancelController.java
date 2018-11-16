package cancel.controller;

import cancel.domain.CalculateRefundResult;
import cancel.domain.CancelOrderInfo;
import cancel.domain.CancelOrderResult;
import cancel.domain.VerifyResult;
import cancel.service.CancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class CancelController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    CancelService cancelService;

    public static int cancelCalculateCache = 0;

    public static int cancelOrderCache = 0;


    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Cancel Service ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/cancelCalculateRefund", method = RequestMethod.POST)
    public CalculateRefundResult calculate(@RequestBody CancelOrderInfo info, @RequestHeader HttpHeaders headers) {
        System.out.println("[Cancel Order Service][Calculate Cancel Refund] OrderId:" + info.getOrderId());
        cancelCalculateCache = cancelCalculateCache + 1;
        CalculateRefundResult calculateRefundResult = null;

        if (cancelCalculateCache <= 4) {
            calculateRefundResult = cancelService.calculateRefund(info, headers);
        } else if (cancelCalculateCache > 4) {
            calculateRefundResult = new CalculateRefundResult(false, "cancel calculate refound more than 4 times", null);
        }
        return calculateRefundResult;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/cancelOrder", method = RequestMethod.POST)
    public CancelOrderResult cancelTicket(@RequestBody CancelOrderInfo info, @CookieValue String loginToken, @CookieValue String loginId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Cancel Order Service][Cancel Ticket] info:" + info.getOrderId());
        cancelOrderCache = cancelOrderCache + 1;
        CancelOrderResult cancelOrderResult = null;
        if (cancelOrderCache <= 3) {
            if (loginToken == null) {
                loginToken = "admin";
            }
            System.out.println("[Cancel Order Service][Cancel Order] order ID:" + info.getOrderId() + "  loginToken:" + loginToken);
            if (loginToken == null) {
                System.out.println("[Cancel Order Service][Cancel Order] Not receive any login token");
                CancelOrderResult result = new CancelOrderResult();
                result.setStatus(false);
                result.setMessage("No Login Token");
                cancelOrderResult = result;
            }
            VerifyResult verifyResult = verifySsoLogin(loginToken, headers);
            if (verifyResult.isStatus() == false) {
                System.out.println("[Cancel Order Service][Cancel Order] Do not login.");
                CancelOrderResult result = new CancelOrderResult();
                result.setStatus(false);
                result.setMessage("Not Login");
                cancelOrderResult = result;
            } else {
                System.out.println("[Cancel Order Service][Cancel Ticket] Verify Success");
                try {
                    cancelOrderResult = cancelService.cancelOrder(info, loginToken, loginId, headers);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }
        } else if (cancelOrderCache > 3) {
            cancelOrderResult = new CancelOrderResult(false,"cancel order more than three times ");
        }
        return cancelOrderResult;
    }

    private VerifyResult verifySsoLogin(String loginToken, @RequestHeader HttpHeaders headers) {
        System.out.println("[Order Service][Verify Login] Verifying....");

        HttpEntity requestTokenResult = new HttpEntity(null, headers);
        ResponseEntity<VerifyResult> reTokenResult = restTemplate.exchange(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                HttpMethod.GET,
                requestTokenResult,
                VerifyResult.class);
        VerifyResult tokenResult = reTokenResult.getBody();
//        VerifyResult tokenResult = restTemplate.getForObject(
//                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
//                VerifyResult.class);


        return tokenResult;
    }

}
