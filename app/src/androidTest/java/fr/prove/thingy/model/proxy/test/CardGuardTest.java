/**
 * @file CardGuardTest.java
 * @brief unit test for CardGuard class
 * @version 1.0
 * @date 14/05/2016
 * @author Guillaume Muret
 * @see fr.prove.thingy.communication.proxy.CardGuard
 * @copyright :
 *	Copyright (c) 2016, PRØVE
 * 	All rights reserved.
 * 	Redistribution and use in source and binary forms, with or without
 * 	modification, are permitted provided that the following conditions are met:
 *
 * 	* Redistributions of source code must retain the above copyright
 * 	  notice, this list of conditions and the following disclaimer.
 * 	* Redistributions in binary form must reproduce the above copyright
 * 	  notice, this list of conditions and the following disclaimer in the
 * 	  documentation and/or other materials provided with the distribution.
 * 	* Neither the name of PRØVE, Angers nor the
 * 	  names of its contributors may be used to endorse or promote products
 *   	derived from this software without specific prior written permission.
 *
 * 	THIS SOFTWARE IS PROVIDED BY PRØVE AND CONTRIBUTORS ``AS IS'' AND ANY
 * 	EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * 	WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * 	DISCLAIMED. IN NO EVENT SHALL PRØVE AND CONTRIBUTORS BE LIABLE FOR ANY
 * 	DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * 	(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * 	LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * 	ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * 	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * 	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package fr.prove.thingy.model.proxy.test;


import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.lang.reflect.Method;

import fr.prove.thingy.bus.BusProtocol;
import fr.prove.thingy.communication.Postman;
import fr.prove.thingy.communication.protocole.ProcessOut;
import fr.prove.thingy.model.proxy.CardGuard;
import fr.prove.thingy.model.proxy.ProxyModel;
import fr.prove.thingy.utils.JsonUtils;


@RunWith(AndroidJUnit4.class)
public class CardGuardTest extends InstrumentationTestCase{
    /**
     * CardGuard use for tests.
     */
    private CardGuard cardGuardTest;


    /**
     * Create the cardGuard useful to the tests.
     *
     * Do before tests.
     *
     * @throws Exception all exception.
     *
     */
    @Override
    public void setUp() throws Exception
    {
        this.cardGuardTest  = new CardGuard(new Postman());

    }

    /**
     * No implanted.
     *
     * Do after the tests.
     *
     * @throws Exception all exception.
     *
     */
    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Precondition to execute the test
     */
    @Test
    public void testPreconditions()
    {
        assertNotNull(this.cardGuardTest);
    }

    /**
     * We test that we have the right format JSON
     * concerned method: Protected String toJSONString(ProcessOut procedureOut, Bundle data) 
     */
    @Test
    public void testToJSONStringAskCheckCardID() throws Exception {

		/* To access to the private methode. */
        String methodeTestee = "toJSONString";
        Class<?>[] parametresMethodetestee = {ProcessOut.class, Bundle.class};

        Method methode; // method to instance

        ProcessOut aTester= ProcessOut.CARD_GUARD_ASK_CHECK_CARD_ID;
        assertNotNull(this.cardGuardTest);

        // Creation of the parameters
        String cardIDaTester = "BB7894561230";
        Bundle bundleATester = new Bundle();
        bundleATester.putString(BusProtocol.BUS_DATA_CARDGUARD_CARDID,cardIDaTester);

        try {
            // try to access at the method
            methode = ProxyModel.class.getDeclaredMethod(methodeTestee, parametresMethodetestee);
            methode.setAccessible(true);
            String messageCree = (String) methode.invoke(this.cardGuardTest, aTester, bundleATester);

            // The different parameters of test
            String process = JsonUtils.strToJson(messageCree).getString("process");
            int numOwner = JsonUtils.strToJson(messageCree).getInt("numOwner");
            String cardID = JsonUtils.strToJson(messageCree).getJSONObject("params").getString("cardID");
            assertEquals(process,"askCheckCardID");
            assertEquals(numOwner,3);
            assertEquals(cardID,cardIDaTester);
        } catch (SecurityException e) {
            fail("Problème de sécurité sur la réflexion.");
        } catch (NoSuchMethodException e) {
            fail("Méthode testée non existante.");
        } catch (IllegalArgumentException e) {
            fail("Arguments de la méthode testée invalides.");
        }

    }

    /**
     * We test that we have the right format JSON
     * concerned method: Protected String toJSONString(ProcessOut procedureOut, Bundle data) 
     */
    @Test
    public void testToJSONStringNewCard() throws Exception {

		/* To access to the private methode. */
        String methodeTestee = "toJSONString";
        Class<?>[] parametresMethodetestee = {ProcessOut.class, Bundle.class};

        Method methode; // method to instance

        ProcessOut aTester= ProcessOut.CARD_GUARD_NEW_CARD;
        assertNotNull(this.cardGuardTest);

        try {
            // try to access at the method
            methode = ProxyModel.class.getDeclaredMethod(methodeTestee, parametresMethodetestee);
            methode.setAccessible(true);
            String messageCree = (String) methode.invoke(this.cardGuardTest, aTester, null);

            // The different parameters of test
            String process = JsonUtils.strToJson(messageCree).getString("process");
            int numOwner = JsonUtils.strToJson(messageCree).getInt("numOwner");
            String params = JsonUtils.strToJson(messageCree).getString("params");

            // Test
            assertEquals(process,"newCard");
            assertEquals(numOwner,3);
            assertEquals(params,"{}");
        } catch (SecurityException e) {
            fail("Problème de sécurité sur la réflexion.");
        } catch (NoSuchMethodException e) {
            fail("Méthode testée non existante.");
        } catch (IllegalArgumentException e) {
            fail("Arguments de la méthode testée invalides.");
        }
    }
}