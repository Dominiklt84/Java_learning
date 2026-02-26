package lt.viko.eif.dalencinovic.first.spring.service;

import lt.viko.eif.dalencinovic.first.spring.db.CustomerRepository;
import lt.viko.eif.dalencinovic.first.spring.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void run(String... args)throws Exception{
        List<Customer> customers=customerRepository.findAll();
        for(Customer customer:customers){
            System.out.println(customer);
        }
        System.in.read();
    }
}
