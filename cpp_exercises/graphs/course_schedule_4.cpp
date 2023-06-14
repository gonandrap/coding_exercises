
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
        set<int> tempMark;
        for (auto currentEdge : prerequisites)          // the goal of this iteration is the step 2 of the algorithm}
        {
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
                tempMark.insert(currentNode);       // subgraph with root=currentNode starting to be processed, mark the root temporarly
                iterateSubGraph(currentNode, neighbors, tempMark, permMark, currentIterationDeps, indDeps);
                tempMark.erase(currentNode);
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
    void iterateSubGraph(int currentNode, const map<int,set<int>> * globalNeighbors, set<int> & tempMark, const set<int> & permMark, set<int>* currentIterationDeps, map<int, set<int>* > & globalIndDeps)
    {
        auto currentNeighbors_it = globalNeighbors->find(currentNode);
        if (currentNeighbors_it != globalNeighbors->end())
        {
            auto currentNeighbors = currentNeighbors_it->second;
            for (auto currentNeighbor : currentNeighbors)
            {
                if (permMark.find(currentNeighbor) != permMark.end())
                {
                    currentIterationDeps->insert(currentNeighbor);      // first add the current neighbor as dependency

                    /*
                        since all ind dependencies were already identified for this current neighbor, we 
                        dont  need to iterate again, but we do have to add all of its deps
                        to the current iteration
                    */ 
                    auto currentNeighborDeps_it = globalIndDeps.find(currentNeighbor);
                    if (currentNeighborDeps_it != globalIndDeps.end())      // not necessary, but it's work to have the extra check
                    {
                        copy(currentNeighborDeps_it->second->begin(), currentNeighborDeps_it->second->end(), inserter(*currentIterationDeps, currentIterationDeps->begin()));
                    } else
                    {
                        cout << "node [" << currentNeighbor << "] not found in [globalIndDeps]" << endl;
                    }
                    
                    
                } else
                {
                    // need to recursively iterate over the subgraph to detect all the indirect dependencies
                    if (tempMark.find(currentNeighbor) != tempMark.end())
                    {
                        /*
                            I dont need to do anything special. I still need to go through it because there are other siblings that still need to be identified
                            QUESTION : do I really need the "tempMark" then?
                        */
                    }
                    currentIterationDeps->insert(currentNeighbor);
                    tempMark.insert(currentNeighbor);
                    iterateSubGraph(currentNeighbor, globalNeighbors, tempMark, permMark, currentIterationDeps, globalIndDeps);
                }
            }
        } else 
        {
            /*
                currentNode doesn't have any neighbors, so no need of keep iterating
            */
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
    vector<vector<int>> preReqs = {{3,4},{2,3},{1,2},{0,1}};
    vector<vector<int>> queries = {{0,4},{4,0},{1,3},{3,0}};
    cout << "result=";
    auto result = sol.checkIfPrerequisite(numCourses, preReqs, queries);
    copy(result.cbegin(), result.cend(), ostream_iterator<int>(cout, " "));
    cout << endl;
}
