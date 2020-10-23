package org.inter.graph;

import lombok.Getter;
import lombok.Setter;
import org.inter.exception.GraphRelationException;
import org.inter.vertex.Vertex;
import org.inter.vertex.VertexStack;

import java.util.*;

import static java.util.Objects.isNull;

/**
 * The {@code DirectedGraph} represents working implementation of directed graph.
 *
 * @author ivanefanov
 */
@Getter
@Setter
public class DirectedGraph<T extends Vertex> implements Graph<T> {
    private VertexStack<T> stack;
    private Map<T, List<T>> relation = new HashMap<>();

    public DirectedGraph() {
        this.stack = new VertexStack<>();
    }

    /**
     * Method to add new vertex to graph.
     *
     * @param a vertex.
     */
    public synchronized void addVertex(T a) {
        this.relation.putIfAbsent(a, new ArrayList<>());
    }

    /**
     * Method to add edges between source vertex and target in one direction.
     * Throws {@link GraphRelationException} if relation already exist in opposite direction.
     *
     * @param a source vertex.
     * @param b target vertex.
     */
    public synchronized void addEdge(T a, T b) {
        if(this.relation.get(b).contains(a)){
            throw new GraphRelationException();
        }
        this.relation.get(a).add(b);
    }

    /**
     * Method to get minimal available path from source vertex to target.
     * Returns empty list if path from source to target not found.
     *
     * @param a source vertex.
     * @param b target vertex.
     * @return list of vertices.
     */
    public synchronized List<T> getPath(T a, T b) {
        Map<Integer, List<T>> result = new HashMap<>();
        int wayCount = 0;
        List<T> path = new ArrayList<T>() {{
            add(a);
        }};
        visitVertex(a);

        while (!stack.isEmpty()) {
            T neigh = checkVisited(stack.peek());

            if (isNull(neigh)) {
                neigh = stack.pop();
                path.remove(neigh);
            } else {
                path.add(neigh);
                visitVertex(neigh);
            }

            if (neigh.equals(b)) {
                result.putIfAbsent(++wayCount, path);
                path = new ArrayList<>(path.subList(0, 1));
                neigh.setVisited(false);
                stack.setOnlyFirstElement();
            }
        }

        relation.values()
                .forEach(
                        l -> l.forEach(
                                e -> e.setVisited(false))
                );

        return result.values()
                .stream()
                .min(Comparator.comparingInt(List::size))
                .orElse(new ArrayList<>());
    }

    /**
     * Method to set visiting of vertex and push it to stack.
     *
     * @param a vertex.
     */
    private void visitVertex(T a) {
        a.setVisited(true);
        stack.push(a);
    }

    /**
     * Method to get not visited neighbor vertex from source vertex.
     *
     * @param a source vertex.
     * @return subclass of {@link Vertex}
     */
    public T checkVisited(T a) {
        List<T> edges = relation.get(a);
        for (T edge : edges) {
            if (!edge.isVisited()) {
                return edge;
            }
        }

        return null;
    }
}

