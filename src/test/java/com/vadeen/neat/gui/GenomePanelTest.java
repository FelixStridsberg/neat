package com.vadeen.neat.gui;

import com.vadeen.neat.io.NeatIO;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;

public class GenomePanelTest {

    private static JFrame frame = new JFrame("gui-test");

    public static void main(String[] args) throws IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, screenSize.width, screenSize.height);
        frame.getContentPane().setLayout(new GridLayout(4, 4));

        for (int i = 1; i <= 16; i++) {
            frame.add(new GenomePanel(NeatIO.genomeFromResource("genomes/gui-" + i + ".json")));
        }

        frame.setVisible(true);
    }
}