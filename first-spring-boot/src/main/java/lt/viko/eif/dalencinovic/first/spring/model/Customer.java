package lt.viko.eif.dalencinovic.first.spring.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@Entity
@Table(name = "customer")
@XmlRootElement
public class Customer extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="Customer_Account",
            joinColumns = @JoinColumn(name="customer_id"),
            inverseJoinColumns = @JoinColumn(name="account_id")
    )
    private List<Account> accounts;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Customer: " +
                "\n\tid=" + getId() +
                "\n\tfirstName="+ firstName+
                "\n\tlastName="+ lastName+
                "\n\temail="+email+
                "\n\taccounts="+accounts;
    }
}
