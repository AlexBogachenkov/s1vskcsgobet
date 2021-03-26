package s1vskcsgobet.core.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s1vskcsgobet.core.database.BetRepository;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.database.UserBetRepository;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.domain.*;
import s1vskcsgobet.core.requests.user.TopUpUserBalanceRequest;
import s1vskcsgobet.core.requests.user.WithdrawFromUserBalanceRequest;
import s1vskcsgobet.core.requests.user_bet.AddUserBetRequest;
import s1vskcsgobet.core.requests.user_bet.ApplyMatchResultToUserBetsRequest;
import s1vskcsgobet.core.requests.user_bet.FindUserBetsByUserIdRequest;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.user_bet.AddUserBetResponse;
import s1vskcsgobet.core.responses.user_bet.ApplyMatchResultToUserBetsResponse;
import s1vskcsgobet.core.responses.user_bet.FindUserBetsByUserIdResponse;
import s1vskcsgobet.core.validators.user_bet.AddUserBetRequestValidator;
import s1vskcsgobet.core.validators.user_bet.ApplyMatchResultToUserBetsRequestValidator;
import s1vskcsgobet.core.validators.user_bet.FindUserBetsByUserIdRequestValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class UserBetService {

    private AddUserBetRequestValidator addUserBetRequestValidator;
    private FindUserBetsByUserIdRequestValidator findUserBetsByUserIdRequestValidator;
    private ApplyMatchResultToUserBetsRequestValidator applyMatchResultToUserBetsRequestValidator;
    private UserBetRepository userBetRepository;
    private UserRepository userRepository;
    private BetRepository betRepository;
    private TeamRepository teamRepository;
    private UserService userService;

    public UserBetService(AddUserBetRequestValidator addUserBetRequestValidator,
                          FindUserBetsByUserIdRequestValidator findUserBetsByUserIdRequestValidator,
                          ApplyMatchResultToUserBetsRequestValidator applyMatchResultToUserBetsRequestValidator,
                          UserBetRepository userBetRepository, UserRepository userRepository, BetRepository betRepository,
                          TeamRepository teamRepository, UserService userService) {
        this.addUserBetRequestValidator = addUserBetRequestValidator;
        this.findUserBetsByUserIdRequestValidator = findUserBetsByUserIdRequestValidator;
        this.applyMatchResultToUserBetsRequestValidator = applyMatchResultToUserBetsRequestValidator;
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

    @Transactional
    public ApplyMatchResultToUserBetsResponse applyMatchResult(ApplyMatchResultToUserBetsRequest request) {
        List<CoreError> errors = applyMatchResultToUserBetsRequestValidator.validate(request);
        if (!errors.isEmpty()) {
            return new ApplyMatchResultToUserBetsResponse(errors);
        }
        Long winningTeamId = teamRepository.findByNameIgnoreCase(request.getWinningTeamName()).get().getId();
        userBetRepository.updateStatusByBetIdAndWinningTeamName(request.getBetId(), winningTeamId, UserBetStatus.WON);
        userBetRepository.updateStatusByBetIdAndNotWinningTeamName(request.getBetId(), winningTeamId, UserBetStatus.LOST);

        List<UserBet> winningUserBets = userBetRepository.findByBetIdAndStatus(request.getBetId(), UserBetStatus.WON);
        winningUserBets.forEach(userBet -> {
            BigDecimal amountWon = userBet.getAmount().multiply(userBet.getWinningTeamCoefficient());
            amountWon = amountWon.setScale(2, RoundingMode.HALF_UP);
            userService.topUpBalance(new TopUpUserBalanceRequest(userBet.getUser().getId(), amountWon));
        });

        return new ApplyMatchResultToUserBetsResponse();
    }

}
