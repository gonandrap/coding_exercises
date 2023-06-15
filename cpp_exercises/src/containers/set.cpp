
#include <iostream>
#include <set>

void using_sets() {
    std::set<int> nodes;

    nodes.insert(1);
    nodes.insert(10);
    nodes.insert(21);

    for(std::set<int>::iterator it = nodes.begin(); it != nodes.end(); ++it) {
        std::cout << *it << std::endl;
    }

    std::set<int>::const_iterator found_it = nodes.find(11);
    if (found_it != nodes.end()) {
        std::cout << "found in : " << *found_it << std::endl;
    } else {
        std::cout << "not found!" << std::endl;
    }

}




int main(int argc, char* argv[]) {
    using_sets();
}