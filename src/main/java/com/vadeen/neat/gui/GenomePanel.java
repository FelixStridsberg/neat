package com.vadeen.neat.gui;

import com.vadeen.neat.gene.ConnectionGene;
import com.vadeen.neat.gene.NodeGene;
import com.vadeen.neat.genome.Genome;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The genome panel is a GUI component for drawing genomes.
 * It is messy it do not work but it is, it certainly is.
 *
 * TODO make less messy, make work, ???, profit.
 */
public class GenomePanel extends JPanel {

    private final static int PADDING = 20;
    private final static int NODE_SIZE = 26;
    private final static int CONNECTION_MARGIN = 14;

    private Genome genome;

    private final Map<Integer, Point> nodeCoordinates = new HashMap<>();

    public GenomePanel(Genome genome) {
        this.genome = genome;
    }

    public void setGenome(Genome genome) {
        this.genome = genome;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setBackground(new Color(245, 245, 245));
        g2.setStroke(new BasicStroke(2));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.clearRect(0, 0, getWidth(), getHeight());

        if (genome == null)
            return;

        drawNodes(g2);
        drawConnections(g2);
    }

    private void drawNodes(Graphics2D g) {
        drawHiddenNodes(g);
    }

    private void drawHiddenNodes(Graphics2D g) {
        List<List<NodeGene>> levels = getLevels();

        int height = getHeight() - PADDING * 2;
        int heightOffset = height/levels.size();

        int i = height - heightOffset/2;
        for (List<NodeGene> nodeLayer : levels) {
            if (i == 0)
                continue;

            drawNodeLevel(g, nodeLayer, i);

            i -= heightOffset;
        }
    }

    private void drawNodeLevel(Graphics2D g, List<NodeGene> nodes, int y) {
        if (nodes.size() == 0)
            return;

        int width = getWidth() - PADDING * 2;
        int widthOffset = width/nodes.size();

        int x = widthOffset/2 + PADDING;
        for (NodeGene n : nodes) {
            Point p = new Point(x, y + PADDING);

            if (n.getType() == NodeGene.Type.HIDDEN)
                g.setColor(Color.BLACK);
            if (n.getType() == NodeGene.Type.INPUT)
                g.setColor(Color.GREEN);
            if (n.getType() == NodeGene.Type.OUTPUT)
                g.setColor(Color.BLUE);


            drawNode(g, n, p);

            nodeCoordinates.put(n.getId(), p);

            x += widthOffset;
        }
    }

    private void drawNode(Graphics2D g, NodeGene n, Point p) {
        g.drawOval(p.x - NODE_SIZE/2, p.y - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);

        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);

        String str = String.valueOf(n.getId());
        Rectangle2D rect = fm.getStringBounds(str, g);

        g.drawString(str, p.x - (int)(rect.getWidth()/2), p.y + fm.getAscent()/2);
    }

    private void drawConnections(Graphics2D g) {
        for (ConnectionGene con : genome.getConnections().values()) {

            Point out = nodeCoordinates.get(con.getOut());
            Point in = nodeCoordinates.get(con.getIn());

            if (out == null)
                out = new Point(0, 0);

            if (in == null)
                in = new Point(0, 0);

            Point offset = calculateOffset(CONNECTION_MARGIN, out, in);

            if (con.isExpressed())
                g.setColor(new Color(0, 0, 0, 200));
            else
                g.setColor(new Color(0, 0, 0, 30));

            drawLine(g, out, in, offset);
        }
    }

    private Point calculateOffset(int offset, Point p1, Point p2) {
        double v = Math.atan2(p2.y - p1.y, p2.x - p1.x);

        int ox = (int)(offset * Math.cos(v));
        int oy = (int)(offset * Math.sin(v));

        return new Point(ox, oy);
    }

    private void drawLine(Graphics2D g2, Point p1, Point p2, Point offset) {
        g2.drawLine(p1.x + offset.x, p1.y + offset.y, p2.x - offset.x, p2.y - offset.y);
    }

    /**
     * Returns levels from genome tree except the output is always at top and inputs is always at bottom.
     */
    private List<List<NodeGene>> getLevels() {
        List<List<NodeGene>> levels = genome.getLevels();

        List<NodeGene> outputs = new ArrayList<>();
        List<NodeGene> inputs = new ArrayList<>();
        List<List<NodeGene>> hiddenLevels = new ArrayList<>();

        for (List<NodeGene> level : levels) {
            List<NodeGene> l = new ArrayList<>();

            for (NodeGene n : level) {
                switch (n.getType()) {
                    case HIDDEN:
                        l.add(n);
                        break;
                    case INPUT:
                        inputs.add(n);
                        break;
                    case OUTPUT:
                        outputs.add(n);
                        break;
                }
            }

            if (l.size() > 0)
                hiddenLevels.add(l);
        }

        List<List<NodeGene>> result = new ArrayList<>();
        result.add(inputs);
        result.addAll(hiddenLevels);
        result.add(outputs);

        return result;
    }
}
