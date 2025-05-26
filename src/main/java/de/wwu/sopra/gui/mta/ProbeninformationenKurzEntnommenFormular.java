package de.wwu.sopra.gui.mta;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Probe;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * GUI-Klasse, die zur Anzeige von Probeninformationen dient.
 * 
 * @author wael Alallan
 */
public class ProbeninformationenKurzEntnommenFormular {
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
	public ProbeninformationenKurzEntnommenFormular(Stage stage, Scene oldScene, Probe probe) {
		this.stage = stage;
		this.oldScene = oldScene;
		this.probe = probe;

		init();
		beobachteAktionen();
		stage.setScene(scene);
	}

	/**
	 * hier werden die GUI-Elemente initialisiert damit das Formular angezeigt weden
	 * kann.
	 */
	private void init() {

		kopfUUB = new UeberschriftUndButton("Probeninformation", "Hauptmen\u00FC", null);
		String[] labels = { "Biomaterialproben-Kategorie:", "Zugeh\u00F6rigeStudie:", "Visitennummer:", "Proben-Barcode",
				"Entnahmedatum", "Beh\u00E4ltertyp"};

		eingabenSLTF = new StackedLabelTextFields(labels);
		String[] buttons = { "Probe dauerhaft entnehmen", "Probendaten l\u00F6schen und Probe dauerhaft entnehmen",
				"Probe wieder hinzuf\u00FCgen" };
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
	 * Methode, die die Anwenderaktionen beobachtet und darauf reagiert.
	 * Je nachdem, was der Benutzer betaetigt, wird man entweder ins MTA-hauptmenue gelangen.
	 * oder kann man die ausgewaehlte Probe wieder hinzufuegen.
	 */
	private void beobachteAktionen() {
		BestaetigungsDialoge bd = new BestaetigungsDialoge(stage, scene, probe);
		
		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(event -> { 
			MtaMenueFormular mmf = new MtaMenueFormular(stage, oldScene);
		});
		buttonsBR.getButton("Probe dauerhaft entnehmen").setOnAction(event -> { 
			GUIHelfer.FehlermeldungFormular("Fehler", "Die Probe konnte nicht dauerhaft entnommen werden", "Die Probe ist kurzfristig entnommen, sie muss erstmal eingelagert sein");
		});
		
		buttonsBR.getButton("Probendaten l\u00F6schen und Probe dauerhaft entnehmen").setOnAction(event -> { 
			GUIHelfer.FehlermeldungFormular("Fehler", "Die Probe konnte nicht gel\u00F6scht werden", "Die Probe ist kurzfristig entnommen, sie muss erstmal eingelagert sein");
		});

		buttonsBR.getButton("Probe wieder hinzuf\u00FCgen").setOnAction(event -> {
			bd.wiederEinlagernDialog();
		});

	}
}
