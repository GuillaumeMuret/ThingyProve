package fr.prove.thingy.constants.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *
 * /**
 * @file AdaptaterTestSuite.java
 * @brief Classe de suite de tests unitaire pour le package constants du programme Thingy.
 * @version 1.0
 * @date 02/05/2016
 * @author Clarisse Girault
 * @copyright cf:/se2017-equipea2/qualite/modeles : « License_BSD »
 *
 */
public class ConstantsTestSuite {
    /**
     * Suite de tests pour le package constants de Thingy App.
     *
     * @return la suite de tests.
     *
     * @see junit.framework.TestSuite
     *
     */
    public static Test suite()
    {

        final TestSuite suite = new TestSuite("Suite de tests pour constants de Thingy.");
        // suite.addTest(new TestSuite(Acompléter.class));
        return suite;
    }
}





