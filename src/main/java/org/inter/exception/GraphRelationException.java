package org.inter.exception;

/**
 * Relation can be only one side exception.
 *
 * @author ivanefanov
 */

public class GraphRelationException extends RuntimeException{
    private static final String MESSAGE = "Relation can be only one side exception.";

    public GraphRelationException() {
        super(MESSAGE);
    }
}
