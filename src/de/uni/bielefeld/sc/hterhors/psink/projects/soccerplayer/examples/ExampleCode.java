package de.uni.bielefeld.sc.hterhors.psink.projects.soccerplayer.examples;

import java.util.List;

import de.uni.bielefeld.sc.hterhors.psink.obie.core.ontology.interfaces.IOBIEThing;
import de.uni.bielefeld.sc.hterhors.psink.obie.core.tools.corpus.OBIECorpus;
import de.uni.bielefeld.sc.hterhors.psink.obie.core.tools.corpus.OBIECorpus.Instance;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.corpus.BigramCorpusProvider;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.evaluation.evaluator.CartesianSearchEvaluator;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.explorer.TemplateExplorer;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.run.param.EInstantiationType;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.run.param.OBIERunParameter;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.run.param.OBIERunParameter.OBIEParameterBuilder;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.utils.OBIEClassFormatter;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.variables.OBIEInstance;
import de.uni.bielefeld.sc.hterhors.psink.obie.ie.variables.OBIEState;
import de.uni.bielefeld.sc.hterhors.psink.obie.projects.soccerplayer.ontology.classes.BirthYear;
import de.uni.bielefeld.sc.hterhors.psink.obie.projects.soccerplayer.ontology.classes.GoalkeeperAssociationFootball;
import de.uni.bielefeld.sc.hterhors.psink.obie.projects.soccerplayer.ontology.classes.HerbieWilliams;
import de.uni.bielefeld.sc.hterhors.psink.obie.projects.soccerplayer.ontology.classes.RobbieHaw;
import de.uni.bielefeld.sc.hterhors.psink.obie.projects.soccerplayer.ontology.classes.Wales;
import de.uni.bielefeld.sc.hterhors.psink.projects.soccerplayer.ie.SoccerPlayerParameterQuickAccess;
import de.uni.bielefeld.sc.hterhors.psink.projects.soccerplayer.ie.SoccerPlayerProjectEnvironment;
import de.uni.bielefeld.sc.hterhors.psink.projects.soccerplayer.ie.templates.BirthYearTemplate;

/**
 * TODO: write documentation and comments!
 * 
 * @author hterhors
 *
 */
public class ExampleCode {

	public static void main(String[] args) {

//		printRawCorpus();

//		exploreEntities();

		evaluateEntities();

	}

	private static void printRawCorpus() {
		
		OBIECorpus rawCorpus = OBIECorpus
				.readRawCorpusData(SoccerPlayerProjectEnvironment.getInstance().getRawCorpusFile());

		for (Instance instance : rawCorpus.getInstances().values()) {

			System.out.println("________" + instance.documentName + "________");
			System.out.println(instance.documentContent);
			System.out.println("____________Template Annotation(s)____________");

			for (List<IOBIEThing> templateAnnotations : instance.annotations.values()) {
				for (IOBIEThing templateAnnotation : templateAnnotations) {
					System.out.println(OBIEClassFormatter.format(templateAnnotation));
				}

			}
			break;

		}
	}

	private static void evaluateEntities() {

		/**
		 * Ground truth Herbie
		 */
		HerbieWilliams goldHerbie = new HerbieWilliams();
		goldHerbie.setPosition(new GoalkeeperAssociationFootball());
		goldHerbie.setBirthYear(new BirthYear("2000"));

		
		/**
		 * Predicted Herbie
		 */
		HerbieWilliams predictedHerbie = new HerbieWilliams();
		predictedHerbie.setPosition(new GoalkeeperAssociationFootball());
		predictedHerbie.setBirthYear(new BirthYear("1999"));
		predictedHerbie.addBirthPlace(new Wales());

		
		/**
		 * Predicted Robbie
		 */
		RobbieHaw predictedRobbie = new RobbieHaw();
		predictedRobbie.setPosition(new GoalkeeperAssociationFootball());
		predictedRobbie.setBirthYear(new BirthYear("1999"));
		predictedRobbie.addBirthPlace(new Wales());

		/**
		 * Exact search
		 */
		CartesianSearchEvaluator evaluator = new CartesianSearchEvaluator();

//		System.out.println(evaluator.prf1(goldHerbie, predictedHerbie));
		System.out.println(evaluator.prf1(goldHerbie, predictedRobbie));
	}

	private static void exploreEntities() {
		OBIEParameterBuilder paramBuilder = getParameter();

		/**
		 * Initialize with empty state!
		 */
		paramBuilder.setInitializer(EInstantiationType.EMPTY);

		
		OBIERunParameter param = paramBuilder.build();

		BigramCorpusProvider corpusProvider = BigramCorpusProvider.loadCorpusFromFile(param);

		/*
		 * George_Howe_(footballer)
		 */
		OBIEInstance instance = corpusProvider.getTrainingCorpus().getInternalInstances().get(1);

		System.out.println("_________" + instance.getName() + "_________");
		System.out.println(instance.getContent());
		System.out.println("___________________________");

		/*
		 * print all birth years
		 */
		instance.getNamedEntityLinkingAnnotations().getAnnotations(BirthYear.class).forEach(System.out::println);

		/**
		 * Test explorer
		 */
		TemplateExplorer te = new TemplateExplorer(param);
//		TemplateCardinalityExplorer tce = new TemplateCardinalityExplorer(param);
//		SlotCardinalityExplorer sce = new SlotCardinalityExplorer(param);

		System.out.println("===========================");
		OBIEState state = new OBIEState(instance, param);
		System.out.println("Current state:");
		System.out.println(OBIEClassFormatter
				.format(state.getCurrentPrediction().getEntityAnnotations().iterator().next().getAnnotationInstance()));
		System.out.println("===========================");
		int size = 0;
		List<OBIEState> generatedClasses = te.getNextStates(state);

		for (OBIEState scioClass : generatedClasses) {
			System.out.println(scioClass);
			System.out.println("==================");
			size++;
		}
		System.out.println(size);
	}

	private static OBIEParameterBuilder getParameter() {
		OBIEParameterBuilder paramBuilder = SoccerPlayerParameterQuickAccess.getREParameter();

		paramBuilder.setEnvironment(SoccerPlayerProjectEnvironment.getInstance());
		paramBuilder.setCorpusDistributor(SoccerPlayerParameterQuickAccess.preDefinedCorpusDistributor.originDist());
		paramBuilder.addTemplate(BirthYearTemplate.class);

		return paramBuilder;
	}
}
