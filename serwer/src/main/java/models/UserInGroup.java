package models;

import javax.persistence.*;

@Entity
@Table(name = "user_in_group")
public class UserInGroup {

    @EmbeddedId
    private UserInGroupId id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private Group group;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    public UserInGroupId getId() {
        return id;
    }

    public void setId(UserInGroupId id) {
        this.id = id;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
}
