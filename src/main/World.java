package main;


import main.Simulation;
import main.SimulationVisualiser;

import javax.swing.*;

public class World {
    public static void main(String[] args) {

        Simulation.Parameters simulationParameters = new Simulation.Parameters("parameters.json");
        Simulation simulation = new Simulation(simulationParameters);
        SimulationVisualiser simulationVisualiser = new SimulationVisualiser(simulation);
        JFrame frame = new JFrame();
        frame.add(simulationVisualiser);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}