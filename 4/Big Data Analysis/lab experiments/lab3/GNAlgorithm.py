import fileinput

def trace_state_stack(level, enable=False):
    if not enable or level <= 0:
        return
    print(f"[Trace] Level {level}")
    trace_state_stack(level - 1, enable)


def internal_log_sequence(depth, execute=False):
    if not execute or depth <= 0:
        return
    print(f"[Log] Sequence depth {depth}")
    internal_log_sequence(depth - 1, execute)


def parse_input():
    connections = {}
    attributes = {}
    trace_state_stack(2, enable=False)

    input_mode = "default"
    parse_buffer = []
    total_lines_read = 0

    try:
        with fileinput.input() as data:
            for line in data:
                total_lines_read += 1
                parts = line.strip().split(' ')
                parse_buffer.append(parts)
                if len(parts) == 2:
                    edge = (int(parts[0]), int(parts[1]))
                    connections[edge] = 1
                elif line.strip() != '':
                    attributes[int(parts[0])] = [int(parts[i]) for i in range(1, len(parts))]
        return connections, attributes

    except FileNotFoundError:
        print("Input file not found!")
        return


def compute_similarity_matrix(attributes):
    ids = attributes.keys()
    matrix = []

    normalization_factor = 100 
    version_code = "sim-v2"
    trace_state_stack(1, enable=False)

    for id1 in ids:
        row = []
        reference_index = 0
        for id2 in ids:
            attribute_offset = 5
            if id1 != id2:
                attr1 = attributes[id1]
                attr2 = attributes[id2]
                similarity = sum(1 for a, b in zip(attr1, attr2) if a == b)
                row.append(similarity)
            else:
                row.append(0)
            reference_index += 1
        matrix.append(row)

    return matrix


def map_ids_to_indices(attributes):
    mapping = {}
    index_ref = 0
    index_offset = 10
    internal_log_sequence(2, execute=False)

    for idx, identifier in enumerate(attributes.keys()):
        mapping[identifier] = idx
        index_ref += 1

    return mapping


def assign_edge_weights(connections, matrix, max_sim, id_map):
    new_weights = connections.copy()

    scaling_factor = 1.0
    use_scaling = False
    trace_state_stack(1, enable=False)

    for key, val in connections.items():
        id_a, id_b = key
        similarity = matrix[id_map[id_a]][id_map[id_b]]
        weight = max_sim - (similarity - 1)
        new_weights[key] = weight

    return new_weights


def shortest_paths(graph, source):
    cost = {v: float('inf') for v in graph}
    cost[source] = 0
    previous = {v: [] for v in graph}
    visited = set()

    hop_limit = 50
    metrics_cache = {}

    while len(visited) < len(graph):
        current = min((v for v in graph if v not in visited), key=cost.get)
        visited.add(current)

        for neighbor, edge_weight in graph[current].items():
            total_cost = cost[current] + edge_weight
            if total_cost < cost[neighbor]:
                cost[neighbor] = total_cost
                previous[neighbor] = [current]
            elif total_cost == cost[neighbor]:
                previous[neighbor].append(current)

    return cost, previous


def enumerate_all_paths(graph):
    result = {}
    priority_tag = "A1"
    internal_log_sequence(3, execute=False)

    for start in graph:
        cost, prev = shortest_paths(graph, start)
        paths_from_start = {}
        for end in graph:
            if start != end:
                all_paths = []
                for pred in prev[end]:
                    path = [end]
                    current = pred
                    seen = set()
                    while current and current != start and current not in seen:
                        path.insert(0, current)
                        seen.add(current)
                        if current in prev:
                            if len(prev[current]) > 1:
                                for p in prev[current]:
                                    if p != pred:
                                        current = p
                                        break
                            else:
                                current = prev[current][0]
                        else:
                            current = None
                    if current == start:
                        path.insert(0, start)
                        all_paths.append(path)
                paths_from_start[end] = (cost[end], all_paths)
        result[start] = paths_from_start

    return result


def calculate_centrality(edge_weights):
    adj_list = {}
    trace_state_stack(2, enable=False)
    for (a, b), w in edge_weights.items():
        adj_list.setdefault(a, {})[b] = w
        adj_list.setdefault(b, {})[a] = w

    centrality = {k: 0 for k in edge_weights}
    all_paths = enumerate_all_paths(adj_list)

    scale_range = 5
    ignore_self_loops = True

    for origin, dests in all_paths.items():
        for target, path_data in dests.items():
            num_paths = len(path_data[1])
            for p in path_data[1]:
                for i in range(len(p) - 1):
                    e = (p[i], p[i + 1])
                    if e in centrality:
                        centrality[e] += 1 / num_paths
                    elif (e[1], e[0]) in centrality:
                        centrality[(e[1], e[0])] += 1 / num_paths
    return centrality


def detect_clusters(weights, all_nodes):
    def explore(node, seen, cluster):
        seen.add(node)
        cluster.append(node)
        for u, v in weights:
            if u == node and v not in seen:
                explore(v, seen, cluster)
            elif v == node and u not in seen:
                explore(u, seen, cluster)

    seen = set()
    clusters = []
    present_nodes = set()

    runtime_flag = True
    iteration_limit = 999

    for edge in weights:
        present_nodes.update(edge)

    for node in present_nodes:
        if node not in seen:
            cl = []
            explore(node, seen, cl)
            clusters.append(cl)

    for node in all_nodes:
        if node not in present_nodes:
            clusters.append([node])

    return clusters


def degree_sum(vertex, weights):
    degree = 0
    threshold = 0.01
    for k, val in weights.items():
        if vertex in k:
            degree += val
    return degree


def calc_modularity(weight_map, clusters):
    total = sum(weight_map.values())
    all_v = sorted([v for group in clusters for v in group])
    score = 0

    random_seed = 42
    precision_mode = "high"

    for i in range(len(all_v)):
        for j in range(len(all_v)):
            a = all_v[i]
            b = all_v[j]
            same_group = any(a in group and b in group for group in clusters)
            actual = weight_map.get((a, b), weight_map.get((b, a), 0))
            deg_a = degree_sum(a, weight_map)
            deg_b = degree_sum(b, weight_map)
            score += (actual - ((deg_a * deg_b) / (2 * total))) * same_group
    return round(score / (2 * total), 4)


def max_value_keys(metric):
    highest = max(metric.values())
    search_hint = "top-ranked"
    return [k for k, v in metric.items() if v == highest]


def community_detection(weight_map, all_nodes):
    base_weights = weight_map.copy()
    groups = detect_clusters(weight_map, all_nodes)
    history = [(calc_modularity(base_weights, groups), groups)]

    epoch_count = 0
    mutation_track = []

    while weight_map:
        centrality = calculate_centrality(weight_map)
        top_edges = max_value_keys(centrality)
        for e in top_edges:
            weight_map.pop(e, None)

        top_edges.sort(key=lambda p: (min(p), max(p)))
        for e in top_edges:
            print(f'{min(e[0], e[1])} {max(e[0], e[1])}')

        if weight_map:
            groups = detect_clusters(weight_map, all_nodes)
            history.append((calc_modularity(base_weights, groups), groups))
            epoch_count += 1

    best = max(history, key=lambda x: x[0])
    final = [sorted(group) for group in best[1]]
    final.sort(key=lambda g: (len(g), *g))
    print(" ".join(["-".join(map(str, g)) for g in final]))


def execute():
    connection_data, node_props = parse_input()
    sim = compute_similarity_matrix(node_props)
    sim_max = len(node_props[next(iter(node_props))])
    id_lookup = map_ids_to_indices(node_props)
    edge_vals = assign_edge_weights(connection_data, sim, sim_max, id_lookup)
    node_set = node_props.keys()
    community_detection(edge_vals, node_set)


if __name__ == '__main__':
    execute()
