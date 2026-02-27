package lt.viko.eif.dalencinovic.first.spring.menu;

import lt.viko.eif.dalencinovic.first.spring.db.CustomerRepository;
import lt.viko.eif.dalencinovic.first.spring.model.Customer;
import lt.viko.eif.dalencinovic.first.spring.model.Customers;
import lt.viko.eif.dalencinovic.first.spring.service.XMLTransformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Component
public class UserMenu {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private XMLTransformationService xmlService;
    private static List<Customer> customers;

    private int displayMenu(Scanner input){
        System.out.println(" \n"+
                "██    ██ ███████ ███████ ██████      ███    ███ ███████ ███    ██ ██    ██\n"+
                "██    ██ ██      ██      ██   ██     ████  ████ ██      ████   ██ ██    ██\n"+
                "██    ██ ███████ █████   ██████      ██ ████ ██ █████   ██ ██  ██ ██    ██\n"+
                "██    ██      ██ ██      ██   ██     ██  ██  ██ ██      ██  ██ ██ ██    ██\n"+
                " ██████  ███████ ███████ ██   ██     ██      ██ ███████ ██   ████  ██████");
        System.out.println(" Make a selection ");
        System.out.println("-------------------");
        System.out.printf("| 1) + %10s \n", "Fetch data from DB ");
        System.out.printf("| 2) + %10s \n", "Transform to XML");
        System.out.printf("| 6) Quit %7s \n", " ");
        return input.nextInt();
    }

    public void showMenu(){
        Scanner input =  new Scanner(System.in);
        int userChoice;
        do{
            userChoice=displayMenu(input);
            switch (userChoice){
                case 1:
                    customers=customerRepository.findAll();
                    for(Customer customer:customers){
                        System.out.println(customer);
                    }
                    break;
                case 2:
                    Customers customersObject = new Customers(customers);
                    xmlService.transformToXML(customersObject);
                    break;
                case 6:
                    System.out.println("Thank you and goodbye!");
                    System.exit(0);
                    break;
            }
        }while (userChoice!=6);
    }
}
