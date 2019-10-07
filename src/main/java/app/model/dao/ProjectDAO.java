package app.model.dao;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "projects")
public class ProjectDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pmo_id")
    private UserDAO pmo;

    @ManyToMany(mappedBy = "projects")
    private List<UserDAO> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDAO getPmo() {
        return pmo;
    }

    public void setPmo(UserDAO pmo) {
        this.pmo = pmo;
    }

    public List<UserDAO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDAO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof ProjectDAO)) return false;

        ProjectDAO projectDAO = (ProjectDAO) o;

        if (id != null && id.equals(projectDAO.id)) return true;

        return  name.equals(projectDAO.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
