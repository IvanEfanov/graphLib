package org.inter.vertex;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of stack structure for {@link Vertex}.
 *
 * @author ivanefanov
 */
@Getter
@Setter
public class VertexStack<T extends Vertex> {
    private int size = 0;
    private int top;
    private List<T> vertexList = new ArrayList<>();

    public VertexStack() {
        this.top = -1;
    }

    /**
     * Method for adding element to stack.
     *
     * @param a subclasses of {@link Vertex}.
     */
    public void push(T a) {
        size++;
        vertexList.add(++top, a);
    }

    /**
     * Method to get and remove the last element from stack.
     *
     * @return last element of stack.
     */
    public synchronized T pop() {
        size--;
        return vertexList.remove(top--);
    }

    /**
     * Method to get last element from stack.
     *
     * @return last element of stack.
     */
    public synchronized T peek() {
        return vertexList.get(top);
    }

    /**
     * Method for checking stack for elements.
     *
     * @return {@code true} if this stack contains no elements.
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Method to set stack with having only first element from current.
     */
    public void setOnlyFirstElement() {
        vertexList = vertexList.subList(0, 1);
        top = 0;
        size = 1;
    }
}
