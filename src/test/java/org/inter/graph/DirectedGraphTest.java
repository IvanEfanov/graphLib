package org.inter.graph;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.inter.exception.GraphRelationException;
import org.inter.vertex.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * UndirectedGraphTest.
 *
 * @author ivanefanov
 */
@NoArgsConstructor
public class DirectedGraphTest {

    private DirectedGraph<City> graph;
    private List<City> vertices;

    @Before
    public void setup() {
        graph = new DirectedGraph<>();

        vertices = asList(
                new City("A"),
                new City("B"),
                new City("C"),
                new City("D"),
                new City("E"),
                new City("F"),
                new City("G"),
                new City("H")
        );
    }

    @Test
    public void addVertex() {
        vertices.forEach(e -> graph.addVertex(e));

        assertEquals(8, graph.getRelation().keySet().size());
    }

    @Test
    public void addEdge() {
        vertices.forEach(e -> graph.addVertex(e));

        graph.addEdge(vertices.get(0), vertices.get(1));
        graph.addEdge(vertices.get(1), vertices.get(2));
        graph.addEdge(vertices.get(3), vertices.get(2));

        assertTrue(graph.getRelation().get(vertices.get(0)).contains(vertices.get(1)));
        assertTrue(graph.getRelation().get(vertices.get(1)).contains(vertices.get(2)));
        assertTrue(graph.getRelation().get(vertices.get(3)).contains(vertices.get(2)));
    }

    @Test(expected = GraphRelationException.class)
    public void addEdgeException() {
        vertices.forEach(e -> graph.addVertex(e));

        graph.addEdge(vertices.get(0), vertices.get(1));
        graph.addEdge(vertices.get(1), vertices.get(0));
    }

    @Test
    public void getPath() {
        vertices.forEach(e -> graph.addVertex(e));

        graph.addEdge(vertices.get(0), vertices.get(1));
        graph.addEdge(vertices.get(1), vertices.get(2));
        graph.addEdge(vertices.get(3), vertices.get(2));
        graph.addEdge(vertices.get(4), vertices.get(3));

        List<City> result = graph.getPath(vertices.get(0), vertices.get(2));

        assertEquals(3, result.size());
    }

    @Test
    public void getEmptyPath() {
        vertices.forEach(e -> graph.addVertex(e));

        graph.addEdge(vertices.get(0), vertices.get(1));
        graph.addEdge(vertices.get(1), vertices.get(2));
        graph.addEdge(vertices.get(4), vertices.get(2));

        List<City> result = graph.getPath(vertices.get(0), vertices.get(4));

        assertEquals(0, result.size());
    }

    /**
     * Subclass of {@link Vertex} for testing directed and undirected graph.
     */
    @Getter
    @Setter
    @ToString(callSuper = true)
    class City extends Vertex {
        public City(String vertexLabel) {
            super(vertexLabel);
        }
    }
}