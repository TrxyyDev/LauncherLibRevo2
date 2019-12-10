package com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.components;

import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;

public class LauncherPasswordField extends PasswordField {

	public LauncherPasswordField(Pane root) {
		setSize(100, 30);
		setPosition(0, 0);
		root.getChildren().add(this);
	}

	public LauncherPasswordField(int w, int h, int pX, int pY, Pane root) {
		setSize(w, h);
		setPosition(pX, pY);
		root.getChildren().add(this);
	}

	public LauncherPasswordField(String s, int w, int h, int pX, int pY, Pane root) {
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
