package s1vskcsgobet.core.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s1vskcsgobet.core.database.BetRepository;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.domain.Bet;
import s1vskcsgobet.core.domain.Team;
import s1vskcsgobet.core.dto.BetDto;
import s1vskcsgobet.core.requests.bet.AddBetRequest;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.bet.AddBetResponse;
import s1vskcsgobet.core.validators.bet.AddBetRequestValidator;

import java.util.List;

@Service
public class BetService {

    private final BetRepository betRepository;
    private final TeamRepository teamRepository;
    private final AddBetRequestValidator addBetRequestValidator;

    public BetService(BetRepository betRepository, TeamRepository teamRepository,
                      AddBetRequestValidator addBetRequestValidator) {
        this.betRepository = betRepository;
        this.teamRepository = teamRepository;
        this.addBetRequestValidator = addBetRequestValidator;
    }

    @Transactional
    public AddBetResponse add(AddBetRequest request) {
        List<CoreError> errors = addBetRequestValidator.validate(request);
        if (!errors.isEmpty()) {
            return new AddBetResponse(errors);
        }
        Team teamA = teamRepository.findByNameIgnoreCase(request.getTeamAName()).get();
        Team teamB = teamRepository.findByNameIgnoreCase(request.getTeamBName()).get();
        Bet bet = new Bet(teamA, teamB, request.getCoefficientTeamA(), request.getCoefficientTeamB(), request.isActive());
        Bet addedBet = betRepository.save(bet);
        return new AddBetResponse(addedBet);
    }
}
