package app.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email")
    private String username;

    @NotNull
    @JsonIgnore
    private String password;

    @NotNull
    @Column(name = "position_id")
    private Byte positionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private UserDAO manager;

    @NotNull
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "projects_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            foreignKey = @ForeignKey(name = "projects_users_users_fk"),
            inverseJoinColumns = {@JoinColumn(name = "project_id")},
            inverseForeignKey = @ForeignKey(name = "projects_users_projects_fk")
    )
    private Set<ProjectDAO> projects;

    @OneToMany(mappedBy="user", fetch=FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<VacationDAO> vacations;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getPositionId() {
        return positionId;
    }

    public void setPositionId(Byte positionId) {
        this.positionId = positionId;
    }

    public Set<ProjectDAO> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectDAO> projects) {
        this.projects = projects;
    }

    public List<VacationDAO> getVacations() {
        return vacations;
    }

    public void setVacations(List<VacationDAO> vacations) {
        this.vacations = vacations;
    }

    public UserDAO getManager() {
        return manager;
    }

    public void setManager(UserDAO manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof UserDAO)) return false;

        UserDAO userDAO = (UserDAO) o;

        if (id != null && id.equals(userDAO.id)) return true;

        return  username.equals(userDAO.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
