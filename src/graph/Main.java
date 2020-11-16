package graph;


import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph("/Users/pravalika/Downloads/roadtrip - Sheet1.csv");
        Map<String, Double> dijkstra =  graph.dijkstra(graph.getVertices().get("Chapel Hill"));
        System.out.println(dijkstra);
    }
}
