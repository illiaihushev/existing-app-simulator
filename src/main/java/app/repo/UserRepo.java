package app.repo;

import app.model.dao.UserDAO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface UserRepo extends CrudRepository<UserDAO, Integer> {
    UserDAO findByUsername(String username);

    @Query("select u from UserDAO u " +
            "where u.manager.id = :id")
    List<UserDAO> findSubordinates(@Param("id") long id);

    @Query("select u.username from UserDAO u " +
            "where u.manager.id = :id")
    List<String> findSubordinateEmails(@Param("id") long id);

    @Query("select u.username from UserDAO u " +
            "where u.manager.username = :email")
    List<String> findSubordinateEmails(@Param("email") String email);

    @Query("select u.username from UserDAO u " +
            "where u.positionId = :posId")
    List<String> findEmailsByPositionId(@Param("posId") byte positionId);

    @Query("select distinct u from UserDAO u " +
            "left join fetch u.projects " +
            "left join fetch u.manager " +
            "left join fetch u.vacations " +
            "where u.username in :usernames")
    List<UserDAO> findByUsernameIn(@Param("usernames") Collection<String> usernames);

}
