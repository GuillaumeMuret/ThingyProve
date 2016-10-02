/**
 * @file TestManagerTest.java
 * @brief unit test for TestManager class
 * @version 1.0
 * @date 14/05/2016
 * @author Guillaume Muret
 * @see fr.prove.thingy.communication.proxy.TestManager
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
import android.support.test.runner.intent.IntentCallback;
import android.test.InstrumentationTestCase;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.lang.reflect.Method;
import java.util.ArrayList;

import fr.prove.thingy.bus.BusProtocol;
import fr.prove.thingy.communication.Postman;
import fr.prove.thingy.communication.protocole.ProcessOut;
import fr.prove.thingy.model.proxy.ProxyModel;
import fr.prove.thingy.model.proxy.TestManager;
import fr.prove.thingy.communication.protocole.ProtocoleVocabulary;
import fr.prove.thingy.utils.JsonUtils;


@RunWith(AndroidJUnit4.class)
public class TestManagerTest extends InstrumentationTestCase{
    /**
     * TestManager use for tests.
     */
    private TestManager testManagerTest;

    /**
     * Create the brain and the postmanUIMock (mock) useful to the tests.
     *
     * Do before tests.
     *
     * @throws Exception all exception.
     *
     */
    @Override
    public void setUp() throws Exception
    {
        this.testManagerTest  = new TestManager(new Postman());

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
        assertNotNull(this.testManagerTest);
    }

    /**
     * We test that we have the right format JSON
     * concerned method: Protected String toJSONString(ProcessOut procedureOut, Bundle data) 
     */
    @Test
    public void testToJSONStringSetStop() throws Exception {

		/* To access to the private methode. */
        String methodeTestee = "toJSONString";
        Class<?>[] parametresMethodetestee = {ProcessOut.class, Bundle.class};

        Method methode; // method to instance

        ProcessOut aTester= ProcessOut.TEST_MANAGER_SET_STOP;
        assertNotNull(this.testManagerTest);

        // Creation of the parameters
        Bundle bundleATester = new Bundle();
        int stateAtester = 0;
        bundleATester.putInt(BusProtocol.BUS_DATA_TEST_MANAGER_STOP,stateAtester);

        try {
            // try to access at the method
            methode = ProxyModel.class.getDeclaredMethod(methodeTestee, parametresMethodetestee);
            methode.setAccessible(true);
            String messageCree = (String) methode.invoke(this.testManagerTest, aTester, bundleATester);

            // The different parameters of test
            String process = JsonUtils.strToJson(messageCree).getString("process");
            int numOwner = JsonUtils.strToJson(messageCree).getInt("numOwner");
            int state = JsonUtils.strToJson(messageCree).getJSONObject("params").getInt("state");
            assertEquals(process,"setStop");
            assertEquals(numOwner,7);
            assertEquals(state,stateAtester);
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
    public void testToJSONStringSetCampaign() throws Exception {

		/* To access to the private methode. */
        String methodeTestee = "toJSONString";
        Class<?>[] parametresMethodetestee = {ProcessOut.class, Bundle.class};

        Method methode; // method to instance

        ProcessOut aTester= ProcessOut.TEST_MANAGER_SET_CAMPAIGN;
        assertNotNull(this.testManagerTest);

        // Creation of the bundle
        Bundle bundleATester = new Bundle();

        // Creation of the result of test
        int resultTestCommunication = 1;
        int resultTestMemory = 0;
        int resultTestAnalogInput = 1;
        int resultTestDigitalInput = 1;
        int resultTestRelay = 1;

        // Items into list
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<Integer> run = new ArrayList<>();

        ids.add(ProtocoleVocabulary.JSON_PARAMS_TEST_COMMUNICATION);
        ids.add(ProtocoleVocabulary.JSON_PARAMS_TEST_MEMORY);
        ids.add(ProtocoleVocabulary.JSON_PARAMS_TEST_ANALOG_INPUT);
        ids.add(ProtocoleVocabulary.JSON_PARAMS_TEST_DIGITAL_INPUT);
        ids.add(ProtocoleVocabulary.JSON_PARAMS_TEST_RELAY);

        run.add(resultTestCommunication);
        run.add(resultTestMemory);
        run.add(resultTestAnalogInput);
        run.add(resultTestDigitalInput);
        run.add(resultTestRelay);

        bundleATester.putStringArrayList(BusProtocol.BUS_DATA_TEST_MANAGER_ID_SCENARIOS, ids);
        bundleATester.putIntegerArrayList(BusProtocol.BUS_DATA_TEST_MANAGER_SHOULD_RUN, run);

        try {
            // try to access at the method
            methode = ProxyModel.class.getDeclaredMethod(methodeTestee, parametresMethodetestee);
            methode.setAccessible(true);
            String messageCree = (String) methode.invoke(this.testManagerTest, aTester, bundleATester);

            // The different parameters of test
            String process = JsonUtils.strToJson(messageCree).getString("process");
            int numOwner = JsonUtils.strToJson(messageCree).getInt("numOwner");

            // get the items
            JSONArray items = new JSONArray();
            items = JsonUtils.strToJson(messageCree).getJSONObject("params").getJSONArray("items");

            // TEST
            assertEquals(process,"setCampaign");
            assertEquals(numOwner,7);

            for(int i=0;i<items.length();i++){
                switch(items.getJSONObject(i).getString("IDScenario")){

                    case ProtocoleVocabulary.JSON_PARAMS_TEST_COMMUNICATION:
                        assertEquals(resultTestCommunication,
                                items.getJSONObject(i).getInt(ProtocoleVocabulary.JSON_PARAMS_RUN));
                        break;

                    case ProtocoleVocabulary.JSON_PARAMS_TEST_MEMORY:
                        assertEquals(resultTestMemory,
                                items.getJSONObject(i).getInt(ProtocoleVocabulary.JSON_PARAMS_RUN));
                        break;

                    case ProtocoleVocabulary.JSON_PARAMS_TEST_ANALOG_INPUT:
                        assertEquals(resultTestAnalogInput,
                                items.getJSONObject(i).getInt(ProtocoleVocabulary.JSON_PARAMS_RUN));
                        break;

                    case ProtocoleVocabulary.JSON_PARAMS_TEST_DIGITAL_INPUT:
                        assertEquals(resultTestDigitalInput,
                                items.getJSONObject(i).getInt(ProtocoleVocabulary.JSON_PARAMS_RUN));
                        break;

                    case ProtocoleVocabulary.JSON_PARAMS_TEST_RELAY:
                        assertEquals(resultTestRelay,
                                items.getJSONObject(i).getInt(ProtocoleVocabulary.JSON_PARAMS_RUN));
                        break;
                }
            }



        } catch (SecurityException e) {
            fail("Problème de sécurité sur la réflexion.");
        } catch (NoSuchMethodException e) {
            fail("Méthode testée non existante.");
        } catch (IllegalArgumentException e) {
            fail("Arguments de la méthode testée invalides.");
        }
    }
}