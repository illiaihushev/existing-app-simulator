package app.service;

import app.model.dao.ProjectDAO;
import app.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepo projectRepo;

    public ProjectServiceImpl(@Autowired ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    @Override
    public List<ProjectDAO> findByNameIn(Collection<String> projects) {
        if (projects.isEmpty()) {
            return Collections.emptyList();
        }
        return projectRepo.findByNameIn(projects);
    }
}
