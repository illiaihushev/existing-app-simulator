package app.service;

import app.model.dao.ProjectDAO;

import java.util.Collection;
import java.util.List;

public interface ProjectService {
    List<ProjectDAO> findByNameIn(Collection<String> projects);
}



