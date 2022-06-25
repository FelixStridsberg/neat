package com.vadeen.neat.tree;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LevelTreeConnectionIndexTest {

    /**
     * Tree:
     *       3
     *    40/|
     *     4 |20
     * 10/30\|
     *  1    2
     */
    @Test
    public void testConnectionIndex() {
        LevelTreeConnectionIndex index = new LevelTreeConnectionIndex();

        index.addConnection(new LevelConnection(10, 1, 4));
        index.addConnection(new LevelConnection(20, 2, 3));
        index.addConnection(new LevelConnection(30, 2, 4));
        index.addConnection(new LevelConnection(40, 4, 3));

        // Verify inputs nodes
        assertThat(index.getInputNodes(1), is(Arrays.asList()));
        assertThat(index.getInputNodes(2), is(Arrays.asList()));
        assertThat(index.getInputNodes(3), is(Arrays.asList(2, 4)));
        assertThat(index.getInputNodes(4), is(Arrays.asList(1, 2)));

        // Verify outputs nodes
        assertThat(index.getOutputNodes(1), is(Arrays.asList(4)));
        assertThat(index.getOutputNodes(2), is(Arrays.asList(3, 4)));
        assertThat(index.getOutputNodes(3), is(Arrays.asList()));
        assertThat(index.getOutputNodes(4), is(Arrays.asList(3)));

        // Verify inputs connections
        assertThat(index.getInputConnections(1), is(Arrays.asList()));
        assertThat(index.getInputConnections(2), is(Arrays.asList()));
        assertThat(index.getInputConnections(3), is(Arrays.asList(20, 40)));
        assertThat(index.getInputConnections(4), is(Arrays.asList(10, 30)));

        // Verify outputs connections
        assertThat(index.getOutputConnections(1), is(Arrays.asList(10)));
        assertThat(index.getOutputConnections(2), is(Arrays.asList(20, 30)));
        assertThat(index.getOutputConnections(3), is(Arrays.asList()));
        assertThat(index.getOutputConnections(4), is(Arrays.asList(40)));
    }
}