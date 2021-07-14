package com.hayward.spring.events;

import de.daslaboratorium.machinelearning.classifier.Classifier;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;

import java.util.ArrayList;
import java.util.Arrays;
//using Bayes Classifier, load some examples and use it when parsing the meeting minutes and details
public class ClassificationRunner {
    public static final Classifier<String, String> bayes =
            new BayesClassifier<String, String>();
    static ArrayList<String> examples = new ArrayList<>();
    static ArrayList<String> correspondingtag = new ArrayList<>();

    public static void load() {
        for (int i = 0; i < examples.size(); i++) {
            bayes.learn(correspondingtag.get(i), Arrays.asList(examples.get(i).split("\\s")));
        }

    }

    static void add() {

        String[] example = new String[]{
                "Council Infrastructure Committee",
                "Hayward Youth Commission", "Hayward Library Commission",
                "City Council",
                "Continuation and Possible Expansion of Hayward Area Shoreline Planning Agency",
                "Coronavirus",
                "Police",
                "Sewers",
                "Trash Cans",
                "Public School",
                "Budget",
                "BART",
                "Technology",
                "Energy",
                "Green Energy",
                "Solar Panels",
                "Pre-K",
                "Housing",
                "Personnel Commission"
        };
        String[] tags = new String[]{
                "Infrastructure",
                "Youth",
                "Library",
                "General Meeting",
                "Environment",
                "Covid-19",
                "Safety",
                "Health/Safety",
                "Health/Safety",
                "Schools",
                "Economy",
                "Transportation",
                "Technology",
                "Green Energy",
                "Green Energy",
                "Green Energy",
                "Childcare",
                "Homelessness",
                "General Meeting"
        };

        for (int i = 0; i < (tags.length + example.length) / 2; i++) {
            examples.add(example[i]);
            correspondingtag.add(tags[i].toLowerCase());
        }
    }

    public String tag(String tag) {
        add();
        load();

        final String[] infastructure = "Council Infrastructure Committee".split("\\s");
        bayes.learn("infrastructure", Arrays.asList(infastructure));
        final String[] youth = "Hayward Youth Commission".split("\\s");
        bayes.learn("youth/learning", Arrays.asList(youth));
        final String[] library = "Library Commission".split("\\s");
        bayes.learn("youth/learning", Arrays.asList(library));
        final String[] general = "City Council".split("\\s");
        bayes.learn("general", Arrays.asList(general));
        final String[] airports = "Airport Committee".split("\\s");
        bayes.learn("infrastructure", Arrays.asList(airports));


        final String[] unknownText1 = tag.split("\\s");
        bayes.setMemoryCapacity(Integer.MAX_VALUE); // remember the last 500 learned classifications
        String classification = bayes.classify(Arrays.asList(unknownText1)).getCategory();
        bayes.setMemoryCapacity(Integer.MAX_VALUE); // remember the last 500 learned classifications
        //System.out.println(((BayesClassifier<String, String>) bayes).classifyDetailed(Arrays.asList(unknownText1)));
        return classification;


    }
}
