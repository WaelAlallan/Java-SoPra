package de.wwu.sopra.gui.mta;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.steuerung.mta.ProbenSteuerung;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * diese Klasse dient zur Anzeige von Probeninformationen.
 * 
 * @author Wael Alallan
 */
public class ProbeninformationenEingelagertFormular {
	private Stage stage;
	private Scene scene, oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow buttonsBR;
	private StackedLabelTextFields eingabenSLTF;
	private Probe probe;

	/**
	 * Bei der Erzeugen dieses Objekts wird die Stage, Scene und die ausgewaelte
	 * Probe mit den uebergebenen Parametern erzeugt. Ausserdem wird die init- und
	 * die beabachteAktion-Methode aufgerufen. Des Weiteren wird die Scene gesetzt.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    - parent stage
	 * @param oldScene - die alte Scene
	 * @param probe    - die ausgewaelte Probe bei SuchergebnisseFormular
	 */
	public ProbeninformationenEingelagertFormular(Stage stage, Scene oldScene, Probe probe) {
		this.stage = stage;
		this.oldScene = oldScene;
		this.probe = probe;

		init();
		beobachteAktionen();
		stage.setScene(scene);
	}

	/**
	 * Initialisierungs-Methode zum initialisieren der GUI-ELemente.
	 */
	private void init() {
		kopfUUB = new UeberschriftUndButton("Probeninformation", "Hauptmen\u00FC", null);
		String[] labels = { "Biomaterialproben-Kategorie:", "Zugeh\u00F6rigeStudie:", "Visitennummer:",
				"Proben-Barcode", "Entnahmedatum", "Beh\u00E4ltertyp" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		String[] buttons = { "Probe dauerhaft entnehmen", "Probendaten l\u00F6schen und Probe dauerhaft entnehmen",
				"Probe kurzfristig entnehmen" };
		buttonsBR = new ButtonRow(buttons);

		eingabenSLTF.getTextField("Biomaterialproben-Kategorie:").setText(probe.getKategorie().toString());
		eingabenSLTF.getTextField("Zugeh\u00F6rigeStudie:").setText(probe.getVisite().getStudie().toString());
		eingabenSLTF.getTextField("Visitennummer:").setText(Integer.toString(probe.getVisite().getVisitenId()));
		eingabenSLTF.getTextField("Proben-Barcode").setText(Integer.toString(probe.getBarcode()));
		eingabenSLTF.getTextField("Entnahmedatum").setText(probe.getVisite().getDatum().toString());
		eingabenSLTF.getTextField("Beh\u00E4ltertyp").setText(probe.getTyp().toString());
		eingabenSLTF.ansehenModus();
		

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, eingabenSLTF.getGridPane());
		gp.addRow(2, buttonsBR.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Methode, die die Anwenderaktionen beobachtet und darauf reagiert. Je nachdem,
	 * was der Benutzer betaetigt, wird man entweder ins MTA-hauptmenue gelangen.
	 * oder kann man die ausgewaelte Probe kurzfristig, dauerhaft entnehmen oder
	 * auch loeschen, dazu wird ein BestaetigungsDialog angezeigt.
	 */
	private void beobachteAktionen() {

		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(event -> {
			MtaMenueFormular mmf = new MtaMenueFormular(stage, oldScene);
		});

		buttonsBR.getButton("Probe dauerhaft entnehmen").setOnAction(event -> {
			BestaetigungsDialoge bgui = new BestaetigungsDialoge(stage, scene, probe);
			bgui.dauerhaftEntnehmenDialog();
		});

		buttonsBR.getButton("Probendaten l\u00F6schen und Probe dauerhaft entnehmen").setOnAction(event -> {
			BestaetigungsDialoge bgui = new BestaetigungsDialoge(stage, scene, probe);
			bgui.dauerhaftEntnehmenUndLoeschenDialog();
		});

		buttonsBR.getButton("Probe kurzfristig entnehmen").setOnAction(event -> {
			BestaetigungsDialoge bgui = new BestaetigungsDialoge(stage, scene, probe);
			bgui.kurzfristigEntnehmenDialog();
			
		});

	}

}
