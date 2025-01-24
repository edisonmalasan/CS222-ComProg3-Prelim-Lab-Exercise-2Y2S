package pexer3;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;

public class PreExercise3Client {
    public static void main(String[] args) {
        String filePath = "res/exer3.xml";
        String serverHost = "localhost";
        int serverPort = 5000;

        try (Socket serverSocket = new Socket(serverHost, serverPort);
             PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {

            System.out.println("Connected to " + serverHost + ":" + serverPort);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);

            NodeList nodeList = document.getElementsByTagName("expression");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element expressionElement = (Element) node;

                    String operand1 = expressionElement.getElementsByTagName("operand1").item(0).getTextContent();
                    String operator = expressionElement.getElementsByTagName("operator").item(0).getTextContent();
                    String operand2 = expressionElement.getElementsByTagName("operand2").item(0).getTextContent();

                    String expression = operator + " " + operand1 + " " + operand2;
                    out.println(expression);
                    System.out.println("Server response: " + in.readLine());
                }
            }
            out.println("terminated....");
            System.out.println("Client Disconnected.");

        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
