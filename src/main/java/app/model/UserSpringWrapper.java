package app.model;

import app.model.dao.ProjectDAO;
import app.model.dao.UserDAO;
import app.model.dao.VacationDAO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserSpringWrapper extends User {
    private UserDAO nested;

    public UserSpringWrapper(String username, String password, Collection<? extends GrantedAuthority> authorities, UserDAO userDAO) {
        super(username, password, authorities);
        this.nested = userDAO;
    }

    public long getId() {
        return nested.getId();
    }

    public Byte getPositionId() {
        return nested.getPositionId();
    }

    public Set<ProjectDAO> getProjects() {
        return nested.getProjects();
    }

    public List<VacationDAO> getVacations() {
        return nested.getVacations();
    }

    public UserDAO getManager() {
        return nested.getManager();
    }

    public UserDAO getNested() {
        return nested;
    }

}
