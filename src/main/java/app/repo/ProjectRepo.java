package app.repo;

import app.model.dao.ProjectDAO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ProjectRepo extends CrudRepository<ProjectDAO, Integer> {
    @Query("from ProjectDAO p where p.pmo.id = :pmoId")
    List<ProjectDAO> findByPmo(@Param("pmoId") long pmoId);

    @Query(nativeQuery = true, value = "select distinct " +
            "p.id as \"id\", p.name as \"name\", p.pmo_id as \"pmo_id\" from projects p " +
            "inner join projects_users pu on p.id = pu.project_id " +
            "inner join users u on u.id = pu.user_id where u.email in :emails")
    List<ProjectDAO> findProjectsByParticipants(@Param("emails") List<String> emails);

    @Query("select p from ProjectDAO p left join fetch p.pmo where p.name in :names")
    List<ProjectDAO> findByNameIn(@Param("names") Collection<String> names);
}
