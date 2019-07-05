package com.vadeen.neat.tree;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LevelTreeTest {

    /**
     * Tree:
     *
     *      1      // Level 0
     */
    @Test
    public void testConstructNonConnectedTree() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));

        // Verify level count.
        assertThat(tree.getLevelCount(), is(1));

        // Verify number of levels and nodes in result.
        List<List<LevelNode>> levels = tree.getLevels();
        assertThat(levels.size(), is(1));
        assertThat(levels.get(0).size(), is(1));    // Level 0

        // Verify correct node on level.
        assertThat(levels.get(0).get(0), is(new LevelNode(1))); // Level 0
    }

    /**
     * Tree:
     *      2       // Level 2
     *     1|
     *      1       // Level 1
     *
     *   (Empty)    // Level 0
     */
    @Test
    public void testConstructSimpleTree() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));
        tree.addNode(new LevelNode(2));

        tree.addConnection(new LevelConnection(1, 1, 2));

        // Verify level count.
        assertThat(tree.getLevelCount(), is(3));

        // Verify number of levels and nodes in result.
        List<List<LevelNode>> levels = tree.getLevels();
        assertThat(levels.size(), is(3));
        assertThat(levels.get(0).size(), is(0));    // Level 0
        assertThat(levels.get(1).size(), is(1));    // Level 1
        assertThat(levels.get(2).size(), is(1));    // Level 2

        // Verify correct node on levels.
        assertThat(levels.get(1).get(0), is(new LevelNode(1))); // Level 1
        assertThat(levels.get(2).get(0), is(new LevelNode(2))); // Level 2
    }

    /**
     * Tree:
     *      3       // Level 2
     *    1/2\
     *    1   2     // Level 1
     */
    @Test
    public void testConstructSimpleTreeTwoNodeBase() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));
        tree.addNode(new LevelNode(2));
        tree.addNode(new LevelNode(3));

        tree.addConnection(new LevelConnection(1, 1, 3));
        tree.addConnection(new LevelConnection(2, 2, 3));

        // Verify level count.
        assertThat(tree.getLevelCount(), is(3));

        // Verify number of levels and nodes in result.
        List<List<LevelNode>> levels = tree.getLevels();
        assertThat(levels.size(), is(3));
        assertThat(levels.get(0).size(), is(0));    // Level 0
        assertThat(levels.get(1).size(), is(2));    // Level 1
        assertThat(levels.get(2).size(), is(1));    // Level 2

        // Verify correct node on levels.
        assertThat(levels.get(1).get(0), is(new LevelNode(1))); // Level 1
        assertThat(levels.get(1).get(1), is(new LevelNode(2))); // Level 1
        assertThat(levels.get(2).get(0), is(new LevelNode(3))); // Level 2
    }

    /**
     * Tree:
     *     2  3     // Level 2
     *    1\2/
     *      1       // Level 1
     */
    @Test
    public void testConstructSimpleTreeTwoNodeTop() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));
        tree.addNode(new LevelNode(2));
        tree.addNode(new LevelNode(3));

        tree.addConnection(new LevelConnection(1, 1, 2));
        tree.addConnection(new LevelConnection(2, 1, 3));

        // Verify level count.
        assertThat(tree.getLevelCount(), is(3));

        // Verify number of levels and nodes in result.
        List<List<LevelNode>> levels = tree.getLevels();
        assertThat(levels.size(), is(3));
        assertThat(levels.get(0).size(), is(0));    // Level 0
        assertThat(levels.get(1).size(), is(1));    // Level 1
        assertThat(levels.get(2).size(), is(2));    // Level 2

        // Verify correct node on levels.
        assertThat(levels.get(1).get(0), is(new LevelNode(1))); // Level 1
        assertThat(levels.get(2).get(0), is(new LevelNode(2))); // Level 2
        assertThat(levels.get(2).get(1), is(new LevelNode(3))); // Level 2
    }

     /**
     * Tree:
     *
     *      3       // Level 3
     *     2|
     *      2       // Level 2
     *     1|
     *      1       // Level 1
     */
    @Test
    public void testStraightThreeLevelTreeConstructedFromBottomToTop() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));
        tree.addNode(new LevelNode(2));
        tree.addNode(new LevelNode(3));

        tree.addConnection(new LevelConnection(1, 1, 2));
        tree.addConnection(new LevelConnection(2, 2, 3));

        // Verify level count.
        assertThat(tree.getLevelCount(), is(4));

        // Verify number of levels and nodes in result.
        List<List<LevelNode>> levels = tree.getLevels();
        assertThat(levels.size(), is(4));
        assertThat(levels.get(0).size(), is(0));    // Level 0
        assertThat(levels.get(1).size(), is(1));    // Level 1
        assertThat(levels.get(1).size(), is(1));    // Level 2
        assertThat(levels.get(1).size(), is(1));    // Level 3

        // Verify correct node on levels.
        assertThat(levels.get(1).get(0), is(new LevelNode(1))); // Level 1
        assertThat(levels.get(2).get(0), is(new LevelNode(2))); // Level 2
        assertThat(levels.get(3).get(0), is(new LevelNode(3))); // Level 3
    }

    /**
     * Tree:
     *
     *      3       // Level 3
     *     1|
     *      2       // Level 2
     *     2|
     *      1       // Level 1
     */
    @Test
    public void testStraightThreeLevelTreeConstructedFromTopToBottom() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));
        tree.addNode(new LevelNode(2));
        tree.addNode(new LevelNode(3));

        tree.addConnection(new LevelConnection(2, 2, 3));
        tree.addConnection(new LevelConnection(1, 1, 2));

        // Verify level count.
        assertThat(tree.getLevelCount(), is(4));

        // Verify number of levels and nodes in result.
        List<List<LevelNode>> levels = tree.getLevels();
        assertThat(levels.size(), is(4));
        assertThat(levels.get(0).size(), is(0));    // Level 0
        assertThat(levels.get(1).size(), is(1));    // Level 1
        assertThat(levels.get(1).size(), is(1));    // Level 2
        assertThat(levels.get(1).size(), is(1));    // Level 3

        // Verify correct node on levels.
        assertThat(levels.get(1).get(0), is(new LevelNode(1))); // Level 1
        assertThat(levels.get(2).get(0), is(new LevelNode(2))); // Level 2
        assertThat(levels.get(3).get(0), is(new LevelNode(3))); // Level 3
    }

    /**
     * Tree:
     *
     *      3       // Level 3
     *    1/2\
     *    5  4       // Level 2
     *  3/   4\
     *  1     2       // Level 1
     */
    @Test
    public void testSimpleTreeLevelTreeWithTwoBase() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));
        tree.addNode(new LevelNode(2));
        tree.addNode(new LevelNode(3));
        tree.addNode(new LevelNode(4));
        tree.addNode(new LevelNode(5));

        tree.addConnection(new LevelConnection(1, 5, 3));
        tree.addConnection(new LevelConnection(2, 4, 3));
        tree.addConnection(new LevelConnection(3, 1, 5));
        tree.addConnection(new LevelConnection(4, 2, 4));

        // Verify level count.
        assertThat(tree.getLevelCount(), is(4));

        // Verify number of levels and nodes in result.
        List<List<LevelNode>> levels = tree.getLevels();
        assertThat(levels.size(), is(4));
        assertThat(levels.get(0).size(), is(0));    // Level 0
        assertThat(levels.get(1).size(), is(2));    // Level 1
        assertThat(levels.get(2).size(), is(2));    // Level 2
        assertThat(levels.get(3).size(), is(1));    // Level 3

        // Verify correct node on levels.
        assertThat(levels.get(1).get(0), is(new LevelNode(1))); // Level 1
        assertThat(levels.get(1).get(1), is(new LevelNode(2))); // Level 1
        assertThat(levels.get(2).get(0), is(new LevelNode(4))); // Level 2
        assertThat(levels.get(2).get(1), is(new LevelNode(5))); // Level 2
        assertThat(levels.get(3).get(0), is(new LevelNode(3))); // Level 3
    }

    /**
     * Tree:
     *
     *      3       // Level 3
     *     /3\
     *   1/  4       // Level 2
     *   /   2\
     *  1     2       // Level 1
     */
    @Test
    public void testSimpleTreeLevelTreeWithTwoBaseDifferentLegs() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));
        tree.addNode(new LevelNode(2));
        tree.addNode(new LevelNode(3));
        tree.addNode(new LevelNode(4));

        tree.addConnection(new LevelConnection(1, 1, 3));
        tree.addConnection(new LevelConnection(2, 2, 4));
        tree.addConnection(new LevelConnection(3, 4, 3));

        // Verify level count.
        assertThat(tree.getLevelCount(), is(4));

        // Verify number of levels and nodes in result.
        List<List<LevelNode>> levels = tree.getLevels();
        assertThat(levels.size(), is(4));
        assertThat(levels.get(0).size(), is(0));    // Level 0
        assertThat(levels.get(1).size(), is(2));    // Level 1
        assertThat(levels.get(2).size(), is(1));    // Level 2
        assertThat(levels.get(3).size(), is(1));    // Level 3

        // Verify correct node on levels.
        assertThat(levels.get(1).get(0), is(new LevelNode(1))); // Level 1
        assertThat(levels.get(1).get(1), is(new LevelNode(2))); // Level 1
        assertThat(levels.get(2).get(0), is(new LevelNode(4))); // Level 2
        assertThat(levels.get(3).get(0), is(new LevelNode(3))); // Level 3
    }

    /**
     * Tree:
     *            7     // Level 4
     *           7|
     *         4  5     // Level 3
     *        /|  |
     *       / |4/|
     *     1/ 3|/ |
     *     /   6 6|     // Level 2
     *    /   2|\ |
     *   /     |5\|
     *  1      2  3     // Level 1
     *
     */
    @Test
    public void testComplexTree() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));
        tree.addNode(new LevelNode(2));
        tree.addNode(new LevelNode(3));
        tree.addNode(new LevelNode(4));
        tree.addNode(new LevelNode(5));
        tree.addNode(new LevelNode(6));
        tree.addNode(new LevelNode(7));

        tree.addConnection(new LevelConnection(1, 1, 4));
        tree.addConnection(new LevelConnection(2, 2, 6));
        tree.addConnection(new LevelConnection(3, 6, 4));
        tree.addConnection(new LevelConnection(4, 6, 5));
        tree.addConnection(new LevelConnection(5, 3, 6));
        tree.addConnection(new LevelConnection(6, 3, 5));
        tree.addConnection(new LevelConnection(7, 5, 7));

        // Verify level count.
        assertThat(tree.getLevelCount(), is(5));

        // Verify number of levels and nodes in result.
        List<List<LevelNode>> levels = tree.getLevels();

        assertThat(levels.size(), is(5));
        assertThat(levels.get(0).size(), is(0));    // Level 0
        assertThat(levels.get(1).size(), is(3));    // Level 1
        assertThat(levels.get(2).size(), is(1));    // Level 2
        assertThat(levels.get(3).size(), is(2));    // Level 3
        assertThat(levels.get(4).size(), is(1));    // Level 4

        // Verify correct node on levels.
        assertThat(levels.get(1).get(0), is(new LevelNode(1))); // Level 1
        assertThat(levels.get(1).get(1), is(new LevelNode(2))); // Level 1
        assertThat(levels.get(1).get(2), is(new LevelNode(3))); // Level 1
        assertThat(levels.get(2).get(0), is(new LevelNode(6))); // Level 2
        assertThat(levels.get(3).get(0), is(new LevelNode(4))); // Level 3
        assertThat(levels.get(3).get(1), is(new LevelNode(5))); // Level 3
        assertThat(levels.get(4).get(0), is(new LevelNode(7))); // Level 4
    }

    /**
     * Tree:
     *      3       // Level 2
     *    1/2\
     *    1   2     // Level 1
     */
    @Test
    public void testHasConnection() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));
        tree.addNode(new LevelNode(2));
        tree.addNode(new LevelNode(3));

        tree.addConnection(new LevelConnection(1, 1, 3));
        tree.addConnection(new LevelConnection(2, 2, 3));

        // Verify level count.
        assertThat(tree.hasConnection(1, 3), is(true));
        assertThat(tree.hasConnection(2, 3), is(true));
        assertThat(tree.hasConnection(3, 1), is(false));
        assertThat(tree.hasConnection(3, 2), is(false));
    }

    /**
     * Tree:
     *
     *      2       // Level 3
     *     1|
     *      3 4     // Level 2
     *     2|/3
     *      1       // Level 1
     *
     * Try to add connection between 2 and 4.
     * It is possible because node 4 is not connected to any nodes lower or equal to 2.
     */
    @Test
    public void testUpgradeLowerLevelNodeWhenNoOutputs() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));
        tree.addNode(new LevelNode(2));
        tree.addNode(new LevelNode(3));
        tree.addNode(new LevelNode(4));

        tree.addConnection(new LevelConnection(1, 3, 2));
        tree.addConnection(new LevelConnection(2, 1, 3));
        tree.addConnection(new LevelConnection(2, 1, 4));

        // Assert levels
        assertThat(tree.getNode(1).getLevel(), is(1));
        assertThat(tree.getNode(2).getLevel(), is(3));
        assertThat(tree.getNode(3).getLevel(), is(2));
        assertThat(tree.getNode(4).getLevel(), is(2));

        // Add connection
        tree.addConnection(new LevelConnection(2, 2, 4));

        // Assert new levels
        assertThat(tree.getNode(1).getLevel(), is(1));
        assertThat(tree.getNode(2).getLevel(), is(3));
        assertThat(tree.getNode(3).getLevel(), is(2));
        assertThat(tree.getNode(4).getLevel(), is(4));
    }

    /**
     * Tree:
     *      2       // Level 5
     *     1|
     *      3       // Level 4
     *     2/\
     *     4  \     // Level 3
     *    3|  |6
     *     5  6     // Level 2
     *     4\/5
     *      1       // Level 1
     *
     * Try to add connection between 4 and 6.
     * It is possible because 6 and all connected nodes above it can be upgraded.
     */
    @Test
    public void testUpgradeLowerLevelNodeWithOutputs() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));
        tree.addNode(new LevelNode(2));
        tree.addNode(new LevelNode(3));
        tree.addNode(new LevelNode(4));
        tree.addNode(new LevelNode(5));
        tree.addNode(new LevelNode(6));

        tree.addConnection(new LevelConnection(1, 3, 2));
        tree.addConnection(new LevelConnection(2, 4, 3));
        tree.addConnection(new LevelConnection(3, 5, 4));
        tree.addConnection(new LevelConnection(4, 1, 5));
        tree.addConnection(new LevelConnection(5, 1, 6));
        tree.addConnection(new LevelConnection(6, 6, 3));

        // Assert levels
        assertThat(tree.getNode(1).getLevel(), is(1));
        assertThat(tree.getNode(2).getLevel(), is(5));
        assertThat(tree.getNode(3).getLevel(), is(4));
        assertThat(tree.getNode(4).getLevel(), is(3));
        assertThat(tree.getNode(5).getLevel(), is(2));
        assertThat(tree.getNode(6).getLevel(), is(2));

        // Add connections
        tree.addConnection(new LevelConnection(6, 4, 6));

        // Assert new levels
        assertThat(tree.getNode(1).getLevel(), is(1));
        assertThat(tree.getNode(2).getLevel(), is(6));
        assertThat(tree.getNode(3).getLevel(), is(5));
        assertThat(tree.getNode(4).getLevel(), is(3));
        assertThat(tree.getNode(5).getLevel(), is(2));
        assertThat(tree.getNode(6).getLevel(), is(4));
    }

    /**
     * Tree:
     *
     *       3      // Level 3
     *      /2\
     *    1/   4    // Level 2
     *    /   3\
     *   1      2   // Level 1
     *
     * Test to add connection from 4 to 1.
     * It is possible because 1 and all out nodes can be upgraded.
     */
    @Test
    public void testAddDownwardsConnection() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));
        tree.addNode(new LevelNode(2));
        tree.addNode(new LevelNode(3));
        tree.addNode(new LevelNode(4));

        tree.addConnection(new LevelConnection(1, 1, 3));
        tree.addConnection(new LevelConnection(2, 4, 3));
        tree.addConnection(new LevelConnection(3, 2, 4));

        // Assert levels
        assertThat(tree.getNode(1).getLevel(), is(1));
        assertThat(tree.getNode(2).getLevel(), is(1));
        assertThat(tree.getNode(3).getLevel(), is(3));
        assertThat(tree.getNode(4).getLevel(), is(2));

        // Add connection
        tree.addConnection(new LevelConnection(4, 4, 1));

        // Assert new levels
        assertThat(tree.getNode(1).getLevel(), is(3));
        assertThat(tree.getNode(2).getLevel(), is(1));
        assertThat(tree.getNode(3).getLevel(), is(4));
        assertThat(tree.getNode(4).getLevel(), is(2));
    }

    /**
     * Tree:
     *    1     // Level 0
     */
    @Test
    public void testAddConnectionToNonExistentInputNodeException() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));

        try {
            tree.addConnection(new LevelConnection(1, 2, 1));
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Invalid connection in:null out:LevelNode{id=1,level=0}"));
        }
    }

    /**
     * Tree:
     *    1     // Level 0
     */
    @Test
    public void testAddConnectionToNonExistentOutputNodeException() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));

        try {
            tree.addConnection(new LevelConnection(1, 1, 2));
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Invalid connection in:LevelNode{id=1,level=0} out:null"));
        }
    }

    /**
     * Tree:
     *    1     // Level 0
     */
    @Test
    public void testAddConnectionToNonExistentNodesException() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));

        try {
            tree.addConnection(new LevelConnection(1, 2, 3));
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Invalid connection in:null out:null"));
        }
    }

    /**
     * Tree:
     *    1     // Level 0
     *
     * Try to add duplicate 1 to 2 connection.
     */
    @Test
    public void testAddConnectionToSameNodeException() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));

        try {
            tree.addConnection(new LevelConnection(1, 1, 1));
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Invalid connection in:LevelNode{id=1,level=0} out:LevelNode{id=1,level=0}"));
        }
    }

    /**
     * Tree:
     *    2     // Level 2
     *    |
     *    1     // Level 1
     *
     * Try to add duplicate 1 to 2 connection.
     */
    @Test
    public void testAddDuplicateConnectionException() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));
        tree.addNode(new LevelNode(2));

        tree.addConnection(new LevelConnection(1, 1, 2));

        try {
            tree.addConnection(new LevelConnection(1, 1, 2));
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Duplicate connection in:LevelNode{id=1,level=1} out:LevelNode{id=2,level=2}"));
        }
    }

    /**
     * Tree:
     *      2       // Level 3
     *    3/4\
     *    3   4     // Level 2
     *    1\2/
     *      1       // Level 1
     *
     * Try to add connection between 2 and 1. Not possible because of cycle.
     */
    @Test
    public void testAddCycleException() {
        LevelTree<LevelConnection, LevelNode> tree = new LevelTree<>();
        tree.addNode(new LevelNode(1));
        tree.addNode(new LevelNode(2));
        tree.addNode(new LevelNode(3));
        tree.addNode(new LevelNode(4));

        tree.addConnection(new LevelConnection(1, 1, 3));
        tree.addConnection(new LevelConnection(1, 1, 4));
        tree.addConnection(new LevelConnection(1, 3, 2));
        tree.addConnection(new LevelConnection(1, 4, 2));

        try {
            tree.addConnection(new LevelConnection(1, 2, 1));
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("LevelNode{id=2,level=3} cannot be connected to LevelNode{id=1,level=1}, will introduce cycle"));
        }
    }
}