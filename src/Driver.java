//Driver class for Snake

//Import swing and awt classes
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Driver {

    //Most panels are opaque
    //Thus a createPanel method
    public JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }
    public static void main(String[] args) {
        //Create Frame (Frame properties are at the end)
        JFrame frame = new JFrame("Snake");

        //Create Root panel
        //Root panel holds the background image
        //and all other panels are not opaque 
        //otherPanels.(setOpaque(false))
        PicturePanel root = new PicturePanel("Snake.png");
        BorderLayout rootLayout = new BorderLayout();
        root.setLayout(rootLayout);

        //Create Top Panel and
        //Create JLabel set to default "score 0"
        //Add Label to top, then add top to root
        JPanel top = new Driver().createPanel();
        JLabel scoreDisplay = new JLabel("Score 0");
        scoreDisplay.setForeground(Color.BLACK);
        top.add(scoreDisplay);
        root.add(top, BorderLayout.NORTH);

        //Left PicturePanel
        //Add panel to root
        //This Panel is Empty and only used to create space
        JPanel left = new Driver().createPanel();
        left.setPreferredSize(new Dimension(100,100));
        root.add(left,BorderLayout.WEST);

        //Right PicturePanel
        //add panel to root
        //This Panel is Empty and only used to create space
        JPanel right = new Driver().createPanel();
        right.setPreferredSize(new Dimension(100,100));
        root.add(right,BorderLayout.EAST);
        

        //Create Bottom Panel
        //Create quit Button that exits the program
        //Add quit to bottom
        JPanel bottom = new Driver().createPanel();
        JButton quit = new JButton("Quit");
        quit.setBackground(Color.CYAN);
        bottom.add(quit);
        root.add(bottom, BorderLayout.SOUTH);

        //quit ActionListener
        quit.addActionListener(e -> {
            System.exit(0);
        });

        //Create Game Panel
        //JFrame size is set exact to ensure
        //display is 400 x 400 pixels
        //Add display to root
        Game display = new Game();
        root.add(display, BorderLayout.CENTER);
        
        //Controls
        //Add key listener to display (game panel)
        display.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                //if key = 'a' or left arrow key, move left
                if (key ==37 || key == 65) {
                    //If the snake is moving in the Horizontal axis
                    //the snake will not go up/down
                    //(prevents snake from moving into itself)
                    if (display.getDeltaX() == 0) {
                        display.goLeft();
                    }
                }
                //if key = 'w' or up arrow key, move up
                else if (key ==38 || key == 87) {
                    //If the snake is moving in the verticle axis
                    //the snake will not go up/down
                    //(prevents snake from moving into itself)
                    if (display.getDeltaY() == 0) {
                        display.goUp();
                    }
                }
                //if key = 'd' or right arrow key, move right
                else if (key == 39 || key == 68) {
                    //If the snake is moving in the Horizontal axis
                    //the snake will not go up/down
                    //(prevents snake from moving into itself)
                    if (display.getDeltaX() == 0) {
                        display.goRight();
                    }
                }
                //if key = 's' or down arrow key, move down
                else if (key == 40 || key == 83) {
                    //If the snake is moving in the verticle axis
                    //the snake will not go up/down
                    //(prevents snake from moving into itself)
                    if (display.getDeltaY() == 0) {
                        display.goDown();
                    }
                }
                
            }

            public void keyReleased(KeyEvent e) {
                //  empty, snake keeps moving
            }
            public void keyTyped(KeyEvent e) {
                //Empty, controlls are in keyPressed
            }
        });

        

        //Graphic updater
        Timer timer = new Timer(100, e ->{
            //When snake is alive: run game
            if (display.isAlive()) {
                //Get temp coordinates = to last snake entity coordinates
                //used for creating new snake during eat method
                int tempXCoord = display.getSnake().get(display.getSnake().size() - 1).getXCoord();
                int tempYCoord = display.getSnake().get(display.getSnake().size() - 1).getYCoord();

                //Change snake position depending on its delta values
                //(determined by keyPressed and snake.go"" methods)
                display.updatePosition();

                //Should conditions defined in eat method be true,
                //new snake entity will be created at temp coords.
                display.eat(tempXCoord, tempYCoord);

                //SetFoodCoords generates new Coords for the food.
                //SetFoodCoords also checks if a new food needs to be generated
                display.setFoodCoords();

                //Repaint the snake panel (the game) and update the score
                display.repaint();
                scoreDisplay.setText("Score " + display.getScore());
                
            } else {
                //If statement to ensure defeat menu is only displayed once
                    if (display.checking()) {
                        display.checkOFF(false);
                        JOptionPane.showMessageDialog(frame, "You lost with a score of " + display.getScore(), "Defeat", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        //start the timer that makes the game move
        timer.setRepeats(true);
        timer.start();

        //Jframe properties
        frame.getContentPane().add(root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(614,499);
        frame.setVisible(true);
        frame.setResizable(false);

        //Return focus to game panel
        display.setFocusable(true);

        //INSTRUCTION PANEL
        //opens popup window to display instructions
        //placed last so the game can set up and place instructions on top
        JOptionPane.showMessageDialog(frame,
            "Use WASD keys or ARROW keys to move UP LEFT DOWN Right respectively.\nEat the apples and don't hit the walls or yourself!",
            "Instructions", JOptionPane.INFORMATION_MESSAGE);
    }
}
