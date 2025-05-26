package de.wwu.sopra.gui.mta;

import java.util.ArrayList;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.model.Probenstatus;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * diese Klasse dient zur Anzeige der Ergebnissen von Probensuchen.
 * 
 * @author Wael Alallan
 */
public class BiomaterialprobenSuchergebnisseFormular {
	private Stage stage;
	private Scene scene, oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow buttonsBR;
	private ListViewUndButtons<Probe> listeLVUB;

	/**
	 * Bei der Erzeugen dieses Objekts wird die Stage, Scene und die Probenliste mit
	 * den uebergebenen Parametern erzeugt. Ausserdem wird die init- und die
	 * beabachteAktion-Methode aufgerufen. Des Weiteren wird die Scene gesetzt.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    - parent stage
	 * @param oldScene - parent scene
	 * @param probenAL - Ergebnisliste der Proben
	 */
	public BiomaterialprobenSuchergebnisseFormular(Stage stage, Scene oldScene, ArrayList<Probe> probenAL) {
		this.stage = stage;
		this.oldScene = oldScene;

		init(probenAL);
		beobachteAktionen();
		stage.setScene(scene);
	}

	/**
	 * Initialisierungs-Methode zum initialisieren der GUI-ELemente.
	 * 
	 * @param probenAL - die Probenergebnisliste wird uebergeben
	 */
	private void init(ArrayList<Probe> probenAL) {

		kopfUUB = new UeberschriftUndButton("Suchergebnisse", "Hauptmen\u00FC", null);
		String[] buttons = {"Abbrechen", "Probe aus Probe erstellen",  "erneut suchen" , "weiter" };

		buttonsBR = new ButtonRow(buttons);
		listeLVUB = GUIHelfer.<Probe>getStandardListViewUndButtons(null, null, probenAL, false, false);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(2, buttonsBR.getGridPane());
		gp.addRow(1, listeLVUB.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Methode, die die Anwenderaktionen beobachtet und darauf reagiert. Je nachdem,
	 * was der Benutzer betaetigt, wird man entweder ins MTA-hauptmenue oder in die
	 * vorherige Scene gelangen. oder kann man zur suchformular wieder
	 * zurueckkehren. Des Weiteren kann man je nach Probenstatus in das
	 * entsprechende Informationsformular gelangen.
	 */

	private void beobachteAktionen() {

		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(event -> {
			MtaMenueFormular mmf = new MtaMenueFormular(stage, scene);
		});
		buttonsBR.getButton("Abbrechen").setOnAction(event -> {
			stage.setScene(oldScene);
		});

		buttonsBR.getButton("weiter").setOnAction(event -> {
			Probe ausgewaelteProbe = listeLVUB.getSelektiertesObjekt();

			if (ausgewaelteProbe != null) {
				if (ausgewaelteProbe.getStatus() == Probenstatus.EINGELAGERT) {
					ProbeninformationenEingelagertFormular bef = new ProbeninformationenEingelagertFormular(stage,
							scene, ausgewaelteProbe);
				} else if (ausgewaelteProbe.getStatus() == Probenstatus.KURZFRISTIGENTNOMMEN) {
					ProbeninformationenKurzEntnommenFormular bef = new ProbeninformationenKurzEntnommenFormular(stage,
							scene, ausgewaelteProbe);

				} else if (ausgewaelteProbe.getStatus() == Probenstatus.DAUERHAFTENTNOMMEN) {
					ProbeninformationenEntferntFormular bef = new ProbeninformationenEntferntFormular(stage, scene,
							ausgewaelteProbe);
				}

			} else {
				GUIHelfer.FehlermeldungFormular("Fehler!", "Es wurde keine Probe angeklickt!",
						"Bitte klicken Sie eine Probe an, um diese ueber Weiter anzeigen lassen zu koennen.");
			}
		});

		buttonsBR.getButton("erneut suchen").setOnAction(event -> {
			BiomaterialprobenSuchenFormular bsf = new BiomaterialprobenSuchenFormular(stage, scene);
		});
		
		buttonsBR.getButton("Probe aus Probe erstellen").setOnAction( event -> { 
			Probe ausgewaehlteProbe = listeLVUB.getSelektiertesObjekt();
			if (ausgewaehlteProbe != null && ausgewaehlteProbe.getStatus() !=  Probenstatus.DAUERHAFTENTNOMMEN) {
				ProbeAusProbeErstellenFormular papef = new ProbeAusProbeErstellenFormular(stage, scene, ausgewaehlteProbe);
			}
			else if(ausgewaehlteProbe == null) {
				
				GUIHelfer.FehlermeldungFormular("Fehler!", "Es wurde keine Probe angeklickt!",
						"Bitte klicken Sie eine Probe an, um diese ueber Weiter anzeigen lassen zu koennen.");
			}
			else
			{
				GUIHelfer.FehlermeldungFormular("Fehler!", "Die Probe ist dauerhaft entnommen!",
						"Aus ihr koennen keine weiteren Proben hergestellt werden.");
			}
			
		});

	}
}
