package de.uni.bielefeld.sc.hterhors.psink.projects.soccerplayer.ie.templates;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.uni.bielefeld.sc.hterhors.psink.obie.ie.run.param.OBIERunParameter;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.templates.AbstractOBIETemplate;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.templates.scope.OBIEFactorScope;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.variables.EntityAnnotation;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.variables.OBIEState;
import de.uni.bielefeld.sc.hterhors.psink.obie.projects.soccerplayer.ontology.interfaces.ISoccerPlayer;
import de.uni.bielefeld.sc.hterhors.psink.projects.soccerplayer.ie.templates.EmptyTemplate.Scope;
import factors.Factor;

/**
 * This is an empty template that serves as code-template to create new ones.
 * 
 * @author hterhors
 *
 */
public class EmptyTemplate extends AbstractOBIETemplate<Scope> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = LogManager.getFormatterLogger(EmptyTemplate.class.getName());

	public EmptyTemplate(OBIERunParameter parameter) {
		super(parameter);
	}

	/**
	 * Define a factor scope for this template
	 * 
	 * @author hterhors
	 *
	 */
	class Scope extends OBIEFactorScope {

		/*
		 * TODO: add variables for feature computation.
		 */

		public Scope(AbstractOBIETemplate<Scope> template) {
			super(template);
		}

	}

	@Override
	public List<Scope> generateFactorScopes(OBIEState state) {
		List<Scope> factors = new ArrayList<>();

		/*
		 * For all soccer player in the document create a new scope.
		 */
		for (EntityAnnotation entityAnnotation : state.getCurrentPrediction().getEntityAnnotations()) {

			ISoccerPlayer soccerPlayer = ((ISoccerPlayer) entityAnnotation.getAnnotationInstance());
			/*
			 * TODO: get variables and pass it to the scope.
			 */

			final Scope scope = new Scope(this);

			factors.add(scope);
		}

		return factors;
	}

	@Override
	public void computeFactor(Factor<Scope> factor) {

		Scope scope = factor.getFactorScope();

		/*
		 * TODO: Get variables from scope and compute features. Add features to feature
		 * vector.
		 */

//		factor.getFeatureVector().set(FEATURE NAME, TRUE/FALSE/DOUBLE);

	}

}
