package lt.viko.eif.dalencinovic.first.spring.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class Customers {
    private List<Customer> customers;

    public Customers() {
    }

    public Customers(List<Customer> customers){
        this.customers=customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    @XmlElement(name="customer")
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
