package s1vskcsgobet.web_ui.controllers.rest;

import org.springframework.web.bind.annotation.*;
import s1vskcsgobet.core.requests.bet.AddBetRequest;
import s1vskcsgobet.core.responses.bet.AddBetResponse;
import s1vskcsgobet.core.responses.bet.FindAllBetsResponse;
import s1vskcsgobet.core.services.BetService;

@RestController
@RequestMapping("/bet")
public class BetRestController {

    private final BetService betService;

    public BetRestController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping(path = "/add",
            consumes = "application/json",
            produces = "application/json")
    public AddBetResponse addBet(@RequestBody AddBetRequest request) {
        return betService.add(request);
    }



    @GetMapping(path = "/findAll",
            produces = "application/json")
    public FindAllBetsResponse findAllBets() {
        return betService.findAll();
    }

}
