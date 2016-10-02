/**
 * @file ProcedureOut.java
 * @brief enum of all the process that could be called on the UI
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

package fr.prove.thingy.communication.protocole;

public enum ProcessOut{
    /**
     * Enum of all the process that could be applied on the raspberry
     */
    BRAIN_ASK_READY                    ("Brain",1,"askReady"),
    BRAIN_STOP_TC                      ("Brain",1,"stopTC"),
    HISTORIAN_ASK_RECENT_HISTORY       ("Historian",2,"askRecentHistory"),
    HISTORIAN_ASK_CARD_HISTORY         ("Historian",2,"askCardHistory"/*"iDResearchCard"*/),
    HISTORIAN_ASK_VALID_SAVE           ("Historian",2,"askValidSave"/*"validTest"*/),
    CARD_GUARD_ASK_CHECK_CARD_ID       ("CardGuard", 3,"askCheckCardID"/*"cardID"*/),
    CARD_GUARD_NEW_CARD                ("CardGuard", 3,"newCard"),
    SCHOLAR_TEST_ASK_NOMINAL_LIST_TEST ("ScholarTest",4,"askNominalListTest"),
    TESTER_GUARD_ASK_CHECK_LOG_TESTER  ("TesterGuard", 5,"askCheckLogTester"/*"operatorID"*/),
    TESTER_RUN_TESTS                   ("Tester",6,"runTests"),
    TEST_MANAGER_SET_CAMPAIGN          ("TestManager",7,"setCampaign"/*"myTestCampaign"*/),
    TEST_MANAGER_SET_STOP              ("TestManager",7,"setStop"/*"stop"*/);

    /**
     * The owner
     */
    public String owner;

    /**
     * The owner's num
     */
    public int numOwner;

    /**
     * The process called
     */
    public String process;

    /**
     * Constructor of the enum with owner, num owner and the process
     * @param owner : the owner
     * @param numOwner : the owner's num
     * @param process : the process called
     */
    ProcessOut(String owner,int numOwner,String process){
        this.owner=owner;
        this.numOwner=numOwner;
        this.process=process;
    }
}
