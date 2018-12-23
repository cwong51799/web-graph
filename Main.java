import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
public class Main {
    public static void main(String[] args) throws Exception{
    	int count = 0;
        Map myMap = new Map();
        Scanner input = new Scanner(System.in);
        boolean cont = true;
        while (cont){
            System.out.print("Input: ");
            String direction = input.nextLine().toUpperCase();
            int answer = 0;
            switch(direction){
                case "W":
                    answer = 1;
                    break;
                case "A":
                    answer = 4;
                    break;
                case "S":
                    answer = 3;
                    break;
                case "D":
                    answer = 2;
                    break;
                case "T": // toggle ink
                	if (myMap.getPen().getInk() == false) 
                		myMap.getPen().setInk(true);
                	else 
                		myMap.getPen().setInk(false);
                	break;
                case "Q":
                	cont = false;
                	break;
            }
            myMap.getPen().setFace(answer);
            int startX = myMap.getPen().getPosX();
            int startY = myMap.getPen().getPosY();
            myMap.moveCursor();
            int endX = myMap.getPen().getPosX();
            int endY = myMap.getPen().getPosY();
            System.out.println("The cursor is at " +  myMap.getPen().getPosX() + ", " + myMap.getPen().getPosY());
            myMap.printMap();
        } 
    }
}
    class myFrame extends JFrame {
    	public myFrame() {
    		setTitle("My Empty Frame");
    		setSize(50, 50);
    		setLocation(0,0);
    	}
    }
    class JFrameGraphics extends JPanel{
    	public JFrameGraphics(int startX, int startY, int endX, int endY) {
    		this.startX = startX;
    		this.startY = startY;
    		this.endX = endX;
    		this.endY = endY;
    	}
    	public int startX, startY, endX, endY;
    	public void paint(Graphics g){ 		
    		g.drawLine(startX, startY, endX, endY);			
    	}

		public int getStartX() {
			return startX;
		}

		public void setStartX(int startX) {
			this.startX = startX;
		}

		public int getStartY() {
			return startY;
		}

		public void setStartY(int startY) {
			this.startY = startY;
		}

		public int getEndX() {
			return endX;
		}

		public void setEndX(int endX) {
			this.endX = endX;
		}

		public int getEndY() {
			return endY;
		}

		public void setEndY(int endY) {
			this.endY = endY;
		}
    }
    class Map {
        public final int mapSize = 10; 
        Cursor pen = new Cursor(mapSize);
        public int[][] map = new int[mapSize][mapSize];
        public Map(){
            this.fillMap();
        }
        // Fills in the map with 0s
        public Cursor getPen(){
            return pen;
        }
        
        public int[][] getMap() {
			return map;
		}
		public void setMap(int[][] map) {
			this.map = map;
		}
		public int getMapSize() {
			return mapSize;
		}
		public void setPen(Cursor pen) {
			this.pen = pen;
		}
		public void fillMap(){
            for (int i=0;i<map.length;i++){
                 for (int j=0;j<map.length;j++){
                    map[i][j] = 0;
                 }
            }
        }
        // Prints the map
        public void printMap(){
            for (int i=0;i<map.length;i++){
                for (int j=0;j<map.length;j++){
                	if (map[j][i] == 0) {
                		System.out.print("  ");
                	}
                	else {
                		System.out.print(map[j][i] + " ");
                	}
                }
                System.out.println();
            }
            System.out.println();
        }
        // Fills the position of the cursor
        public void moveCursor(){
            switch(pen.getFace()){
                case 1:
                    if (pen.getPosY() == 0){
                        return;
                    }
                    break;
                case 2:
                    if (pen.getPosX() == mapSize-1){
                        return;
                    }
                    break;
                case 3:
                    if (pen.getPosY() == mapSize-1){
                        return;
                    }
                    break;
                case 4:
                    if (pen.getPosX() == 0){
                        return;
                    }
                    break;
            }
            pen.advance();
            fillCursor();
        }
        public void fillCursor(){
            if (pen.getInk() == true){
                map[pen.getPosX()][pen.getPosY()] = 1;
            }
        }
        public double compareMaps(Map m) {
        	if (mapSize != m.getMapSize()) {
        		System.out.println("The maps are of different sizes and cannot be compared.");
        		return -1;
        	}
        	int similarities = 0;
        		for (int i=0;i<mapSize;i++) {
        			for (int j=0;j<mapSize;j++) {
        				if (map[i][j] == m.getMap()[i][j]) {
        					similarities++;
        				}
        			}
        		}
        		double totalPixels = mapSize * mapSize;
        		double percentSame = (similarities *100 / totalPixels); 
        	return percentSame;
        }
    }
   class Cursor {
    int posX = 0;
    int posY = 0;
    int mapSize;
    public int face = 0; // 1 = up, 2 = right, 3 = down, 4 = left.
    boolean ink = false;
    public Cursor(int mapSize){
        this.mapSize = mapSize;
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public void setposX(int newPosX){
        posX = newPosX;
    }
    public void setposY(int newPosY){
        posY = newPosY;
    }
    public boolean getInk(){
        return ink;
    }
    public void setInk(boolean b){
        ink = b;
    }
    public void moveLeft(){
        posX--;
    }
    public void moveRight(){
        posX++;
    }
    public void moveUp(){
        posY--;
    }
    public void moveDown(){
        posY++;
    }
    public int getFace(){
        return face;
    }
    public void setFace(int newFace){
        this.face = newFace;
    }
   public void advance(){
      switch(face){
          case 1:
                moveUp();
                break;
          case 2:
                moveRight();
                break;
          case 3:
                moveDown();
                break;
          case 4:
                moveLeft();
                break;
          default:  
        }
    }
}
    