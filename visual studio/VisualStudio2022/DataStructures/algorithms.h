#pragma once

#include <string>
#include <vector>

namespace algorithms
{
	std::string path_normalizer(const std::string& path);

	void sort_big_file(const std::string& path);

	int country_count(std::vector< std::vector<int> >& A);

	int highest_power_2(int N);

	std::string reduce_string(std::string& S);			// https://app.codility.com/demo/results/trainingATDFZQ-JWP/
}