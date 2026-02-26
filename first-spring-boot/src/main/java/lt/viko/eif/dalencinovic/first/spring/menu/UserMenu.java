package lt.viko.eif.dalencinovic.first.spring.menu;

import lt.viko.eif.dalencinovic.first.spring.db.CustomerRepository;
import lt.viko.eif.dalencinovic.first.spring.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class UserMenu {
    @Autowired
    private CustomerRepository customerRepository;
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
        System.out.printf("| 6) Quit %7s \n", " ");
        return input.nextInt();
    }

    public void showMenu(){
        Scanner input =  new Scanner(System.in);
    }
}
