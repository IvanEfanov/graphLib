package org.inter.graph;

import java.util.List;

/**
 * Graph.
 *
 * @author ivanefanov
 */
public interface Graph<T> {

    void addVertex(T vertex);

    void addEdge(T a, T b);

    List<T> getPath(T a, T b);
}
