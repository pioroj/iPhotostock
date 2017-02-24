package testchat.network;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class WebBrowser {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String address = scanner.nextLine();
        Socket socket = new Socket(address, 80);
        OutputStream os = socket.getOutputStream();
        os.write("GET / HTTP/1.1\r\n".getBytes());
        os.write("\r\n".getBytes());

        InputStream is = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
    }

}
