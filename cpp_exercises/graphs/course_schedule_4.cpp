
#include <vector>
#include <iostream>
#include <iterator>
#include <map>
#include <set>
#include <queue>

using namespace std;

class CourseScheduleFour
{
public:
 
    

    /*
        This solution is giving me time limit exceeded, which makes sense because I'm trying to build an enourmous graph, where for each node,
        I have a set of all posible dependencies (direct + indirect). Even though that is correct, seems to be an overkill.
    */
    vector<bool> checkIfPrerequisite(int numCourses, vector<vector<int>>& prerequisites, vector<vector<int>>& queries)
    {
        /*
            This is a 3-step algorithm:
                1. create the graph structure (node adjancency : <node, direct_neighbors>)
                2. create the graph indirect structure (node adjancency recharged : <node, direct_neighbors+indirect_neighbors>)
                3. answer query as part of expected output for the algorithm
        */

        map<int, set<int>* > indDeps;
        map<int,set<int>> * neighbors = buildNeighbors(prerequisites, new map<int,set<int>>());         // step 1

        set<int> permMark;
        for (auto currentEdge : prerequisites)          // the goal of this iteration is the step 2 of the algorithm}
        {
            //cout << "Iterating current edge [" << currentEdge[0] << "," << currentEdge[1] << "]" << endl;

            set<int> tempMark;
            int currentNode = currentEdge[0];

            set<int>* currentIterationDeps;
            auto indDepFind = indDeps.find(currentNode);
            if (indDepFind != indDeps.end())
            {
                // currentNode has indirect deps already, use it
                currentIterationDeps = indDepFind->second;
            } else
            {
                // currentNode doesnt have indirect deps, create the container
                currentIterationDeps = new set<int>();
                indDeps.insert({currentNode, currentIterationDeps});
            }

            if (permMark.find(currentNode) == permMark.end())
            {
                iterateSubGraph(currentNode, neighbors, permMark, indDeps);
                permMark.insert(currentNode);       // subgraph with root=currentNode was processed, can be marked permanently
            }
        }

        vector<bool> result = vector<bool>(queries.size());
        for (int i = 0; i < queries.size(); i++)
        {
            auto foundDependency = indDeps.find(queries[i][0]);
            result[i] = foundDependency != indDeps.end() &&         // first : the edge has to be found in the structure with all indirect dependencies of the graph 
                        (foundDependency->second->find(queries[i][1]) != foundDependency->second->end());       // second : the "to" part of the edge has to be found as an indirect dependency of the "from" part of the edge
        }

        return result;
    }

private:
    void iterateSubGraph(int currentNode, const map<int,set<int>> * globalNeighbors, set<int> & permMark, map<int, set<int>* > & globalIndDeps)
    {
        //cout << "Iteration subgraph with root=" << currentNode << endl;

        set<int> * tempMark = new set<int>();               // tempMark = dependencies (direct + indirect) of subgraph with root=currentNode
        auto currentNeighbors_it = globalNeighbors->find(currentNode);
        if (currentNeighbors_it != globalNeighbors->end())
        {
            auto currentNeighbors = currentNeighbors_it->second;
            for (auto currentNeighbor : currentNeighbors)
            {
                tempMark->insert(currentNeighbor);      // every direct neighbor is a direct dependency for "currentNode"

                if (permMark.find(currentNeighbor) != permMark.end())
                {
                    /*
                        since all ind dependencies were already identified for this current neighbor, we 
                        dont  need to iterate again, but we do have to add all of its deps
                        to the current iteration
                    */ 
                } else
                {
                    iterateSubGraph(currentNeighbor, globalNeighbors, permMark, globalIndDeps);
                }
                keepIndirectDeps(*tempMark, currentNeighbor, globalIndDeps);
            }
            /*
                When I'm done with all the neighbors (and their neighbors, recursively), "tempMark" has all nodes visited, which represents the indirectDependencies,
                which tells me that "currentNeighbor" can be flag with the "permMark"
            */
            //cout << "Done iteration of neighbors for [" << currentNode << "]" << endl;
            permMark.insert(currentNode);
            addIndirectDeps(*tempMark, currentNode, globalIndDeps);
            return;
        } else 
        {
            /*
                currentNode doesn't have any neighbors, so no need of keep iterating
            */
            //cout << "No neighbors for [" << currentNode << "]" << endl;

            //NOTE : let's try not adding the leafs to as "permMark"
            //permMark.insert(currentNode);
            addIndirectDeps(*tempMark, currentNode, globalIndDeps);
            return;
        }

        // Once we are done iterating, we can add the dependencies identified in "tempMark" to globalIndDeps[currentNode]
        //cout << "Done iteration of neighbors for [" << currentNode << "]" << endl;
        permMark.insert(currentNode);
        addIndirectDeps(*tempMark, currentNode, globalIndDeps);
    }


    /*
        Will copy from "destinyDependencies[destinyKey] to "pendingDependencies"
    */
    void keepIndirectDeps(set<int> & pendingDependencies, int destinyKey, map<int, set<int>* > & destinyDependencies)
    {
        auto destinyDependencies_it = destinyDependencies.find(destinyKey);
        if (destinyDependencies_it != destinyDependencies.end())      // not necessary, but it's work to have the extra check
        {
            copy(destinyDependencies_it->second->begin(), destinyDependencies_it->second->end(), inserter(pendingDependencies, pendingDependencies.begin()));
        } else 
        {
            cout << "[keepIndirectDeps] couldnt find dependencies for destinyKey=" << destinyKey << endl;
        }
    }

    /*
        Will copy from "pendingDependencies" to "destinyDependencies[destinyKey]"
    */
    void addIndirectDeps(set<int> & pendingDependencies, int destinyKey, map<int, set<int>* > & destinyDependencies)
    {
        auto destinyDependencies_it = destinyDependencies.find(destinyKey);
        if (destinyDependencies_it != destinyDependencies.end())      // not necessary, but it's work to have the extra check
        {
            copy(pendingDependencies.begin(), pendingDependencies.end(), inserter(*destinyDependencies_it->second, destinyDependencies_it->second->begin()));
        } else
        {
            // we first need to create the entry in "destinyDependencies" for "destinyKey"
            set<int> * destinyDependenciesContainer = new set<int>();
            copy(pendingDependencies.begin(), pendingDependencies.end(), inserter(*destinyDependenciesContainer, destinyDependenciesContainer->begin()));
            destinyDependencies.insert({destinyKey, destinyDependenciesContainer});
        }
    }

    map<int,set<int>> * buildNeighbors(const vector<vector<int>>& prerequisites, map<int,set<int>> * neighbors)
    {
        for(auto edge : prerequisites)
        {
            auto from = edge[0];
            auto to = edge[1];
            auto from_neighbors_it = neighbors->find(from);
            if (from_neighbors_it != neighbors->end())
            {
                from_neighbors_it->second.insert(to);
            } else
            {
                // the node "from" from the edge wasn't added to the "neighbors" structure yet, let's add it with the initial dep "from"
                set<int> from_neighbors;
                from_neighbors.insert(to);
                neighbors->insert({from, from_neighbors});
                neighbors->insert({to, set<int>()});            // add the "to" part of the edge just to make sure it will have its own entry (is part of the verification at the end)
            }
        }
        return neighbors;
    }
};

int main(int argc, char* argv[])
{
    CourseScheduleFour sol = CourseScheduleFour();
    int numCourses = 5;
    vector<vector<int>> preReqs = {{0,1},{1,2},{2,3},{3,4}};
    vector<vector<int>> queries = {{0,4},{4,0},{1,3},{3,0}};
    auto result = sol.checkIfPrerequisite(numCourses, preReqs, queries);
    cout << "result=";
    copy(result.cbegin(), result.cend(), ostream_iterator<int>(cout, " "));
    cout << endl;
}
