"""
We are given an array A consisting of N integers. In one move, we can choose any element in this array and replace it with any value.

We are interested in the longest consistent segment of identical elements. For example, given an array A = [3, -3, 3, 3, 3, 1, -3], the segment that we are looking for is [3, 3, 3].

What is the maximum length of such a segment that we can achieve by performing at most three moves on the array?

Write a function that, given an array A of N integers, returns the maximum length of a consistent segment of identical elements in the array A that can be obtained after replacing up to three elements in the array.

Examples:
Given A = [-9, 8], your function should return 2. One of the correct ways to obtain such a solution is to use one move and change 8 into -9.
Given A = [1, 1, -10, 3, -10, 3, -10], your function should return 6. By performing three moves we can obtain the following array: [1, -10, -10, -10, -10, -10, -10]
Given A = [2, 3, 3, 3, 3, 1], your function should return 6. We can perform two moves to replace the first and last elements with 3s.
Given A = [3, 3, 2, 1, 2, 2, 9, 3, 3], your function should return 6. By performing three moves we can obtain the following array: [3, 3, 2, 2, 2, 2, 2, 2, 3].

Assume that:
N is an integer within the range [1..10];
each element of array A is an integer within the range [−10..10].

Int replace=0;
Int window_size=0;
Int freq_max=0;


[1100011222]
First pass : max i would be 3 -> [2,2,2], [0,0,0]
Also got [1,1], [11]

7 1’s	-> optimal
6 1’s

2 -> 1, [[7,9]]
1 -> 2, [[0,1],[2,6]]
"""

from typing import List, Dict, Tuple


def segment_distance(interval : Tuple[int,int]) -> int:
	return interval[1] - interval[0] + 1

def gap_to_next_segment(segments : List[Tuple[int, int]], i : int) -> int:
	return segments[i+1][0] - segments[i][1] - 1 if i < len(segments) - 1 else -1

def find_max_seg(a : List[int]) -> int:
	freq : Dict[int, List[Tuple[int, int]]] = dict()

	# first iteration : identify existing segments
	i = 0
	while i < len(a):
		j = i
		while j < len(a) and a[i] == a[j]:
			j += 1

		freq.setdefault(a[i], []).append((i, j-1))
		i = j

	#if i == len(a) - 1:			# edge case : last element is a segment of a unique element
#		freq.setdefault(a[i], []).append((i, i))

	longest_segment = 0
	for segments in freq.values():
		i = 0
		while i < len(segments):
			j = i
			available_transformations = 3
			accumulated_length = 0
			# in this iteration I determine how many segments I can chain together using 3 transformation at most
			gap_to_next = gap_to_next_segment(segments, j)
			while j<len(segments) and available_transformations > 0 and gap_to_next >= 0 and gap_to_next <= available_transformations:			# move as further as you can in between segments of the same type
				accumulated_length += segment_distance(segments[j]) + gap_to_next
				available_transformations -= gap_to_next
				j += 1
				gap_to_next = gap_to_next_segment(segments, j)
			if j < len(segments) or available_transformations == 0:
				accumulated_length += segment_distance(segments[j])		# include the last segments before achieving the max possible number of transformations

			accumulated_length += available_transformations

			if available_transformations != 3:
				# was able to concatenate two or more segments
				longest_segment = accumulated_length if accumulated_length > longest_segment else longest_segment
			else:
				current_segment_distance = segment_distance(segments[i]) + 3
				longest_segment =  current_segment_distance if current_segment_distance > longest_segment else longest_segment
			
			i += 1


	return min(longest_segment, len(a))

if __name__ == '__main__':
	l = [-9, 8]
	print(f'input={l} -> {find_max_seg(l)}')
	l = [1, 1, -10, 3, -10, 3, -10]
	print(f'input={l} -> {find_max_seg(l)}')
	l = [2, 3, 3, 3, 3, 1]
	print(f'input={l} -> {find_max_seg(l)}')
	l = [3, 3, 2, 1, 2, 2, 3, 3]
	print(f'input={l} -> {find_max_seg(l)}')
	print('--')
	l = [3, 3, 2, 3, 3, 2, 3, 3, 3, 3]
	print(f'input={l} -> {find_max_seg(l)}')
	l = [1,1,0,1,1,0,1,1,0,10]
	print(f'input={l} -> {find_max_seg(l)}')