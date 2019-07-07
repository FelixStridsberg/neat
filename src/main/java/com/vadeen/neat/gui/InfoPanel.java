package com.vadeen.neat.gui;

import com.vadeen.neat.generation.Generation;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private Generation generation;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, getWidth(), getHeight());

        if (generation == null)
            return;

        Font f = g2.getFont();
        g2.setFont(f.deriveFont(16.0f));
        g2.drawString("Generation: " + generation.getGenerationNumber(), 10, 20);
        g2.drawString(String.format("Best fitness: %.01f", generation.getBestGenome().getFitness()), 10, 38);
        g2.drawString("Species: " + generation.getSpecies().size(), 10, 56);
    }

    public void setGeneration(Generation g) {
        this.generation = g;
    }
}
