/**
 * @file CommunicationTestSuite.java
 * @brief Classe de tests unitaires de la classe Communication.
 * @version 1.0
 * @date 06/05/2016
 * @author Clarisse Girault (première version : Guillaume Muret)
 * @see fr.prove.thingy.communication.Communication
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


package fr.prove.thingy.communication.test;

import android.os.Bundle;
import android.util.Log;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import fr.prove.thingy.communication.Communication;
import fr.prove.thingy.communication.Postman;
import fr.prove.thingy.communication.protocole.ProcessOut;
import fr.prove.thingy.model.proxy.Brain;
import fr.prove.thingy.model.proxy.CardGuard;
import fr.prove.thingy.model.proxy.Historian;
import fr.prove.thingy.model.proxy.ScholarTest;
import fr.prove.thingy.model.proxy.TestManager;
import fr.prove.thingy.model.proxy.Tester;
import fr.prove.thingy.model.proxy.TesterGuard;

import static org.powermock.api.mockito.PowerMockito.mockStatic;




//Both @PrepareForTest and @RunWith are needed for `whenNew` to work
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Communication.class,ProcessOut.class,Postman.class,Log.class,Brain.class})

public class CommunicationTest extends TestCase {

    /**
     * Communication useful for launching tests.
     */
    private Communication communication;
    private String forBundle;


    /**
     * Mock useful to create communication.
     */
    @Mock
    Postman postamUIMock = PowerMockito.mock(Postman.class);
    Brain brainMock = PowerMockito.mock(Brain.class);
    Historian historianMock=PowerMockito.mock(Historian.class);
    CardGuard cardGuardMock=PowerMockito.mock(CardGuard.class);
    ScholarTest scholarTestMock=PowerMockito.mock(ScholarTest.class);
    TesterGuard testerGuardMock=PowerMockito.mock(TesterGuard.class);
    Tester testerMock=PowerMockito.mock(Tester.class);
    TestManager testActionTestManagerMock=PowerMockito.mock(TestManager.class);
    String stringMock=PowerMockito.mock(String.class);

    /**
     * create the communication
     * <p>
     * <p>do before launching the test</p>
     *
     * @throws Exception every exception.
     */
    @Before
    public void setUp() throws Exception {
        PowerMockito.whenNew(Postman.class).withNoArguments().thenReturn(postamUIMock); //we want a postam mock
        this.communication = new Communication();
        this.communication.brain=brainMock;
        this.communication.historian=historianMock;
        this.communication.cardGuard=cardGuardMock;
        this.communication.scholarTest=scholarTestMock;
        this.communication.testerGuard=testerGuardMock;
        this.communication.tester=testerMock;
        this.communication.testManager=testActionTestManagerMock;
        mockStatic(Log.class);
    }

    /**
     *
     * <p>
     * <p>Do after test.</p>
     *
     * @throws Exception every exception.
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }


    /**
     * Precondition
     */
    @Test
    public void testPreconditions() {
        assertNotNull(this.communication);
    }


    /**
     * Teste the invocation of the right methods.
     * <p>
     * <p>concerned method : public void processManager(ProcessOut procedureOut, Bundle b)</p>
     */
    @Test
    public void testProcessManager() {
        /*when(if (BuildConfig.DEBUG) Log.d(any(String.class), any(String.class))).thenAnswer(new Answer<Integer>() {
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                String tag = (String) invocation.getArguments()[0];
                String msg = (String) invocation.getArguments()[1];
                System.out.println("[" + tag + "] " + msg);
                return 0;
            }
        });*/

        ProcessOut aTester = ProcessOut.BRAIN_ASK_READY;
        Mockito.doNothing().when(this.communication.brain).encode(aTester,null); //we want the method do nothing
        this.communication.processManager(aTester,null);
        Mockito.verify(this.communication.brain, Mockito.times(1)).encode(aTester,null); // we check we ask the method once.

        aTester=ProcessOut.BRAIN_STOP_TC;
        Mockito.doNothing().when(this.communication.brain).encode(aTester,null);
        this.communication.processManager(aTester,null);
        Mockito.verify(this.communication.brain, Mockito.times(1)).encode(aTester,null);

        aTester=ProcessOut.HISTORIAN_ASK_RECENT_HISTORY;
        Mockito.doNothing().when(this.communication.historian).encode(aTester,null);
        this.communication.processManager(aTester,null);
        Mockito.verify(this.communication.historian,Mockito.times(1)).encode(aTester,null);

        Bundle b=new Bundle();
        b.putString(forBundle,"test");

        aTester=ProcessOut.HISTORIAN_ASK_CARD_HISTORY;
        Mockito.doNothing().when(this.communication.historian).encode(aTester,b);
        this.communication.processManager(aTester,b);
        Mockito.verify(this.communication.historian,Mockito.times(1)).encode(aTester,b);


        aTester=ProcessOut.HISTORIAN_ASK_VALID_SAVE;
        Mockito.doNothing().when(this.communication.historian).encode(aTester,b);
        this.communication.processManager(aTester,b);
        Mockito.verify(this.communication.historian,Mockito.times(1)).encode(aTester,b);



        aTester=ProcessOut.CARD_GUARD_ASK_CHECK_CARD_ID;
        Mockito.doNothing().when(this.communication.cardGuard).encode(aTester,b);
        this.communication.processManager(aTester,b);
        Mockito.verify(this.communication.cardGuard,Mockito.times(1)).encode(aTester,b);



        aTester=ProcessOut.CARD_GUARD_NEW_CARD;
        Mockito.doNothing().when(this.communication.cardGuard).encode(aTester,b);
        this.communication.processManager(aTester,b);
        Mockito.verify(this.communication.cardGuard,Mockito.times(1)).encode(aTester,b);


    }
}
