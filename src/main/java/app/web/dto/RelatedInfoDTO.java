package app.web.dto;

import java.util.Collection;

public class RelatedInfoDTO {
    private Collection<UserDTO> employees;
    private Collection<UserDTO> directs;
    private Collection<UserDTO> lines;
    private Collection<UserDTO> pmos;

    private Collection<ProjectDTO> projects;

    public RelatedInfoDTO(Collection<UserDTO> employees, Collection<UserDTO> directs, Collection<UserDTO> lines, Collection<UserDTO> pmos, Collection<ProjectDTO> projects) {
        this.employees = employees;
        this.directs = directs;
        this.lines = lines;
        this.pmos = pmos;
        this.projects = projects;
    }

    public Collection<UserDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(Collection<UserDTO> employees) {
        this.employees = employees;
    }

    public Collection<UserDTO> getDirects() {
        return directs;
    }

    public void setDirects(Collection<UserDTO> directs) {
        this.directs = directs;
    }

    public Collection<UserDTO> getLines() {
        return lines;
    }

    public void setLines(Collection<UserDTO> lines) {
        this.lines = lines;
    }

    public Collection<UserDTO> getPmos() {
        return pmos;
    }

    public void setPmos(Collection<UserDTO> pmos) {
        this.pmos = pmos;
    }

    public Collection<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(Collection<ProjectDTO> projects) {
        this.projects = projects;
    }




}


