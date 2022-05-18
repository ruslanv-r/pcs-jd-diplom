import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine;
         try {
             engine = new BooleanSearchEngine(new File("pdfs"));
       }catch (IOException e){
           System.out.println("ошибка");
           e.printStackTrace();
           return;
       }
        //System.out.println(engine.search("если"));


        // здесь создайте сервер, который отвечал бы на нужные запросы
        // слушать он должен порт 8989
        // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
        int port = 8989;

        try (ServerSocket serverSocket = new ServerSocket(port);) {

            while (true) {
                System.out.println("Starting server at " + port + "...");
                try (
                        Socket connection = serverSocket.accept();
                        PrintWriter out = new PrintWriter(connection.getOutputStream(), true); //исх
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())); //вх

                ) {
                    System.out.println("Port - " + connection.getPort());
                    String inValue = in.readLine();
                    System.out.println(inValue);

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    String json = gson.toJson(engine.search(inValue));

                    System.out.println(json);
                    out.println(json);
//            Map map = gson.fromJson(inValue,Map.class);

                    //if (map.containsKey())

                    //serverSocket.close();

                }
            }

        } catch (IOException e){
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}