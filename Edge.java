public class Edge{
    private String destination;
    private float distance;
    private String line;

    public Edge(String destination, String line, float distance){
        this.distance = distance;
        this.line = line;
        this.destination=destination;

}

    public float getDistance(){
        return distance;
    }
    public String getDestination(){
        return destination;
    }
    public String getLine(){
        return line;
    }

}
