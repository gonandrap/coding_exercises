package java_exercises.graphs;
// https://leetcode.com/problems/keys-and-rooms/submissions/

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class KeysAndRooms {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        /*
            Comments
                - each state can have one of the three states:
                    - first time visited (no mark)
                    - partially explored (temporary mark)
                    - fully explored (permanent mark)

                - in addition I need to keep note of the nodes visited (Set<Integer>). When length of that set equals the number of nodes, we are done!
                    - can I use the fully explored data structure?
                - revelation insight
                    - do I really need the permanent mark? I need to iterate over all nodes, not necessarly all edges.
                        - I will still keep it because I need to make sure that there is no further edges from a nodes that could potentially reach the 
                            remaining subgraph
                - UPDATE : once the neighbors structure is created, there is no need to iterate over the structure "rooms", I just need to iterate 
                    over the graph (structure "neighbors") starting from node=0. At the end, if the number of nodes I was able to reach is equal to the
                    total number of nodes, then result is true. 
                    IN OTHER WORDS, what I have to do is to make sure every node is reachable (dont need to check every edge)
        */

        Set<Integer> roomsReachables = new HashSet<Integer>();

        Map<Integer, Set<Integer>> neighbors = createNeighborsStructure(rooms);

        Stack<Integer> toVisit = new Stack<Integer>();
        toVisit.push(0);
        while (toVisit.empty() == false) {
            Integer currentNode = toVisit.pop();
            if (roomsReachables.contains(currentNode)) {
                continue;
            } else {
                roomsReachables.add(currentNode);
            }
            pushAll(toVisit, neighbors.get(currentNode));
        }

        return rooms.size() == roomsReachables.size();
    }

    void pushAll(Stack<Integer> toVisit, Set<Integer> neighbors) {
        for(Integer neighbor : neighbors) {
            toVisit.push(neighbor);
        }
    }

    Map<Integer, Set<Integer>> createNeighborsStructure(List<List<Integer>> rooms) {
        Map<Integer, Set<Integer>> result = new HashMap<Integer, Set<Integer>>();
        for (int i=0; i<rooms.size(); i++ ) {
            Set<Integer> currentNeighbors = result.getOrDefault(i, new HashSet<Integer>());
            List<Integer> neighborList = rooms.get(i);
            currentNeighbors.addAll(neighborList);
            result.put(i, currentNeighbors);         // do I need to put again the entire list? when I the neighborList doesnt automatically update the pointer?
        }
        return result;
    }
}
