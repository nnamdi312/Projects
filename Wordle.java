package application;
	
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.input.MouseEvent; 


public class Wordle extends Application {
	private int currentRow = 1;
	private int currentColumn = 0;
	private Stack<Label> stackLabel = new Stack<Label>();
	private LinkedList<Label> queueLabel = new LinkedList<Label>();
	private static String word = "GHOST";
	private static HashMap<String, Integer> correctLetters = new HashMap<String, Integer>();
	private HashMap<String, Integer> previousLetters = new HashMap<String, Integer>();
	private HashMap<String, KeyboardLetter> keyByValue = new HashMap<String, KeyboardLetter>();
	private static final String[] KEYBOARD_LETTERS = { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "ENTER", "Z", "X", "C", "V", "B" , "N", "M", "DELETE" };
	private GridPane grid;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			grid = new GridPane();
			final int numCols = 5;
	        final int numRows = 10;
	        for (int i = 0; i < numCols; i++) {
	            ColumnConstraints colConst = new ColumnConstraints();
	            colConst.setPercentWidth(100.0 / numCols);
	            grid.getColumnConstraints().add(colConst);
	        }
	        for (int i = 0; i < numRows; i++) {
	            RowConstraints rowConst = new RowConstraints();
	            rowConst.setPercentHeight(100.0 / numRows);
	            grid.getRowConstraints().add(rowConst);         
	        }
			root.setTop(grid);
			Label label = new Label("Wordle");
			label.setFont(new Font("Arial", 20));
			grid.add(label, 2, 0, 3, 1); // column, row, col span, row span
			
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 5; j++) {
					Rectangle rectangle = new Rectangle(65, 50);
					rectangle.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");
					grid.add(rectangle, j, i+1);
				}
			}
			
			int index = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 5; j++) {
					GridPane keyboardGrid = new GridPane();
					final int keyboardCols = 2;
			        final int keyboardRows = 1;
			        for (int k = 0; k < keyboardCols; k++) {
			            ColumnConstraints colConst = new ColumnConstraints();
			            colConst.setPercentWidth(100.0 / keyboardCols);
			            keyboardGrid.getColumnConstraints().add(colConst);
			        }
			        for (int k = 0; k < keyboardRows; k++) {
			            RowConstraints rowConst = new RowConstraints();
			            rowConst.setPercentHeight(100.0 / keyboardRows);
			            keyboardGrid.getRowConstraints().add(rowConst);         
			        }
			        
			        boolean skipFirstKey = (i == 1 || i == 2) && j == 0;
			        if (!skipFirstKey) {
			        	Rectangle rectangle = new Rectangle(32, 50);
			        	rectangle.setId(String.valueOf(index));
						rectangle.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");
						keyboardGrid.add(rectangle, 0, 0);
						
						rectangle.setOnMouseClicked(new EventHandler<MouseEvent>()
				        {
				            @Override
				            public void handle(MouseEvent t) {
				            	int index = Integer.parseInt(((Rectangle) t.getSource()).getId());
				            	String value = KEYBOARD_LETTERS[index];
				            	if (value.equals("DELETE")) {
				            		deleteCharacter();
				            	}
				            	else if (value.equals("ENTER")) {
				            		submitAnswer(primaryStage);
				            	}
				            	else {
				            		addCharacter(value);
				            	}
				            }
				        });
						
						Label keyboardLabel = new Label(KEYBOARD_LETTERS[index]);
						keyboardLabel.setFont(new Font("Arial", 8));
						keyboardLabel.setStyle("-fx-font-weight: bold;");
						keyboardGrid.add(keyboardLabel, 0, 0);
		            	GridPane.setHalignment(keyboardLabel, HPos.CENTER);
		            	keyByValue.put(KEYBOARD_LETTERS[index], new KeyboardLetter(KEYBOARD_LETTERS[index], keyboardGrid, rectangle, 0, 0));
		            	index++;
		            	
			        }
					//Rectangle rectangle2 = makeFirstKeyLarger ? new Rectangle(65, 50) : new Rectangle(32, 50);
			        Rectangle rectangle2 = new Rectangle(32, 50);
			        rectangle2.setId(String.valueOf(index));
					rectangle2.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");
					keyboardGrid.add(rectangle2, 1, 0);
					
					Label keyboardLabel = new Label(KEYBOARD_LETTERS[index]);
					keyboardLabel.setFont(new Font("Arial", 8));
					keyboardLabel.setStyle("-fx-font-weight: bold;");
					keyboardGrid.add(keyboardLabel, 1, 0);
	            	GridPane.setHalignment(keyboardLabel, HPos.CENTER);
	            	keyByValue.put(KEYBOARD_LETTERS[index], new KeyboardLetter(KEYBOARD_LETTERS[index], keyboardGrid, rectangle2, 1, 0));
	            	index++;
					
	            	rectangle2.setOnMouseClicked(new EventHandler<MouseEvent>()
			        {
			            @Override
			            public void handle(MouseEvent t) {
			            	int index = Integer.parseInt(((Rectangle) t.getSource()).getId());
			            	String value = KEYBOARD_LETTERS[index];
			            	if (value.equals("DELETE")) {
			            		deleteCharacter();
			            	}
			            	else if (value.equals("ENTER")) {
			            		submitAnswer(primaryStage);
			            	}
			            	else {
			            		addCharacter(value);
			            	}
			            }
			        });
	            	
					GridPane.setConstraints(keyboardGrid, j, i+7);
					grid.getChildren().add(keyboardGrid);
				}
			}
			
			grid.setVgap(10);
			
			Scene scene = new Scene(root,350,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	            @Override
	            public void handle(KeyEvent event) {
	            	if (event.getCode() == KeyCode.BACK_SPACE) {
	            		deleteCharacter();
	            	}
	            	else if (event.getCode() == KeyCode.ENTER) {
	            		// User has completed a 5 letter word and wants to submit.
	            		submitAnswer(primaryStage);
	            	}
	            	else {
	            		addCharacter(event.getText());
	            	}
	            }
	        });
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addCharacter(String ch) {
		if (currentColumn < 5) {
        	Label label = new Label(ch.toUpperCase());
        	label.setFont(new Font("Arial", 24));
        	label.setStyle("-fx-font-weight: bold;");
        	grid.add(label, currentColumn, currentRow);
        	GridPane.setHalignment(label, HPos.CENTER);
        	currentColumn++;
        	stackLabel.push(label);
        	queueLabel.add(label);	
		}
	}
	
	private void deleteCharacter() {
		currentColumn--;
		stackLabel.pop().setText("");
		queueLabel.removeLast();
	}
	
	private void submitAnswer(Stage primaryStage) {
		if (currentColumn == 5) {
			int currentIndex = 0;
			boolean perfect = true;
			for (Label label : queueLabel) {
				KeyboardLetter value = keyByValue.get(label.getText());
				if (label.getText().equals(word.substring(currentIndex, currentIndex+1))) {
					value.getRectangle().setStyle("-fx-fill: green; -fx-stroke: black; -fx-stroke-width: 1;");
					Rectangle rectangle = new Rectangle(65, 50);
					rectangle.setStyle("-fx-fill: green; -fx-stroke: black; -fx-stroke-width: 1;");
					grid.add(rectangle, currentIndex, currentRow);
					Label letter = new Label(label.getText());
					letter.setFont(new Font("Arial", 24));
					letter.setStyle("-fx-font-weight: bold;");
	            	grid.add(letter, currentIndex, currentRow);
	            	GridPane.setHalignment(letter, HPos.CENTER);
	            	if (previousLetters.containsKey(label.getText())) {
						previousLetters.put(label.getText(), previousLetters.get(label.getText())+1);
					} else {
						previousLetters.put(label.getText(), 1);
					}
				}
				currentIndex++;
			}
			currentIndex = 0;
			while (!queueLabel.isEmpty()) {
				Label label = queueLabel.remove();
				KeyboardLetter value = keyByValue.get(label.getText());
				if (!label.getText().equals(word.substring(currentIndex, currentIndex+1))) {
					if (word.contains(label.getText()) && previousLetters.getOrDefault(label.getText(), 0) < correctLetters.getOrDefault(label.getText(), 0)) {
						Rectangle rectangle = new Rectangle(65, 50);
						rectangle.setStyle("-fx-fill: yellow; -fx-stroke: black; -fx-stroke-width: 1;");
						grid.add(rectangle, currentIndex, currentRow);
						Label letter = new Label(label.getText());
						letter.setFont(new Font("Arial", 24));
						letter.setStyle("-fx-font-weight: bold;");
		            	grid.add(letter, currentIndex, currentRow);
		            	GridPane.setHalignment(letter, HPos.CENTER);
		            	value.getRectangle().setStyle("-fx-fill: yellow; -fx-stroke: black; -fx-stroke-width: 1;");
		            	perfect = false;
					} else {
						if (correctLetters.getOrDefault(label.getText(), 0) == 0) {
							value.getRectangle().setStyle("-fx-fill: gray; -fx-stroke: black; -fx-stroke-width: 1;");
						}
						
						perfect = false;
					}
					if (previousLetters.containsKey(label.getText())) {
						previousLetters.put(label.getText(), previousLetters.get(label.getText())+1);
					} else {
						previousLetters.put(label.getText(), 1);
					}
				}
				currentIndex++;
			}
			currentRow++;
			currentColumn = 0;
			previousLetters.clear();
			stackLabel.clear();
			queueLabel.clear();
			
			if (perfect) {
				wonGame(primaryStage);
			}
		}
	}
	
	private void wonGame(Stage primaryStage) {
		Popup popup = new Popup();
    	Label popupLabel = new Label("Congrats you won!");
        // set background
    	popupLabel.setStyle("-fx-text-fill: white; -fx-background-color: green; -fx-border-color: black;");
    	Image img = new Image("/Content/trophy.jpg");
        ImageView view = new ImageView(img);
        view.setFitHeight(80);
        view.setPreserveRatio(true);
        popupLabel.setGraphic(view);
    	
        // add the label
        popup.getContent().add(popupLabel);
  
        // set size of label
        popupLabel.setMinWidth(80);
        popupLabel.setMinHeight(50);
  
        // set auto hide
        popup.setAutoHide(true);
        popup.show(primaryStage);
	}
	
	public static void main(String[] args) {
		// get the word
		// populate the correct letters dictionary
		for (int i = 0; i < word.length(); i++) {
			String letter = word.substring(i, i+1);
			if (correctLetters.containsKey(letter)) {
				correctLetters.put(letter, correctLetters.get(letter+1));
			} else {
				correctLetters.put(letter, 1);
			}
		}
		
		launch(args);
	}
}
