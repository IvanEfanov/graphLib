package org.inter.vertex;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Vertex.
 *
 * @author ivanefanov
 */
@Getter
@Setter
@ToString
public class Vertex {

    /**
     * Flag that used for getting path.
     */
    private boolean isVisited = false;

    /**
     * Label with naming of vertex.
     */
    private String vertexLabel;

    public Vertex(String vertexLabel) {
        this.vertexLabel = vertexLabel;
    }
}
