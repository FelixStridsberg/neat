package com.vadeen.neat.gui;

import com.vadeen.neat.generation.Generation;
import com.vadeen.neat.stats.NeatStats;

import javax.swing.*;
import java.awt.*;

public class VisualPanel extends JPanel {

    private final NeatStats stats = new NeatStats();
    private final StatsPanel statsPanel = new StatsPanel(stats);
    private final GenomePanel genomePanel = new GenomePanel(null);
    private final InfoPanel infoPanel = new InfoPanel();
    private final JPanel top = new JPanel();

    public VisualPanel() {
        top.setLayout(new GridLayout(1, 2));
        top.add(infoPanel);
        top.add(genomePanel);

        setLayout(new GridLayout(2, 1));
        add(top);
        add(statsPanel);
    }

    public void addGeneration(int gen, Generation g) {
        stats.addGeneration(g);
        genomePanel.setGenome(g.getBestGenome());
        infoPanel.setGeneration(gen, g);
        repaint();
    }
}
