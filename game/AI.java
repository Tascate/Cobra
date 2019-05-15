package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;

/**
 * AI class to make the AI, which is the opponent.
 */
public class AI extends Character {
	int wins; // number of wins
	
	/**
	 * AI constructor to create the AI, which is the opponent of the other player, and sets their wins to 0.
	 * @param s - speed of AI
	 * @param c - color of AI
	 * @param a - angle of AI
	 */
	public AI(int row, int col, int speed, Color color, int angle) {
		super(row, col, speed, color, angle);
		wins = 0;
	}
	//2D boolean (true/false) array
	boolean map[][];
	//this represents the position of the AI on the map
	int x, y;
	//this int will be 1 - 4 and will represent a direction
	//1 - up
	//2 - right
	//3 - down
	//4 - left
	int direction = 1;
	
	/**
	* Creates the grid that the game takes place on and sets the grid to false except the player positions
	* @param startX - starting column position
	* @param startY - starting row position
	*/
	void init(FieldObject[][] grid){
	    //set the x and y to a starting position
	    x = row;
	    y = col;
	    //this will set the entire array to false
	    map = new boolean [grid.length][grid[0].length];
	    int i = 0, j = 0;
	    while (i<grid.length){
	        while(j<grid[0].length){
	        	if (grid[i][j] == null) {
	        		map[i][j] = false;
	        	}
	        	else {
	        		map[i][j] = true;
	        	}
	            j++;
	        }
	        j = 0;
	        i++;
	    }

	    //and this will set the starting position to true
	    map[x][y] = true;
	}
	
	/**
	* Enables the player to move around and detects if player goes out of bounds
	*/
	void tick(){
	    try{
	        //try to go straight and will error if 
	        //it cannot because it would be out of bounds
	        switch(direction){
	        case 1: //up
	            if(!map[x][y+1]){ //will error out here if the block in front is out of bounds
	                map[x][y+1] = true;
	                setAngle(90);
	            }
	            return;
	        case 2: //right
	            if(!map[x+1][y]){ //will error out here if the block in front is out of bounds
	                map[x+1][y] = true;
	                setAngle(0);
	            }
	            return;
	        case 3: //down
	            if(!map[x][y-1]){ //will error out here if the block in front is out of bounds
	                map[x][y-1] = true;
	                setAngle(270);
	            }
	            return;
	        case 4: //left
	            if(!map[x-1][y]){ //will error out here if the block in front is out of bounds
	                map[x-1][y] = true;
	                setAngle(180);
	            }
	            return;
	        }
	    }catch(Exception e){

	    }
	    //this next bit will only be  triggered if the AI could not go straight

	    //define two temporary variables, fairly self explanatory.
	    boolean canGoLeft = false, canGoRight = false;

	    //defines whether it is safe to go left
	    //turn left respectively
	    //test if straight is safe
	    direction--;
	    try{
	        //try to go straight and will error if 
	        //it cannot because it would be out of bounds
	        switch(direction){
	        case 1: //up
	            if(!map[x][y+1]){ //will error out here if the block in front is out of bounds
	                canGoLeft = true;
	            }else canGoLeft = false;
	            break;
	        case 2: //right
	            if(!map[x+1][y]){ //will error out here if the block in front is out of bounds
	                canGoLeft = true;
	            }else canGoLeft = false;
	            break;
	        case 3: //down
	            if(!map[x][y-1]){ //will error out here if the block in front is out of bounds
	                canGoLeft = true;
	            }else canGoLeft = false;
	            break;
	        case 4: //left
	            if(!map[x-1][y]){ //will error out here if the block in front is out of bounds
	                canGoLeft = true;
	            }else canGoLeft = false;
	            break;
	        }
	    }catch(Exception e){
	        canGoLeft = false;
	    }
	    //return to original direction before test
	    direction++;

	    //now perform the same test but with turning right
	    //turn right respectively
	    direction++;
	    try{
	        //try to go straight and will error if 
	        //it cannot because it would be out of bounds
	        switch(direction){
	        case 1: //up
	            if(!map[x][y+1]){ //will error out here if the block in front is out of bounds
	                canGoRight = true;
	            }else canGoRight = false;
	            break;
	        case 2: //right
	            if(!map[x+1][y]){ //will error out here if the block in front is out of bounds
	                canGoRight = true;
	            }else canGoRight = false;
	            break;
	        case 3: //down
	            if(!map[x][y-1]){ //will error out here if the block in front is out of bounds
	                canGoRight = true;
	            }else canGoRight = false;
	            break;
	        case 4: //left
	            if(!map[x-1][y]){ //will error out here if the block in front is out of bounds
	                canGoRight = true;
	            }else canGoRight = false;
	            break;
	        }
	    }catch(Exception e){
	        canGoRight = false;
	    }
	    direction--;

	    if(canGoLeft&&!canGoRight){//can only go left
	        direction--;
	        try{
	            //try to go straight and will error if 
	            //it cannot because it would be out of bounds
	        	switch(direction){
		        case 1: //up
		            if(!map[x][y+1]){ //will error out here if the block in front is out of bounds
		                map[x][y+1] = true;
		                setAngle(90);
		            }
		            return;
		        case 2: //right
		            if(!map[x+1][y]){ //will error out here if the block in front is out of bounds
		                map[x+1][y] = true;
		                setAngle(0);
		            }
		            return;
		        case 3: //down
		            if(!map[x][y-1]){ //will error out here if the block in front is out of bounds
		                map[x][y-1] = true;
		                setAngle(270);
		            }
		            return;
		        case 4: //left
		            if(!map[x-1][y]){ //will error out here if the block in front is out of bounds
		                map[x-1][y] = true;
		                setAngle(180);
		            }
		            return;
		        }
		    }catch(Exception e){

		    }
	    }else if(!canGoLeft&&canGoRight){//can only go right
	        direction++;
	        try{
	            //try to go straight and will error if 
	            //it cannot because it would be out of bounds
	        	switch(direction){
		        case 1: //up
		            if(!map[x][y+1]){ //will error out here if the block in front is out of bounds
		                map[x][y+1] = true;
		                setAngle(90);
		            }
		            return;
		        case 2: //right
		            if(!map[x+1][y]){ //will error out here if the block in front is out of bounds
		                map[x+1][y] = true;
		                setAngle(0);
		            }
		            return;
		        case 3: //down
		            if(!map[x][y-1]){ //will error out here if the block in front is out of bounds
		                map[x][y-1] = true;
		                setAngle(270);
		            }
		            return;
		        case 4: //left
		            if(!map[x-1][y]){ //will error out here if the block in front is out of bounds
		                map[x-1][y] = true;
		                setAngle(180);
		            }
		            return;
		        }
		    }catch(Exception e){

		    }
	    }else if(canGoLeft&&canGoRight){//can go either way so it will pick randomly
	        if(System.currentTimeMillis()%2==0){//random, will be true half of the time and false the other half
	            //change direction one anticlockwise
	            direction--;
	            try{
	                //try to go straight and will error if 
	                //it cannot because it would be out of bounds
	            	switch(direction){
	    	        case 1: //up
	    	            if(!map[x][y+1]){ //will error out here if the block in front is out of bounds
	    	                map[x][y+1] = true;
	    	                setAngle(90);
	    	            }
	    	            return;
	    	        case 2: //right
	    	            if(!map[x+1][y]){ //will error out here if the block in front is out of bounds
	    	                map[x+1][y] = true;
	    	                setAngle(0);
	    	            }
	    	            return;
	    	        case 3: //down
	    	            if(!map[x][y-1]){ //will error out here if the block in front is out of bounds
	    	                map[x][y-1] = true;
	    	                setAngle(270);
	    	            }
	    	            return;
	    	        case 4: //left
	    	            if(!map[x-1][y]){ //will error out here if the block in front is out of bounds
	    	                map[x-1][y] = true;
	    	                setAngle(180);
	    	            }
	    	            return;
	    	        }
	    	    }catch(Exception e){

	    	    }
	        }else{
	            //change direction one clockwise
	            direction++;
	            try{
	                //try to go straight and will error if 
	                //it cannot because it would be out of bounds
	            	switch(direction){
	    	        case 1: //up
	    	            if(!map[x][y+1]){ //will error out here if the block in front is out of bounds
	    	                map[x][y+1] = true;
	    	                setAngle(90);
	    	            }
	    	            return;
	    	        case 2: //right
	    	            if(!map[x+1][y]){ //will error out here if the block in front is out of bounds
	    	                map[x+1][y] = true;
	    	                setAngle(0);
	    	            }
	    	            return;
	    	        case 3: //down
	    	            if(!map[x][y-1]){ //will error out here if the block in front is out of bounds
	    	                map[x][y-1] = true;
	    	                setAngle(270);
	    	            }
	    	            return;
	    	        case 4: //left
	    	            if(!map[x-1][y]){ //will error out here if the block in front is out of bounds
	    	                map[x-1][y] = true;
	    	                setAngle(180);
	    	            }
	    	            return;
	    	        }
	    	    }catch(Exception e){

	    	    }
	        }
	    }
	}
}
