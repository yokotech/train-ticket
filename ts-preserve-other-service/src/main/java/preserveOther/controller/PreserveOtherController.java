package preserveOther.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import preserveOther.domain.OrderTicketsInfo;
import preserveOther.domain.OrderTicketsResult;
import preserveOther.service.PreserveOtherService;

@RestController
public class PreserveOtherController {

    @Autowired
    private PreserveOtherService preserveService;

    private static int preserveOtherCache = 0;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ PreserveOther Service ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/preserveOther", method = RequestMethod.POST)
    public OrderTicketsResult preserve(@RequestBody OrderTicketsInfo oti,@CookieValue String loginId,
                                       @CookieValue String loginToken, @RequestHeader HttpHeaders headers){

        preserveOtherCache = preserveOtherCache + 1;

        System.out.println("[Preserve Other Service][Preserve] Account " + loginId + " order from " +
                oti.getFrom() + " -----> " + oti.getTo() + " at " + oti.getDate());

        System.out.println(preserveOtherCache + "--preserveOtherCache");
        OrderTicketsResult orderTicketsResult = null;
        orderTicketsResult = preserveService.preserve(oti,loginId,loginToken,headers);
        orderTicketsResult.setMessage(orderTicketsResult.getMessage()+"__"+preserveOtherCache);

        return orderTicketsResult;
    }
}
