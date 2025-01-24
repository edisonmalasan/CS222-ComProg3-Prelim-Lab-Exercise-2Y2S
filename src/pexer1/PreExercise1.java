package pexer1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PreExercise1 {
    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            boolean search = true;
            int hostCount = 0;

            while (search) {
                System.out.print("Host " + (++hostCount) + " - Type IP address/Hostname: ");
                String hostInput = bufferedReader.readLine();

                try {
                    InetAddress[] inetAddresses = InetAddress.getAllByName(hostInput);

                    System.out.println("Number of Hosts/IPs: " + inetAddresses.length);
                    System.out.println("Host name\tIP Address");

                    for (InetAddress inetAddress : inetAddresses) {
                        System.out.println(inetAddress.getHostName() + "\t" + inetAddress.getHostAddress());
                    }
                } catch (UnknownHostException ex) {
                    System.out.println("Unknown host: " + hostInput);
                }

                System.out.print("Search another [y/n]? ");
                String choice = bufferedReader.readLine().trim().toLowerCase();

                // Check user input for continuation
                search = choice.equals("y");
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        System.out.println("Program terminated.");
    }
}
