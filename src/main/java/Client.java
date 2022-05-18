import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        System.out.println("Введите искомое слово");
        Scanner scanner = new Scanner(System.in);

        String word = scanner.nextLine();

        System.out.println("слово введено");

        try (Socket socket = new Socket("localhost", 8989);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            out.println(word);
            String inValue = in.readLine();
            System.out.println(inValue);
        }

    }
}
