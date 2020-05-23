package com.vadeen.neat.gene;

import com.vadeen.neat.tree.LevelConnection;

/**
 * The connection gene is a gene connecting two nodes.
 *
 * Every connection gene has a innovation number which is inherited during cross over. That way we can calculateDistance two
 * genomes and figure out which connection genes comes from the same mutation. This information can be used to know
 * how far apart two genomes are.
 *
 * Connection genes also have a weight which the values passing through the connection is multiplied by.
 *
 * Connections can be either expressed or not expressed. Non expressed connections are ignored during calculations,
 * but may be become expressed again later on.
 */
public class ConnectionGene extends LevelConnection {

    /**
     * Weight of the connection. Values passing through is multiplied by this.
     */
    private float weight;

    /**
     * If this if false, the connection is ignored in all calculations.
     */
    private boolean expressed;

    /**
     * @param in Node id this connection starts at.
     * @param out Node id this connection ends at.
     * @param weight Weight of connection.
     * @param expressed Determines if the connection is expressed or not.
     * @param innovation The innovation number of the connection gene.
     */
    public ConnectionGene(int in, int out, float weight, boolean expressed, int innovation) {
        super(innovation, in, out);
        this.weight = weight;
        this.expressed = expressed;
    }

    /**
     * Copy constructor.
     *
     * @param c ConnectionGene to copy.
     */
    public ConnectionGene(ConnectionGene c) {
        this(c.in, c.out, c.weight, c.expressed, c.id);
    }

    public int getInnovation() {
        return id;
    }

    public void setExpressed(boolean expressed) {
        this.expressed = expressed;
    }

    public boolean isExpressed() {
        return expressed;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "ConnectionGene{" +
                "id=" + id +
                ",in=" + in +
                ",out=" + out +
                ",weight=" + weight +
                ",expressed=" + expressed +
                "}";
    }

    public boolean equals(final Object o) {
        if (o == this)
            return true;

        if (!(o instanceof ConnectionGene))
            return false;

        ConnectionGene other = (ConnectionGene) o;
        if (Float.compare(getWeight(), other.getWeight()) != 0)
            return false;

        return isExpressed() == other.isExpressed();
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + Float.floatToIntBits(getWeight());
        result = result * PRIME + (isExpressed() ? 79 : 97);
        return result;
    }
}
