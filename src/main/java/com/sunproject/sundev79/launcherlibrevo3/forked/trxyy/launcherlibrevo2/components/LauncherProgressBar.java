package com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.components;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

public class LauncherProgressBar extends ProgressBar {
	
	public String currentFile;
	
	public LauncherProgressBar() {}
	
	public LauncherProgressBar(Pane contentPane) {
		contentPane.getChildren().add(this);
	}
	
	public void setSize(int width_, int height_) {
		setPrefSize(width_, height_);
		setWidth(width_);
		setHeight(height_);
	}
	
	public void setSize(double d, double height_) {
		setPrefSize(d, height_);
		setWidth(d);
		setHeight(height_);
	}
	
	public void setInvisible() {
		setBackground(null);
	}

	public void setPosition(int posX, int posY) {
		setLayoutX(posX);
		setLayoutY(posY);
	}
	
	public void setPosition(double d, double e) {
		setLayoutX(d);
		setLayoutY(e);
	}
	
	public void setCurrentFile(String file) {
		this.currentFile = file;
	}
	
	public String getCurrentFile() {
		return this.currentFile;
	}
	
}
