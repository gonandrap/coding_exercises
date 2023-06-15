
#include <iostream>
#include <string>

class Palindrome {

    public:
        bool isPalindrome(int x) {
            std::string input = std::to_string(x);
            int i = 0;
            int j = input.size() - 1;
            while (i<j) {
                if (input.at(i++) != input.at(j--))
                    return false;
            } 
            return true;
        }
};


int main(int argc, char* argv[]) {
    auto elem = Palindrome();
    int x = 121;
    std::cout << "is palindrome " << elem.isPalindrome(x) << std::endl;
}
