package lab.scd.net.socket;

import java.net.*;
import java.io.*;

public class ClientSimplu {
  public static void main(String[] args) throws Exception {
    Socket socket = null;
    try {
      InetAddress server_address = InetAddress.getByName("localhost");
      socket = new Socket(server_address, 1900);

      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
      BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

      String expression;
      System.out.println("Introduceți două numere și o operație (ex: 5 + 3 sau END pentru a termina):");
      while (!(expression = userInput.readLine()).equals("END")) {
        out.println(expression); // Send the operation to the server
        String response = in.readLine(); // Read server response
        System.out.println("Server: " + response);
      }
      out.println("END"); // Send termination signal
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (socket != null) socket.close();
    }
  }
}