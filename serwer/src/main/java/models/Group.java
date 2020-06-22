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
    @Column(name = "information")
    private String information;
    @Column(name = "payment_info")
    private String paymentInfo;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package aPackage;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

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

    public List<UserInGroup> getUserInGroups() {
        return userInGroups;
    }

    public void setUserInGroups(List<UserInGroup> userInGroups) {
        this.userInGroups = userInGroups;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
}
