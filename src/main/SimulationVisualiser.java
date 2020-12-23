package main;

import maps_elements.data_types.Vector2d;

import javax.swing.*;
import java.awt.*;

public class SimulationVisualiser extends JPanel {
    private static final int entitySize = 2;
    private static final Color steppeColor = Color.GREEN;
    private static final Color jungleColor = Color.YELLOW;

    private Simulation simulation;

    public SimulationVisualiser(Simulation simulation) {
        this.simulation = simulation;
        int width = simulation.getMap().getWidth();
        int height = simulation.getMap().getHeight();
        this.setPreferredSize(new Dimension(entitySize*width, entitySize*height));
    }

    public void drawSimulation(Simulation simulation) {
        repaint();
    }

    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        int width = simulation.getMap().getWidth();
        int height = simulation.getMap().getHeight();
        graphics2D.setPaint(steppeColor);
        graphics2D.fillRect(0, 0, entitySize*width, entitySize*height);

        Vector2d junglePosition = simulation.getMap().getJunglePosition();
        graphics2D.setPaint(jungleColor);
        graphics2D.fillRect(entitySize*junglePosition.x, entitySize*junglePosition.y,
                entitySize*simulation.getMap().getJungleWidth(), entitySize*simulation.getMap().getJungleHeight());
    }
}
