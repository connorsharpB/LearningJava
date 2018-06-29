import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class conwayBoard{
	// Make size of array and number of animal at initial t=0
	Integer sizeX;
	Integer sizeY;
	Integer animalNumber;

	//List of all the animal positions
	ArrayList<animalClass> animalList = new ArrayList<animalClass>();

	public conwayBoard(Integer sX, Integer sY, Integer aN, ArrayList<Integer> weapons){

		sizeX = sX;
		sizeY = sY;
		animalNumber = aN;
		Random rn = new Random();
		//Randomly place all animals
		for (int i =0; i<animalNumber;i++){


			int randomNumX = rn.nextInt(sizeX)+1;
			int randomNumY = rn.nextInt(sizeY)+1;
			int weaponry = weapons.get(i);

			animalClass myPoint = new animalClass(randomNumX, randomNumY, weaponry, i);
			animalList.add(myPoint);

		}

	}
	//print animal positions
	public void printList(){

		System.out.println(animalList);
	}

	public Integer getX(){

		return sizeX;
	}
	public  Integer getY(){
		return sizeY;
	}

	//ditribution decides which animals breed or not
	public void breedAnimals(){
		ArrayList<animalClass> newBreeds = new ArrayList<animalClass>();
		Random rn = new Random();
		for (animalClass animal : animalList){
			int breedProb = rn.nextInt(10)+1;
			int breedThresh;
			if (animal.wType == 1){
				breedThresh = 7;
			}else{
				breedThresh = 2;
			}
			if( breedProb > breedThresh){
				//get neighbourhood for where new animals can be placed
				ArrayList<animalClass> neighbourhood = new ArrayList<animalClass>();
				ArrayList<animalClass> usableNeighbourhood = new ArrayList<animalClass>();
				neighbourhood = animal.getBreedNeighbourhood(50,50);

				//Make sure that animals can't occupy the same space
				for (animalClass point : neighbourhood){
					boolean freeSpace= false;
					for(animalClass animalpoint : animalList){

						if (point.comparePoints(animalpoint)){
							freeSpace=true;
						}
					}
					if(freeSpace==false){
						usableNeighbourhood.add(point);

					}

				}

				if (usableNeighbourhood.size() > 0){
					//pick random square for bornHere
					animalClass bornHere = usableNeighbourhood.get(0);


					int rnd = new Random().nextInt(usableNeighbourhood.size());
					bornHere = usableNeighbourhood.get(rnd);


					animalClass myPoint = new animalClass(bornHere.x, bornHere.y, bornHere.kill, bornHere.wType);
					newBreeds.add(myPoint);
				}



			}

		}
		//Add new animals to animalList
		animalList.addAll(newBreeds);
		//System.out.print("After breeding"+animalList.size());


	}

	public ArrayList<animalClass> killBoard(){
		ArrayList<animalClass> savedAnimals = new ArrayList<animalClass>();
		ArrayList<animalClass> deadAnimals  = new ArrayList<animalClass>();
		Random rn = new Random();

		for (animalClass animal : animalList){
			int killChance =0;

			if (animal.alive){
				ArrayList<animalClass> neighbourhood = new ArrayList<animalClass>();
				ArrayList<animalClass> usableNeighbourhood = new ArrayList<animalClass>();
				neighbourhood = animal.getBreedNeighbourhood(50,50);
				while (killChance < 30){
					int killProb = rn.nextInt(100)+1;
					killChance+=1;
					if( killProb <= animal.kill){

						//get neighbourhood for where new animals can be placed

						outerloop:
						//Make sure that animals can't occupy the same space
						for (animalClass point : neighbourhood){
							for(animalClass animalpoint : animalList){
								if (point.compareKillPoints(animalpoint)){
									animalpoint.alive = false;

									break outerloop;
								}
							}

						}
					}
				}
			}
		}
		for (animalClass dorAanimal : animalList){

			if(dorAanimal.alive){
				savedAnimals.add(dorAanimal);
			}else{
				deadAnimals.add(dorAanimal);
			}

		}

		animalList = savedAnimals;
		return deadAnimals;

	}




	public void animalsEat(foodBoard cornBoard){
		ArrayList<animalClass> aliveAnimals = new ArrayList<animalClass>();
		for (animalClass animal : animalList){
			ArrayList<animalClass> neighbourhood = new ArrayList<animalClass>();
			//animal.printPoint();
			neighbourhood = animal.getEatNeighbourhood(50,50);

			int eaten = 0;


			//Find the amount of corn about to stop a loop
			int sumofCorn=0;
			for(animalClass point: neighbourhood){
				sumofCorn += cornBoard.getValue(point.x, point.y);
			}

			animalClass eatHere;
			for(animalClass point: neighbourhood){


				while( eaten <= 0 && sumofCorn > 0){
					int rnd = new Random().nextInt(neighbourhood.size());
					eatHere = neighbourhood.get(rnd);
					if (cornBoard.getValue(eatHere.x,eatHere.y) > 0){
						cornBoard.changeBoard(eatHere.x, eatHere.y, -1);
						eaten+=1;
						sumofCorn-=1;

					}
				}

			}

			if(eaten >= 0){
				aliveAnimals.add(animal);
				//animal.printPoint();
			}
		animalList = aliveAnimals;

		}
		//System.out.println("Alive animals" +animalList.size());


	}

	public void printAnimalList(){

		System.out.println("Number of animals alive:" + animalList.size());
	}

	public void saveImage(int imageNo, ArrayList<animalClass> deadList){

		int[][] multD;
		int[][] typeD;
		multD = new int[sizeX][sizeY];
		typeD = new int[sizeX][sizeY];
		//Color newColor = new Color(0,0,0);

		for (int i = 0; i<multD.length; i++) {
    		for (int j = 0; j<multD[i].length; j++) {
        		multD[i][j] = 0 ;
        		typeD[i][j] = -1;
    		}
		}

		for(animalClass animal : animalList){

			multD[animal.x][animal.y] = 255;
			typeD[animal.x][animal.y] = animal.wType;

		}

		for(animalClass deadAnimal : deadList){

			typeD[deadAnimal.x][deadAnimal.y] = 2;
		}


		try {
		    BufferedImage image = new  BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		    for(int i = 0; i<multD.length; i++){
		        for(int c = 0; c<multD[i].length; c++) {
		            int a = multD[i][c];



		            if( typeD[i][c] == -1){

		            	Color newColor = new Color(255,255,255);
		            	image.setRGB(i, c, newColor.getRGB());

		            }else if (typeD[i][c] == 0) {
		            	Color newColor = new Color(0,a,0);
		            	image.setRGB(i, c, newColor.getRGB());
		            }else if (typeD[i][c] == 1){

		            	Color newColor = new Color(0,0,a);
		            	image.setRGB(i, c, newColor.getRGB());
		            }else{
		            	Color newColor = new Color(255,0,0);
		            	image.setRGB(i, c, newColor.getRGB());
		            }


		    		//Color newColor = new Color(0,0,0);

        		}
    		}
		    ImageIO.write(image, "jpg", new File("animalImages/GrayScale" + imageNo+".jpg"));
		}

		catch(Exception e) {}
	}

	public ArrayList<Integer> speciesCount(){
		int type1=0;
		int type2=0;
		ArrayList<Integer> specList = new ArrayList<Integer>();
		for(animalClass animal : animalList){

			if( animal.wType == 0){
				type1+=1;
			}else{
				type2+=1;
			}
		}
		specList.add(type1);
		specList.add(type2);
		return specList;

	}

	public static void main(String[] args){
		//make animal list and board size
		int animalNo = 3;
		ArrayList<Integer> weaponry = new ArrayList<Integer>();
		weaponry.add(0);
		weaponry.add(90);
		weaponry.add(50);
		conwayBoard animalBoard = new conwayBoard(50, 50, animalNo, weaponry);
		//animalBoard.printList();

		//make  food Board size based on animal board
		foodBoard cornBoard = new foodBoard(animalBoard.getX(), animalBoard.getY());


		// animals breed based on probability
		animalBoard.breedAnimals();

		//animals eat within their neighbourhood
		animalBoard.animalsEat(cornBoard);
		//cornBoard.printBoard();
		cornBoard.regrowBoard();
		animalBoard.animalsEat(cornBoard);
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		ArrayList<Integer> tempList1 = new ArrayList<Integer>();
		ArrayList<Integer> tempList2 = new ArrayList<Integer>();
		for(int i=0;i<=80;i++){
			animalBoard.animalsEat(cornBoard);
			animalBoard.breedAnimals();
			cornBoard.regrowBoard();




			ArrayList<animalClass> deadAnimals = new ArrayList<animalClass>();
			deadAnimals= animalBoard.killBoard();
			animalBoard.saveImage(i, deadAnimals);
			tempList = animalBoard.speciesCount();
			tempList1.add(tempList.get(0));
			tempList2.add(tempList.get(1));

			System.out.println("Iteration: "+i);



		}


		System.out.println(tempList1);
		System.out.println(tempList2);

	}

}
