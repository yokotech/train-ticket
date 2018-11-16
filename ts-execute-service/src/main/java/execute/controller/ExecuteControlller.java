package execute.controller;

import execute.domain.TicketExecuteInfo;
import execute.domain.TicketExecuteResult;
import execute.serivce.ExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExecuteControlller {

    @Autowired
    private ExecuteService executeService;

    private static int collectNumCache = 0;

    private static int executeNumCache = 0;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Execute Service ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/execute/execute", method = RequestMethod.POST)
    public TicketExecuteResult executeTicket(@RequestBody TicketExecuteInfo info, @RequestHeader HttpHeaders headers) {
        System.out.println("[Execute Service][Execute] Id:" + info.getOrderId());
        executeNumCache = executeNumCache + 1;
        TicketExecuteResult ticketExecuteResult = null;
        if (executeNumCache <= 2) {
            ticketExecuteResult = executeService.ticketExecute(info, headers);
        } else if (executeNumCache > 2) {
            ticketExecuteResult = new TicketExecuteResult(false, "execute too many times");
        }
        return ticketExecuteResult;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/execute/collected", method = RequestMethod.POST)
    public TicketExecuteResult collectTicket(@RequestBody TicketExecuteInfo info, @RequestHeader HttpHeaders headers) {
        System.out.println("[Execute Service][Collect] Id:" + info.getOrderId());
        collectNumCache = collectNumCache + 1;
        TicketExecuteResult ticketExecuteResult = null;
        if (collectNumCache <= 3) {
            ticketExecuteResult = executeService.ticketCollect(info, headers);
        } else if (collectNumCache > 3) {
            ticketExecuteResult = new TicketExecuteResult(false, "collected too many yimes");
        }
        return ticketExecuteResult;
    }
}
