import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;

// Java 8 code
public class JavaFXTrayIconSample extends Application {

    // application stage is stored so that it can be shown and hidden based on system tray icon operations.
    private Stage stage;

    protected static final JMetro jMetro = new JMetro(Style.DARK);

    // a timer allowing the tray icon to provide a periodic notification event.
    private Timer notificationTimer = new Timer();

    // format used to display the current time in a tray icon notification.
    private DateFormat timeFormat = SimpleDateFormat.getTimeInstance();

    // sets up the javafx application.
    // a tray icon is setup for the icon, but the main stage remains invisible until the user
    // interacts with the tray icon.
    @Override
    public void start(final Stage stage) throws IOException {
        // stores a reference to the stage.
        this.stage = stage;

        // instructs the javafx system not to exit implicitly when the last application window is shut.
        Platform.setImplicitExit(false);

        // sets up the tray icon (using awt code run on the swing thread).
        javax.swing.SwingUtilities.invokeLater(this::addAppToTray);

        // out stage will be translucent, so give it a transparent style.
        stage.initStyle(StageStyle.TRANSPARENT);

        HBox main = new HBox();
        main.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        VBox mainLeft = new VBox();
        HBox mainLeftTop = new HBox();

        Button screen1 = new Button();
        ImageView screenView1 = new ImageView(new Image(new FileInputStream("D:\\Documents\\Workspace\\Yeenbilight\\src\\main\\resources\\screen_icon.png")));
        screenView1.setFitWidth(65);
        screenView1.setFitHeight(65);
        screen1.setGraphic(screenView1);
        screen1.setStyle("-fx-background-color: transparent");
        screen1.setFocusTraversable(false);
        Label screen1Label = new Label("Screen 1");
        BorderPane screen1Container = new BorderPane();
        screen1Container.setCenter(screen1);
        screen1Container.setAlignment(screen1Label, Pos.CENTER);
        screen1Container.setBottom(screen1Label);
        mainLeftTop.getChildren().add(screen1Container);
        mainLeftTop.setHgrow(screen1Container, Priority.ALWAYS);

        Button screen2 = new Button();
        ImageView screenView2 = new ImageView(new Image(new FileInputStream("D:\\Documents\\Workspace\\Yeenbilight\\src\\main\\resources\\screen_icon.png")));
        screenView2.setFitWidth(65);
        screenView2.setFitHeight(65);
        screen2.setGraphic(screenView2);
        screen2.setStyle("-fx-background-color: transparent");
        screen2.setFocusTraversable(false);
        Label screen2Label = new Label("Screen 2");
        BorderPane screen2Container = new BorderPane();
        screen2Container.setCenter(screen2);
        screen2Container.setAlignment(screen2Label, Pos.CENTER);
        screen2Container.setBottom(screen2Label);
        mainLeftTop.getChildren().add(screen2Container);
        mainLeftTop.setHgrow(screen2Container, Priority.ALWAYS);

        HBox mainLeftBottom = new HBox();

        Button light1 = new Button();
        ImageView lightView1 = new ImageView(new Image(new FileInputStream("D:\\Documents\\Workspace\\Yeenbilight\\src\\main\\resources\\light_icon.png")));
        lightView1.setFitWidth(65);
        lightView1.setFitHeight(65);
        light1.setGraphic(lightView1);
        light1.setStyle("-fx-background-color: transparent");
        light1.setFocusTraversable(false);
        Label light1Label = new Label("Light 1");
        BorderPane light1Container = new BorderPane();
        light1Container.setAlignment(light1Label, Pos.CENTER);
        light1Container.setCenter(light1);
        light1Container.setBottom(light1Label);
        light1Label.setStyle("-fx-padding: 0 0 10 0");

        mainLeftBottom.getChildren().add(light1Container);
        mainLeftBottom.setHgrow(light1Container, Priority.ALWAYS);

        Button light2 = new Button();
        ImageView lightView2 = new ImageView(new Image(new FileInputStream("D:\\Documents\\Workspace\\Yeenbilight\\src\\main\\resources\\light_icon.png")));
        lightView2.setFitWidth(65);
        lightView2.setFitHeight(65);
        light2.setGraphic(lightView2);
        light2.setStyle("-fx-background-color: transparent");
        light2.setFocusTraversable(false);
        Label light2Label = new Label("Light 2");
        BorderPane light2Container = new BorderPane();
        light2Container.setAlignment(light2Label, Pos.CENTER);
        light2Container.setBottom(light2Label);
        light2Container.setCenter(light2);
        light2Label.setStyle("-fx-padding: 0 0 10 0");
        mainLeftBottom.getChildren().add(light2Container);
        mainLeftBottom.setHgrow(light2Container, Priority.ALWAYS);

        Slider slider = new Slider(0, 100, 1);
        slider.setShowTickMarks(false);
        slider.setShowTickLabels(false);
        slider.setOrientation(Orientation.VERTICAL);
        slider.setMajorTickUnit(0.25f);
        slider.setBlockIncrement(0.1f);
        slider.setFocusTraversable(false);
        VBox sliderVbox = new VBox();
        Label sliderLabel = new Label("Brightness");
        sliderVbox.getChildren().add(slider);
        sliderVbox.getChildren().add(sliderLabel);
        sliderLabel.setStyle("-fx-padding: 0 0 10 0");
        sliderVbox.setVgrow(slider, Priority.ALWAYS);
        sliderVbox.setMargin(slider, new Insets(5, 25, 5, 25));
        sliderVbox.setAlignment(Pos.CENTER);


        mainLeft.getChildren().add(mainLeftTop);
        mainLeft.setVgrow(mainLeftTop, Priority.ALWAYS);
        mainLeft.getChildren().add(mainLeftBottom);
        mainLeft.setVgrow(mainLeftBottom, Priority.ALWAYS);
        main.getChildren().add(mainLeft);
        main.setHgrow(mainLeft, Priority.ALWAYS);
        main.getChildren().add(sliderVbox);

        Scene scene = new Scene(main, 400, 250);
        jMetro.setScene(scene);
        stage.setScene(scene);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to the lower right corner of the visible bounds of the main screen
        stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 400);
        stage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 250);
        stage.setWidth(400);
        stage.setHeight(250);

        //Hide the stage when click outside of it
        stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                stage.hide();
            }
        });
    }

    /**
     * Sets up a system tray icon for the application.
     */
    private void addAppToTray() {
        try {
            // ensure awt toolkit is initialized.
            java.awt.Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support.
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                Platform.exit();
            }

            // set up a system tray icon.
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(ImageIO.read(new File("D:\\Documents\\Workspace\\Yeenbilight\\src\\main\\resources\\app_icon.png")));

            // if the user clicks on the tray icon, show the main app stage.
            trayIcon.addMouseListener(new MouseListener() {
                private void showStage() {
                    if (stage != null) {
                        stage.show();
                        stage.toFront();
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //Show the stage after a simple click
                    Platform.runLater(this::showStage);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            // if the user selects the default menu item (which includes the app name),
            // show the main app stage.
            java.awt.MenuItem openItem = new java.awt.MenuItem("hello, world");
            openItem.addActionListener(event -> Platform.runLater(this::showStage));

            // the convention for tray icons seems to be to set the default icon for opening
            // the application stage in a bold font.
            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            // to really exit the application, the user must go to the system tray icon
            // and select the exit option, this will shutdown JavaFX and remove the
            // tray icon (removing the tray icon will also shut down AWT).
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(event -> {
                notificationTimer.cancel();
                Platform.exit();
                tray.remove(trayIcon);
            });

            // setup the popup menu for the application.
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

            // create a timer which periodically displays a notification message.
//            notificationTimer.schedule(
//                    new TimerTask() {
//                        @Override
//                        public void run() {
//                            javax.swing.SwingUtilities.invokeLater(() ->
//                                    trayIcon.displayMessage(
//                                            "hello",
//                                            "The time is now " + timeFormat.format(new Date()),
//                                            java.awt.TrayIcon.MessageType.INFO
//                                    )
//                            );
//                        }
//                    },
//                    5_000,
//                    60_000
//            );

            // add the application tray icon to the system tray.
            tray.add(trayIcon);
        } catch (java.awt.AWTException | IOException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }

    /**
     * Shows the application stage and ensures that it is brought ot the front of all stages.
     */
    private void showStage() {
        if (stage != null) {
            stage.show();
            stage.toFront();
        }
    }

    public static void main(String[] args) throws IOException, java.awt.AWTException {
        // Just launches the JavaFX application.
        // Due to way the application is coded, the application will remain running
        // until the user selects the Exit menu option from the tray icon.
        launch(args);
    }
}