package trees;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class Bipartition {
    public boolean possibleBipartition(int n, int[][] dislikes) {
        /*
            comments
                - i think it is the same that the graph is oriented or not, but i will make it oriented so i iterate half of the edges
                - first solution attempt:
                    - start from the first one and place it in one group (A)
                    - place all its neighbors in the other group (B)
                    - for each neighbor j:          // iteration should be done in BFS as usual (temp / permanent mark maybe)
                        - if j is in B, return FALSE
                        - if j is in A, fine
                        - if j isnt in A nor B, place it in A
                    - DOESNT WORK : because doesnt support not connected graphs
                - second solution attempt
                    - similar to first solution, with the caveat that the main iteration is based on "dislikes" instead of
                      iterating only based on neighbors
                    - maybe i dont even need to build the neighbors structure
        */

        Set<Integer> setA = new HashSet<Integer>();
        Set<Integer> setB = new HashSet<Integer>();
        Map<Integer, Set<Integer>> neighbors = createNeighborsStructure(dislikes);

        Queue<Integer> nodesBeingIterated = new LinkedList<Integer>();
        Integer initialNode = dislikes[0][0];
        nodesBeingIterated.add(initialNode);
        setA.add(initialNode);          // need to start with something

        int i = 0;
        while (i < dislikes.length) {
            int[] currentEdge = dislikes[i];
            
            System.out.println(String.format("setA=[%s], setB=[%s], currentEdge=[%s]", setA.toString(), setB.toString(), Arrays.toString(currentEdge)));

            Set<Integer> firstElemNeighbors = neighbors.getOrDefault(currentEdge[0], new HashSet<Integer>());
            Set<Integer> secondElemNeighbors = neighbors.getOrDefault(currentEdge[1], new HashSet<Integer>());
            if (sameSet(currentEdge[0], currentEdge[1], setA, setB)) {
                return false;
            } else if (bothInSet(currentEdge[0], currentEdge[1], setA, setB)) {
                // nothing to do, they are separated into sets already
            } else if (setA.contains(currentEdge[0])) {
                // we know that currentEdge[0] is not in setB (otherwise i would have entered second if clause)
                if (setB.stream().anyMatch(secondElemNeighbors::contains)) {
                    // cant place the element in B because a neighbor is already there
                    return false;
                } else {
                    setB.add(currentEdge[1]);
                }
            } else if (setB.contains(currentEdge[0])) {
                // we know that currentEdge[0] is not in setA (otherwise i would have entered second if clause)
                if (setA.stream().anyMatch(secondElemNeighbors::contains)) {
                    // cant place the element in A because a neighbor is already there
                    return false;
                } else {
                    setA.add(currentEdge[1]);
                }
            } else if (setA.contains(currentEdge[1])) {
                // currentEdge[0] is not yet assigned but currentEdge[1] is already in A, so currentEdge[0] has to go in B, which
                // is possible only if no neighbors are already there
                if (setB.stream().anyMatch(firstElemNeighbors::contains)) {
                    return false;
                } else {
                    setB.add(currentEdge[0]);
                }
            } else if (setB.contains(currentEdge[1])) {
                // currentEdge[0] is not yet assigned but currentEdge[1] is already in B, so currentEdge[0] has to go in A, which
                // is possible only if no neighbors are already there
                if (setA.stream().anyMatch(firstElemNeighbors::contains)) {
                    return false;
                } else {
                    setA.add(currentEdge[0]);
                }
            } else {
               setA.add(currentEdge[0]);            // need to start with something
               continue;                            // dont advance "i" to revisit the same edge but now with "currentEdge[0]" in setA
            }

            i += 1;
        }
        return true;
    }


    public boolean sameSet(int elemA, int elemB, Set<Integer> setA, Set<Integer> setB) {
        return setA.contains(elemA) && setA.contains(elemB) || setB.contains(elemA) && setB.contains(elemB);
    }

    public boolean bothInSet(int elemA, int elemB, Set<Integer> setA, Set<Integer> setB) {
        return (setA.contains(elemA) || setB.contains(elemA)) && 
                (setA.contains(elemB) || setB.contains(elemB));
    }

    Map<Integer, Set<Integer>> createNeighborsStructure(int[][] dislikes) {
        Map<Integer, Set<Integer>> result = new HashMap<Integer, Set<Integer>>();
        for (int i=0; i<dislikes.length; i++ ) {
            int[] currentEdge = dislikes[i];
            Set<Integer> currentNeighbors = result.getOrDefault(currentEdge[0], new HashSet<Integer>());
            currentNeighbors.add(currentEdge[1]);
            result.put(currentEdge[0], currentNeighbors);         // do I need to put again the entire list? when I the neighborList doesnt automatically update the pointer?
        }
        return result;
    }

    public static void main(String[] args) {
        //int[][] edges = {{1,2},{1,3},{2,3}};
        int n = 10;
        int [][] edges = {{1,2},{1,3},{1,4},{1,5},{1,8},{1,6},{1,9},{1,7},{1,10}};
        Bipartition sol = new Bipartition();
        System.out.println(sol.possibleBipartition(3, edges));
    }
}