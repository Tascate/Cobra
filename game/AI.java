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
	public AI(int s,Color c,int a) {
		super(s,c,a);
		wins = 0;
	}
	//2D boolean (true/false) array
	boolean map[][] = new boolean[20][20];
	//this represents the position of the AI on the map
	int x = 10, y = 10;
	//this int will be 1 - 4 and will represent a direction
	//1 - up
	//2 - right
	//3 - down
	//4 - left
	int direction = 1;

	void init(int startX, int startY){
	    //set the x and y to a starting position
	    x = startX;
	    y = startY;
	    //this will set the entire array to false
	    int i = 0, j = 0;
	    while (i<20){
	        while(j<20){
	            map[i][j] = false;
	            j++;
	        }
	        j = 0;
	        i++;
	    }

	    //and this will set the starting position to true
	    map[x][y] = true;
	}

	void tick(){
	    try{
	        //try to go straight and will error if 
	        //it cannot because it would be out of bounds
	        switch(direction){
	        case 1: //up
	            if(!map[x][y-1]){ //will error out here if the block in front is out of bounds
	                map[x][y-1] = true;
	                y--;
	            }
	            return;
	        case 2: //right
	            if(!map[x+1][y]){ //will error out here if the block in front is out of bounds
	                map[x+1][y] = true;
	                x++;
	            }
	            return;
	        case 3: //down
	            if(!map[x][y+1]){ //will error out here if the block in front is out of bounds
	                map[x][y+1] = true;
	                y++;
	            }
	            return;
	        case 4: //left
	            if(!map[x-1][y]){ //will error out here if the block in front is out of bounds
	                map[x-1][y] = true;
	                x--;
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
	            if(!map[x][y-1]){ //will error out here if the block in front is out of bounds
	                canGoLeft = true;
	            }else canGoLeft = false;
	            break;
	        case 2: //right
	            if(!map[x+1][y]){ //will error out here if the block in front is out of bounds
	                canGoLeft = true;
	            }else canGoLeft = false;
	            break;
	        case 3: //down
	            if(!map[x][y+1]){ //will error out here if the block in front is out of bounds
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
	            if(!map[x][y-1]){ //will error out here if the block in front is out of bounds
	                canGoRight = true;
	            }else canGoRight = false;
	            break;
	        case 2: //right
	            if(!map[x+1][y]){ //will error out here if the block in front is out of bounds
	                canGoRight = true;
	            }else canGoRight = false;
	            break;
	        case 3: //down
	            if(!map[x][y+1]){ //will error out here if the block in front is out of bounds
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
	                if(!map[x][y-1]){ //will error out here if the block in front is out of bounds
	                    map[x][y-1] = true;
	                    y--;
	                }
	                return;
	            case 2: //right
	                if(!map[x+1][y]){ //will error out here if the block in front is out of bounds
	                    map[x+1][y] = true;
	                    x++;
	                }
	                return;
	            case 3: //down
	                if(!map[x][y+1]){ //will error out here if the block in front is out of bounds
	                    map[x][y+1] = true;
	                    y++;
	                }
	                return;
	            case 4: //left
	                if(!map[x-1][y]){ //will error out here if the block in front is out of bounds
	                    map[x-1][y] = true;
	                    x--;
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
	                if(!map[x][y-1]){ //will error out here if the block in front is out of bounds
	                    map[x][y-1] = true;
	                    y--;
	                }
	                return;
	            case 2: //right
	                if(!map[x+1][y]){ //will error out here if the block in front is out of bounds
	                    map[x+1][y] = true;
	                    x++;
	                }
	                return;
	            case 3: //down
	                if(!map[x][y+1]){ //will error out here if the block in front is out of bounds
	                    map[x][y+1] = true;
	                    y++;
	                }
	                return;
	            case 4: //left
	                if(!map[x-1][y]){ //will error out here if the block in front is out of bounds
	                    map[x-1][y] = true;
	                    x--;
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
	                    if(!map[x][y-1]){ //will error out here if the block in front is out of bounds
	                        map[x][y-1] = true;
	                        y--;
	                    }
	                    return;
	                case 2: //right
	                    if(!map[x+1][y]){ //will error out here if the block in front is out of bounds
	                        map[x+1][y] = true;
	                        x++;
	                    }
	                    return;
	                case 3: //down
	                    if(!map[x][y+1]){ //will error out here if the block in front is out of bounds
	                        map[x][y+1] = true;
	                        y++;
	                    }
	                    return;
	                case 4: //left
	                    if(!map[x-1][y]){ //will error out here if the block in front is out of bounds
	                        map[x-1][y] = true;
	                        x--;
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
	                    if(!map[x][y-1]){ //will error out here if the block in front is out of bounds
	                        map[x][y-1] = true;
	                        y--;
	                    }
	                    return;
	                case 2: //right
	                    if(!map[x+1][y]){ //will error out here if the block in front is out of bounds
	                        map[x+1][y] = true;
	                        x++;
	                    }
	                    return;
	                case 3: //down
	                    if(!map[x][y+1]){ //will error out here if the block in front is out of bounds
	                        map[x][y+1] = true;
	                        y++;
	                    }
	                    return;
	                case 4: //left
	                    if(!map[x-1][y]){ //will error out here if the block in front is out of bounds
	                        map[x-1][y] = true;
	                        x--;
	                    }
	                    return;
	                }
	            }catch(Exception e){

	            }
	        }
	    }
	}
}
