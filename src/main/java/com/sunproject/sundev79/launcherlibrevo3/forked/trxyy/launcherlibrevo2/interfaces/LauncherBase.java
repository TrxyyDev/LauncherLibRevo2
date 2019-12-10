package com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.interfaces;

import java.awt.Point;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.Init;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.LauncherConstants;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.components.LauncherAlert;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils.Logger;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class LauncherBase {

	final Point dragDelta = new Point();

	public LauncherBase(final Stage stage, Scene scene, StageStyle style, Mover moveable) {
		Logger.write("Preparing panel.");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			new LauncherAlert(e.toString(), AlertType.ERROR);
			Logger.err(e.toString());
		} catch (InstantiationException e) {
			new LauncherAlert(e.toString(), AlertType.ERROR);
			Logger.err(e.toString());
		} catch (IllegalAccessException e) {
			new LauncherAlert(e.toString(), AlertType.ERROR);
			Logger.err(e.toString());
		} catch (UnsupportedLookAndFeelException e) {
			new LauncherAlert(e.toString(), AlertType.ERROR);
			Logger.err(e.toString());
		}

		stage.initStyle(style);
		stage.setResizable(false);
		stage.setTitle(LauncherConstants.getTitle());
		stage.setWidth(LauncherConstants.getWidth());
		stage.setHeight(LauncherConstants.getHeight());
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent windowEvent) {
				Platform.exit();
				System.exit(0);
			}
		});
		if (moveable.equals(Mover.MOVE_ON_CLICK)) {
			scene.setOnMousePressed(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent mouseEvent) {
					dragDelta.x = (int) (stage.getX() - mouseEvent.getScreenX());
					dragDelta.y = (int) (stage.getY() - mouseEvent.getScreenY());
				}
			});
			scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent mouseEvent) {
					stage.setX(mouseEvent.getScreenX() + dragDelta.x);
					stage.setY(mouseEvent.getScreenY() + dragDelta.y);
				}
			});
		}
		stage.setScene(scene);
		if (Init.getConfiguration().getCurrentlyMaintenance()) {
			new LauncherAlert("MAINTENANCE EN COURS.", "Nos serveurs sont actuellement en maintenance. Nous améliorons l'expérience de jeu afin de vous fournir d'amples nouveautés. \nVeuillez nous excuser pour cette attente, nous vous prions de ressayer dans quelques minutes. \n\nVous pouvez suivre la maintenance sur " + Init.getConfiguration().getMaintenanceUrl() + "\nCordialement, L'équipe de " + LauncherConstants.getTitle() + ".");
		}
		else {
			stage.show();
		}
		Logger.write("Showed panel.");
//		showCopyright();
	}

	public void setIconImage(Stage primaryStage, Image img) {
		primaryStage.getIcons().add(img);
	}

//	private static void showCopyright() {
//		Logger.err("LauncherLib par Trxyy 2018-2028");
//		Logger.err("Trxyy et/ou les clients conservent les droits suivants:");
//		Logger.err(">> Autorise <<");
//		Logger.err("\t- Utilisation Publique");
//		Logger.err("\t- Utilisation Personelle");
//		Logger.err("\t- Utilisation dans Projets");
//		Logger.err(">> Interdit <<");
//		Logger.err("\t- Reproduction");
//		Logger.err("\t- Travaux dérivés");
//		Logger.err("\t- Distribution");
//		Logger.err("\t- Modification");
//		Logger.err("\t- Sous-licenses");
//	}

}