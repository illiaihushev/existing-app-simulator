package app.web.dto;

import app.model.dao.ProjectDAO;

public class ProjectDTO {
    private String name;
    private String pmo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPmo() {
        return pmo;
    }

    public void setPmo(String pmo) {
        this.pmo = pmo;
    }

    public ProjectDTO(ProjectDAO project) {
        this.name = project.getName();
        this.pmo = project.getPmo().getUsername();
    }
}