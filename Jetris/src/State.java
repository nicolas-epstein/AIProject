
//This is the class that is stored as a representation of that state
//I may have gotten the dimesions of the tetris field wrong so you can change them as
//as you wish
public class State {
	
	protected boolean[][] blocks = new boolean[Tetris.ROWS][Tetris.COLUMNS];
	protected boolean[][] nextBlock = new boolean[4][4];
	
	//values to assess reward of state
	int[] columnHeights = new int[10];	//heighest occupied cell in each column 
	int maxHeight = 0;	//heighest occupied cell in ANY column 
	int emptyCells = 0;
	
	public State(boolean[][] newBlocks, boolean[][] newNextBlock){
		blocks = deepClone(newBlocks);
		nextBlock = deepClone(newNextBlock);
	}
	
	//secondary constructor based on game info
	public State(int[] columnHeights, int maxHeight, int emptyCells){
		this.columnHeights = columnHeights;
		this.maxHeight = maxHeight;
		this.emptyCells = emptyCells;
		
	}
	
	private boolean[][] deepClone(boolean[][] doubleArray){
		boolean[][] newArray = new boolean[doubleArray.length][doubleArray[0].length];
		for(int i = 0; i < doubleArray.length; i++){
			newArray[i] = doubleArray[i].clone();
		}
		return newArray;
	}
	
	public void printState(){
		//printing out max height of state before next piece enters game
		System.out.println("Max Height: " + maxHeight);
		System.out.println("Column Height Data: " + Arrays.toString(columnHeights));
		System.out.println("# of Empty Cells: " + emptyCells);
	}
	
	public boolean equals(State otherState){
		boolean[][] otherBlocks = otherState.blocks;
		for(int i = 0; i < blocks.length; i++){
			for(int j = 0; j < blocks[0].length; j++){
				if(blocks[i][j] != otherBlocks[i][j]){
					return false;
				}
			}
		}
		boolean[][] otherNextBlock = otherState.nextBlock;
		for(int i =0; i < nextBlock.length; i++){
			for(int j = 0; j < nextBlock[0].length; j++){
				if(nextBlock[i][j] != otherNextBlock[i][j]);
			}
		}
		
		return true;
	}
	
	public State clone(){
		return new State(deepClone(blocks), deepClone(nextBlock));
	}
	

}
