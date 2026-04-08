package org.app;

import com.google.gson.Gson;

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class TaskService {
    public void saveTasks(List<Task> taskList){
        Gson gson= new Gson();
        String json = gson.toJson(taskList);

        try{
            FileWriter writer=new FileWriter("tasks.json");
            writer.write(json);
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
