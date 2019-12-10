package com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.LauncherConstants;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.accounts.Account;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class OptionsScreen {

	public OptionsScreen() {
		List<String> choices = new ArrayList<>();
		choices.add("1G");
		choices.add("2G");
		choices.add("3G");
		choices.add("4G");
		choices.add("5G");
		choices.add("6G");
		choices.add("7G");
		choices.add("8G");

		ChoiceDialog<String> dialog = new ChoiceDialog<>(Account.getRam(), choices);
		dialog.setTitle("Reglages 1/2");
//		dialog.initStyle(StageStyle.UTILITY);
		dialog.setHeaderText("Parametres de Lancement");
		dialog.setContentText("RAM allouee: ");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			Account.setRam(result.get());
			displaySecondOptions();
		}
		result.ifPresent(letter -> System.out.println("RAM: " + letter));
	}

	private void displaySecondOptions() {
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Reglages 2/2");
		dialog.setHeaderText("Parametres de Lancement");

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Valider", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField gameW = new TextField();
		gameW.setPromptText("Taille fenêtre horizontale");
		gameW.setText(LauncherConstants.getGameWidth());
		TextField gameH = new TextField();
		gameH.setPromptText("Taille fenêtre verticale");
		gameH.setText(LauncherConstants.getGameHeight());

		grid.add(new Label("Taille fenêtre horizontale"), 0, 0);
		grid.add(gameW, 1, 0);
		grid.add(new Label("Taille fenêtre verticale"), 0, 1);
		grid.add(gameH, 1, 1);

		Node validButton = dialog.getDialogPane().lookupButton(loginButtonType);
		dialog.getDialogPane().setContent(grid);
		Platform.runLater(() -> gameW.requestFocus());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(gameW.getText(), gameH.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();
		result.ifPresent(usernamePassword -> {
			LauncherConstants.setGameWidth(gameW.getText());
			LauncherConstants.setGameHeight(gameH.getText());
		});
	}

}
