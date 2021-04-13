package s1vskcsgobet.core.services;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import s1vskcsgobet.security.BetsUser;

@Service
public class ApplicationContextService {

    public Long getUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        BetsUser user = (BetsUser) context.getAuthentication().getPrincipal();
        return user.getId();
    }

}
