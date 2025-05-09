    import java.util.*;

    // MetroMap class will contain all station and connections and to manipulate them
    public class Metro{
        private Map<String, Station> stations;          // Map data structure that contains station name with the station object
        private Map<String, List<Edge>> connections;  // Map data structure that contains station name with the list of directly connected station names
        // Constructor method that initializes the station and connections object
        public Metro() {
            stations = new HashMap<>();
            connections = new HashMap<>();
        }
    
        // Add a station to the map, associating it with a line
        public void addStation(String name, String line) {
            stations.putIfAbsent(name, new Station(name)); // Create station if not already present
            stations.get(name).addLine(line);              // Add the line to the station putIfAbsent is within method
        }
    
        // Add a bidirectional connection between two stations
        public void addConnection(String from, String to, String line, float distance) {

            connections.putIfAbsent(from, new ArrayList<>());// Initializes the connection for the first station then inputs values does it both ways as metros are bidirectional
            connections.get(from).add(new Edge(to, line, distance));

            connections.putIfAbsent(to, new ArrayList<>());
            connections.get(to).add(new Edge(from, line, distance));
        } 
        public Map<String, Station> getStations(){
            return stations;
        }
        public Map<String, List<Edge>> getConnections(){
            return connections;
        }

        public List<String> getShortestPath(String start, String end) {
            
            Map<String, Double> distances = new HashMap<>();// Map to store the shortest distance to each station
            Set<String> visited = new HashSet<>();// Set to track visited stations
            Map<String, String> previousStations = new HashMap<>();// Map to store the previous station in the path for reconstruction
    
            for (String station : stations.keySet()) {// Initialize distances to infinity for all stations to set to accurate values when visited
                distances.put(station, Double.MAX_VALUE);
            }
            distances.put(start, 0.0);// The distance to the start station is 0
            String currentStation = start;// The current station to explore (starts with the start station)
    
            while (currentStation != null) {
                visited.add(currentStation);// Mark the current station as visited
                List<Edge> neighbors = connections.get(currentStation);// Get the neighbors of the current station
    
                for (Edge connection : neighbors) {// Explore all neighbors
                    String neighbor = connection.getDestination();
                    double newDistance = distances.get(currentStation) + connection.getDistance();
    
                    // If we find a shorter path to the neighbor, update its distance
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previousStations.put(neighbor, currentStation);
                    }
                }
    
                // Find the next unvisited station with the smallest distance
                currentStation = null;
                double smallestDistance = Double.MAX_VALUE;
                for (String station : stations.keySet()) {
                    if (!visited.contains(station) && distances.get(station) < smallestDistance) {
                        smallestDistance = distances.get(station);
                        currentStation = station;
                    }
                }
    
                // If there is no reachable unvisited station, break out of the loop
                if (currentStation == null) break;
            }
    
            // Reconstruct the path by tracing back from the end station
            List<String> path = new ArrayList<>();
            String station = end;
            while (station != null) {
                path.add(station);
                station = previousStations.get(station);
            }
    
            List<String> reversed = new ArrayList<>();
            for (int i = path.size() - 1; i >= 0; i--) {
                reversed.add(path.get(i));
            }
            
    
            return reversed.size() == 1 ? null : reversed;  // Return null if no path was found
        }
        public double totalDistance(List<String> path) {
            double totalDistance = 0.0;
    
            for (int i = 0; i < path.size() - 1; i++) {
                String currentStation = path.get(i);
                String nextStation = path.get(i + 1);
    
                // Find the connection between the current and next station
                for (Edge connection : connections.get(currentStation)) {
                    if (connection.getDestination().equals(nextStation)) {
                        totalDistance += connection.getDistance();
                        break;  // Exit the loop once the connection is found
                    }
                }
            }
    
            return totalDistance;
        }
        public List<String> getLeastTransfersPath(String start, String end) {
            Map<String, Integer> transfersMap = new HashMap<>(); // station -> number of transfers
            Map<String, String> previous = new HashMap<>(); // station -> previous station
            Map<String, String> usedLines = new HashMap<>();    // station -> line used to reach it
        
            Set<String> visited = new HashSet<>();
            List<String> toVisit = new ArrayList<>();
        
            transfersMap.put(start, 0);
            toVisit.add(start);
        
            while (!toVisit.isEmpty()) {
                String current = toVisit.remove(0);
                visited.add(current);
        
                int currentTransfers = transfersMap.get(current);
                String currentLine = usedLines.getOrDefault(current, null);
        
                for (Edge connection : connections.getOrDefault(current, new ArrayList<>())) {
                    String neighbor = connection.getDestination();
                    String line = connection.getLine();
        
                    // Calculate transfer: +1 if changing lines
                    int newTransfers = (currentLine == null || currentLine.equals(line))
                                     ? currentTransfers
                                     : currentTransfers + 1;
        
                    if (!transfersMap.containsKey(neighbor) || newTransfers < transfersMap.get(neighbor)) {
                        transfersMap.put(neighbor, newTransfers);
                        previous.put(neighbor, current);
                        usedLines.put(neighbor, line);
        
                        if (!visited.contains(neighbor)) {
                            toVisit.add(neighbor);
                        }
                    }
                }
            }
        
            // Reconstruct path
            List<String> path = new ArrayList<>();
            String current = end;
        
            if (!previous.containsKey(end) && !start.equals(end)) {
                return null; // No path found
            }
        
            while (current != null) {
                path.add(current);
                current = previous.get(current);
            }
        
            // Reverse path manually
            List<String> reversed = new ArrayList<>();
            for (int i = path.size() - 1; i >= 0; i--) {
                reversed.add(path.get(i));
            }
        
            return reversed;
        }
            
    public boolean isStation(String station){
        if(stations.containsKey(station)==true){
            return true;
        }
        else{
            return false;
        }
    }
}