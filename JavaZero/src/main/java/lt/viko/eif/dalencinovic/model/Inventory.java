package lt.viko.eif.dalencinovic.model;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Integer> stock= new HashMap<>();

    public void addStock(String productName, int quantity){
        stock.put(productName,stock.getOrDefault(productName,0)+quantity);
    }

    public int getStock(String productName){
        return stock.getOrDefault(productName,0);
    }

    public void reduceStock(String productName, int quantity){
        int current = stock.getOrDefault(productName,0);

        int updated = current-quantity;

        if (updated<0){
            updated=0;
        }
        stock.put(productName,updated);
    }
}
