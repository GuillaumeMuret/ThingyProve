package fr.prove.thingy.adaptater.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *
 * /**
 * @file AdaptaterTestSuite.java
 * @brief Classe de suite de tests unitaire pour l'adaptater du programme Thingy.
 * @version 1.0
 * @date 02/05/2016
 * @author Clarisse Girault
 * @copyright cf:/se2017-equipea2/qualite/modeles : « License_BSD »
 *
 */
public class AdaptaterTestSuite {
    /**
     * Suite de tests pour l'adaptater de Thingy.
     *
     * @return la suite de tests.
     *
     * @see junit.framework.TestSuite
     *
     */
    public static Test suite()
    {

        final TestSuite suite = new TestSuite("Suite de tests pour l'adaptater de Thingy.");
       // suite.addTest(new TestSuite(Acompléter.class));
        return suite;
    }
}
