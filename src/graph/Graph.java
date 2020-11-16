package graph;


import minBinHeap.MinBinaryHeap;
import minBinHeap.PrioritizedImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Graph {
    private Map<String, Vertex> _vertices;
    private List<List<String>> _data;

    public Graph(String fileName) { //all file information into hashmap
        _vertices = new HashMap<>();
        createGraph(fileName);
    }
    public Graph() {
        _vertices = new HashMap<>();
    }


    public double calculateWeight(int distance, int traffic, int scenery, int attractions) {
        double weight = 0;
        weight = (traffic + scenery + attractions)/distance;
        return weight;
    }

    public Map<String, Double> dijkstra(Vertex start) {
        //need list, priority queue, map
        //where string is the vertex name and double is the distance from start

        Map <String, Double> returnMap = new HashMap<>(); //table created to return
        List <Vertex> vertices = new ArrayList<Vertex>(); //list created (where dequeued/visited vertices go)

        //start vertex gets its fields written in
        start.setDistanceFromSource(0); //distance is 0
        start.setPreviousVertex(null); //it has no incoming nodes so it has no previous vertex
        MinBinaryHeap <Vertex,Double> pq = new MinBinaryHeap<Vertex,Double>(); //priority queue created where vertex is value and priority is a double

        //start is enqueued onto priority queue first
        pq.enqueue(start, start.getDistanceFromSource());

        while(pq.size() != 0) { //while the priority queue is not empty

            Vertex vertex = pq.dequeue(); //dequeue the min from pq and set as the vertex you are using
            vertices.add(vertex); //add the vertex to vertices list (like in the slides, goes from pq to list)
            List <Edge> edges = vertex.getEdges(); //list created (where the edges of the vertices go)

            for(Edge edge:edges){ //for all values in edges list (for all the edges of the vertex)
                if(vertices.contains(edge.getDestination())) { //if the destination of the vertex's edge is known
                    //2 cases

                    if(Arrays.asList(pq).contains(new PrioritizedImpl<Vertex,Double>(edge.getDestination(),edge.getWeight()))) {
                        edge.getDestination().setDistanceFromSource(vertex.getDistanceFromSource() + edge.getWeight());
                        edge.getDestination().setPreviousVertex(vertex);
                        pq.enqueue(edge.getDestination(), edge.getDestination().getDistanceFromSource());
                    }

                   else if(edge.getDestination().getDistanceFromSource() > vertex.getDistanceFromSource()+edge.getWeight()){
                        edge.getDestination().setDistanceFromSource(vertex.getDistanceFromSource()+edge.getWeight());
                        edge.getDestination().setPreviousVertex(vertex);
                   }
                }
            }
            returnMap.put(vertex.getLabel(),vertex.getDistanceFromSource()); //put the vertex in the map
        }
        return returnMap;
    }

        //else { //else vertex is not known

        // }

    // Do not edit anything below

    /*
    reads through each entry in csv and calls readLine to create edges and vertices.
     */
    public void createGraph(String fileName) {
        readCSV(fileName);
        for(List<String> list: _data) {
            readLine(list.get(0), list.get(1), Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3))
                    , Integer.parseInt(list.get(4)), Integer.parseInt(list.get(5)));
        }
    }
    /*
    reads through each line of csv and puts data into an ArrayList
     */
    public void readCSV(String fileName) {
        _data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                _data.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    converts one entry of data into vertices and edges
     */
    public void readLine(String src, String dest, int distance, int traffic, int scenery, int attractions) {
        // find source and destination nodes, if they don't exist, create them
        Vertex source = _vertices.get(src);
        if(source == null)
            source = new VertexImpl(src);
        Vertex destination = _vertices.get(dest);
        if(destination == null)
            destination = new VertexImpl(dest);

        // calculate weight of edge
        double weight = calculateWeight(distance, traffic, scenery, attractions);

        //create edge
        Edge e = new EdgeImpl(source, destination, weight);
        source.addEdge(e);

        //add reverse direction edge
        e = new EdgeImpl(destination, source, weight);
        destination.addEdge(e);

        //add to graph
        _vertices.put(src, source);
        _vertices.put(dest, destination);

    }

    public Map<String, Vertex> getVertices() {
        return _vertices;
    }





}
