package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Mars rover coding challenge implementation");

        try {
            SquadCoordinator squadCoordinator = new SquadCoordinator();

            Rover rover1 = new Rover("Rover1");
            squadCoordinator.deployRover(rover1);
            squadCoordinator.commandRover(rover1.getName(), "FFRFF");

            Rover rover2 = new Rover("Rover2");
            squadCoordinator.deployRover(rover2);
            squadCoordinator.commandRover(rover2.getName(), "LBBR");
            squadCoordinator.commandRover(rover2.getName(), "FF");

            for (int i = 0; i < 5; i++) {
                squadCoordinator.commandRover(rover2.getName(), "F");
            }
            squadCoordinator.commandRover(rover2.getName(), "R");
            for (int i = 0; i < 5; i++) {
                squadCoordinator.commandRover(rover2.getName(), "B");
            }
        } catch (Exception e) {
            logger.error("Unhandled exception: {}", e.getMessage(), e);
        }
    }
}
