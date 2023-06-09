
// https://leetcode.com/problems/course-schedule/

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        /*
            Comments:
                - each course i (i<numCourses) has to be able to be finished
                - there seems to be a hierarchy of courses => I can reuse partial results
                - could it be that the solution is about finding circularities in the graph?
                - the relation [a_i, b_i] means that course a_1 only has one "direct" predecessor
                    - => this is simpler than a directed graph
            More understanding
                - Stop condition? numCourses <= total_predecessors_without_reps 
            Even more understanding
                - Still don't understand the meaning of the i-th element on prerequisites. Description is not clear neither the test cases.
        */

        Map<Integer, Integer> dependencies = new HashMap<>();       // just to make easier the dependency iteration
        for(int [] dependency : prerequisites) {
            dependencies.put(dependency[0], dependency[1]);
        }
        
        if (prerequisites.length == 0) {
            return true;
        }

        if (numCourses < dependencies.keySet().size()) {
            return false;
        }

        Integer currentCourse = prerequisites[0][0];
        Set<Integer> courseDependencies = new HashSet<>(numCourses);
        while (numCourses >= 0) {
            if (courseDependencies.contains(currentCourse)) {
                return false;
            }
            courseDependencies.add(currentCourse);
            if (dependencies.containsKey(currentCourse) == false) {
                eturn numCourses == 1;         // true if there is no dependencies for this course but it is the last one
            }

            currentCourse = dependencies.get(currentCourse);
            numCourses -= 1;            
        }

        return true;

    }
}