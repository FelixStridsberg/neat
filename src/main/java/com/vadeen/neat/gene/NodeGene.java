package com.vadeen.neat.gene;

import com.vadeen.neat.tree.LevelNode;

import java.util.Objects;

/**
 * A node gene is a place holder between the connections containing some meta data.
 */
public class NodeGene extends LevelNode {

    /**
     * Type of node.
     *
     * The type is ordered from input to output.
     */
    public enum Type {
        INPUT(0),
        HIDDEN(1),
        OUTPUT(2);

        private int order;

        Type(int order) {
            this.order = order;
        }

        public int getOrder() {
            return order;
        }
    }

    /**
     * Node type.
     */
    private Type type;

    /**
     * @param type Type of node.
     * @param id Id of node.
     */
    public NodeGene(Type type, int id) {
        super(id);
        this.type = type;
    }

    /**
     * Copy constructor.
     *
     * @param o Node to be copied.
     */
    public NodeGene(NodeGene o) {
        super(o);
        this.type = o.type;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        return "NodeGene{" +
                "id=" + id +
                ",level=" + getLevel() +
                ",type=" + type +
                "}";
    }

    public boolean equals(final Object o) {
        if (o == this)
            return true;

        if (!(o instanceof NodeGene))
            return false;

        NodeGene other = (NodeGene) o;

        if (!super.equals(o))
            return false;

        return type == other.type;
    }

    public int hashCode() {
        final int PRIME = 59;
        return super.hashCode() * PRIME + Objects.hash(type);
    }
}
