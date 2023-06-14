
#include <vector>

// https://leetcode.com/problems/rotate-array/


using namespace std;

class RotateArray
{
public:
    void rotate(vector<int>& nums, int k)
    {
        int n = nums.size();
        vector<int> aux = vector<int>(n);

        long from_index = (0 + k) % n;
        long to_index = (from_index + k) % n;

        int i = 0;        
        while (i < n)
        {
            aux[(to_index+i) % n] = nums[(from_index+i) % n];
            i++;
        }

        std::copy(aux.begin(), aux.end(), nums.begin());
    }
};

int main(int argc, char* argv)
{
    return 0;
}