package s1vskcsgobet.core.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s1vskcsgobet.core.database.BetRepository;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.domain.Bet;
import s1vskcsgobet.core.domain.Team;
import s1vskcsgobet.core.requests.bet.AddBetRequest;
import s1vskcsgobet.core.requests.bet.ChangeBetIsActiveStatusRequest;
import s1vskcsgobet.core.requests.bet.DeleteBetByIdRequest;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.bet.AddBetResponse;
import s1vskcsgobet.core.responses.bet.ChangeBetIsActiveStatusResponse;
import s1vskcsgobet.core.responses.bet.DeleteBetByIdResponse;
import s1vskcsgobet.core.responses.bet.FindAllBetsResponse;
import s1vskcsgobet.core.validators.bet.AddBetRequestValidator;
import s1vskcsgobet.core.validators.bet.ChangeBetIsActiveStatusRequestValidator;
import s1vskcsgobet.core.validators.bet.DeleteBetByIdRequestValidator;

import java.util.List;

@Service
public class BetService {

    private final BetRepository betRepository;
    private final TeamRepository teamRepository;
    private final AddBetRequestValidator addBetRequestValidator;
    private final DeleteBetByIdRequestValidator deleteBetByIdRequestValidator;
    private final ChangeBetIsActiveStatusRequestValidator changeBetIsActiveStatusRequestValidator;

    public BetService(BetRepository betRepository, TeamRepository teamRepository,
                      AddBetRequestValidator addBetRequestValidator, DeleteBetByIdRequestValidator deleteBetByIdRequestValidator,
                      ChangeBetIsActiveStatusRequestValidator changeBetIsActiveStatusRequestValidator) {
        this.betRepository = betRepository;
        this.teamRepository = teamRepository;
        this.addBetRequestValidator = addBetRequestValidator;
        this.deleteBetByIdRequestValidator = deleteBetByIdRequestValidator;
        this.changeBetIsActiveStatusRequestValidator = changeBetIsActiveStatusRequestValidator;
    }

    @Transactional
    public AddBetResponse add(AddBetRequest request) {
        List<CoreError> errors = addBetRequestValidator.validate(request);
        if (!errors.isEmpty()) {
            return new AddBetResponse(errors);
        }
        Team teamA = teamRepository.findByNameIgnoreCase(request.getTeamAName()).get();
        Team teamB = teamRepository.findByNameIgnoreCase(request.getTeamBName()).get();
        Bet bet = new Bet(teamA, teamB, request.getCoefficientTeamA(), request.getCoefficientTeamB(),
                request.getStage(), request.isActive());
        Bet addedBet = betRepository.save(bet);
        return new AddBetResponse(addedBet);
    }

    @Transactional
    public DeleteBetByIdResponse deleteById(DeleteBetByIdRequest request) {
        List<CoreError> errors = deleteBetByIdRequestValidator.validate(request);
        if (!errors.isEmpty()) {
            return new DeleteBetByIdResponse(errors);
        }
        betRepository.deleteById(request.getBetId());
        return new DeleteBetByIdResponse(true);
    }

    public FindAllBetsResponse findActive() {
        List<Bet> activeBets = betRepository.findByIsActiveOrderByIdDesc(true);
        return new FindAllBetsResponse(activeBets);
    }

    @Transactional
    public ChangeBetIsActiveStatusResponse changeIsActiveStatus(ChangeBetIsActiveStatusRequest request) {
        List<CoreError> errors = changeBetIsActiveStatusRequestValidator.validate(request);
        if (!errors.isEmpty()) {
            return new ChangeBetIsActiveStatusResponse(errors);
        }
        boolean changed = betRepository.changeIsActiveStatus(request.getBetId(), request.isNewIsActiveStatus()) > 0;
        return new ChangeBetIsActiveStatusResponse(changed);
    }

    public FindAllBetsResponse findAll() {
        List<Bet> allBets = betRepository.findAllByOrderByIdDesc();
        return new FindAllBetsResponse(allBets);
    }

}
