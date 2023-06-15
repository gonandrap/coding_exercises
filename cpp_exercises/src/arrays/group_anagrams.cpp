
#include <vector>
#include <string>
#include <map>
#include<bits/stdc++.h>

// https://leetcode.com/problems/group-anagrams/description/

using namespace std;

class GroupAnagrams
{
public:
    vector<vector<string>> groupAnagrams(vector<string>& strs)
    {
        map<string, vector<string>*> groups;
        for(auto it = strs.cbegin(); it != strs.cend(); ++it)
        {
            auto current_elem = *it;
            auto current_elem_sorted = current_elem;
            sort(current_elem_sorted.begin(), current_elem_sorted.end());
            auto current_elem_find = groups.find(current_elem_sorted);
            if (current_elem_find != groups.end())
            {
                // found a matching anagram
                vector<string>* matchingGroup = current_elem_find->second;
                matchingGroup->push_back(current_elem);
            } else
            {
                vector<string> * newGroup = new vector<string>();
                newGroup->push_back(current_elem);
                groups.insert({current_elem_sorted, newGroup});
            }
        }
        return createResponse(groups);
    }

    vector<vector<string>> createResponse(map<string, vector<string>*> groups) const
    {
        vector<vector<string>> response;
        for(auto it = groups.cbegin(); it != groups.cend(); ++it)
        {
            response.push_back(*(it->second));
        }
        return response;
    }
};