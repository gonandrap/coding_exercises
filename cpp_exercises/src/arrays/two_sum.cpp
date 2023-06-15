
#include <vector>
#include <iterator>
#include <iostream>

class TwoSum {

    public:
        std::vector<int> twoSum(std::vector<int>& nums, int target) {
            std::vector<int> result = std::vector<int>();

            int i = 0;
            while (i < nums.size() - 1) {
                int j = i+1;
                while(j < nums.size()) {
                    if (nums.at(i) + nums.at(j) == target) {
                        result.insert(result.end(), i);
                        result.insert(result.end(), j);
                        return result;
                    }
                    ++j;
                }
                ++i;
            }
            return result;
        }
};

int main(int argc, char* argv[]) {
    TwoSum ts = TwoSum();
    std::vector<int> input = {2,7,11,15};
    int target = 9;
    
    std::vector<int> result = ts.twoSum(input, target);
    std::copy(result.begin(), result.end(), std::ostream_iterator<int>(std::cout, " "));
    std::cout << std::endl;
    return 0;
}