package securite;

import java.awt.*;
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import securite.model.Securite;
import securite.view.SecuriteOverviewController;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    // Pour gerer les drag and drop de l'interface BibliothequeRootLayout
    private double xOffSet = 0;
    private double yOffSet = 0;

    /**
     * The data as an observable list of history hashed words.
     */
    private ObservableList<Securite> historyData = FXCollections.observableArrayList();

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Returns the data as an observable list of Persons.
     * @return
     */
    public ObservableList<Securite> getHistoryData() {
        return historyData;
    }

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        historyData.add(new Securite("c45b473fa67c70e838dc1602fd0c285e8f24dcfb5f8d74026cec053833f0552a5887d44d10" +
                "e452e2e11ef22e31af739ede620f64c98512389f36ada6556fd14e","$54gM-meZeRp42I"));
        historyData.add(new Securite("f794a45c7ed4976ad9231821dcc64dbaec797c580ef5ddf8ba0a955c98b3eaff3a1b34974f" +
                "ad6bbd855a64330436438008a8aff365dc8f356774293604bedc0c","X^8=p*gQ&@Fe7K/"));
        historyData.add(new Securite("5521513879ab1e71c8aa922b056443e416772b620297feec29a476236b782a3bc02ec9cb59" +
                "4cef5f5ad491c2b5348f9f18b966299948c6d3e280e2fb84bd09c1","{P^$^#yf&LjHmmc"));
        historyData.add(new Securite("5b4a1519ec56b667e64343694e613ab55cef3be4c99d45928e4f460fb0d95b284053357c63" +
                "8888700cfada9c82aae13e306a63ed75193e291a53af30b3337d9b","Ft<|hSHnu^_NL:c"));
        historyData.add(new Securite("f340454485c7812cdc437b553e073c1a881096eba985980102fb1f3cfcc85b170c7093cfdb" +
                "e5c47c0e67bc65b8acc53aac7aa4a92e6cffcf7f13cad1efde27f2","%!?DsHf?}-=^-I5"));
        historyData.add(new Securite("22295f4bcfd059a4866079673b484fd26d003f012dac893ed6eca8ab1dacaf458d63197ff3" +
                "c3deb53831f56633d54cd055aafeeaff5cd5141b5ea5b786e280d1","Q{?qP,M8Pu;!Ncj"));
        historyData.add(new Securite("5a93f6541d1872bfb6478ae54f0f519cc9bef5b57bce42206d5ac8a6f547ba50d76304c8cf" +
                "435c2e519bbf5321164fe486031f7edcc00cca4bd3b79917f57d96","Ne*M(noc_kw_vQ1"));
        historyData.add(new Securite("4e7e327bb7692d09b33c3f60b4fd3e61beb0c32fe01a7b5b5be2fefd491fb1ec8b48b69aaf" +
                "1b143fc13b22be88ef4e916fe0f4a30908b9fce502b8d0e0d73d03","`Br+VyEf^</<9[V"));
        historyData.add(new Securite("ab1d55a6fc64f7cce967b74d3213b375cf9ef6f7cfcf240f60433c6eb475851a8e8ecdc50b" +
                "044559380e9843f23097bd7f81d8bfb76863455ebf5753f8f33703","106,opG'.b8Wa3,"));
        historyData.add(new Securite("e90fc04687f0293070721a1d5710d4a77d8c43317cc1714541b0f3abb2ae246a3b78ff9459" +
                "e1dad23ddc03c14dfbe10659ee124226b62c3abf0aa20f490cd8cd","5Q:=h\"z;L^Cb@Vb"));
    }


    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SecuriteRootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);

            // Reparametrage de la scene
            //primaryStage.initStyle(StageStyle.DECORATED.UNDECORATED);
            primaryStage.setResizable(false);
            //récuperer la dimension de l'écran et regle la position de l'application a 1/2 de l'espasce qu'il y a entre elle et l'ecran
            // en d'autres termes, centrage de l'application a l'ecran
            Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
            int longueur = (tailleMoniteur.width - (int)primaryStage.getWidth()) / 2 ;
            int hauteur = (tailleMoniteur.height - (int)primaryStage.getHeight()) / 2 ;
            primaryStage.setX(longueur);
            primaryStage.setY(hauteur);


            // gestion des drag and drop de l'interface de connexion
            rootLayout.setOnMousePressed(event -> {
                xOffSet = event.getSceneX();
                yOffSet = event.getSceneY();
            });
            rootLayout.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() - xOffSet);
                primaryStage.setY(event.getScreenY() - yOffSet);
            });


            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the securite overview inside the root layout.
     */
    public void showSecuriteOverview() {
        try {
            // Load Securite overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SecuriteOverview.fxml"));
            AnchorPane personOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            SecuriteOverviewController controller = (SecuriteOverviewController) loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("HASH WORD CRACKER");

        // Set the application icon.
        this.primaryStage.getIcons().add(new Image("file:ressource/logo1.jpg"));

        initRootLayout();

        showSecuriteOverview();
    }

    public static void main(String[] args) {
        launch(args);
    }
}