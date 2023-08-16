from typing import List, Tuple, Set

class Solution:
    def findCircleNum(self, isConnected: List[List[int]]) -> int:
        already_connected : Set[int] = set()
        number_provinces = 0
        for city in range(len(isConnected)):
            (connected, neighboors) = self.any_neighboor_connected(city, isConnected, already_connected)
            if not connected:
                number_provinces += 1
            
            already_connected = already_connected.union(neighboors)

        return number_provinces 

    
    def any_neighboor_connected(self, city : int, isConnected: List[List[int]], already_counted : Set[int]) -> Tuple[bool, Set[int]]:
        neighboors : Set[int] = set([city])
        marked_neighboors : Set[int] = set()
        result : bool = False

        while len(neighboors) > 0:
            current_neighboor = neighboors.pop()
            if current_neighboor in marked_neighboors:
                continue
            else:
                marked_neighboors.add(current_neighboor)

            result = result or current_neighboor in already_counted
            neighboors = neighboors.union(self.get_neighboors(current_neighboor, isConnected))

        return tuple([result, marked_neighboors])

    def get_neighboors(self, city : int, isConnected: List[List[int]]) -> Set[int]:
        result : Set[int] = set()
        for i in range(len(isConnected)):
            if i == city:
                continue
            if isConnected[city][i] == 1:
                result.add(i)
        
        return result