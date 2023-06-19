
#include <vector>
#include <iostream>
#include <numeric>

// https://leetcode.com/problems/maximum-average-subarray-i/description/

using namespace std;

class MaxAvgSubarray {
public:
    // DEPRECATED due to time limit exceed (in case k is big)
    double findMaxAverage_dep(vector<int>& nums, int k) {
        double maxAvg = std::accumulate(nums.begin(), nums.begin()+k, 0) / double(k);       // covers the case when k = n

        for (auto it = nums.begin()++; it <= nums.end() - k; ++it) {
            double accum = std::accumulate(it, it+k, 0.0) / double(k);
            maxAvg = (maxAvg >= accum) ? maxAvg : accum;
        }
        return maxAvg;
    }

    double findMaxAverage(vector<int>& nums, int k)
    {
        double slidingAccum = std::accumulate(nums.begin(), nums.begin()+k, 0);
        double maxAvg = slidingAccum;       // covers the case when k = n
    
        for (auto it = ++nums.begin(); it <= nums.end() - k; ++it) {
            slidingAccum = slidingAccum - *(it-1) + *(it+(k-1));
            maxAvg = (maxAvg >= slidingAccum) ? maxAvg : slidingAccum;
        }
        return maxAvg / double(k);
    }
};

int main(int argc, char* argv) {
    std::vector<int> nums = {1,12,-5,-6,50,3};
    int k = 4;

    MaxAvgSubarray elem = MaxAvgSubarray();
    elem.findMaxAverage(nums, k);

    return 0;
}
