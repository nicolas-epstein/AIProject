import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.awt.Robot;
import java.awt.event.KeyEvent;

//Class in which all the data from the game passes
public class TetrisAI {

	private Robot keyPressAgent;	//robot used for keypress events, allows the AI to play Jetris
	HashSet stateTable = new HashSet();

	private final double exploration = .1;

	// Will need to sort this data occasionally so that the odds of this taking
	// a long time
	// will be small
	ArrayList<TableEntry> recordedData = new ArrayList<TableEntry>();

	private int lastScoreValue = 0;

	public TetrisAI() {

	}

	public String makeMove(State current, int newScoreValue, State rightMove, State upMove, State leftMove,
			State downMove) {
		int reward = newScoreValue - lastScoreValue;
		lastScoreValue = newScoreValue;
		String move = "";
		TableEntry tableEntry = null;
		if (stateTable.contains(current)) {

			for (int i = 0; i < recordedData.size(); i++) {

				if (recordedData.get(i).checkState(current)) {
					tableEntry = recordedData.get(i);

					i = recordedData.size();
					Random RNG = new Random();

					if (RNG.nextDouble() > (1 - exploration)) {
						move = makeRandomMove();
					}

					else {
						move = tableEntry.getBestMove();
					}
				}
			}
		}
		//If it is a new state
		else{
			stateTable.add(current);
			move = makeRandomMove();
			tableEntry = new TableEntry(current);
			recordedData.add(tableEntry);
		}
		State nextState = null;
		if(move.equals("Up")){
			nextState = upMove;
			//Press the up key to rotate blocks
			  keyPressAgent.keyPress(KeyEvent.VK_UP);
			  keyPressAgent.keyRelease(KeyEvent.VK_UP);
		}
		else if(move.equals("Down")){
			nextState = downMove;
			//Press the down key to drop blocks
			  keyPressAgent.keyPress(KeyEvent.VK_DOWN);
			  keyPressAgent.keyRelease(KeyEvent.VK_DOWN);
		}
		else if(move.equals("Right")){
			nextState = rightMove;
			//Press the right key to move blocks 
			  keyPressAgent.keyPress(KeyEvent.VK_RIGHT);
			  keyPressAgent.keyRelease(KeyEvent.VK_RIGHT);
		}
		else if(move.equals("Left")){
			nextState = leftMove;
			//Press the left key to move blocks 
			  keyPressAgent.keyPress(KeyEvent.VK_LEFT);
			  keyPressAgent.keyRelease(KeyEvent.VK_LEFT);
		}
		double nextTurnQValue = 0;
		if(stateTable.contains(nextState)){
			for(int i = 0; i < recordedData.size(); i++){
				if(recordedData.get(i).checkState(nextState)){
					nextTurnQValue = recordedData.get(i).actions.getMaxValue();
				}
			}
		}
		else{
			TableEntry newState = new TableEntry(nextState);
			nextTurnQValue = 0;
		}
		
		
		tableEntry.updateValue(move, reward, nextTurnQValue);
		return move;
	}

	private String makeRandomMove() {
		Random RNG = new Random();
		double randDecimal = RNG.nextDouble();
		if (randDecimal < 0.25) {
			return "Up";
		} else if (randDecimal >= 0.25 && randDecimal < 0.5) {
			return "Down";
		} else if (randDecimal >= 0.5 && randDecimal < 0.75) {
			return "Left";
		} else {
			return "Right";
		}
	}

}
