package lab.scd.net.socket;

import java.io.*;
import java.net.*;

public class ServerSimplu {
  public static void main(String[] args) throws IOException {
    ServerSocket ss = null;
    Socket s = null;

    try {
      ss = new ServerSocket(1900); // Create server socket
      System.out.println("Serverul asteapta conexiuni...");

      while (true) {
        s = ss.accept(); // Wait for a connection
        new ClientHandler(s).start(); // Handle client in a new thread
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (ss != null) ss.close();
    }
  }
}

class ClientHandler extends Thread {
  private Socket socket;

  public ClientHandler(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

      String line;
      while ((line = in.readLine()) != null) {
        if (line.equals("END")) {
          break; // Exit loop on "END"
        }

        double result = calculate(line); // Calculate result from client input
        out.println("Rezultatul: " + result); // Send result back to the client
      }
      System.out.println("Client disconnected.");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private double calculate(String expression) {
    String[] parts = expression.split(" ");
    double num1 = Double.parseDouble(parts[0]);
    String operator = parts[1];
    double num2 = Double.parseDouble(parts[2]);

    switch (operator) {
      case "+":
        return num1 + num2;
      case "-":
        return num1 - num2;
      case "*":
        return num1 * num2;
      case "/":
        return num1 / num2;
      default:
        return 0; // Unknown operator
    }
  }
}