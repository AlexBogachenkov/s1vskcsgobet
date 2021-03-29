package s1vskcsgobet.web_ui.controllers.rest;

import org.springframework.web.bind.annotation.*;
import s1vskcsgobet.core.requests.bet.AddBetRequest;
import s1vskcsgobet.core.requests.bet.ChangeBetIsActiveStatusRequest;
import s1vskcsgobet.core.requests.bet.DeleteBetByIdRequest;
import s1vskcsgobet.core.responses.bet.AddBetResponse;
import s1vskcsgobet.core.responses.bet.ChangeBetIsActiveStatusResponse;
import s1vskcsgobet.core.responses.bet.DeleteBetByIdResponse;
import s1vskcsgobet.core.responses.bet.FindAllBetsResponse;
import s1vskcsgobet.core.services.BetService;

@RestController
@RequestMapping("/bet")
public class BetRestController {

    private final BetService betService;

    public BetRestController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public AddBetResponse addBet(@RequestBody AddBetRequest request) {
        return betService.add(request);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public DeleteBetByIdResponse deleteBetById(@PathVariable Long id) {
        DeleteBetByIdRequest request = new DeleteBetByIdRequest();
        request.setBetId(id);
        return betService.deleteById(request);
    }

    @PatchMapping(consumes = "application/json", produces = "application/json")
    public ChangeBetIsActiveStatusResponse addBet(@RequestBody ChangeBetIsActiveStatusRequest request) {
        return betService.changeIsActiveStatus(request);
    }

    @GetMapping(produces = "application/json")
    public FindAllBetsResponse findAllBets() {
        return betService.findAll();
    }

}
