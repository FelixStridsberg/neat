package com.vadeen.neat.gui;

import com.vadeen.neat.stats.NeatStats;
import com.vadeen.neat.stats.info.GenomeInfo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StatsPanel extends JPanel {

    private final static int GRAPH_WIDTH = 10;

    private final NeatStats stats;

    public StatsPanel(NeatStats stats) {
        this.stats = stats;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, getWidth(), getHeight());

        paintFitnessGraph(g2);
    }

    private void paintFitnessGraph(Graphics2D g) {
        List<GenomeInfo> genomeInfos = stats.getBestGenomeOverTime();


        float max = 4.0f;
        for (GenomeInfo info : genomeInfos) {
            float fitness = info.getFitness();
            if (fitness > max)
                max = fitness;
        }

        for (int i = 0; i <= 10; i++) {
            int y = getHeight() - (int)((i/max * (getHeight() - 10))*max/10.0f) + 5;
            g.setColor(new Color(0, 0, 0, 20));
            g.drawLine(40, y, getWidth(), y);

            g.setColor(new Color(0, 0, 0, 100));
            g.drawString(String.format("%.02f", (i*(max/10.0f))), 5, y + 4);

        }

        g.setColor(Color.BLACK);
        int o = getWidth()/(GRAPH_WIDTH * ((genomeInfos.size()/GRAPH_WIDTH) + 1));

        int lastX = o;
        int lastY = getHeight();

        for (GenomeInfo info : new ArrayList<>(genomeInfos)) {
            int y = getHeight() - (int)(info.getFitness()/max * (getHeight() - 10)) + 5;
            int x = lastX + o;

            g.drawLine(lastX, lastY, x, y);

            lastX = x;
            lastY = y;
        }
    }
}


