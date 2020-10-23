package org.inter.graph;

import lombok.Getter;
import lombok.Setter;
import org.inter.node.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * The {@code WeightedGraph} class represents working implementation of weighted graph.
 *
 * @author ivanefanov
 */
@Getter
@Setter
public class WeightedGraph<T extends Node> {

    /**
     * Set of {@link Node} that current weighted graph contains.
     */
    private Set<T> nodes = new HashSet<>();

    public synchronized void addNode(T n) {
        nodes.add(n);
    }

    /**
     * Returns {@link List} of {@link Node} that represents the shortest path from source node to target.
     *
     * @param source {@link Node}
     * @param target {@link Node}
     * @return list of nodes
     */
    public synchronized List<T> getShortestPathFromSource(T source, T target) {
        source.calculatePathsFromThis();
        List<T> shortestPath = (List<T>) target.getShortestPath();
        shortestPath.add(target);
        return shortestPath;
    }

    /**
     * Method to concat new nodes name by function.
     *
     * @param function function.
     */
    public void concatNewNodeName(Function<String, String> function){
        nodes.forEach(n-> n.setName(function.apply(n.getName())));
    }
}
