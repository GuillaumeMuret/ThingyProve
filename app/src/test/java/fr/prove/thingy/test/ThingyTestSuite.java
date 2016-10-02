package fr.prove.thingy.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import fr.prove.thingy.communication.test.CommunicationTestSuite;
import fr.prove.thingy.model.test.ModelTestSuite;
import fr.prove.thingy.adaptater.test.AdaptaterTestSuite;
import fr.prove.thingy.constants.test.ConstantsTestSuite;


/**
 *
 * /**
 * @file ThingyTestSuite.java
 * @brief Classe de lancement des Suites de test du programme Thingy.
 * @version 1.0
 * @date 29/04/2016
 * @author Clarisse Girault
 * @copyright cf:/se2017-equipea2/qualite/modeles : « License_BSD »
 *
 */

public class ThingyTestSuite {
    /**
     * Suite de tests.
     *
     * @return la suite de tests.
     *
     * @see junit.framework.TestSuite
     *
     */
    public static Test suite()
    {
        final TestSuite suite = new TestSuite("Suite de tests pour Thingy");

		/* suite de tests pour l'exemple de Thingy */
        suite.addTest(CommunicationTestSuite.suite());
       // suite.addTest(AdaptaterTestSuite.suite());
        suite.addTest(ModelTestSuite.suite());
        //suite.addTest(ConstantsTestSuite.suite());

        return suite;
    }
}












