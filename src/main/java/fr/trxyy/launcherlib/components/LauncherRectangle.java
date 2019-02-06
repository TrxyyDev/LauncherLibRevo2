package fr.trxyy.launcherlib.components;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LauncherRectangle extends Rectangle {

	public LauncherRectangle(Pane root, int x, int y, int sX, int sY) {
		setX(x);
		setY(y);
		setWidth(sX);
		setHeight(sY);
		root.getChildren().add(this);
	}

	public LauncherRectangle(int width, int height) {
		setWidth(width);
		setHeight(height);
	}

	public void setColor(Color color) {
		setStroke(color);
	}
}
