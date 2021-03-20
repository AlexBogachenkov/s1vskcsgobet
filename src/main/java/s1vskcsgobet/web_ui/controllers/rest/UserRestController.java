package s1vskcsgobet.web_ui.controllers.rest;

import org.springframework.web.bind.annotation.*;
import s1vskcsgobet.core.requests.user.AddUserRequest;
import s1vskcsgobet.core.requests.user.DeleteUserByNicknameRequest;
import s1vskcsgobet.core.responses.user.AddUserResponse;
import s1vskcsgobet.core.responses.user.DeleteUserByNicknameResponse;
import s1vskcsgobet.core.responses.user.FindAllUsersResponse;
import s1vskcsgobet.core.services.UserService;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public AddUserResponse addUser(@RequestBody AddUserRequest request) {
        return userService.add(request);
    }

    @DeleteMapping(path = "/{nickname}", produces = "application/json")
    public DeleteUserByNicknameResponse deleteUserByNickname(@PathVariable String nickname) {
        DeleteUserByNicknameRequest request = new DeleteUserByNicknameRequest();
        request.setNickname(nickname);
        return userService.deleteByNickname(request);
    }

    @GetMapping(produces = "application/json")
    public FindAllUsersResponse findAllTeams() {
        return userService.findAll();
    }

}
