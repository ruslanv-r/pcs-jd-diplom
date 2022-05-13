import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        //System.out.println(engine.search("если"));





        // здесь создайте сервер, который отвечал бы на нужные запросы
        // слушать он должен порт 8989
        // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
        int port = 8989;


        while (true) {

            System.out.println("Starting server at " + port + "...");
            ServerSocket serverSocket = new ServerSocket(port);
            Socket connection = serverSocket.accept();
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true); //исх
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())); //вх
            System.out.println("Port - " + connection.getPort());
            String inValue= in.readLine();
            System.out.println(inValue);

//            engine.search


            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String json = gson.toJson(engine.search(inValue));

            System.out.println(json);

//            Map map = gson.fromJson(inValue,Map.class);

            //if (map.containsKey())

            serverSocket.close();

        }









    }
}