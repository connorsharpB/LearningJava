import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class animalClass{

	int x;
	int y;
	int weaponryReach;
	int weapontype;
	int kill;
	int wType;
	boolean alive = true;
	

	public animalClass(int x, int y, int killingProbabaility, int wType){


		this.x = x;
		this.y = y;
		this.kill = killingProbabaility;
		this.wType = wType;
		//myPoint.move(xpos, ypos);
	}

	public void printPoint(){

		System.out.println("x: "+this.x +" y: "+this.y);
	}


	public ArrayList<animalClass> getEatNeighbourhood(int sizeX, int sizeY){

		ArrayList<animalClass> neighbours = new ArrayList<animalClass>();

		//make a short array to move 1 either side
		ArrayList<Integer> intArray = new ArrayList<Integer>();
		for(Integer i = -1; i<2; i++){

			intArray.add(i);
		}

		for(int moveUp : intArray){

			if ((this.x + moveUp) > 0 && (this.x + moveUp) < sizeX){

				for(int moveAcross : intArray){

					if ((this.y + moveAcross) > 0 && (this.y + moveAcross) < sizeY){
						animalClass neighbourPoint = new animalClass( (this.x + moveUp), (this.y + moveAcross),this.kill, this.wType);
						neighbours.add(neighbourPoint);
					}
				}
			}
		}

		
		
		
		return neighbours;

	}

	public ArrayList<animalClass> getBreedNeighbourhood(int sizeX, int sizeY){

		ArrayList<animalClass> neighbours = new ArrayList<animalClass>();

		//make a short array to move 1 either side
		ArrayList<Integer> intArray = new ArrayList<Integer>();
		intArray.add(-1);
		intArray.add(1);
		intArray.add(0);

		for(int moveUp : intArray){

			if ((this.x + moveUp) > 0 && (this.x + moveUp) < sizeX){

				for(int moveAcross : intArray){

					if ((this.y + moveAcross) > 0 && (this.y + moveAcross) < sizeY){
						animalClass neighbourPoint = new animalClass( (this.x + moveUp), (this.y + moveAcross), this.kill, this.wType);
						neighbours.add(neighbourPoint);
					}
				}
			}
		}

		
		
		
		return neighbours;

	}

	public boolean comparePoints(animalClass point2){
		if((this.x == point2.x) && (this.y == point2.y)){
			return true;
		}else{
			return false;
		}

	}

		public boolean compareKillPoints(animalClass point2){
		if(((this.x == point2.x) && (this.y == point2.y)) && (this.wType != point2.wType)){
			return true;
		}else{
			return false;
		}

	}



}