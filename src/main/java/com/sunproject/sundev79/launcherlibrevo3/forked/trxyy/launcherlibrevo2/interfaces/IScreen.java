package com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.interfaces;

import java.awt.Desktop;
import java.awt.MouseInfo;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.LauncherConstants;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.components.LauncherProgressBar;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.update.GameUpdater;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils.FontLoader;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils.Logger;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils.ResourceLocation;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

@SuppressWarnings("restriction")


public class IScreen {
	private static Rectangle downloadRect;
	private static Label downloadPercentLabel;
	private static Label downloadFileLabel;
	private static GameUpdater gameUpdater = new GameUpdater();
	private static LauncherProgressBar progressBar;
	private static DecimalFormat df2 = new DecimalFormat(".##");
	public static Timeline timeline;
	public static Timeline timeline_;
	public static ResourceLocation resources;
	private static ImageView logoImage;
	private static int posX = 0;
	private static int posY = 0;
	
	public IScreen() {}
	
	public static void drawLogo(Image img, int posX_, int posY_, int sizeX, int sizeY, Pane root, Mover animate) {
		logoImage = new ImageView();
		logoImage.setImage(img);
		logoImage.setFitWidth(sizeX);
		logoImage.setFitHeight(sizeY);
		posX = posX_;
		posY = posY_;
		logoImage.setLayoutX(posX);
		logoImage.setLayoutY(posY);
		root.getChildren().add(logoImage);
		if (animate.equals(Mover.MOVE_ON_CLICK)) {
			animateLogo();
		}
	}
	
	private static void animateLogo() {
        AnimationTimer animate = new AnimationTimer() {
            @Override
            public void handle(long now) {
                float multplicatorSize = 54.25f;
            	int mouseX = MouseInfo.getPointerInfo().getLocation().x;
            	int mouseY = MouseInfo.getPointerInfo().getLocation().y;
            	logoImage.setLayoutX(posX - (mouseX / multplicatorSize));
        		logoImage.setLayoutY(posY - (mouseY / multplicatorSize));
            }
        }; animate.start();
	}
	
	public static void drawBackgroundImage(Pane root, String img) {
		ImageView backgroundImage = new ImageView();
		backgroundImage.setImage(resources.loadImage(img));
		backgroundImage.setFitWidth(LauncherConstants.getWidth());
		backgroundImage.setFitHeight(LauncherConstants.getHeight());
		backgroundImage.setLayoutX(0);
		backgroundImage.setLayoutY(0);
	    root.getChildren().add(backgroundImage);
	}
	
	public static void drawAnimatedBackground(Pane root, String media, boolean soundEnabled) {
        MediaPlayer player = new MediaPlayer(resources.getMedia(media));
        MediaView viewer = new MediaView(player);
        viewer.setFitWidth(LauncherConstants.getWidth());
        viewer.setFitHeight(LauncherConstants.getHeight());
        player.setAutoPlay(true);
        player.setMute(soundEnabled);
        viewer.setPreserveRatio(false);
        player.setCycleCount(-1);
        player.play();
        root.getChildren().add(viewer);
	}

	public static Image loadImage(String image) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(IScreen.class.getResourceAsStream(getResourceLocation() + image));
		} catch (IOException e) {
			Logger.write("Echec du chargement de la ressource demandée.");
		}
		Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
		return fxImage;
	}

	public static void openLink(String urlString) {
		try {
			Desktop.getDesktop().browse(new URL(urlString).toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateAndLaunch(Pane contentPane) {
		progressBar = new LauncherProgressBar();
		
		downloadRect = new Rectangle(0, 0, LauncherConstants.getWidth(), LauncherConstants.getHeight());
		downloadRect.setFill(Color.BLACK);
		downloadRect.setOpacity(0.0);
		contentPane.getChildren().add(downloadRect);
		changeOpa();
		
		downloadPercentLabel = new Label("MISE A JOUR");
		downloadPercentLabel.setTextFill(Color.WHITE);
		downloadPercentLabel.setFont(FontLoader.loadFontLocal("BebasNeue-Regular.otf", "Bebas Neue", 80F));
		downloadPercentLabel.setPrefSize(LauncherConstants.getWidth(), 50);
		downloadPercentLabel.setLayoutX(0);
		downloadPercentLabel.setLayoutY(150);
		downloadPercentLabel.setTextAlignment(TextAlignment.CENTER);
		downloadPercentLabel.setAlignment(Pos.CENTER);
		contentPane.getChildren().add(downloadPercentLabel);
		
		downloadFileLabel = new Label("Fichier inconnu");
		downloadFileLabel.setTextFill(Color.WHITE);
		downloadFileLabel.setFont(FontLoader.loadFontLocal("BebasNeue-Regular.otf", "Bebas Neue", 35F));
		downloadFileLabel.setPrefSize(LauncherConstants.getWidth(), 50);
		downloadFileLabel.setTextAlignment(TextAlignment.CENTER);
		downloadFileLabel.setAlignment(Pos.CENTER);
		downloadFileLabel.setLayoutX(0);
		downloadFileLabel.setLayoutY(250);
		contentPane.getChildren().add(downloadFileLabel);
		
		 //Screen not showed.
		Logger.write("Checking custom resources...");
        timeline = new Timeline(new KeyFrame(Duration.seconds(0), e -> downloadPercentLabel.setText("" + df2.format(gameUpdater.getProgressBar().getProgress() * 100) + "%")), new KeyFrame(Duration.seconds(0.1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        timeline_ = new Timeline(new KeyFrame(Duration.seconds(0), e -> downloadFileLabel.setText("" + gameUpdater.getProgressBar().getCurrentFile())), new KeyFrame(Duration.seconds(0.1)));
        timeline_.setCycleCount(Animation.INDEFINITE);
        timeline_.play();
        
		gameUpdater.checkForUpdate(progressBar);
	}
	
	private static void changeOpa() {
		new Thread() {
			public void run() {
		        try
		        {
					while (downloadRect.getOpacity() < 0.8f) {
						double opa = downloadRect.getOpacity() + 0.005F;
						downloadRect.setOpacity(opa);
						Thread.sleep(1);
					}
					
		        }
		        catch (Exception ex) {
		        	ex.printStackTrace();
		        }
			}
		}.start();
	}
	
	public static void extractNatives() {
		Platform.runLater(() -> downloadPercentLabel.setText("Preparation"));
		Platform.runLater(() -> downloadFileLabel.setText("Extraction des natives"));
	}
	
	public static void prepareLaunch() {
		Platform.runLater(() -> downloadPercentLabel.setText("Lancement..."));
		Platform.runLater(() -> downloadFileLabel.setText("Lancement du jeu"));
	}
	
	public static void verifyInstall() {
		Platform.runLater(() -> downloadPercentLabel.setText("Verification"));
		Platform.runLater(() -> downloadFileLabel.setText("Verification des fichiers"));
	}
	
	public static String getResourceLocation() {
		return LauncherConstants.getResourceLocation();
	}

	public static void playSound(String sound) {
		URL resourceUrl = IScreen.class.getResource(getResourceLocation() + sound);
		Media theMedia = null;
		try {
			theMedia = new Media(resourceUrl.toURI().toString());
		} catch (URISyntaxException e) {
			Logger.write(e.toString());
		}
		final MediaPlayer mediaPlayer = new MediaPlayer(theMedia);
		mediaPlayer.play();
	}
}
