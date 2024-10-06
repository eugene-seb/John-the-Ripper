package securite.view;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import securite.MainApp;
import securite.model.Securite;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;

public class SecuriteOverviewController {
    @FXML
    private TableView<Securite> historyTable;
    @FXML
    private TableColumn<Securite, String> hashColumn;
    @FXML
    private TableColumn<Securite, String> pwdColumn;

    @FXML
    private JFXTextField hashTextField;
    @FXML
    private JFXTextField fileTextField;
    @FXML
    private Label pwdLabel;

    @FXML
    private RadioButton buttonMD5 ;
    @FXML
    private RadioButton buttonSHA512 ;

    // Reference to the main application.
    private MainApp mainApp;


    /**
     * get algorithm kind
     * @return
     */
    private String choisirAlgo(){
        if(buttonMD5.isSelected())
            return "MD5" ;
        else{
            buttonSHA512.setSelected(true);
            return "SHA-512" ;
        }
    }

    /**
     * Called when the user clicks on the Genereted button.
     */
    @FXML
    private void handleGenereted() {
        if(buttonMD5.isSelected())
            hashTextField.setText(hacher(genererMDP(),choisirAlgo()));
        else  // Default hash genereted : SHA-512
            hashTextField.setText(hacher(genererMDP(),choisirAlgo()));

        pwdLabel.setText("No Matching");
    }

    /**
     * Called when the user clicks on the Folders button.
     */
    @FXML
    private void handleFolders() {
        FileChooser choix = new FileChooser();
        choix.setTitle("Select the Word List");
        choix.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files","*.txt"));

        File fichier = choix.showOpenDialog(mainApp.getPrimaryStage());
        if(fichier != null)
        {
            fileTextField.setText(fichier.getPath());
        }
    }

    /**
     * Called when the user clicks on the cancel button.
     */
    @FXML
    private void handleCancel() {
        hashTextField.setText("");
        fileTextField.setText("");
        buttonSHA512.setSelected(true);
        pwdLabel.setText("No Matching");
    }



    /**
     * Called when the user clicks on the Verified button.
     */
    @FXML
    private void handleVerified() {
        String mdpTmp = hashTextField.getText();
        String algo = choisirAlgo();
        if (fileTextField.getText() == null || fileTextField.getText().length() == 0)
            if(confirmWordListGenerated())
                fileTextField.setText("ressource/Word List.txt");
            else{
                pwdLabel.setText("No Matching");
                return ;
            }
        try {
            File fichier = new File(fileTextField.getText());
            FileReader fileR = new FileReader(fichier);
            BufferedReader bufferedR = new BufferedReader(fileR);
            String str; // lecteur de ligne
            String[] mots = null; // liste de mots de la ligne actuelle

            // Lecture de chaque ligne du fihier
            while ((str = bufferedR.readLine()) != null) {
                // Split des mots par " "
                mots = str.split(" ");

                // Parcours des mots de la ligne en cours un a un
                for (String mot : mots)
                    if (mdpTmp.matches(hacher(mot,algo))) {
                        pwdLabel.setText("Matching : " + mot);
                        Securite tempSecu = new Securite(mdpTmp,mot);
                        mainApp.getHistoryData().add(tempSecu);
                        bufferedR.close(); // close the current flux
                        return ;
                    } else {
                        pwdLabel.setText("No Matching");
                    }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Erreur : " + ex);
            pwdLabel.setText("No Matching");
        } catch (IOException ex) {
            System.out.println("Erreur : " + ex);
            pwdLabel.setText("No Matching");
        }
    }

    /**
     * Called when the user clicks on the Quit button.
     */
    @FXML
    private void handleQuit(){
        System.exit(0);
    }

    /**
     * Cette mothode permet de hacher un mot avec un algo au choix :
     * MD5, SHA, SHA-1, SHA-224, SHA-256, SHA-384, SHA-512
     * Son avantage est de ne pas se limite a un seul algorithme de hachege.
     * @param motAHacher
     * @param algoDeHachage
     * @return
     */
    private static String hacher(String motAHacher, String algoDeHachage)
    {
        byte[] hache = null ;
        StringBuilder sb = new StringBuilder();

        try{
            hache = MessageDigest.getInstance(algoDeHachage).digest(motAHacher.getBytes());

            // convertion
            for(int i = 0 ; i < hache.length ; i++)
                sb.append(Integer.toString((hache[i] & 0xff) + 0x100, 16).substring(1));
        }catch(NoSuchAlgorithmException ex){
            System.out.println("Erreur : " + ex);
        }
        return sb.toString() ;
    }

    /**
     * selection d'un nombre aleatoire en utilisant la correspondance
     * du code ASCII : si rand.nextInt(93) donne 0, le caractere
     * correspondant sera 33, sinon 33 + nbr generer par rand.
     * @return
     */
    private static String genererMDP(){
        Random rand = new Random();
        String str = "";
        for(int i = 0 ; i < 15 ; i++)
        {
            char c = (char)(rand.nextInt(93) + 33);
            str += c ;
        }
        return str ;
    }

    /**
     * Fills hash text fields and pwd label to show details about the Securite
     * If the specified securite is null, all text fields are cleared.
     *
     * @param secu the securite or null
     */
    private void showSecuriteDetails(Securite secu) {
        if (secu != null) {
            // Fill the labels with info from the Securite object.
            hashTextField.setText(secu.getHash());
            pwdLabel.setText("Matching : " + secu.getPwd());

            // we deselected radio button because we unknow which one had used
            buttonSHA512.setSelected(false);
            buttonMD5.setSelected(false);
        } else {
            // Person is null, remove all the text.
            hashTextField.setText("");
            pwdLabel.setText("");
        }
    }

    /**
     * Confirm la generation de la word list
     * @return
     */
    private Boolean confirmWordListGenerated(){
        // Nothing file in fileTextField
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Word List");
        alert.setHeaderText("No Word List Selected");
        alert.setContentText("Generated Word List ?");

        Optional<ButtonType> option = alert.showAndWait();

        if(option.get() == ButtonType.OK)
            return true ;
        else
            return false ;
    }

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SecuriteOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the history table with the two columns.
        hashColumn.setCellValueFactory(cellData -> cellData.getValue().hashProperty());
        pwdColumn.setCellValueFactory(cellData -> cellData.getValue().pwdProperty());

        // Clear Securite details.
        showSecuriteDetails(null);

        // Listen for selection changes and show the securtie details when changed.
        historyTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showSecuriteDetails(newValue));

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        historyTable.setItems(mainApp.getHistoryData());
    }
}