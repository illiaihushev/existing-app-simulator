package app.config;

import app.model.UserSpringWrapper;
import app.model.dao.ProjectDAO;
import app.repo.UserRepo;
import app.model.dao.UserDAO;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserSpringWrapper loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDAO user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        UserDAO mngr = user.getManager();
        for (ProjectDAO project : user.getProjects()) {
            Hibernate.initialize(project.getPmo());
        }
        Hibernate.initialize(user);
        if (mngr != null) {
            Hibernate.initialize(mngr);
        }

        return new UserSpringWrapper(user.getUsername(), user.getPassword(), Collections.emptyList(), user);
    }
}
