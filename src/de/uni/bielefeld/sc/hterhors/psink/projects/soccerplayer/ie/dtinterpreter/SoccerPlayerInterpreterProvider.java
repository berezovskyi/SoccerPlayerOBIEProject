package de.uni.bielefeld.sc.hterhors.psink.projects.soccerplayer.ie.dtinterpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import de.uni.bielefeld.sc.hterhors.psink.obie.ie.dtinterpreter.IDatatypeInterpretation;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.dtinterpreter.IInterpreter;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.dtinterpreter.INumericInterpreter;
import de.uni.bielefeld.sc.hterhors.psink.obie.projects.soccerplayer.ontology.classes.BirthYear;
import de.uni.bielefeld.sc.hterhors.psink.obie.projects.soccerplayer.ontology.interfaces.ISoccerPlayerThing;
import de.uni.bielefeld.sc.hterhors.psink.projects.soccerplayer.ie.dtinterpreter.impl.BirthYearInterpreter;

public class SoccerPlayerInterpreterProvider implements IInterpreter<ISoccerPlayerThing> {

	private static SoccerPlayerInterpreterProvider instance = null;

	public static SoccerPlayerInterpreterProvider getInstance() {

		if (instance == null)
			instance = new SoccerPlayerInterpreterProvider();

		return instance;
	}

	@Override
	public List<IDatatypeInterpretation> interpret(String textMention) {
		if (textMention == null || textMention.isEmpty())
			return null;
		/**
		 * TODO: Add more.
		 */
		List<IDatatypeInterpretation> interpretations = new ArrayList<>();

		INumericInterpreter semantics1 = new BirthYearInterpreter.Builder().interprete(textMention).build()
				.normalize();
		if (semantics1.exists()) {
			interpretations.add(semantics1);
		}

		return interpretations;
	}

	@Override
	public IDatatypeInterpretation interpret(Class<? extends ISoccerPlayerThing> classType, Matcher matcher) {

		IDatatypeInterpretation semantics = null;
		if (BirthYear.class == classType) {
			semantics = new BirthYearInterpreter.Builder().interprete(matcher.group()).build().normalize();
		} else {
			throw new IllegalArgumentException("Unknown data type for: " + classType.getSimpleName());
		}

		if (semantics != null && semantics.exists()) {
			return semantics;
		}
		return semantics;
	}

	@Override
	public IDatatypeInterpretation interpret(Class<? extends ISoccerPlayerThing> classType, final String textMention) {

		final IDatatypeInterpretation semantics;
		if (textMention == null || textMention.isEmpty())
			return null;

		if (BirthYearInterpreter.class == classType) {
			semantics = new BirthYearInterpreter.Builder().interprete(textMention).build().normalize();
		} else {
			throw new IllegalArgumentException(
					"Unknown data type for(" + textMention + "): " + classType.getSimpleName());
		}
		return semantics;
	}

}
