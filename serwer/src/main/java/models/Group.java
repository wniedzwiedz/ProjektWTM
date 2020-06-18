package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group_entity")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_generator")
    @SequenceGenerator(name="group_generator", sequenceName = "group_seq", allocationSize=50)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "bank_account_number")
    private String bankAccountNumber;
    @Column(name = "max_number_of_members")
    private int maxNumberOfMembers;
    @Column(name = "login_hash")
    private String loginHash;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "group_hash")
    private String groupHash;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package aPackage;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "group")
    private List<Payment> payments;

    @OneToMany(mappedBy = "group")
    private List<UserInGroup> userInGroups;

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

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public int getMaxNumberOfMembers() {
        return maxNumberOfMembers;
    }

    public void setMaxNumberOfMembers(int maxNumberOfMembers) {
        this.maxNumberOfMembers = maxNumberOfMembers;
    }

    public String getLoginHash() {
        return loginHash;
    }

    public void setLoginHash(String loginHash) {
        this.loginHash = loginHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getGroupHash() {
        return groupHash;
    }

    public void setGroupHash(String groupHash) {
        this.groupHash = groupHash;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<UserInGroup> getUserInGroups() {
        return userInGroups;
    }

    public void setUserInGroups(List<UserInGroup> userInGroups) {
        this.userInGroups = userInGroups;
    }
}