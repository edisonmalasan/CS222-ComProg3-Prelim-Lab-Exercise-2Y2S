package pexer3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PreExercise3Server {
    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);

            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                System.out.println("Client connected.");

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals("exit")) {
                        System.out.println("Client disconnected.");
                        break;
                    }

                    String result = processExpression(inputLine);
                    out.println(result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String processExpression(String inputLine) {
        try {
            String[] tokens = inputLine.split(" ");
            if (tokens.length != 3) {
                return inputLine + " = Invalid Expression";
            }

            double operand1 = Double.parseDouble(tokens[0]);
            String operator = tokens[1];
            double operand2 = Double.parseDouble(tokens[2]);

            double result = 0;

            switch (operator) {
                case "^" -> {
                    result = Math.pow(operand1, operand2);
                }
                case "*" -> {
                    result = (operand1 * operand2);
                }
                case "/" -> {
                    result = (operand1 / operand2);
                }
                case "%" -> {
                    result = (operand1 % operand2);
                }
                case "+" -> {
                    result = (operand1 + operand2);
                }
                case "-" -> {
                    result = (operand1 - operand2);
                }
                default -> {
                    return inputLine + " = Invalid Expression";
                }
            }
            return inputLine + " = " + result;
        } catch (Exception e) {
            return  inputLine + " = Invalid Expression";
        }
    }
}
