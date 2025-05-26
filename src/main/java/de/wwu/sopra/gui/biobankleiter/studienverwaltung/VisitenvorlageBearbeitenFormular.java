package de.wwu.sopra.gui.biobankleiter.studienverwaltung;

import java.util.Optional;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.ProbenVorlage;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.VisitenVorlage;
import de.wwu.sopra.steuerung.biobankleiter.VisitenvorlagenSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * GUI Klasse zum erstellen des Visitenvorlagen bearbeiten Fensters
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan, Cedric Vadder
 */
public class VisitenvorlageBearbeitenFormular {

	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private VisitenVorlage visitenVorlage;
	private Studie studie;
	private UeberschriftUndButton kopfUUB;
	private UeberschriftUndButton ueberKopfStudienName;
	private StackedLabelTextFields eingabenSLTF;
	private ListViewUndButtons<ProbenVorlage> listeLVUB;

	private ButtonRow buttonsBR;
	private GridPane gp;

	/**
	 * Der Konstruktor der Klasse
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage          das aktive Fenster wird uebergeben
	 * @param oldScene       die alte Scene wird uebergeben
	 * @param visitenVorlage die zugehoerige Visitenvorlage wird uebergeben
	 * @param studie         die zugehoerige Studie wird uebergeben
	 */
	public VisitenvorlageBearbeitenFormular(Stage stage, Scene oldScene, VisitenVorlage visitenVorlage, Studie studie) {
		this.stage = stage;
		this.oldScene = oldScene;
		this.visitenVorlage = visitenVorlage;
		this.studie = studie;
		init();
		beobachteAktionen();

		stage.setScene(scene);
	}

	/**
	 * Initialisierungsmethode der Scene, hierzu werden die Hilfsklassen benutzt um
	 * die Klassenattribute zu initialisieren, welche dann automatisch GridPanes
	 * erstellen, welche dann in eine weitere GridPane gesetzt werden. Dann werden
	 * Gewichte gesetzt und und die Scene wird initialisiert damit das neue Fenster
	 * angezeigt werden kann.
	 */
	private void init() {
		ueberKopfStudienName = new UeberschriftUndButton(studie.getName(), null, null, 20);
		kopfUUB = new UeberschriftUndButton("Visitenvorlage bearbeiten", "Hauptmen\u00FC", null);
		String[] labels = { "Name:", "Med. Daten (mit Komma getrennt):", "Zeitabstand in Tagen:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		eingabenSLTF.bearbeitenModus();
		eingabenSLTF.getTextField("Name:").setText(visitenVorlage.getName());
		// Eckige Klammer werden entfernt, um Dopplungen zu vermeiden
		String medizinischeDaten = visitenVorlage.getMedizinischeDatenBezeichnung().toString();
		String medizinischeDatenFormatiert = medizinischeDaten.substring(1, medizinischeDaten.length() - 1);
		eingabenSLTF.getTextField("Med. Daten (mit Komma getrennt):").setText(medizinischeDatenFormatiert);
		eingabenSLTF.getTextField("Zeitabstand in Tagen:")
				.setText("" + visitenVorlage.getZeitZurLetztenVisiteInTagen());
		eingabenSLTF.bearbeitenModus();

		String[] buttonLabels = { "Abbrechen", "L\u00F6schen", "Best\u00E4tigen" };
		buttonsBR = new ButtonRow(buttonLabels);

		listeLVUB = GUIHelfer.<ProbenVorlage>getStandardListViewUndButtons("Proben:", null,
				visitenVorlage.getProbenVorlage(), true, true);

		gp = new GridPane();
		gp.addRow(0, ueberKopfStudienName.getGridPane());
		gp.addRow(1, kopfUUB.getGridPane());
		gp.addRow(2, eingabenSLTF.getGridPane());
		gp.addRow(3, listeLVUB.getGridPane());
		gp.addRow(4, buttonsBR.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, new float[] { 5.0f, 15.0f, 25.0f, 40.0f, 15.0f });
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Buttons erhalten ihre Funktionalitaet
	 */
	private void beobachteAktionen() {
		buttonsBR.getButton("Abbrechen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				VisitenvorlageAnsehenFormular vvaF = new VisitenvorlageAnsehenFormular(stage, scene, visitenVorlage,
						studie);
			}
		});

		buttonsBR.getButton("L\u00F6schen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				try {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setHeaderText("Bitte best\u00e4tigen!");
					alert.setContentText("Sind sie sicher, dass die Visitenvorlage gel\u00f6scht werden soll?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						VisitenvorlagenSteuerung vvS = new VisitenvorlagenSteuerung();
						vvS.visitenvorlageLoeschen(studie, visitenVorlage);
						StudieBearbeitenFormular sbF = new StudieBearbeitenFormular(stage, scene, studie);
					}
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Loeschen", exception);
				}
			}
		});

		buttonsBR.getButton("Best\u00E4tigen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				VisitenvorlagenSteuerung vvS = new VisitenvorlagenSteuerung();
				try {
					vvS.visitenvorlageBearbeiten(studie, visitenVorlage, eingabenSLTF.getTextField("Name:").getText(),
							eingabenSLTF.getTextField("Med. Daten (mit Komma getrennt):").getText(),
							eingabenSLTF.getTextField("Zeitabstand in Tagen:").getText());
					// Auch wenn man dabei ist eine neue Studie anzulegen, kommt man zum
					// StudieBearbeitenFormular
					// Sonst muesste gespeichert werden, ob ein Studie bearbeitet oder angelegt wird
					StudieBearbeitenFormular sbF = new StudieBearbeitenFormular(stage, scene, studie);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Bearbeiten", exception);
				}

			}
		});

		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				BiobankLeiterMenueFormular bmf = new BiobankLeiterMenueFormular(stage, scene);
			}
		});

		listeLVUB.getButton("Weiter").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				VisitenvorlagenSteuerung vvS = new VisitenvorlagenSteuerung();
				try {
					if (listeLVUB.getSelektiertesObjekt() != null) {
						VisitenVorlage bearbeitet = vvS.visitenvorlageBearbeiten(studie, visitenVorlage,
								eingabenSLTF.getTextField("Name:").getText(),
								eingabenSLTF.getTextField("Med. Daten (mit Komma getrennt):").getText(),
								eingabenSLTF.getTextField("Zeitabstand in Tagen:").getText());
						ProbenvorlageAnsehenFormular pvaF = new ProbenvorlageAnsehenFormular(stage, scene, studie,
								bearbeitet, listeLVUB.getSelektiertesObjekt());
					} else {
						throw new IllegalArgumentException("Keine Probenvorlage ausgewaehlt.");
					}
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Auswahl", exception);
				}

			}
		});

		listeLVUB.getButton("Hinzuf\u00FCgen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				VisitenvorlagenSteuerung vvS = new VisitenvorlagenSteuerung();
				try {
					VisitenVorlage bearbeitet = vvS.visitenvorlageBearbeiten(studie, visitenVorlage,
							eingabenSLTF.getTextField("Name:").getText(),
							eingabenSLTF.getTextField("Med. Daten (mit Komma getrennt):").getText(),
							eingabenSLTF.getTextField("Zeitabstand in Tagen:").getText());
					ProbenvorlageHinzufuegenFormular phF = new ProbenvorlageHinzufuegenFormular(stage, scene,
							bearbeitet, studie);

				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Hinzufuegen", exception);
				}

			}
		});

	}

}
