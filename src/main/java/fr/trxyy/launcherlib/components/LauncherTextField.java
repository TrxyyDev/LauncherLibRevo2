package fr.trxyy.launcherlib.components;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class LauncherTextField extends TextField {

	public LauncherTextField(Pane root) {
		setSize(100, 30);
		setPosition(0, 0);
		root.getChildren().add(this);
	}

	public LauncherTextField(String s, Pane root) {
		setText(s);
		setSize(100, 30);
		setPosition(0, 0);
		root.getChildren().add(this);
	}

	public LauncherTextField(int w, int h, int pX, int pY, Pane root) {
		setSize(w, h);
		setPosition(pX, pY);
		root.getChildren().add(this);
	}

	public LauncherTextField(String s, int w, int h, int pX, int pY, Pane root) {
		setText(s);
		setSize(w, h);
		setPosition(pX, pY);
		root.getChildren().add(this);
	}

	public void setSize(int width_, int height_) {
		setPrefSize(width_, height_);
	}

	public void setPosition(int posX, int posY) {
		setLayoutX(posX);
		setLayoutY(posY);
	}

	public void setVoidText(String s) {
		setPromptText(s);
	}

}
