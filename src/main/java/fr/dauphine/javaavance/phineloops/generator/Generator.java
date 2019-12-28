package fr.dauphine.javaavance.phineloops.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fr.dauphine.javaavance.phineloops.model.EmptyShape;
import fr.dauphine.javaavance.phineloops.model.Game;
import fr.dauphine.javaavance.phineloops.model.IShape;
import fr.dauphine.javaavance.phineloops.model.LShape;
import fr.dauphine.javaavance.phineloops.model.QShape;
import fr.dauphine.javaavance.phineloops.model.Shape;
import fr.dauphine.javaavance.phineloops.model.TShape;
import fr.dauphine.javaavance.phineloops.model.XShape;

public class Generator {
	
	private Game game;
	private static int NORTH = 0;
	private static int EAST = 1;
	private static int SOUTH = 2;
	private static int WEST = 3;
	private int node = 0;
	private int road = 0;
	private int maxCc=0;

	public Generator(Game game) {
		this.game = game;
		this.maxCc = game.getMaxCC();
	}

	/**
	 * 	Generate a random set of shapes that form a solution 
	 */
	public void generateSolution() {
		int h = game.getHeight();
		int w = game.getWidth();
		Shape[][] board = game.getBoard();
		// Generation of the set of Legal Shapes (Should be member of game ?)
		Shape topLeftCornerLegalShapes[] = generateTopLeftCornerLegalShapes();
		Shape topBorderLegalShapes[] = generateTopBorderLegalShapes();
		Shape topRightCornerLegalShapes[] = generateTopRightCornerLegalShapes();
		Shape leftBorderLegalShapes[] = generateLeftBorderLegalShapes();
		Shape bottomLeftCornerLegalShapes[] = generateBottomLeftCornerLegalShapes();
		Shape bottomBorderLegalShapes[] = generateBottomBorderLegalShapes();
		Shape bottomRightCornerLegalShapes[] = generateBottomRightCornerLegalShapes();
		Shape rightBorderLegalShapes[] = generateRightBorderLegalShapes();
		List<Shape> allShape = generateAllShapes();
		
		Random rand = new Random();

		//Grid Traversal putting each correct shape regarding the legal and feasible shapes with respect to the previous ones
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {

				if (i == 0 && j == 0) // Top Left Corner
				{
					int randomIndex = rand.nextInt(topLeftCornerLegalShapes.length);
					board[i][j] = topLeftCornerLegalShapes[randomIndex];
				}

				else if (i == 0 && j < w - 1) // Top Border
				{
					if (board[i][j - 1].getConnections()[EAST]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : topBorderLegalShapes) {
							if (shape.getConnections()[WEST])
								feasibleShapes.add(shape);
						}
						// random parmi les shapes qui ont connection WEST
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : topBorderLegalShapes) {
							if (!shape.getConnections()[WEST])
								feasibleShapes.add(shape);
						}
						// random parmi les shapes qui n'ont pas connection WEST
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					}
				}
				else if (i == 0 && j == (w - 1)) // Top Right Corner
				{
					if (board[i][j - 1].getConnections()[EAST]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : topRightCornerLegalShapes) {
							if (shape.getConnections()[WEST])
								feasibleShapes.add(shape);
						}
						// random parmi les shapes qui ont connection WEST
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : topRightCornerLegalShapes) {
							if (!shape.getConnections()[WEST])
								feasibleShapes.add(shape);
						}
						// random parmi les shapes qui n'ont pas connection WEST
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					}
				} 
				else if (i < h - 1 && j == 0) // Left Border
				{
					if (board[i - 1][j].getConnections()[SOUTH]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : leftBorderLegalShapes) {
							if (shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						// random parmi les shapes qui ont connection WEST
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : leftBorderLegalShapes) {
							if (!shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						// random parmi les shapes qui n'ont pas connection WEST
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					}
				} 
				else if (i < h - 1 && j == w - 1) // Right Border
				{

					if (board[i][j - 1].getConnections()[EAST] && board[i - 1][j].getConnections()[SOUTH]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : rightBorderLegalShapes) {
							if (shape.getConnections()[WEST] && shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else if (board[i][j - 1].getConnections()[EAST] && !board[i - 1][j].getConnections()[SOUTH]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : rightBorderLegalShapes) {
							if (shape.getConnections()[WEST] && !shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else if (!board[i][j - 1].getConnections()[EAST] && board[i - 1][j].getConnections()[SOUTH]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : rightBorderLegalShapes) {
							if (!shape.getConnections()[WEST] && shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : rightBorderLegalShapes) {
							if (!shape.getConnections()[WEST] && !shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					}
				} 
				else if (i == h - 1 && j == 0) // Bottom Left Corner
				{
					if (board[i - 1][j].getConnections()[SOUTH]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : bottomLeftCornerLegalShapes) {
							if (shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						// random parmi les shapes qui ont connection WEST
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : bottomLeftCornerLegalShapes) {
							if (!shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						// random parmi les shapes qui n'ont pas connection WEST
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					}
				} 
				else if (i == h - 1 && j < w - 1) // Bottom Border
				{
					if (board[i][j - 1].getConnections()[EAST] && board[i - 1][j].getConnections()[SOUTH]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : bottomBorderLegalShapes) {
							if (shape.getConnections()[WEST] && shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else if (board[i][j - 1].getConnections()[EAST] && !board[i - 1][j].getConnections()[SOUTH]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : bottomBorderLegalShapes) {
							if (shape.getConnections()[WEST] && !shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else if (!board[i][j - 1].getConnections()[EAST] && board[i - 1][j].getConnections()[SOUTH]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : bottomBorderLegalShapes) {
							if (!shape.getConnections()[WEST] && shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : bottomBorderLegalShapes) {
							if (!shape.getConnections()[WEST] && !shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					}
				} 
				else if (i == h - 1 && j == w - 1) // Bottom Right Corner
				{
					if (board[i][j - 1].getConnections()[EAST] && board[i - 1][j].getConnections()[SOUTH]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : bottomRightCornerLegalShapes) {
							if (shape.getConnections()[WEST] && shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else if (board[i][j - 1].getConnections()[EAST] && !board[i - 1][j].getConnections()[SOUTH]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : bottomRightCornerLegalShapes) {
							if (shape.getConnections()[WEST] && !shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else if (!board[i][j - 1].getConnections()[EAST] && board[i - 1][j].getConnections()[SOUTH]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : bottomRightCornerLegalShapes) {
							if (!shape.getConnections()[WEST] && shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : bottomRightCornerLegalShapes) {
							if (!shape.getConnections()[WEST] && !shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					}
				} 
				else // Rest of cases
				{
					if (board[i][j - 1].getConnections()[EAST] && board[i - 1][j].getConnections()[SOUTH]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : allShape) {
							if (shape.getConnections()[WEST] && shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else if (board[i][j - 1].getConnections()[EAST] && !board[i - 1][j].getConnections()[SOUTH]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : allShape) {
							if (shape.getConnections()[WEST] && !shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else if (!board[i][j - 1].getConnections()[EAST] && board[i - 1][j].getConnections()[SOUTH]) {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : allShape) {
							if (!shape.getConnections()[WEST] && shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					} else {
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for (Shape shape : allShape) {
							if (!shape.getConnections()[WEST] && !shape.getConnections()[NORTH])
								feasibleShapes.add(shape);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						board[i][j] = feasibleShapes.get(randomIndex);
					}
				}
			}
		}
		System.out.println(game);
	}

	
	
	
	
	/**
	 * 	Shuffle the shapes of the board given in parameters to a random position 
	 * @param game
	 */
	public void shuffleGame(Game game) {
		Random rand = new Random();
		for (Shape[] shapes : game.getBoard()) {
			for (Shape shape : shapes) {
				for (int i = 0; i < rand.nextInt(4); i++)
					shape.rotate();
			}
		}
	}
	
	/**
	 *  Generate a solvable board with exactly nbcc connected components 
	 * @param nbcc
	 */
	public void generateSolution(int nbcc) 
	{
		Random rand= new Random();
		int h = game.getHeight();
		int w = game.getWidth();
		//We define a random box of the grid in which the connected component will start putting his shapes 
		int departi=rand.nextInt(h);
		int departj=rand.nextInt(w);
		int finParcours=(int)((float)(h*w)/(float)(nbcc))-2; //We give each connected component an assigned number of boxes that they can put their shapes in
		Shape[][] board = game.getBoard();
		// Generation of the set of Legal Shapes (Should be member of game ?)
		Shape topLeftCornerLegalShapes[] = generateTopLeftCornerLegalShapes();
		Shape topBorderLegalShapes[] = generateTopBorderLegalShapes();
		Shape topRightCornerLegalShapes[] = generateTopRightCornerLegalShapes();
		Shape leftBorderLegalShapes[] = generateLeftBorderLegalShapes();
		Shape bottomLeftCornerLegalShapes[] = generateBottomLeftCornerLegalShapes();
		Shape bottomBorderLegalShapes[] = generateBottomBorderLegalShapes();
		Shape bottomRightCornerLegalShapes[] = generateBottomRightCornerLegalShapes();
		Shape rightBorderLegalShapes[] = generateRightBorderLegalShapes();
		List<Shape> allShape = generateAllShapes();
		ArrayList<Shape> toPutShapes = new ArrayList<Shape>();
		ArrayList<Shape> connectedComponent = new ArrayList<Shape>();
		
		//We initialize the boxes with emptyshapes first since it's the shape we will favour
		for (int i=0;i<h;i++)
		{
			for(int j=0;j<w;j++)
			{
				board[i][j]=new EmptyShape(0,i,j);
			}
		}
		
		//Imagine if you get a grid of 4x4 ...
		if(finParcours==2)//Quasi-Déterministe forcément un qshape vers le bas ou vers la droite en première case
		{
			
		}

		//We put the first piece on the board depending on the position
		if (departi==0 && departj==0)
		{
			int randomIndex = rand.nextInt(topLeftCornerLegalShapes.length);
			Shape placedShape = topLeftCornerLegalShapes[randomIndex];
			placedShape.setI(departi);
			placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
			board[departi][departj] = placedShape;
			for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
			{
				neighbour.setReservedBy(board[departi][departj]);
					toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				/*On met une feasible shapes de left corner 
				board[departi][departj]=rand.feasibleshapes */
			}
			else if (departi==0 && departj<w-1)
			{
				int randomIndex = rand.nextInt(topLeftCornerLegalShapes.length);
				Shape placedShape = topBorderLegalShapes[randomIndex];
				placedShape.setI(departi);
				placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi][departj] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
				{
					neighbour.setReservedBy(board[departi][departj]);
					toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				//On met une feasible shapes de top border 
			}
			else if (departi == 0 && departj == (w - 1))
			{
				int randomIndex = rand.nextInt(topLeftCornerLegalShapes.length);
				Shape placedShape = topRightCornerLegalShapes[randomIndex];
				placedShape.setI(departi);
				placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi][departj] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
				{
					neighbour.setReservedBy(board[departi][departj]);
					toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				//On met une feasible shapes de top border 
			}
			else if (departi<(h-1) && departj==0)
			{
				int randomIndex = rand.nextInt(topLeftCornerLegalShapes.length);
				Shape placedShape = leftBorderLegalShapes[randomIndex];
				placedShape.setI(departi);
				placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi][departj] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
				{
					neighbour.setReservedBy(board[departi][departj]);
					toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				//On met une feasible shapes de top border 
			}
			else if (departi<(h-1) && departj==(w-1))
			{
				int randomIndex = rand.nextInt(topLeftCornerLegalShapes.length);
				Shape placedShape = rightBorderLegalShapes[randomIndex];
				placedShape.setI(departi);
				placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi][departj] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
				{
					neighbour.setReservedBy(board[departi][departj]);
					toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				//On met une feasible shapes de top border 
			}
			else if (departi==h-1 && departj==0)
			{
				int randomIndex = rand.nextInt(topLeftCornerLegalShapes.length);
				Shape placedShape = bottomLeftCornerLegalShapes[randomIndex];
				placedShape.setI(departi);
				placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi][departj] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
				{
					neighbour.setReservedBy(board[departi][departj]);
					toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				//On met une feasible shapes de top border 
			}
			else if (departi==h-1 && departj<(w-1))
			{
				int randomIndex = rand.nextInt(topLeftCornerLegalShapes.length);
				Shape placedShape = bottomBorderLegalShapes[randomIndex];
				placedShape.setI(departi);
				placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi][departj] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
				{
					neighbour.setReservedBy(board[departi][departj]);
					toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				//On met une feasible shapes de top border 
			}
			else if (departi==h-1 && departj==(w-1))
			{
				int randomIndex = rand.nextInt(topLeftCornerLegalShapes.length);
				Shape placedShape = bottomRightCornerLegalShapes[randomIndex];
				placedShape.setI(departi);
				placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi][departj] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
				{
					neighbour.setReservedBy(board[departi][departj]);
					toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				//On met n importe quelle feasible shapes 
			}
			else 
			{
				int randomIndex = rand.nextInt(allShape.size());
				Shape placedShape = allShape.get(randomIndex); //Mettre tout en tableau 
				placedShape.setI(departi);
				placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
					board[departi][departj] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
				{
					neighbour.setReservedBy(board[departi][departj]);
					toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
			}
			connectedComponent.add(board[departi][departj]);
			/*
			 * System.out.println(board[departi][departj].getSymbol()+" "+board[departi][
			 * departj].getI()+" "+board[departi][departj].getJ()); for(Shape sh :
			 * toPutShapes) { System.out.println(sh.getI()+" "+sh.getJ()); }
			 */
			
			
			
//******************************************************* TRAVERSAL ******************************************************************			
			

		for(int i=0;i<finParcours && toPutShapes.size()!=0 && i<(finParcours-toPutShapes.size());i++)
		{
			//We have to regenerate the legal values because other wise they could overlap
			int nextPlacedShapeIndex = rand.nextInt(toPutShapes.size()); // -1 ? 
			Shape nextPlacedShape = toPutShapes.get(nextPlacedShapeIndex);
			toPutShapes.remove(nextPlacedShapeIndex);
			departi=nextPlacedShape.getI();
			departj=nextPlacedShape.getJ();
					//We put the first piece on the board depending on the position
			if (departi==0 && departj==0)
					{
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for(Shape sh:generateTopLeftCornerLegalShapes())
						{
							int token=0;
							sh.setI(departi);
							sh.setJ(departj);
							for(Shape reservation : nextPlacedShape.getReservedBy() )
							{
								if(game.areShapesConnected(reservation, sh) && !game.isObstructed(sh,board[departi][departj].getReservedBy())) //I need to know who has put the shape as his neighbour in the board 
									token++;
							}
							if(token==nextPlacedShape.getReservedBy().size()) feasibleShapes.add(sh);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						Shape placedShape = feasibleShapes.get(randomIndex);
						placedShape.setI(departi);
						placedShape.setJ(departj);//Car On ne peut pas connaitre leurs position à l'avance mais inutile vu que déjà set dans la boucle des sh ... 
						board[departi][departj] = placedShape;
						for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
						{
							neighbour.setReservedBy(board[departi][departj]);
							if(!toPutShapes.contains(neighbour))
								toPutShapes.add(neighbour); // We add the position that we will have to put a piece in to close the connected component
						}
						/*On met une feasible shapes de left corner 
						board[departi][departj]=rand.feasibleshapes */
					}
					else if (departi==0 && departj<w-1)
					{
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						for(Shape sh:generateTopBorderLegalShapes())
						{
							int token=0;
							sh.setI(departi);
							sh.setJ(departj);
							for(Shape reservation : nextPlacedShape.getReservedBy() )
							{
								if(game.areShapesConnected(reservation, sh) && !game.isObstructed(sh,board[departi][departj].getReservedBy())) //I need to know who has put the shape as his neighbour in the board 
									token++;
							}
							if(token==nextPlacedShape.getReservedBy().size()) feasibleShapes.add(sh);
						}
						int randomIndex = rand.nextInt(feasibleShapes.size());
						Shape placedShape = feasibleShapes.get(randomIndex);
						placedShape.setI(departi);
						placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
						board[departi][departj] = placedShape;
						for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
						{
							neighbour.setReservedBy(board[departi][departj]);
							if(!toPutShapes.contains(neighbour))
								toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
						}
						//On met une feasible shapes de top border 
					}
					else if (departi == 0 && departj == (w - 1))
					{
							ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
							for(Shape sh:generateTopRightCornerLegalShapes())
							{
								int token=0;
								sh.setI(departi);
								sh.setJ(departj);
								for(Shape reservation : nextPlacedShape.getReservedBy() )
								{
									if(game.areShapesConnected(reservation, sh) && !game.isObstructed(sh,board[departi][departj].getReservedBy())) //I need to know who has put the shape as his neighbour in the board 
										token++;
								}
								if(token==nextPlacedShape.getReservedBy().size()) feasibleShapes.add(sh);
							}
							int randomIndex = rand.nextInt(feasibleShapes.size());
							Shape placedShape = feasibleShapes.get(randomIndex);
							placedShape.setI(departi);
							placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
							board[departi][departj] = placedShape;
							for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
							{
								if(!toPutShapes.contains(neighbour))
									toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
							}
							//On met une feasible shapes de top border 
						}
						else if (departi<(h-1) && departj==0)
						{
							ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
							for(Shape sh:generateLeftBorderLegalShapes())
							{
								int token=0;
								sh.setI(departi);
								sh.setJ(departj);
								for(Shape reservation : nextPlacedShape.getReservedBy() )
								{
									if(game.areShapesConnected(reservation, sh) && !game.isObstructed(sh,board[departi][departj].getReservedBy())) //I need to know who has put the shape as his neighbour in the board 
										token++;
								}
								if(token==nextPlacedShape.getReservedBy().size()) feasibleShapes.add(sh);
							}
							int randomIndex = rand.nextInt(feasibleShapes.size());
							Shape placedShape = feasibleShapes.get(randomIndex);
							placedShape.setI(departi);
							placedShape.setJ(departj);//On ne peut pas connaitre leur position à l'avance ... 
							board[departi][departj] = placedShape;
							for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
							{
								neighbour.setReservedBy(board[departi][departj]);
								if(!toPutShapes.contains(neighbour))
									toPutShapes.add(neighbour);// We add the connection to close to an arraylist 
							}
							//On met une feasible shapes de top border 
						}
						else if (departi<(h-1) && departj==(w-1))
						{
							ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
							for(Shape sh:generateRightBorderLegalShapes())
							{
								int token=0;
								sh.setI(departi);
								sh.setJ(departj);
								for(Shape reservation : nextPlacedShape.getReservedBy() )
								{
									if(game.areShapesConnected(reservation, sh) && !game.isObstructed(sh,board[departi][departj].getReservedBy())) //I need to know who has put the shape as his neighbour in the board 
										token++;
								}
								if(token==nextPlacedShape.getReservedBy().size()) feasibleShapes.add(sh);
							}
							int randomIndex = rand.nextInt(feasibleShapes.size());
							Shape placedShape = feasibleShapes.get(randomIndex);
							placedShape.setI(departi);
							placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
							board[departi][departj] = placedShape;
							for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
							{
								neighbour.setReservedBy(board[departi][departj]);
								if(!toPutShapes.contains(neighbour))
									toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
							}
							//On met une feasible shapes de top border 
						}
						else if (departi==h-1 && departj==0)
						{
							ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
							for(Shape sh:generateBottomLeftCornerLegalShapes())
							{
								int token=0;
								sh.setI(departi);
								sh.setJ(departj);
								for(Shape reservation : nextPlacedShape.getReservedBy() )
								{
									if(game.areShapesConnected(reservation, sh) && !game.isObstructed(sh,board[departi][departj].getReservedBy())) //I need to know who has put the shape as his neighbour in the board 
										token++;
								}
								if(token==nextPlacedShape.getReservedBy().size()) feasibleShapes.add(sh);
							}
							int randomIndex = rand.nextInt(feasibleShapes.size()); 
							Shape placedShape = feasibleShapes.get(randomIndex);
							placedShape.setI(departi);
							placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
							board[departi][departj] = placedShape;
							for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
							{
								neighbour.setReservedBy(board[departi][departj]);
								if(!toPutShapes.contains(neighbour))
									toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
							}
							//On met une feasible shapes de top border 
						}
						else if (departi==h-1 && departj<(w-1))
						{
							ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
							for(Shape sh:generateBottomBorderLegalShapes())
							{
								int token=0;
								sh.setI(departi);
								sh.setJ(departj);
								for(Shape reservation : nextPlacedShape.getReservedBy() )
								{
									if(game.areShapesConnected(reservation, sh) && !game.isObstructed(sh,board[departi][departj].getReservedBy())) //I need to know who has put the shape as his neighbour in the board 
										token++;
								}
								if(token==nextPlacedShape.getReservedBy().size()) feasibleShapes.add(sh);
							}
							int randomIndex = rand.nextInt(feasibleShapes.size());
							Shape placedShape = feasibleShapes.get(randomIndex);
							placedShape.setI(departi);
							placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
							board[departi][departj] = placedShape;
							for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
							{
								neighbour.setReservedBy(board[departi][departj]);
								if(!toPutShapes.contains(neighbour))
									toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
							}
							//On met une feasible shapes de top border 
						}
						else if (departi==h-1 && departj==(w-1))
						{
							ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
							for(Shape sh:generateBottomRightCornerLegalShapes())
							{
								int token=0;
								sh.setI(departi);
								sh.setJ(departj);
								for(Shape reservation : nextPlacedShape.getReservedBy() )
								{
									if(game.areShapesConnected(reservation, sh) && !game.isObstructed(sh,board[departi][departj].getReservedBy())) //I need to know who has put the shape as his neighbour in the board 
										token++;
								}
								if(token==nextPlacedShape.getReservedBy().size()) feasibleShapes.add(sh);
							}
							int randomIndex = rand.nextInt(feasibleShapes.size());
							Shape placedShape = feasibleShapes.get(randomIndex);
							placedShape.setI(departi);
							placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
							board[departi][departj] = placedShape;
							for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
							{
								neighbour.setReservedBy(board[departi][departj]);
								if(!toPutShapes.contains(neighbour))
									toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
							}
							//On met n importe quelle feasible shapes 
						}
						else 
						{
							ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
							for(Shape sh:generateAllShapes())
							{
								int token=0;
								sh.setI(departi);
								sh.setJ(departj);
								for(Shape reservation : nextPlacedShape.getReservedBy() )
								{
									if(game.areShapesConnected(reservation, sh) && !game.isObstructed(sh,nextPlacedShape.getReservedBy())) //I need to know who has put the shape as his neighbour in the board 
										token++;
								}
								if(token==nextPlacedShape.getReservedBy().size()) feasibleShapes.add(sh);
							}
							int randomIndex = rand.nextInt(feasibleShapes.size());
							Shape placedShape = feasibleShapes.get(randomIndex);//Mettre tout en tableau 
							placedShape.setI(departi);
							placedShape.setJ(departj);//On ne peut pas connaitre leurs position à l'avance ... 
							board[departi][departj] = placedShape;
							for (Shape neighbour:game.getToConnectNeighbors(board[departi][departj]))
							{
								neighbour.setReservedBy(board[departi][departj]);
								if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
									toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
							}
						}		
		}
		//On bouche les pièces éventuellement pas fermées
		
		connectedComponentCloser(toPutShapes,board);
		//FLUSH TOPUTSHAPES 
		toPutShapes.clear();			
		//}while(toPutShapes.size()!=0);
		//random parmis les shapes connectable avec la précédente (orientation)
				
		//Now lets create another connected component, We should find an emptyShape avalaible (Has at least 1 emptyshape neighbour then we start again by putting feasible shapes considering his surrounding (border... and already put shapes)	
		
		
		
		
		
		
		
//*******************************************************************OTHER CONNECTED COMPONENT**********************************************
		
		
		
		
		
		
		
		
		
		for(int k=0;k<nbcc-1;k++)
		{
			Shape topLeftCornerLegalShapes2[] = generateRightBorderLegalShapes();
			Shape topBorderLegalShapes2[] = generateRightBorderLegalShapes();
			Shape topRightCornerLegalShapes2[] = generateRightBorderLegalShapes();
			Shape leftBorderLegalShapes2[] = generateRightBorderLegalShapes();
			Shape bottomLeftCornerLegalShapes2[] = generateRightBorderLegalShapes();
			Shape bottomBorderLegalShapes2[] = generateRightBorderLegalShapes();
			Shape bottomRightCornerLegalShapes2[] = generateRightBorderLegalShapes();
			Shape rightBorderLegalShapes2[] = generateRightBorderLegalShapes();
			List<Shape> allShape2 = generateAllShapes();
			//We choose another emptyshapes in the board for a new entry 
			ArrayList<Shape> feasibleEntry = new ArrayList<Shape>();
			for(Shape[] shapelines:board)
			{
				for(Shape sh:shapelines)
				{
					if(sh.getType()==0 && game.hasEmptyNeighbor(sh))
						feasibleEntry.add(sh);
				}
			}
			//int resaForOthers=feasibleEntry.size();
			int newEntryIndex = rand.nextInt(feasibleEntry.size());
			Shape newEntry = feasibleEntry.get(newEntryIndex);
			//Now we get the coord for the new entry
			int departi1=newEntry.getI();
			int departj1=newEntry.getJ();
			
			//We place a random shapes for the new connected component but by paying caution to the already placed shapes
			if (departi1==0 && departj1==0)
			{
				ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
				for(Shape sh:topLeftCornerLegalShapes2)
				{
					boolean willWork=true;
					sh.setI(departi1);
					sh.setJ(departj1);
					Shape[] shneighbor = game.getConnectionNeighbors(sh);
					for(Shape nb:shneighbor)
					{
						if (nb.getType()!=0) willWork=false; //If we have a case to complete for this shape but it is already occupied (i.e not empty) ,we don't put it 
					}
					if(willWork) feasibleShapes.add(sh);
				}
				int randomIndex = rand.nextInt(feasibleShapes.size());
				Shape placedShape = feasibleShapes.get(randomIndex);
				placedShape.setI(departi1);
				placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi1][departj1] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
				{
					neighbour.setReservedBy(board[departi1][departj1]);
					if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
						toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				/*On met une feasible shapes de left corner 
				board[departi][departj]=rand.feasibleshapes */
			}
			else if (departi1==0 && departj1<w-1)
			{
				ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
				for(Shape sh:topBorderLegalShapes2)
				{
					boolean willWork=true;
					sh.setI(departi1);
					sh.setJ(departj1);
					Shape[] shneighbor = game.getConnectionNeighbors(sh);
					for(Shape nb:shneighbor)
					{
						if (nb.getType()!=0) willWork=false;
					}
					if(willWork) feasibleShapes.add(sh);
				}
				int randomIndex = rand.nextInt(feasibleShapes.size());
				Shape placedShape = feasibleShapes.get(randomIndex);
				placedShape.setI(departi1);
				placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi1][departj1] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
				{
					neighbour.setReservedBy(board[departi1][departj1]);
					if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
						toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				//On met une feasible shapes de top border 
			}
			else if (departi1 == 0 && departj1 == (w - 1))
			{
				ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
				for(Shape sh:topRightCornerLegalShapes2)
				{
					boolean willWork=true;
					sh.setI(departi1);
					sh.setJ(departj1);
					Shape[] shneighbor = game.getConnectionNeighbors(sh);
					for(Shape nb:shneighbor)
					{
						if (nb.getType()!=0) willWork=false;
					}
					if(willWork) feasibleShapes.add(sh);
				}
				int randomIndex = rand.nextInt(feasibleShapes.size());
				Shape placedShape = feasibleShapes.get(randomIndex);
				placedShape.setI(departi1);
				placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi1][departj1] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
				{
					neighbour.setReservedBy(board[departi1][departj1]);
					if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
						toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				//On met une feasible shapes de top border 
			}
			else if (departi1<(h-1) && departj1==0)
			{
				ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
				for(Shape sh:leftBorderLegalShapes2)
				{
					boolean willWork=true;
					sh.setI(departi1);
					sh.setJ(departj1);
					Shape[] shneighbor = game.getConnectionNeighbors(sh);
					for(Shape nb:shneighbor)
					{
						if (nb.getType()!=0) willWork=false;
					}
					if(willWork) feasibleShapes.add(sh);
				}
				int randomIndex = rand.nextInt(feasibleShapes.size());
				Shape placedShape = feasibleShapes.get(randomIndex);
				placedShape.setI(departi1);
				placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi1][departj1] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
				{
					neighbour.setReservedBy(board[departi1][departj1]);
					if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
						toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				//On met une feasible shapes de top border 
			}
			else if (departi1<(h-1) && departj1==(w-1))
			{
				ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
				for(Shape sh:rightBorderLegalShapes2)
				{
					boolean willWork=true;
					sh.setI(departi1);
					sh.setJ(departj1);
					Shape[] shneighbor = game.getConnectionNeighbors(sh);
					for(Shape nb:shneighbor)
					{
						if (nb.getType()!=0) willWork=false;
					}
					if(willWork) feasibleShapes.add(sh);
				}
				int randomIndex = rand.nextInt(feasibleShapes.size());
				Shape placedShape = feasibleShapes.get(randomIndex);
				placedShape.setI(departi1);
				placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi1][departj1] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
				{
					neighbour.setReservedBy(board[departi1][departj1]);
					toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				//On met une feasible shapes de top border 
			}
			else if (departi1==h-1 && departj1==0)
			{
				ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
				for(Shape sh:bottomLeftCornerLegalShapes2)
				{
					boolean willWork=true;
					sh.setI(departi1);
					sh.setJ(departj1);
					Shape[] shneighbor = game.getConnectionNeighbors(sh);
					for(Shape nb:shneighbor)
					{
						if (nb.getType()!=0) willWork=false;
					}
					if(willWork) feasibleShapes.add(sh);
				}
				int randomIndex = rand.nextInt(feasibleShapes.size());
				Shape placedShape = feasibleShapes.get(randomIndex);
				placedShape.setI(departi1);
				placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi1][departj1] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
				{
					neighbour.setReservedBy(board[departi1][departj1]);
					if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
						toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				//On met une feasible shapes de top border 
			}
			else if (departi1==h-1 && departj1<(w-1))
			{
				ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
				for(Shape sh:bottomBorderLegalShapes2)
				{
					boolean willWork=true;
					sh.setI(departi1);
					sh.setJ(departj1);
					Shape[] shneighbor = game.getConnectionNeighbors(sh);
					for(Shape nb:shneighbor)
					{
						if (nb.getType()!=0) willWork=false;
					}
					if(willWork) feasibleShapes.add(sh);
				}
				int randomIndex = rand.nextInt(feasibleShapes.size());
				Shape placedShape = feasibleShapes.get(randomIndex);
				placedShape.setI(departi1);
				placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi1][departj1] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
				{
					neighbour.setReservedBy(board[departi1][departj1]);
					if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
						toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				//On met une feasible shapes de top border 
			}
			else if (departi1==h-1 && departj1==(w-1))
			{
				ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
				for(Shape sh:bottomRightCornerLegalShapes2)
				{
					boolean willWork=true;
					sh.setI(departi1);
					sh.setJ(departj1);
					Shape[] shneighbor = game.getConnectionNeighbors(sh);
					for(Shape nb:shneighbor)
					{
						if (nb.getType()!=0) willWork=false;
					}
					if(willWork) feasibleShapes.add(sh);
				}
				int randomIndex = rand.nextInt(feasibleShapes.size());
				Shape placedShape = feasibleShapes.get(randomIndex);
				placedShape.setI(departi1);
				placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi1][departj1] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
				{
					neighbour.setReservedBy(board[departi1][departj1]);
					if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
						toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
				//On met n importe quelle feasible shapes 
			}
			else 
			{
				ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
				for(Shape sh:allShape2)
				{
					boolean willWork=true;
					sh.setI(departi1);
					sh.setJ(departj1);
					Shape[] shneighbor = game.getConnectionNeighbors(sh);
					for(Shape nb:shneighbor)
					{
						if (nb.getType()!=0) willWork=false;
					}
					if(willWork) feasibleShapes.add(sh);
					//TO ADD ANOTHER CONDITION
				}
				int randomIndex = rand.nextInt(feasibleShapes.size());
				Shape placedShape = feasibleShapes.get(randomIndex);//Mettre tout en tableau 
				placedShape.setI(departi1);
				placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
				board[departi1][departj1] = placedShape;
				for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
				{
					neighbour.setReservedBy(board[departi1][departj1]);
					if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
						toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
				}
			}
			connectedComponent.add(board[departi1][departj1]);
			//System.out.println(this.game+"\n");
			/*
			 * System.out.println(board[departi][departj].getSymbol()+" "+board[departi][
			 * departj].getI()+" "+board[departi][departj].getJ()); for(Shape sh :
			 * toPutShapes) { System.out.println(sh.getI()+" "+sh.getJ()); }
			 */
			
			
			
			for(int i=0;i<finParcours && toPutShapes.size()!=0 && i<(finParcours-toPutShapes.size());i++)
			{
				int nextPlacedShapeIndex = rand.nextInt(toPutShapes.size()); // -1 ? 
				Shape nextPlacedShape = toPutShapes.get(nextPlacedShapeIndex);
				toPutShapes.remove(nextPlacedShapeIndex);
				departi1=nextPlacedShape.getI();
				departj1=nextPlacedShape.getJ();
				//We put the rest of the piece for the connected component 
				if (departi1==0 && departj1==0)
				{
					ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
					for(Shape sh:generateTopLeftCornerLegalShapes())
					{
						boolean willWork=true;
						sh.setI(departi1);
						sh.setJ(departj1);
						Shape[] shneighbor = game.getConnectionNeighbors(sh);
						for(Shape nb:shneighbor)
						{
							if (nb.getType()!=0 && !game.areShapesConnected(sh, nb)) willWork=false; //We have to be able to put a shape in his toConnectNeighbors that will close the connected COmponent => All toConnectNeghbors have to be empty 
						}
						//Il ne rentre pas dans la boucle si il y a un seul élément ... 
						for(Shape toConnectsh:nextPlacedShape.getReservedBy())
						{
							if(!(game.areShapesConnected(toConnectsh, sh))) willWork=false; //We have to put shape that can be connected to all his reservedBy neighbors 
						}
						//I need to know who has put the shape as his neighbour in the board 
						if (willWork) feasibleShapes.add(sh);
					}
					int randomIndex = rand.nextInt(feasibleShapes.size());
					Shape placedShape = feasibleShapes.get(randomIndex);
					placedShape.setI(departi1);
					placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
					board[departi1][departj1] = placedShape;
					for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
					{
						neighbour.setReservedBy(board[departi1][departj1]);
						if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
							toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
					}
					/*On met une feasible shapes de left corner 
					board[departi][departj]=rand.feasibleshapes */
				}
				else if (departi1==0 && departj1<w-1)
				{
					ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
					for(Shape sh:generateTopBorderLegalShapes())
					{
						boolean willWork=true;
						sh.setI(departi1);
						sh.setJ(departj1);
						Shape[] shneighbor = game.getConnectionNeighbors(sh);
						for(Shape nb:shneighbor)
						{
							if (nb.getType()!=0 && !game.areShapesConnected(sh, nb)) willWork=false;
						}
						//Il ne rentre pas dans la boucle si il y a un seul élément ... 
						for(Shape toConnectsh:nextPlacedShape.getReservedBy())
						{
							if(!(game.areShapesConnected(toConnectsh, sh))) willWork=false; //We have to put shape that can be connected to all his reservedBy neighbors 
						}
						//I need to know who has put the shape as his neighbour in the board 
						if (willWork) feasibleShapes.add(sh);
					}
					int randomIndex = rand.nextInt(feasibleShapes.size());
					Shape placedShape = feasibleShapes.get(randomIndex);
					placedShape.setI(departi1);
					placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
					board[departi1][departj1] = placedShape;
					for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
					{
						neighbour.setReservedBy(board[departi1][departj1]);
						if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
							toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
					}
					//On met une feasible shapes de top border 
				}
				else if (departi1 == 0 && departj1 == (w - 1))
							{
								ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
								for(Shape sh:generateTopRightCornerLegalShapes())
								{
									boolean willWork=true;
									sh.setI(departi1);
									sh.setJ(departj1);
									Shape[] shneighbor = game.getConnectionNeighbors(sh);
									for(Shape nb:shneighbor)
									{
										if (nb.getType()!=0 && !game.areShapesConnected(sh, nb)) willWork=false;
									}
									//Il ne rentre pas dans la boucle si il y a un seul élément ... 
									for(Shape toConnectsh:nextPlacedShape.getReservedBy())
									{
										if(!(game.areShapesConnected(toConnectsh, sh))) willWork=false; //We have to put shape that can be connected to all his reservedBy neighbors 
									}
									//I need to know who has put the shape as his neighbour in the board 
									if (willWork) feasibleShapes.add(sh);
								}
								int randomIndex = rand.nextInt(feasibleShapes.size());
								Shape placedShape = feasibleShapes.get(randomIndex);
								placedShape.setI(departi1);
								placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
								board[departi1][departj1] = placedShape;
								for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
								{
									neighbour.setReservedBy(board[departi1][departj1]);
									if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
										toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
								}
								//On met une feasible shapes de top border 
							}
				else if (departi1<(h-1) && departj1==0)
							{
								ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
								for(Shape sh:generateLeftBorderLegalShapes())
								{
									boolean willWork=true;
									sh.setI(departi1);
									sh.setJ(departj1);
									Shape[] shneighbor = game.getConnectionNeighbors(sh);
									for(Shape nb:shneighbor)
									{
										if (nb.getType()!=0 && !game.areShapesConnected(sh, nb)) willWork=false;
									}
									//Il ne rentre pas dans la boucle si il y a un seul élément ... 
									for(Shape toConnectsh:nextPlacedShape.getReservedBy())
									{
										if(!(game.areShapesConnected(toConnectsh, sh))) willWork=false; //We have to put shape that can be connected to all his reservedBy neighbors 
									}
									//I need to know who has put the shape as his neighbour in the board 
									if (willWork) feasibleShapes.add(sh);
								}
								int randomIndex = rand.nextInt(feasibleShapes.size());
								Shape placedShape = feasibleShapes.get(randomIndex);
								placedShape.setI(departi1);
								placedShape.setJ(departj1);//On ne peut pas connaitre leur position à l'avance ... 
								board[departi1][departj1] = placedShape;
								for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
								{
									neighbour.setReservedBy(board[departi1][departj1]);
									if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
										toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
								}
								//On met une feasible shapes de top border 
							}
				else if (departi1<(h-1) && departj1==(w-1))
							{
								ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
								for(Shape sh:generateRightBorderLegalShapes())
								{
									boolean willWork=true;
									sh.setI(departi1);
									sh.setJ(departj1);
									Shape[] shneighbor = game.getConnectionNeighbors(sh);
									for(Shape nb:shneighbor)
									{
										if (nb.getType()!=0 && !game.areShapesConnected(sh, nb)) willWork=false;
									}
									//Il ne rentre pas dans la boucle si il y a un seul élément ... 
									for(Shape toConnectsh:nextPlacedShape.getReservedBy())
									{
										if(!(game.areShapesConnected(toConnectsh, sh))) willWork=false; //We have to put shape that can be connected to all his reservedBy neighbors 
									}
									//I need to know who has put the shape as his neighbour in the board 
									if (willWork) feasibleShapes.add(sh);
								}
								int randomIndex = rand.nextInt(feasibleShapes.size());
								Shape placedShape = feasibleShapes.get(randomIndex);
								placedShape.setI(departi1);
								placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
								board[departi1][departj1] = placedShape;
								for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
								{
									neighbour.setReservedBy(board[departi1][departj1]);
									if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
										toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
								}
								//On met une feasible shapes de top border 
							}
				else if (departi1==h-1 && departj1==0)
							{
								ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
								for(Shape sh:generateBottomLeftCornerLegalShapes())
								{
									boolean willWork=true;
									sh.setI(departi1);
									sh.setJ(departj1);
									Shape[] shneighbor = game.getConnectionNeighbors(sh);
									for(Shape nb:shneighbor)
									{
										if (nb.getType()!=0 && !game.areShapesConnected(sh, nb)) willWork=false;
									}
									//Il ne rentre pas dans la boucle si il y a un seul élément ... 
									for(Shape toConnectsh:nextPlacedShape.getReservedBy())
									{
										if(!(game.areShapesConnected(toConnectsh, sh))) willWork=false; //We have to put shape that can be connected to all his reservedBy neighbors 
									}
									//I need to know who has put the shape as his neighbour in the board 
									if (willWork) feasibleShapes.add(sh);
								}
								int randomIndex = rand.nextInt(feasibleShapes.size());
								Shape placedShape = feasibleShapes.get(randomIndex);
								placedShape.setI(departi1);
								placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
								board[departi1][departj1] = placedShape;
								for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
								{
									neighbour.setReservedBy(board[departi1][departj1]);
									if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
										toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
								}
								//On met une feasible shapes de top border 
							}
				else if (departi1==h-1 && departj1<(w-1))
							{
								ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
								for(Shape sh:generateBottomBorderLegalShapes())
								{
									boolean willWork=true;
									sh.setI(departi1);
									sh.setJ(departj1);
									Shape[] shneighbor = game.getConnectionNeighbors(sh);
									for(Shape nb:shneighbor)
									{
										if (nb.getType()!=0 && !game.areShapesConnected(sh, nb)) willWork=false;
									}
									//Il ne rentre pas dans la boucle si il y a un seul élément ... 
									for(Shape toConnectsh:nextPlacedShape.getReservedBy())
									{
										if(!(game.areShapesConnected(toConnectsh, sh))) willWork=false; //We have to put shape that can be connected to all his reservedBy neighbors 
									}
									//I need to know who has put the shape as his neighbour in the board 
									if (willWork) feasibleShapes.add(sh);
								}
								int randomIndex = rand.nextInt(feasibleShapes.size());
								Shape placedShape = feasibleShapes.get(randomIndex);
								placedShape.setI(departi1);
								placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
								board[departi1][departj1] = placedShape;
								for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
								{
									neighbour.setReservedBy(board[departi1][departj1]);
									if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
										toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
								}
								//On met une feasible shapes de top border 
							}
				else if (departi1==h-1 && departj1==(w-1))
							{
								ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
								for(Shape sh:generateBottomRightCornerLegalShapes())
								{
									boolean willWork=true;
									sh.setI(departi1);
									sh.setJ(departj1);
									Shape[] shneighbor = game.getConnectionNeighbors(sh);
									for(Shape nb:shneighbor)
									{
										if (nb.getType()!=0 && !game.areShapesConnected(sh, nb)) willWork=false;
									}
									//Il ne rentre pas dans la boucle si il y a un seul élément ... 
									for(Shape toConnectsh:nextPlacedShape.getReservedBy())
									{
										if(!(game.areShapesConnected(toConnectsh, sh))) willWork=false; //We have to put shape that can be connected to all his reservedBy neighbors 
									}
									//I need to know who has put the shape as his neighbour in the board 
									if (willWork) feasibleShapes.add(sh);
								}
								int randomIndex = rand.nextInt(feasibleShapes.size());
								Shape placedShape = feasibleShapes.get(randomIndex);
								placedShape.setI(departi1);
								placedShape.setJ(departj1);//On ne peut pas connaitre leurs position à l'avance ... 
								board[departi1][departj1] = placedShape;
								for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
								{
									neighbour.setReservedBy(board[departi1][departj1]);
									if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
										toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
								}
					//On met n importe quelle feasible shapes 
				}
				else 
				{
					ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
					for(Shape sh:generateAllShapes())
					{
						boolean willWork=true;
						//We assign the tested shape to the position of the toPutShapes so it can be tested
						sh.setI(departi1);
						sh.setJ(departj1);
						Shape[] shneighbor = game.getConnectionNeighbors(sh);
						//We test if the shape will connect to his neighbors 
						for(Shape nb:shneighbor)
						{
							if (nb.getType()!=0 && !game.areShapesConnected(sh, nb)) willWork=false;
						}
						// ... then we have to put shape that can be connected to all his reservedBy neighbors 
						for(Shape toConnectsh:nextPlacedShape.getReservedBy())
						{
							if(!(game.areShapesConnected(toConnectsh, sh))) willWork=false; 
						}
						if (willWork) feasibleShapes.add(sh);
					}
					//Now we get a random shapes among the feasible one 
					int randomIndex = rand.nextInt(feasibleShapes.size());
					Shape placedShape = feasibleShapes.get(randomIndex);
					placedShape.setI(departi1);
					placedShape.setJ(departj1);
					board[departi1][departj1] = placedShape;
					for (Shape neighbour:game.getToConnectNeighbors(board[departi1][departj1]))
					{
									neighbour.setReservedBy(board[departi1][departj1]);
							if(!toPutShapes.contains(neighbour)) // && neighbour.getType()==0
										toPutShapes.add(neighbour); // We add the connection to close to an arraylist 
					}
				}
			}
			//Closer
			connectedComponentCloser(toPutShapes,board);
			toPutShapes.clear();
		}
		System.out.println(game);
	}
	
	
	/**
	 * Put the correct shapes to connected Component to close them 
	 * @param toPutShapes
	 * @param board
	 */
	private void connectedComponentCloser(ArrayList<Shape> toPutShapes,Shape[][] board)
	{
		if (!toPutShapes.isEmpty())
		{
			for(Shape sh:toPutShapes)
			{
				switch(sh.getReservedBy().size())
				{
					case 1:
						board[sh.getI()][sh.getJ()]=new QShape(game.getQOrientationForOpenConnection(sh),sh.getI(),sh.getJ());
						break;
					case 2:
						ArrayList<Shape> feasibleShapes = new ArrayList<Shape>();
						Shape selectedShape = null;
						for(Shape twoOutShapes: this.generateAllShapes())
						{
							if(twoOutShapes.getVConnections()==2)
							feasibleShapes.add(twoOutShapes);
						}
						for(Shape ssh: feasibleShapes) 
						{
							ssh.setI(sh.getI());
							ssh.setJ(sh.getJ());
							if(game.areShapesConnected(ssh,sh.getReservedBy().get(0)) && game.areShapesConnected(ssh,sh.getReservedBy().get(1)))
							{
								selectedShape=ssh;
							}
						}
						board[sh.getI()][sh.getJ()]=selectedShape; 
						break;
						//board[sh.getI()][sh.getJ()]=(I Shape)
					case 3:
						board[sh.getI()][sh.getJ()]=new TShape(game.getTOrientationForOpenConnection(sh),sh.getI(),sh.getJ());
						break;
					case 4:
						board[sh.getI()][sh.getJ()]=new XShape(0,sh.getI(),sh.getJ());
						break;
					default: 
						break;
				}
			}
		}
		toPutShapes.clear();
	}
	
	
	
	private void generateConnectedComponent()
	{
		
	}
	
	/**
	 * Generate a solvable game board with random shapes 
	 */
	public void generate() {
		this.generateSolution();
		this.shuffleGame(this.game);
	}


	/**
	 * 	Generate a solvable game board with random shapes and a maximum number of connected components
	 * @param nbcc
	 */
	public void generate(int nbcc)
	{
		System.out.println("generate grid with maximum "+nbcc+" connected components");
		//Random rand = new Random();
		//int exactNbCC=rand.nextInt(nbcc)+1; //We select a random number of connected components inferior to nbcc 
		int exactNbCC=nbcc;
		this.generateSolution(exactNbCC);
		this.shuffleGame(this.game);	
	}
	
	
											//Generation of the shapes to put on the board 
	
	/**
	 * 
	 * @return TopLeftCornerLegalShapes
	 */
	private Shape[] generateTopLeftCornerLegalShapes()
	{
		return new Shape[]{ new EmptyShape(0, 0, 0), new QShape(1, 0, 0), new QShape(2, 0, 0),
				new LShape(1, 0, 0) };
	}
	
	/**
	 * 
	 * @return TopBorderLegalShapes
	 */
	private Shape[] generateTopBorderLegalShapes()
	{
		return new Shape[]{ new EmptyShape(0, 0, 0), new QShape(2, 0, 0), new QShape(3, 0, 0),
				new LShape(2, 0, 0) };
	}
	
	/**
	 * 
	 * @return
	 */
	private Shape[] generateTopRightCornerLegalShapes()
	{
		return new Shape[]{ new EmptyShape(0, 0, 0), new QShape(2, 0, 0), new QShape(3, 0, 0),
				new LShape(2, 0, 0) };
	}
	
	/**
	 * 
	 * @return LeftBorderLegalShapes
	 */
	private Shape[] generateLeftBorderLegalShapes()
	{
		return new Shape[]{ new EmptyShape(0, 0, 0), new QShape(1, 0, 0), new QShape(0, 0, 0),
				new QShape(2, 0, 0), new IShape(0, 0, 0), new TShape(1, 0, 0), new LShape(0, 0, 0),
				new LShape(1, 0, 0) };
	}
	
	/**
	 * 
	 * @return RightBorderLegalShapes
	 */
	private Shape[] generateRightBorderLegalShapes()
	{
		return new Shape[]{ new EmptyShape(0, 0, 0), new QShape(0, 0, 0), new QShape(3, 0, 0),
				new QShape(2, 0, 0), new IShape(0, 0, 0), new TShape(3, 0, 0), new LShape(2, 0, 0),
				new LShape(3, 0, 0) };
	}
	
	/**
	 * 
	 * @return BottomLeftCornerLegalShapes
	 */
	private Shape[] generateBottomLeftCornerLegalShapes()
	{
		return new Shape[]{ new EmptyShape(0, 0, 0), new QShape(0, 0, 0), new QShape(1, 0, 0),
			new LShape(0, 0, 0) };
	}
	
	/**
	 * 
	 * @return BottomBorderLegalShapes
	 */
	private Shape[] generateBottomBorderLegalShapes()
	{
		return new Shape[]{ new EmptyShape(0, 0, 0), new QShape(0, 0, 0), new QShape(1, 0, 0),
				new QShape(3, 0, 0), new IShape(1, 0, 0), new TShape(0, 0, 0), new LShape(0, 0, 0),
				new LShape(3, 0, 0) };
	}
	
	/**
	 * 
	 * @return BottomRightCornerLegalShapes
	 */
	private Shape[] generateBottomRightCornerLegalShapes()
	{
		return new Shape[]{ new EmptyShape(0, 0, 0), new QShape(0, 0, 0), new QShape(3, 0, 0),
				new LShape(3, 0, 0) };
	}
	
	/**
	 * 
	 * @return AllShapes
	 */
	private List<Shape> generateAllShapes()
	{
		List<Shape> allshapes = Arrays.asList(new EmptyShape(0, 0, 0), 
				new QShape(0, 0, 0), new QShape(1, 0, 0), new QShape(2, 0, 0), new QShape(3, 0, 0), 
				new IShape(0, 0, 0), new IShape(1, 0, 0), 
				new TShape(0, 0, 0), new TShape(1, 0, 0), new TShape(2, 0, 0),new TShape(3, 0, 0), 
				new XShape(0, 0, 0), 
				new LShape(0, 0, 0), new LShape(1, 0, 0), new LShape(2, 0, 0), new LShape(3, 0, 0));
		return allshapes;
	}
}