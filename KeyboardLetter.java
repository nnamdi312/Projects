package application;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class KeyboardLetter {
	
	private String value;
	private Rectangle rectangle;
	private GridPane gridPane;
	private int rowNum;
	private int colNum;
	
	public KeyboardLetter(String value, GridPane gridPane, Rectangle rectangle, int rowNum, int colNum) {
		super();
		this.value = value;
		this.rectangle = rectangle;
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.gridPane = gridPane;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Rectangle getRectangle() {
		return rectangle;
	}
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public int getColNum() {
		return colNum;
	}
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public GridPane getGridPane() {
		return gridPane;
	}

	public void setGridPane(GridPane gridPane) {
		this.gridPane = gridPane;
	}
	
	

}
