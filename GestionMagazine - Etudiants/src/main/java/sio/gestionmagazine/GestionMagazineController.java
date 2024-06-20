package sio.gestionmagazine;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sio.gestionmagazine.Model.Article;
import sio.gestionmagazine.Model.Magazine;
import sio.gestionmagazine.Services.ServiceArticle;
import sio.gestionmagazine.Services.ServiceMagazine;
import sio.gestionmagazine.Services.ServicePigiste;
import sio.gestionmagazine.Tools.ConnexionBDD;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GestionMagazineController implements Initializable {
    ServiceMagazine serviceMagazine;
    ServiceArticle serviceArticle;
    ServicePigiste servicePigiste;
    ConnexionBDD cnx;
    int idMag;
    @FXML
    private TableColumn tcNomMagazine;
    @FXML
    private TableColumn tcNomSpecialite;
    @FXML
    private TableColumn tcNomPigiste;
    @FXML
    private TableColumn tcNomArticle;
    @FXML
    private TableColumn tcNbPages;
    @FXML
    private TableColumn tcNumeroMagazine;
    @FXML
    private TableView<Article> tvArticles;
    @FXML
    private TableView<Magazine> tvMagazines;
    @FXML
    private TextField txtMontantArticle;
    @FXML
    private TextField txtMontantMagazine;
    @FXML
    private TableColumn tcNumeroArticle;
    @FXML
    private Button btnAjouterArticle;
    @FXML
    private ComboBox cboChoixPigiste;
    @FXML
    private Slider sldNbPages;
    @FXML
    private TextField txtNomArticle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cnx = new ConnexionBDD();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        serviceArticle = new ServiceArticle();
        servicePigiste = new ServicePigiste();
        serviceMagazine = new ServiceMagazine();
        tcNumeroMagazine.setCellValueFactory(new PropertyValueFactory<>("idMag"));
        tcNomMagazine.setCellValueFactory(new PropertyValueFactory<>("nomMag"));
        tcNomSpecialite.setCellValueFactory(new PropertyValueFactory<>("nomSpe"));

        tcNumeroArticle.setCellValueFactory(new PropertyValueFactory<>("idArticle"));
        tcNomPigiste.setCellValueFactory(new PropertyValueFactory<>("nomPigiste"));
        tcNomArticle.setCellValueFactory(new PropertyValueFactory<>("titreArticle"));
        tcNbPages.setCellValueFactory(new PropertyValueFactory<>("nbPages"));

        try {
            tvMagazines.setItems(FXCollections.observableArrayList(serviceMagazine.getAllMagazines()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void tblMagazinesClicked(Event event) throws SQLException {
        int idMag = tvMagazines.getSelectionModel().getSelectedItem().getIdMag();

        try {
            tvArticles.setItems(FXCollections.observableArrayList(serviceArticle.getAllArticlesbyMagazine(idMag)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txtMontantMagazine.setText(String.valueOf(serviceMagazine.getMontantMagazineById(idMag)));
        cboChoixPigiste.setItems(FXCollections.observableArrayList(servicePigiste.getAllPigistesbyMagazine(idMag)));
        cboChoixPigiste.getSelectionModel().selectFirst();

    }

    @FXML
    public void tvArticlesClicked(Event event) throws SQLException {
        int idArt = tvArticles.getSelectionModel().getSelectedItem().getIdArticle();
        txtMontantArticle.setText(String.valueOf(serviceArticle.getMontantArticleById(idArt)));

    }

    @FXML
    public void btnAjouterArticleClicked(Event event) throws SQLException {
        if (tvMagazines.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("");
            alert.setContentText("Veuillez selectionner un magazine");
            alert.showAndWait();
        }
        else if (txtNomArticle.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("");
            alert.setContentText("Veuillez saisir un titre pour votre article");
            alert.showAndWait();
        }
        else {
            idMag = tvMagazines.getSelectionModel().getSelectedItem().getIdMag();
            String nomArticle = txtNomArticle.getText();
            int nbPages = (int) sldNbPages.getValue();
            String nomPigiste = cboChoixPigiste.getSelectionModel().getSelectedItem().toString();
            serviceArticle.ajouterArticle(idMag, nomArticle, nbPages,nomPigiste);

            try {
                tvArticles.setItems(FXCollections.observableArrayList(serviceArticle.getAllArticlesbyMagazine(idMag)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            tvArticles.refresh();



        }

    }
}