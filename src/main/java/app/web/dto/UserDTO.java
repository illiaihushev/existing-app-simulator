package app.web.dto;

import app.model.dao.ProjectDAO;
import app.model.dao.UserDAO;
import app.model.dao.VacationDAO;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDTO {
    private String username;
    private String name;
    private String manager;
    private byte position;
    private Collection<VacationDTO> vacations;
    private Collection<String> projects;

    public UserDTO(UserDAO user) {
        this.username = user.getUsername();

        this.name = user.getName();

        UserDAO mngr = user.getManager();
        if (mngr != null){
            this.manager = user.getManager().getUsername();
        }

        this.position = user.getPositionId();

        this.vacations = user.getVacations().stream().map(VacationDTO::new).collect(Collectors.toList());
        this.projects = user.getProjects().stream().map(ProjectDAO::getName).collect(Collectors.toList());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public byte getPosition() {
        return position;
    }

    public void setPosition(byte position) {
        this.position = position;
    }

    public Collection<VacationDTO> getVacations() {
        return vacations;
    }

    public void setVacations(Collection<VacationDTO> vacations) {
        this.vacations = vacations;
    }

    public Collection<String> getProjects() {
        return projects;
    }

    public void setProjects(Collection<String> projects) {
        this.projects = projects;
    }
}