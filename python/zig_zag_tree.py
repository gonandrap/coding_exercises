

from dataclasses import dataclass

@dataclass
class Tree:
    x: int = 0
    l: "Tree" = None
    r: "Tree" = None

LEFT_TURN = 0
RIGHT_TURN = 1

def solution(T : Tree) -> int:
    if T is None:
        return 0
    else:
        l_zig_zags = solution_aux(T.l, LEFT_TURN) if not T.l is None else 0
        r_zig_zags = solution_aux(T.r, RIGHT_TURN) if not T.r is None else 0
        return max(l_zig_zags, r_zig_zags)


def solution_aux(T : Tree, last_turn : int) -> int:
    l_zig_zags_with_turn = l_zig_zags_without_turn = r_zig_zags_with_turn = r_zig_zags_without_turn = 0

    if not T.l is None:
        # count possibilities for turning left now
        l_zig_zags_with_turn = 1 + solution_aux(T.l, LEFT_TURN) if last_turn == RIGHT_TURN else 0
        l_zig_zags_without_turn = solution_aux(T.l, LEFT_TURN) if last_turn == LEFT_TURN else 0
    
    if not T.r is None:
        # count possibilities for turning right now
        r_zig_zags_with_turn = 1 + solution_aux(T.r, RIGHT_TURN) if last_turn == LEFT_TURN else 0
        r_zig_zags_without_turn = solution_aux(T.r, RIGHT_TURN) if last_turn == RIGHT_TURN else 0

    return max(l_zig_zags_with_turn, l_zig_zags_without_turn, r_zig_zags_with_turn, r_zig_zags_without_turn)