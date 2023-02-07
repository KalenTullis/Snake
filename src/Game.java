import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;


public class Game extends JPanel {
        private int deltaX = 0;
        private int deltaY = 0;
        private int foodXCoord= 80;
        private int foodYCoord = 60;
        private int foodNum = 1;
        private int score = 0;
        private Boolean alive = true;
        private Boolean check = true;
        private ArrayList<Entity> snake = new ArrayList<Entity>();

        //Game constructor to set background and border
        public Game() {
            this.setOpaque(false);
            snake.add(new Entity(40,60));
        }

        //returns the snake array when called
        //used to get temporary coordinates of the last snake entity
        //(coordinates are used when driver calls eat method)
        public ArrayList<Entity> getSnake() {
            return snake;
        }

        //Returns checking boolean used
        //to display defeat screen
        public Boolean checking() {
            return check;
        }
        //turns check off important:
        //this prevents End game panel
        //from displaying forever
        public void checkOFF(Boolean off) {
            check = off;
        }

        //When isAlive returns false the
        //driver's timer will begin displaying
        //end screen.
        public Boolean isAlive() {
            return alive;
        }

        //returns deltaX / deltaY respectively
        //Used to prevent snake from moving into itself
        public int getDeltaX() {
            return deltaX;
        }
        public int getDeltaY() {
            return deltaY;
        }

        //Returns Score
        //Placed in Game field so
        //the value can be changed
        public int getScore() {
            return score;
        }

        //Change Direction of snake HEAD
        public void goLeft() {
            deltaX = -20;
            deltaY = 0;
        }
            
        public void goRight() {
            deltaX = 20;
            deltaY = 0;
        }
            
        public void goUp() {
            deltaX = 0;
            deltaY = -20;
        }
            
        public void goDown() {
            deltaX = 0;
            deltaY = 20;
        }

        //Generate new food coordinates and use logic to 
        //prevent food from spawning inside the snake
        public void setFoodCoords() {
            //Check if food needs to be spawned
            if (foodNum == 0) {

                //(all blocks in the 'grid' are 20 x 20 pixels 
                //placed by factors of 20 thus making an artificial grid)

                //Create random number 1-20 and multiply
                //by 20 to place block on the 'grid'
                int xCoord = ((int)(Math.random()*20) * 20);
                int yCoord = ((int)(Math.random()*20) * 20);

                //Iterate over every snake entity
                //if they share coordinates, reroll coordinates
                //(Placed in while loop in the low chance they reroll the same coordinate)
                for (int i = 0; i < snake.size(); i++) {
                    while ((xCoord == snake.get(i).getXCoord()) && (yCoord == snake.get(i).getYCoord())) {
                        xCoord = ((int)(Math.random()*20) * 20);
                        yCoord = ((int)(Math.random()*20) * 20);
                    }
                }

                //Set FoodCoordinates to be repainted
                foodXCoord = xCoord;
                foodYCoord = yCoord;

                //Raise foodNum to 1 so more food doesn't spawn
                foodNum += 1;
            }
        }
    
        //Uses temp values stored in Driver's timer
        //to create a new entity at those coordinates
        public void eat(int lastSnakePartXCoord, int LastSnakePartYCoord) {
            //Check if the HEAD of the snake
            //has the same coordinates as the food (collision)
            if ((snake.get(0).getXCoord() == foodXCoord) && (snake.get(0).getYCoord() == foodYCoord)) {
                snake.add(new Entity(lastSnakePartXCoord, LastSnakePartYCoord));

                //Indicate new food needs to spawn and increase score
                foodNum--;
                score++;
            }
            else return; //If statement is false, do nothing
        }
    
        //Check if Snake is killed, if not
        //move snake
        public void updatePosition() {
            //if snake moves off screen via left or right: end game
            if (snake.get(0).getXCoord() < 0 || snake.get(0).getXCoord() > super.getSize().getWidth()-20) {
                alive = false;
                return;
            }
            //if snake moves off screen via up or down: end game
            if (snake.get(0).getYCoord() < 0 || snake.get(0).getYCoord() > super.getSize().getHeight()-20) {
                alive = false;
                return;
            }
            //Iterate over every snake entity
            //if snake HEAD collides with any snake entity: end game
            for (int i = 1; i < snake.size(); i++) {
                if ((snake.get(0).getXCoord() == snake.get(i).getXCoord()) && (snake.get(0).getYCoord() == snake.get(i).getYCoord())) {
                    alive = false;
                    return;
                }
            }
            //Iterate over every snake entity
            //starting at the LAST entity
            //and set coordinates to the coordinates
            //of next in line entity
            for (int i = snake.size()-1; i > 0; i--) {
                snake.get(i).setXCoord(snake.get(i-1).getXCoord());
                snake.get(i).setYCoord(snake.get(i-1).getYCoord());
            }
            //set HEAD coordinate determined by
            //the delta values
            snake.get(0).setYCoord(snake.get(0).getYCoord() + deltaY);
            snake.get(0).setXCoord(snake.get(0).getXCoord() + deltaX);
        }
        
        //Paint all entities
        public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //Iterate over every snake entity and using mod 2
        //determine if block should be green or light green
        for (int i = 0; i < snake.size(); i++) {
            //even numbers are light green
            if (i % 2 == 0) {
                //set color to light green and paint using snake entity coordinates
                g.setColor(Color.GREEN);
                g.fillRect(snake.get(i).getXCoord(), snake.get(i).getYCoord(), 20, 20); 
            }
            //odd numbers are dark green
            else {
                //set color using RGB value and paint using snake entity coordinates
                g.setColor(new Color(0,128,43));
                g.fillRect(snake.get(i).getXCoord(), snake.get(i).getYCoord(), 20, 20); 
            }
        }

        //paint food red using food coordinates
        g.setColor(Color.RED);
        g.fillRect(foodXCoord, foodYCoord, 20, 20);
        
        }
}
