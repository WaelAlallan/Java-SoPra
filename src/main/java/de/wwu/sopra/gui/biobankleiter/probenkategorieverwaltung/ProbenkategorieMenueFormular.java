package de.wwu.sopra.gui.biobankleiter.probenkategorieverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.ProbenKategorie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse zum erstellen des GUI-Fensters der Probenkategorieuebersicht
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class ProbenkategorieMenueFormular {
	private Stage stage;
	private Scene scene, oldScene;

	private TextField suchenTF;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow filternBR;
	private StackedLabelTextFields eingabenSLTF;

	private ListViewUndButtons<ProbenKategorie> listeLVUB;

	/**
	 * Konstruktor zum erstellen der Klasse, bekommt die Stage und das alte Fenster
	 * uebergeben.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage              das aktive Fenster wird als Parameter uebergeben.
	 * @param oldScene           das alte Fenster
	 * @param probenKategorienAL ArrayList von probenkategorien welche im neuen
	 *                           Fenster angezeigt werden
	 */
	public ProbenkategorieMenueFormular(Stage stage, Scene oldScene, ArrayList<ProbenKategorie> probenKategorienAL) {
		this.stage = stage;
		this.oldScene = oldScene;

		init(probenKategorienAL);
		beobachteAktionen();
		stage.setScene(scene);
	}

	/**
	 * Initialisierungsmethode der Scene, hierzu werden die Hilfsklassen benutzt um
	 * die Klassenattribute zu initialisieren, welche dann automatisch GridPanes
	 * erstellen, welche dann in eine weitere GridPane gesetzt werden. Dann werden
	 * Gewichte gesetzt und und die Scene wird initialisiert damit das neue Fenster
	 * angezeigt werden kann.
	 * @param probenKategorienAL anzuzeigenden Probenkategorien in einer Arraylist
	 */
	void init(ArrayList<ProbenKategorie> probenKategorienAL) {

		kopfUUB = new UeberschriftUndButton("Probenkategorie\u00FCbersicht", "Hauptmen\u00FC", null);
		String[] buttonFilternLabel = { null, "Filtern" };
		filternBR = new ButtonRow(buttonFilternLabel);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, filternBR.getGridPane());

		listeLVUB = GUIHelfer.<ProbenKategorie>getStandardListViewUndButtons(null, null, probenKategorienAL, true,
				true);
		gp.addRow(2, listeLVUB.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Funktionalitaet der Buttons.
	 */
	void beobachteAktionen() {
		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(new EventHandler<ActionEvent>() {
			@Override /**
						 * Enqueues an action to be performed on a different thread than your own
						 */
			public void handle(ActionEvent e) {
				BiobankLeiterMenueFormular blmf = new BiobankLeiterMenueFormular(stage, scene);
			}
		});

		listeLVUB.getButton("Hinzuf\u00FCgen").setOnAction(new EventHandler<ActionEvent>() {
			@Override /**
						 * Enqueues an action to be performed on a different thread than your own
						 */
			public void handle(ActionEvent e) {
				ProbenkategorieHinzufuegenFormular paf = new ProbenkategorieHinzufuegenFormular(stage, scene);
			}
		});

		listeLVUB.getButton("Weiter").setOnAction(new EventHandler<ActionEvent>() {
			@Override /**
						 * Enqueues an action to be performed on a different thread than your own
						 */
			public void handle(ActionEvent e) {
				ProbenKategorie probenKategorie = listeLVUB.getSelektiertesObjekt();
				if (probenKategorie != null) {
					ProbenkategorieAnsehenFormular baf = new ProbenkategorieAnsehenFormular(stage, scene,
							probenKategorie);
				} else {
					GUIHelfer.AutomatischesFehlermeldungFormular("Auswahl", null);
				}
			}
		});

		filternBR.getButton("Filtern").setOnAction(new EventHandler<ActionEvent>() {
			@Override /**
						 * Enqueues an action to be performed on a different thread than your own
						 */
			public void handle(ActionEvent e) {
				ProbenkategorieFilternFormular pff = new ProbenkategorieFilternFormular(stage, scene);
			}
		});

	}

}
