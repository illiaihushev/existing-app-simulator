package app.service;

import app.model.dao.ProjectDAO;
import app.model.dao.UserDAO;
import app.repo.PositionsStore;
import app.repo.ProjectRepo;
import app.repo.UserRepo;
import app.web.dto.InfoToUpdateTitlesDTO;
import app.web.dto.ProjectDTO;
import app.web.dto.RelatedInfoDTO;
import app.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static app.util.StreamUtil.concatLists;
import static app.util.StreamUtil.concatCollectionsToSet;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ProjectService projectService;
    private final ProjectRepo projectRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, ProjectService projectService, ProjectRepo projectRepo) {
        this.userRepo = userRepo;
        this.projectService = projectService;
        this.projectRepo = projectRepo;
    }

    @Override
    public UserDAO findByUsername(String email) {
        return userRepo.findByUsername(email);
    }

    @Override
    public InfoToUpdateTitlesDTO retrieveInfoToUpdateTitles(UserDAO user) {
        InfoToUpdateTitlesDTO titles = new InfoToUpdateTitlesDTO();

        byte posId = user.getPositionId();

        if (posId == PositionsStore.LM) {
            fillRelatedNamesForLM(user, titles);
        } else {
            if (posId == PositionsStore.PMO) {
                fillRelatedNamesForPMO(user, titles);

            } else {
                if (posId == PositionsStore.DM) {
                    fillRelatedNamesForDM(user, titles);
                } else {
                    fillRelatedNamesForEmp(user, titles);
                }
            }
        }

        return titles;
    }

    @Override
    public RelatedInfoDTO retrieveRelevantInfo(InfoToUpdateTitlesDTO titles) {

        Collection<String> emps = titles.getEmployees();
        Collection<String> directs = titles.getDirectManagers();
        Collection<String> lines = titles.getLineManagers();
        Collection<String> pmos = titles.getPMOs();

        Collection<String> projects = titles.getProjects();

        Collection<UserDAO> filledEmps = findByUsernameIn(emps);
        Collection<UserDAO> filledDirects = findByUsernameIn(directs);
        Collection<UserDAO> filledLines = findByUsernameIn(lines);
        Collection<UserDAO> filledPmos = findByUsernameIn(pmos);

        Collection<ProjectDAO> filledProjects = projectService.findByNameIn(projects);

        List<UserDTO> empsDTO = userDaosToDtos(filledEmps);
        List<UserDTO> directsDTO = userDaosToDtos(filledDirects);
        List<UserDTO> linesDTO = userDaosToDtos(filledLines);
        List<UserDTO> pmosDTO = userDaosToDtos(filledPmos);

        Collection<ProjectDTO> projectsDTO = filledProjects.stream().map(ProjectDTO::new).collect(Collectors.toList());

        return new RelatedInfoDTO(empsDTO, directsDTO, linesDTO, pmosDTO, projectsDTO);
    }

    private List<UserDTO> userDaosToDtos(Collection<UserDAO> filledEmps) {
        return filledEmps.stream().map(UserDTO::new).collect(toList());
    }

    private List<UserDAO> findByUsernameIn(Collection<String> users) {
        if (users.isEmpty()) {
            return Collections.emptyList();
        }
        return userRepo.findByUsernameIn(users);
    }

    private void fillRelatedNamesForEmp(UserDAO user, InfoToUpdateTitlesDTO titles) {
        titles.setEmployees(singletonList(user.getUsername()));

        UserDAO manager = user.getManager();

        if (manager.getPositionId() == PositionsStore.DM) {
            titles.setDirectManagers(singletonList(manager.getUsername()));
            titles.setLineManagers(singletonList(manager.getManager().getUsername()));
        } else {
            titles.setDirectManagers(Collections.emptyList());
            titles.setLineManagers(singletonList(manager.getUsername()));
        }

        Set<ProjectDAO> projects = user.getProjects();

        titles.setPMOs(extractPMOsNames(projects));

        titles.setProjects(extractProjectsNames(projects));
    }

    private void fillRelatedNamesForDM(UserDAO user, InfoToUpdateTitlesDTO titles) {
        titles.setLineManagers(singletonList(user.getManager().getUsername()));

        String dmName = user.getUsername();
        titles.setDirectManagers(singletonList(dmName));

        List<String> emps = userRepo.findSubordinateEmails(user.getId());
        titles.setEmployees(emps);

        List<String> workers = new ArrayList<>(emps);
        workers.add(dmName);

        List<ProjectDAO> projects = projectRepo.findProjectsByParticipants(workers);

        titles.setPMOs(extractPMOsNames(projects));

        titles.setProjects(extractProjectsNames(projects));
    }

    private void fillRelatedNamesForLM(UserDAO user, InfoToUpdateTitlesDTO titles) {
        titles.setLineManagers(singletonList(user.getUsername()));

        List<UserDAO> subordinates = userRepo.findSubordinates(user.getId());

        Map<Byte, List<String>> positionsSubordinates = groupUsernamesByPosition(subordinates);

        List<String> directs = positionsSubordinates.get(PositionsStore.DM);
        if (directs == null) directs = Collections.emptyList();
        titles.setDirectManagers(directs);

        List<String> lineEmps = positionsSubordinates.get(PositionsStore.EMP);
        if (lineEmps == null) lineEmps = Collections.emptyList();

        List<String> directManagers = positionsSubordinates.get(PositionsStore.DM);
        titles.setDirectManagers(directManagers);

        List<String> emps = directManagers.stream()
                .map(userRepo::findSubordinateEmails)
                .collect(concatLists());

        emps.addAll(lineEmps);
        titles.setEmployees(emps);

        List<String> workers = new ArrayList<>(emps);
        workers.addAll(directManagers);

        List<ProjectDAO> projects = projectRepo.findProjectsByParticipants(workers);

        titles.setPMOs(extractPMOsNames(projects));

        titles.setProjects(extractProjectsNames(projects));

    }

    private void fillRelatedNamesForPMO(UserDAO user, InfoToUpdateTitlesDTO titles) {
        List<ProjectDAO> projects = projectRepo.findByPmo(user.getId());

        Map<Byte, List<String>> positionsSubordinates = projectsToUsernamesByPosition(projects);

        List<String> directs = positionsSubordinates.get(PositionsStore.DM);
        if (directs == null) directs = Collections.emptyList();
        titles.setDirectManagers(directs);

        List<String> emps = positionsSubordinates.get(PositionsStore.EMP);
        if (emps == null) emps = Collections.emptyList();
        titles.setEmployees(emps);

        titles.setLineManagers(Collections.emptyList());

        titles.setProjects(extractProjectsNames(projects));

        List<String> allPMOs = userRepo.findEmailsByPositionId(PositionsStore.PMO);
        titles.setPMOs(allPMOs);
    }


    private Map<Byte, List<String>> projectsToUsernamesByPosition(List<ProjectDAO> projects) {
        return groupUsernamesByPosition(projects.stream()
                .map(ProjectDAO::getUsers)
                .collect(concatCollectionsToSet()));
    }

    private Map<Byte, List<String>> groupUsernamesByPosition(Collection<UserDAO> users) {
        return users.stream()
                .collect(
                        groupingBy(
                                UserDAO::getPositionId,
                                mapping(UserDAO::getUsername, toList())));
    }


    private List<String> extractProjectsNames(Collection<ProjectDAO> projects) {
        return projects.stream()
                .map(ProjectDAO::getName)
                .collect(toList());
    }

    private List<String> extractPMOsNames(Collection<ProjectDAO> projects) {
        return projects.stream()
                .map(projectDAO -> projectDAO.getPmo().getUsername())
                .collect(toList());
    }
}
