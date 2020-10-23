package org.inter.graph;

import lombok.*;
import org.inter.node.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

/**
 * WeightedGraphTest.
 *
 * @author ivanefanov
 */
@NoArgsConstructor
public class WeightedGraphTest {

    private WeightedGraph<State> graph;
    private List<State> states;

    @Before
    public void setup() {
        graph = new WeightedGraph<>();

        states = asList(
                new State("1", "A"),
                new State("2", "B"),
                new State("3", "C"),
                new State("4", "D"),
                new State("5", "E"),
                new State("6", "F")

        );

        graph.addNode(states.get(0));
        graph.addNode(states.get(1));
        graph.addNode(states.get(2));
        graph.addNode(states.get(3));
        graph.addNode(states.get(4));
        graph.addNode(states.get(5));

        states.get(0).addAdjacentNode(states.get(1), 11);
        states.get(0).addAdjacentNode(states.get(2), 13);

        states.get(1).addAdjacentNode(states.get(3), 12);
        states.get(1).addAdjacentNode(states.get(5), 13);

        states.get(2).addAdjacentNode(states.get(4), 11);

        states.get(3).addAdjacentNode(states.get(4), 3);
        states.get(3).addAdjacentNode(states.get(5), 2);

        states.get(5).addAdjacentNode(states.get(4), 6);
    }


    @Test
    public void getShortestPatTest() {
        State state = graph.getNodes()
                .stream()
                .filter(e -> e.getCode().equals("1"))
                .findFirst()
                .get();


        List<State> shortestPathFromSource = graph.getShortestPathFromSource(state, states.get(4));
        int weightPath = shortestPathFromSource.stream()
                .filter(e-> e.getCode().equals("5"))
                .mapToInt(State::getWeight).sum();

        assertEquals(3, shortestPathFromSource.size());
        assertEquals(24, weightPath);
    }

    @Test
    public void concatNewNodeNameTest(){
        Function<String, String> function = x -> x.concat("_code_01");

        graph.concatNewNodeName(function);

        assertEquals("A_code_01", states.get(0).getName());
        assertEquals("B_code_01", states.get(1).getName());
        assertEquals("C_code_01", states.get(2).getName());
    }

    /**
     * Subclass of {@link Node} for testing weighted graph.
     */
    @Getter
    @Setter
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = false)
    static
    class State extends Node {

        private String code;

        public State(String code, String name) {
            super(name);
            this.code = code;
        }
    }
}