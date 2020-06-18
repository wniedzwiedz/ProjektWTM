package models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "package_entity")
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pack_generator")
    @SequenceGenerator(name="pack_generator", sequenceName = "pack_seq", allocationSize=50)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @OneToMany(mappedBy = "aPackage")
    private List<Group> groups;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
