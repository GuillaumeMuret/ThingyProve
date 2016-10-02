/**
 * @file Communication.java
 * @brief Class of communication (create the postman and has all the proxys)
 *
 * @version 1.0
 * @date 14/04/2016
 * @author Guillaume Muret
 * @copyright
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
 *
 */

package fr.prove.thingy.communication;

import android.os.Bundle;

import java.io.IOException;

import fr.prove.thingy.communication.protocole.ProcessOut;
import fr.prove.thingy.model.proxy.Brain;
import fr.prove.thingy.model.proxy.CardGuard;
import fr.prove.thingy.model.proxy.Historian;
import fr.prove.thingy.model.proxy.ScholarTest;
import fr.prove.thingy.model.proxy.TestManager;
import fr.prove.thingy.model.proxy.Tester;
import fr.prove.thingy.model.proxy.TesterGuard;
import fr.prove.thingy.communication.protocole.Proxy;

public class Communication extends Thread
{

	/**
	 * The postman who send the messages for the UI
	 */
	public Postman postman;

	/**
	 * The Distributor of the different methode call after a reception
	 */
	public Distributor distributor;

	/**
	 * Proxy of the Brain (encode the methode to json format)
	 */
	public Brain brain;

	/**
	 * Proxy of the Historian (encode the methode to json format)
	 */
	public Historian historian;

	/**
	 * Proxy of the CardGuard (encode the methode to json format)
	 */
	public CardGuard cardGuard;

	/**
	 * Proxy of the ScholarTest (encode the methode to json format)
	 */
	public ScholarTest scholarTest;

	/**
	 * Proxy of the TesterGuard (encode the methode to json format)
	 */
	public TesterGuard testerGuard;

	/**
	 * Proxy of the Tester (encode the methode to json format)
	 */
	public Tester tester;

	/**
	 * Proxy of the TesterManager (encode the methode to json format)
	 */
	public TestManager testManager;

	/**
	 * Builder of the communication
	 */
	public Communication()
	{
		//The postman
		this.postman =new Postman();

		//All the proxy
		this.brain=new Brain(this.postman);
		this.historian=new Historian(this.postman);
		this.cardGuard=new CardGuard(this.postman);
		this.scholarTest=new ScholarTest(this.postman);
		this.testerGuard=new TesterGuard(this.postman);
		this.tester=new Tester(this.postman);
		this.testManager=new TestManager(this.postman);

		//The distributor
		this.distributor=new Distributor();
	}

	/**
	 * Process which ask to the postman to read a message
	 * @return the received message
     */
	public String readComMessage() throws IOException{
		if (this.getPostman().getStateSocket() == Postman.CONNECTED){
			return postman.readMessage();
		}
		return null;
	}

	/**
	 * Function called when the UI want to call a process on the raspberry
	 * @param procedureOut : the process call on the raspberry
	 * @param data : the data to send
     */
	public void processManager(ProcessOut procedureOut, Bundle data)
	{
		switch (procedureOut.owner) {
			case Proxy.BRAIN:
				this.brain.encode(procedureOut,data);
				break;

			case Proxy.HISTORIAN:
				this.historian.encode(procedureOut,data);
				break;

			case Proxy.CARD_GUARD:
				this.cardGuard.encode(procedureOut,data);
				break;

			case Proxy.SCHOLAR_TEST:
				this.scholarTest.encode(procedureOut,data);
				break;

			case Proxy.TESTER_GUARD:
				this.testerGuard.encode(procedureOut,data);
				break;

			case Proxy.TESTER:
				this.tester.encode(procedureOut,data);
				break;

			case Proxy.TEST_MANAGER:
				this.testManager.encode(procedureOut,data);
				break;

			default:
				// Nothing to do...
				break;
		}
	}

	/**
	 * Process called to close the socket
	 */
	public void closeSocket() {
		if (this.postman != null){
			this.postman.closeSocket();
		}
	}

	/**
	 * Getter of the postman
	 * @return the postman
     */
	public Postman getPostman(){
		return this.postman;
	}
}