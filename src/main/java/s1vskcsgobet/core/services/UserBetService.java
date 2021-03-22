package s1vskcsgobet.core.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s1vskcsgobet.core.database.BetRepository;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.database.UserBetRepository;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.domain.*;
import s1vskcsgobet.core.requests.user.WithdrawFromUserBalanceRequest;
import s1vskcsgobet.core.requests.user_bet.AddUserBetRequest;
import s1vskcsgobet.core.requests.user_bet.FindUserBetsByUserIdRequest;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.user_bet.AddUserBetResponse;
import s1vskcsgobet.core.responses.user_bet.FindUserBetsByUserIdResponse;
import s1vskcsgobet.core.validators.user_bet.AddUserBetRequestValidator;
import s1vskcsgobet.core.validators.user_bet.FindUserBetsByUserIdRequestValidator;

import java.util.List;

@Service
public class UserBetService {

    private AddUserBetRequestValidator addUserBetRequestValidator;
    private FindUserBetsByUserIdRequestValidator findUserBetsByUserIdRequestValidator;
    private UserBetRepository userBetRepository;
    private UserRepository userRepository;
    private BetRepository betRepository;
    private TeamRepository teamRepository;
    private UserService userService;

    public UserBetService(AddUserBetRequestValidator addUserBetRequestValidator,
                          FindUserBetsByUserIdRequestValidator findUserBetsByUserIdRequestValidator,
                          UserBetRepository userBetRepository, UserRepository userRepository,
                          BetRepository betRepository, TeamRepository teamRepository, UserService userService) {
        this.addUserBetRequestValidator = addUserBetRequestValidator;
        this.findUserBetsByUserIdRequestValidator = findUserBetsByUserIdRequestValidator;
        this.userBetRepository = userBetRepository;
        this.userRepository = userRepository;
        this.betRepository = betRepository;
        this.teamRepository = teamRepository;
        this.userService = userService;
    }

    @Transactional
    public AddUserBetResponse add(AddUserBetRequest request) {
        List<CoreError> errors = addUserBetRequestValidator.validate(request);
        if (!errors.isEmpty()) {
            return new AddUserBetResponse(errors);
        }
        userService.withdrawFromBalance(new WithdrawFromUserBalanceRequest(request.getUserId(), request.getAmount()));
        User user = userRepository.findById(request.getUserId()).get();
        Bet bet = betRepository.findById(request.getBetId()).get();
        Team winningTeam = teamRepository.findByNameIgnoreCase(request.getWinningTeamName()).get();
        UserBet userBet = new UserBet(user, bet, winningTeam, request.getWinningTeamCoefficient(),
                request.getAmount(), UserBetStatus.PENDING);
        return new AddUserBetResponse(userBetRepository.save(userBet));
    }

    @Transactional
    public FindUserBetsByUserIdResponse findByUserId(FindUserBetsByUserIdRequest request) {
        List<CoreError> errors = findUserBetsByUserIdRequestValidator.validate(request);
        if (!errors.isEmpty()) {
            return new FindUserBetsByUserIdResponse(errors, null);
        }
        List<UserBet> userBets = userBetRepository.findByUserIdOrderByIdDesc(request.getUserId());
        return new FindUserBetsByUserIdResponse(null, userBets);
    }

}
