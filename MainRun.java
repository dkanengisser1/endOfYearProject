import java.util.*;
import java.io.*;

public class MainRun{
    public static void shortestPath(Metro metro, String station1, String station2){
        System.out.println("This is the shortest path between the two stations");
        List<String> path = metro.getShortestPath(station1,station2);
        if (path == null || path.isEmpty()) {
            System.out.println("No path found.");
            return;
        }

        String previousLine = null;
        int numberOfChanges=0;
        for (int i = 0; i < path.size() - 1; i++) {
            String from = path.get(i);
            String to = path.get(i + 1);
            String currentLine = null;

            
            for (Edge connection : metro.getConnections().get(from)) {
                if (connection.getDestination().equals(to)) {
                    currentLine = connection.getLine();
                    break;
                }
            }
    
    
            if (previousLine != null && !previousLine.equals(currentLine)) {
                System.out.println("Transfer to " + currentLine);
                numberOfChanges+=1;
            }
    
            System.out.println("" + to + " on the " + currentLine + " line");
            previousLine = currentLine;
        }
        System.out.println("Total distance is = " + metro.totalDistance(path) + "(minutes)");
        System.out.println("Number of changes is = " + numberOfChanges);

    }

    public static void leastChanges(Metro metro, String station1, String station2){
        System.out.println("This is the least line changes");
        List<String> path = metro.getLeastTransfersPath(station1,station2);
        if (path == null || path.isEmpty()) {
            System.out.println("No path found.");
            return;
        }

        String previousLine = null;
        int numberOfChanges=0;
        for (int i = 0; i < path.size() - 1; i++) {
            String from = path.get(i);
            String to = path.get(i + 1);
            String currentLine = null;

            
            for (Edge connection : metro.getConnections().get(from)) {
                if (connection.getDestination().equals(to)) {
                    currentLine = connection.getLine();
                    break;
                }
            }
    
    
            if (previousLine != null && !previousLine.equals(currentLine)) {
                System.out.println("Transfer to " + currentLine);
                numberOfChanges+=1;
            }
    
            System.out.println("" + to + " on the " + currentLine + " line");
            previousLine = currentLine;
        }
        System.out.println("Total distance is = " + metro.totalDistance(path) + "(minutes)");
        System.out.println("Number of changes is = " + numberOfChanges);

    }

    public static void main(String[] args){

        try{
        String filename = args[0];  // Get the file name from command-line argument
    

        Metro metro = new Metro();
        // Load metro data from a CSV file and populate the map
        
        BufferedReader reader = new BufferedReader(new FileReader(filename));// Open the file for reading


        String line = reader.readLine();// Read the first line (header: From,To,Line,Time) and skip it

        // Read each remaining line in the file until last line which is null
        while ((line = reader.readLine()) != null) {

            String[] parts = line.split(","); // Split the line by commas


            if (parts.length < 4) continue; // Skip lines that don't have exactly 4 fields to prevent error

            // Trim spaces from each field and assign to variables trimmed to standardise
            String from = parts[0].trim();// Starting station
            String to = parts[1].trim();// Destination station
            String metroLine = parts[2].trim();// Line name 
            float distance = Float.parseFloat(parts[3].trim());// Parse the travel time as an float/decimal

            metro.addStation(from, metroLine);// Add the Station to the map using the parsed data (All repeats will be ignored)
            metro.addStation(to, metroLine);
            metro.addConnection(from, to, metroLine, distance);// Add the connection to the map using the parsed data
        }
        reader.close();// Close the file reader
        Scanner scanner = new Scanner(System.in); // Create Scanner object


        // Add Connections (Station1, Station2, Travel Time in Minutes)

        // Display Metro Map
        String station1;
        String station2;
        boolean isStation = false;// Creates a variable that will determine if correct station is given
        do{// Do while loop so atleast 1 loop will happen and if it correct first time it will not repeat
            System.out.println("Please input the starting station");
            station1 = scanner.nextLine();
            isStation = metro.isStation(station1);// Uses metro method that will look into the Station map to check if it is in the map
        }while (isStation==false);
        isStation = false;
        do{
            System.out.println("Please input the end station");
            station2 = scanner.nextLine();
            isStation = metro.isStation(station2);
            }while (isStation==false);
        String option;
        boolean isOption = false;
        do{
            System.out.println("Do you want the shortest path or the least changes");
            option = scanner.nextLine();
            option.trim();
            if(option.equals("shortest path") || option.equals("least changes")){
                isOption = true;
            }
            }while (isOption==false);
        scanner.close();
        if(option.equals("shortest path")){
            shortestPath(metro,station1,station2);
        }
        else if (option.equals("least changes")){
            leastChanges(metro, station1, station2);
        }
    }
    catch (IOException e) {
        System.err.println("Error loading map from file: " + e.getMessage());
    }
}
}
