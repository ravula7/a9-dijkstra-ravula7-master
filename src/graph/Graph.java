package graph;


import minBinHeap.MinBinaryHeap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

        //where string is the vertex name and double is the distance from start
        Map <String, Double> returnMap = new HashMap<>(); //table created
        List <Vertex> vertices = new ArrayList<Vertex>();
        start.setDistanceFromSource(0);
        start.setPreviousVertex(null);
        MinBinaryHeap <Vertex,Double> pq = new MinBinaryHeap<Vertex,Double>(); //queue created
        pq.enqueue(start, start.getDistanceFromSource());
        while(pq.size() != 0) {
            Vertex vertex = pq.dequeue();
            vertices.add(vertex);
            List <Edge> edges = vertex.getEdges();
            for(Edge edge:edges){
                if(vertices.contains(edge.getDestination())){ //if vertex is known
                    //if(){

                    //}
                   // else if(edge.getDestination().getDistanceFromSource() > vertex.getDistanceFromSource()+edge.getWeight()){
                        edge.getDestination().setDistanceFromSource(vertex.getDistanceFromSource()+edge.getWeight());
                        edge.getDestination().setPreviousVertex(vertex);
                   // }
                }
                //else { //else vertex is not known

               // }
            }
            returnMap.put(vertex.getLabel(),vertex.getDistanceFromSource());
        }
        return returnMap;
    }



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
