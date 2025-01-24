package pexer2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PreExercise2 {
    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);

            while (true) {
                System.out.println("Waiting for connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress().getHostAddress());

                new Thread(() -> clientHandler(clientSocket)).start();
            }
        } catch (Exception e) {
            System.err.println("Error Occurred in the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void clientHandler(Socket clientSocket) {
        try(
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter streamWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                ) {
            streamWriter.println("What is your name? ");
            String name = streamReader.readLine();

            int age;
            while (true) {
                streamWriter.println("What is your age? ");
                try {
                    age = Integer.parseInt(streamReader.readLine());
                    if (age <= 0) {
                        throw new NumberFormatException();
                    } else if (age > 130) {
                        throw new NumberFormatException();
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    streamWriter.println("Invalid age");
                }
            }

            if (age >= 18) {
                streamWriter.println(name + ", you may exercise your right to vote!");
            } else {
                streamWriter.println(name + ", you are still too young to vote!");
            }
            streamWriter.println("Thank you and good day.");

            System.out.println("Client session ended: " + clientSocket.getInetAddress());
        } catch (Exception e) {
            System.err.println("Error while handling client: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (Exception e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}