package org.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        List<Task> taskList = new ArrayList<>();
        TaskService taskService = new TaskService();
        int choose=0;
        int number;
        Scanner scanner = new Scanner(System.in);
        while (choose!=5){
            System.out.println("Menu");
            System.out.println("1. Add task");
            System.out.println("2. Show task");
            System.out.println("3. Delete task");
            System.out.println("4. Mark task as done");
            System.out.println("5. Quit");
            System.out.println("Your choose: ");
            choose=scanner.nextInt();
            scanner.nextLine();
            switch (choose){
                case 1:
                    System.out.println("Enter task title: ");
                    String title = scanner.nextLine();
                    Task task = new Task(title,false);
                    taskList.add(task);
                    taskService.saveTasks(taskList);
                    break;
                case 2:
                    System.out.println("All tasks:");
                    for (int i=0;i<taskList.size();i++){
                        System.out.println((i+1)+". "+taskList.get(i));
                    }
                    break;
                case 3:
                    System.out.println("Which task you want to delete (number):");
                    number = scanner.nextInt();
                    scanner.nextLine();
                    number--;
                    if(number>=0&& number<taskList.size()){
                        taskList.remove(number);
                        taskService.saveTasks(taskList);
                    }else {
                        System.out.println("Invalid choose, try again.");
                    }
                    break;
                case 4:
                    System.out.println("Which task you done: ");
                    number = scanner.nextInt();
                    scanner.nextLine();
                    number--;
                    if(number>=0&& number<taskList.size()){
                        taskList.get(number).setDone(true);
                        System.out.println("Task marked as done!");
                        taskService.saveTasks(taskList);
                    }else {
                        System.out.println("Invalid choose, try again.");
                    }
                    break;
                case 5:
                    System.out.println("Bye");
                    return;
                default:
                    System.out.println("Wrong choose, try again!");
                    break;

            }
        }
    }
}

