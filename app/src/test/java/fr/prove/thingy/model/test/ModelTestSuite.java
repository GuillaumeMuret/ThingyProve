package fr.prove.thingy.model.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by François L. on 02/05/16.
 *
 * Classe de suite de tests unitaires pour le modèle
 */
public class ModelTestSuite {

    /**
     * Suite de tests pour la communication
     *
     * @return la suite de tests.
     *
     * @see TestSuite
     *
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite("Suite de tests pour le modèle de l'application Android Thingy.");

        suite.addTest(new TestSuite(TestItemTest.class));
        suite.addTest(new TestSuite(HistoryItemTest.class));

        return suite;
    }

}
