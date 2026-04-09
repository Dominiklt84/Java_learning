package lt.viko.eif.dalencinovic;

public class Main {
    static void main(String[] args) {
        JettyServer jettyServer=new JettyServer();

        try {
            jettyServer.start();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
