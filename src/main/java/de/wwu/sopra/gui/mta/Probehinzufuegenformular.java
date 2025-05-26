package de.wwu.sopra.gui.mta;

import java.util.ArrayList;
import java.util.Optional;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.ProbenBehaelterTyp;
import de.wwu.sopra.model.ProbenKategorie;
import de.wwu.sopra.steuerung.biobankleiter.ProbenBehaeltertypHinzufuegenSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.ProbentypHinzufuegenSteuerung;
import de.wwu.sopra.steuerung.mta.ProbenSteuerung;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * GUI-Klasse, die zum Hinzufuegen von Proben dient.
 * 
 * @author Wael Alallan
 */
public class Probehinzufuegenformular {
	private Stage stage;
	private Scene scene, oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow buttonsBR1, buttonsBR2;
	private StackedLabelTextFields eingabenSLTF1, eingabenSLTF3;
	private ProbenKategorie probenKategorie;
	private ProbenBehaelterTyp behaelterTyp;

	/**
	 * Konstruktor. Hier wird die init- und die beabachteAktion-Methode aufgerufen.
	 * Des Weiteren wird die Scene gesetzt.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    - die aktive Stage
	 * @param oldScene - die alte Scene
	 */
	public Probehinzufuegenformular(Stage stage, Scene oldScene) {
		this.stage = stage;
		this.oldScene = oldScene;

		init();
		beobachteAktionen();
		stage.setScene(scene);
	}

	/**
	 * hier werden die GUI-Elemente initialisiert damit das Formular angezeigt weden
	 * kann.
	 */
	private void init() {
		kopfUUB = new UeberschriftUndButton("Probe neu anlegen", "Hauptmen\u00FC", null);
		String[] label1 = { "Visitennummer:", "Proben-Barcode" };
		String[] label3 = { "Probenkategorie:", "Probenbeh\u00E4ltertyp:" };

		eingabenSLTF1 = new StackedLabelTextFields(label1);
		eingabenSLTF3 = new StackedLabelTextFields(label3);
		eingabenSLTF3.ansehenModus();

		String[] buttons1 = { null, null, "Probenkategorie & Beh\u00E4ltertyp w\u00E4hlen" };
		buttonsBR1 = new ButtonRow(buttons1);
		String[] buttons2 = { null, "Probe anlegen", null };
		buttonsBR2 = new ButtonRow(buttons2);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, eingabenSLTF1.getGridPane());
		gp.addRow(3, buttonsBR1.getGridPane());
		gp.addRow(2, eingabenSLTF3.getGridPane());
		gp.addRow(4, buttonsBR2.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**

	 * Methode, die die Anwenderaktionen beobachtet und darauf reagiert. Je nachdem,
	 * was der Benutzer betaetigt, wird man entweder ins MTA-hauptmenue gelangen.
	 * Und bei richtigen Angeben wird eine Probe im System angelegt, sonst wird eine
	 * Fehlermeldung angezeigt
	 */
	private void beobachteAktionen() {
		ProbenSteuerung probenSt = new ProbenSteuerung();
		ProbentypHinzufuegenSteuerung phs = new ProbentypHinzufuegenSteuerung();
		ProbenBehaeltertypHinzufuegenSteuerung pbhs = new ProbenBehaeltertypHinzufuegenSteuerung();

		buttonsBR1.getButton("Probenkategorie & Beh\u00E4ltertyp w\u00E4hlen").setOnAction(event -> {
			ArrayList<ProbenKategorie> probenKategorieAL = phs.getProbenKategorie("");
			ArrayList<ProbenBehaelterTyp> probenbehaeltertypAL = pbhs.getProbenbehaeltertyp("", "", "", "", "");
			ProbenbehaeltertypUndKategorieWaehlenFormular pukwf = new ProbenbehaeltertypUndKategorieWaehlenFormular(
					probenKategorieAL, probenbehaeltertypAL);

			Optional<Pair<ProbenKategorie, ProbenBehaelterTyp>> auswahl = pukwf.showAndWait();

			if (auswahl.isPresent()) {
				if ((auswahl.get().getKey() != null) && (auswahl.get().getValue() != null)) {
					probenKategorie = auswahl.get().getKey();
					behaelterTyp = auswahl.get().getValue();
					eingabenSLTF3.getTextField("Probenkategorie:").setText(auswahl.get().getKey().toString());
					eingabenSLTF3.getTextField("Probenbeh\u00E4ltertyp:").setText(auswahl.get().getValue().toString());				

				}
			}
		});

		buttonsBR2.getButton("Probe anlegen").setOnAction(event -> {
			String visitenNummer = eingabenSLTF1.getText("Visitennummer:");
			String barcode = eingabenSLTF1.getText("Proben-Barcode");
		
			try {
				BestaetigungsDialoge bd = new BestaetigungsDialoge(stage, scene, null);
				String nachricht = probenSt.probeEintragen(probenKategorie, behaelterTyp, visitenNummer, barcode, null);
				bd.probenAnlegenDialog(nachricht);
				MtaMenueFormular mmf = new MtaMenueFormular(stage ,scene);
				
			} catch (Exception exception) {
				GUIHelfer.FehlermeldungFormular("Fehler!", "Fehler beim Hinzuf\u00FCgen einer Probe!",
						exception.getMessage());
			}
			
		});

		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(event -> {
			MtaMenueFormular mmf = new MtaMenueFormular(stage, scene);
		});

	}

}