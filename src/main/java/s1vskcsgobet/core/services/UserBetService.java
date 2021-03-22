package s1vskcsgobet.core.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s1vskcsgobet.core.database.BetRepository;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.database.UserBetRepository;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.domain.*;
import s1vskcsgobet.core.requests.user_bet.AddUserBetRequest;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.user_bet.AddUserBetResponse;
import s1vskcsgobet.core.validators.user_bet.AddUserBetRequestValidator;

import java.util.List;

@Service
public class UserBetService {

    private AddUserBetRequestValidator validator;
    private UserBetRepository userBetRepository;
    private UserRepository userRepository;
    private BetRepository betRepository;
    private TeamRepository teamRepository;

    public UserBetService(AddUserBetRequestValidator validator, UserBetRepository userBetRepository,
                          UserRepository userRepository, BetRepository betRepository, TeamRepository teamRepository) {
        this.validator = validator;
        this.userBetRepository = userBetRepository;
        this.userRepository = userRepository;
        this.betRepository = betRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public AddUserBetResponse add(AddUserBetRequest request) {
        List<CoreError> errors = validator.validate(request);
        if (!errors.isEmpty()) {
            return new AddUserBetResponse(errors);
        }
        User user = userRepository.findById(request.getUserId()).get();
        Bet bet = betRepository.findById(request.getBetId()).get();
        Team winningTeam = teamRepository.findByNameIgnoreCase(request.getWinningTeamName()).get();
        UserBet userBet = new UserBet(user, bet, winningTeam, request.getAmount(), UserBetStatus.PENDING);
        return new AddUserBetResponse(userBetRepository.save(userBet));
    }

}
