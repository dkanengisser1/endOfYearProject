import java.util.*;

// Represents a metro station
public class Station {
    private String name;// The name of the station
    private Set<String> lines;// The set of metro lines the station belongs to

    public Station(String name) {
        this.name = name;
        this.lines = new HashSet<>();// Using Set to avoid duplicate lines 
    }
    // Return the name of the station
    public String getName() {
        return name;
    }

    // Add a metro line to this station
    public void addLine(String line) {
        lines.add(line);// Adds as it is a set will not be a repeat
    }

    // Return all lines the station is on
    public Set<String> getLines() {
        return lines;
    }

}