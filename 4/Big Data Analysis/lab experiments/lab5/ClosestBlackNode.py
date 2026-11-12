import sys
from collections import deque

def solve():
    line1 = sys.stdin.readline().strip().split()
    if not line1: return
    n = int(line1[0])
    e = int(line1[1])

    graph = [[] for _ in range(n)]
    distances = [-1] * n
    closest_node = [-1] * n
    queue = deque()

    for i in range(n):
        node_type = int(sys.stdin.readline().strip())
        if node_type == 1:
            distances[i] = 0
            closest_node[i] = i
            queue.append(i)

    for _ in range(e):
        u, v = map(int, sys.stdin.readline().strip().split())
        graph[u].append(v)
        graph[v].append(u)

    while queue:
        current_node = queue.popleft()
        for neighbor in graph[current_node]:
            if distances[neighbor] == -1:
                distances[neighbor] = distances[current_node] + 1
                closest_node[neighbor] = closest_node[current_node]
                queue.append(neighbor)

    for i in range(n):
        print(f"{closest_node[i]} {distances[i]}")


if __name__ == "__main__":
    solve()
