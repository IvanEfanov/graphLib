package org.inter.node;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.*;

/**
 * The {@code Node} class represents nodes for weighted graphs.
 *
 * @author ivanefanov
 */
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class Node {

    /**
     * Name of node.
     */
    private String name;

    /**
     * Weight of node. Imitation of infinity value by default.
     */
    private Integer weight = Integer.MAX_VALUE;

    /**
     * Field represents adjacent nodes for current node.
     */
    private Map<Node, Integer> adjacentNodes = new HashMap<>();

    /**
     * Field represents the shortest paths to all nodes for current node after call method {@link Node#calculatePathsFromThis()}.
     */
    private List<Node> shortestPath = new LinkedList<>();

    public Node(String name) {
        this.name = name;
    }

    /**
     * Method to add adjacent node and weight to current node.
     *
     * @param destinationNode {@link Node}
     * @param weight weight
     */
    public synchronized void addAdjacentNode(Node destinationNode, int weight) {
        this.adjacentNodes.putIfAbsent(destinationNode, weight);
    }

    /**
     * Method to calculate the shortest paths to all nodes for current node.
     */
    public synchronized void calculatePathsFromThis() {
        Set<Node> adjNodes = new HashSet<>();
        Set<Node> unAdjNodes = new HashSet<>();

        unAdjNodes.add(this.setWeight(0));

        while (!unAdjNodes.isEmpty()) {
            Node currentNode = getLowestWeightNode(unAdjNodes);
            unAdjNodes.remove(currentNode);

            currentNode.getAdjacentNodes()
                    .forEach((k, v) -> {
                        if (!adjNodes.contains(k)) {
                            calculateMinWeight(k, v, currentNode);
                            unAdjNodes.add(k);
                        }
                    });
            adjNodes.add(currentNode);
        }
    }

    /**
     * Returns the lowest weight node from not adjacent nodes.
     *
     * @param unAdjNodes {@link Set} of {@link Node}
     * @return {@link Node}
     */
    private synchronized Node getLowestWeightNode(Set<Node> unAdjNodes) {
        return unAdjNodes.stream()
                .min(Comparator.comparingInt(Node::getWeight))
                .orElse(null);
    }

    /**
     * Method to calculate weight between actual distance and new one.
     *
     * @param evaluationNode {@link Node}
     * @param edgeWeigh edge weight.
     * @param sourceNode {@link Node}
     */
    private synchronized void calculateMinWeight(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        int amountWeight = sourceNode.getWeight() + edgeWeigh;
        if (amountWeight < evaluationNode.getWeight()) {
            evaluationNode.setWeight(amountWeight);
            evaluationNode.setShortestPath(new LinkedList<Node>(sourceNode.getShortestPath()) {{
                add(sourceNode);
            }});
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
