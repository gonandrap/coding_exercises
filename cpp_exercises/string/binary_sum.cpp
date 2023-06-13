
#include <iostream>
#include <string>

using namespace std;

class BinarySum {
public:
    string addBinary(string a, string b) {
        
        auto longest_str = (a.size() >= b.size()) ? a : b;
        auto shortest_str = (a.size() < b.size()) ? a : b;
        string result;
        auto result_it = result.rbegin();

        auto longest_it = longest_str.rbegin();
        auto shortest_it = shortest_str.rbegin();
        int digit_sum = 0;
        while(longest_it != longest_str.rend()) {
            digit_sum += ((int) (*longest_it == '1')) + ((int) (shortest_it != shortest_str.rend() && *shortest_it == '1'));
            switch (digit_sum) {
                case 0:
                    result = result.insert(0, "0");
                    digit_sum = 0;
                    break;
                case 1:
                    result = result.insert(0, "1");
                    digit_sum = 0;
                    break;
                case 2:
                    result = result.insert(0, "0");
                    digit_sum = 1;
                    break;
                case 3:
                    result = result.insert(0, "1");
                    digit_sum = 1;
                    break;
                default:
                    std::cout << "unexpected digit_sum [" << digit_sum << "]" << std::endl;
            }
            
            ++longest_it;
            if (shortest_it != shortest_str.rend()) {
                shortest_it++;
            }
        }

        if (digit_sum > 0) {
            result.insert(0, "1");
        }
        return result;
    }
};


int main(int argc, int* argv[]) {
    string a = "10100000100100110110010000010101111011011001101110111111111101000000101111001110001111100001101";
    string b = "110101001011101110001111100110001010100001101011101010000011011011001011101111001100000011011110011";
    
    BinarySum elem = BinarySum();
    std::cout << "result " << elem.addBinary(a,b) << std::endl;
}