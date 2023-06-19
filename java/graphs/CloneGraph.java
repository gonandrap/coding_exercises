package java.graphs;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


// https://leetcode.com/problems/clone-graph/

public class CloneGraph {

    // Definition for a Node.
    public class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    public Node cloneGraph(Node node) {
        System.out.println(String.format("Starting with node=[%s]", node));
        // What is the value of "node" if the graph is empty? node == null?
        
       //System.out.println(String.format("Root call, node=[%s], val=[%d]", node, node.val));

       if (node == null) {       // this shouldnt happen, but just in case....
           System.out.println("Got [null] node");
           return null;
       } else {
           System.out.println("[calling [cloneGraph_rec]");

           Map<Integer, Node> nodesPendingCreation = new HashMap<Integer, Node>();
           Map<Integer, Node> nodesFullyCreated = new HashMap<Integer, Node>();
           return cloneGraph_rec(node, nodesPendingCreation, nodesFullyCreated);
       }
   }
   
   public Node cloneGraph_rec(Node node, Map<Integer, Node> nodesPendingCreation, Map<Integer, Node> nodesFullyCreated) {
       System.out.println(String.format("starting [cloneGraph_rec] with node=[%s] and val=[%d]", node, node.val));

       // temporary mark current node 
       Node newNode = new Node(node.val);      // we know for sure that "node" is not null
       nodesPendingCreation.put(newNode.val, newNode);

       // Iterate over neighbors
       for(Node neighbor : node.neighbors) {
           System.out.println(String.format("[during iteration] current node val=[%d] neighbor=[%s] and neighbor val=[%d]", node.val, neighbor, neighbor.val));

           if (nodesPendingCreation.containsKey(neighbor.val)) {
               // if the neighbor is temporary marked, it means that there is a cycle, no need to call recursively, just use the temporarly market node
               newNode.neighbors.add(nodesPendingCreation.get(neighbor.val));
           } else if (nodesFullyCreated.containsKey(neighbor.val)) {
               // when this would happen?
               System.out.println(String.format("node [%s] with val [%d] is fully created", neighbor, neighbor.val));
               newNode.neighbors.add(nodesFullyCreated.get(neighbor.val));
           } else {
               newNode.neighbors.add(cloneGraph_rec(neighbor, nodesPendingCreation, nodesFullyCreated));
           }
       }

       nodesPendingCreation.remove(newNode.val);
       nodesFullyCreated.put(newNode.val, newNode);
       System.out.println("ending [cloneGraph_rec]");
       return newNode;
   }

    public static void main(String[] args) {
        //CloneGraph cg = new CloneGraph();
        //int [][] graphList;
        // TODO : need to convert from int[][] to Node
    }
}
