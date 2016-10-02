/**
 * @file Distributor.java
 * @brief Instantiate the different process that could be called on the raspberry
 *
 * @version 1.0
 * @date 06/05/16
 * @author Guillaume MURET
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

import org.json.JSONObject;

import java.util.HashMap;

import fr.prove.thingy.bus.BusResponse;
import fr.prove.thingy.communication.distribution.AlarmUI;
import fr.prove.thingy.communication.distribution.ICommand;
import fr.prove.thingy.communication.distribution.SetCardExistance;
import fr.prove.thingy.communication.distribution.SetCardHistory;
import fr.prove.thingy.communication.distribution.SetCardID;
import fr.prove.thingy.communication.distribution.SetEndTests;
import fr.prove.thingy.communication.distribution.SetNominalListTests;
import fr.prove.thingy.communication.distribution.SetOperatorAccess;
import fr.prove.thingy.communication.distribution.SetOperatorInfo;
import fr.prove.thingy.communication.distribution.SetReady;
import fr.prove.thingy.communication.distribution.SetRecentHistory;
import fr.prove.thingy.communication.distribution.SetStateSave;
import fr.prove.thingy.communication.distribution.SetStepTestEndResult;
import fr.prove.thingy.communication.distribution.ValidCampaign;
import fr.prove.thingy.communication.protocole.ProcessIn;

public class Distributor {

    /**
     * The process that could be called on the raspberry
     */
    private HashMap<String, ICommand> commands;

    /**
     * Main constructor -> create the different commands
     */
    public Distributor() {
        // Create the object commands which will have all the process
        this.commands = new HashMap<>();

        // the different command
        this.commands.put(ProcessIn.ALARM_UI, new AlarmUI());
        this.commands.put(ProcessIn.SET_CARD_EXISTANCE, new SetCardExistance());
        this.commands.put(ProcessIn.SET_CARD_HISTORY, new SetCardHistory());
        this.commands.put(ProcessIn.SET_CARD_ID, new SetCardID());
        this.commands.put(ProcessIn.SET_END_TEST, new SetEndTests());
        this.commands.put(ProcessIn.SET_NOMINAL_LIST_TESTS, new SetNominalListTests());
        this.commands.put(ProcessIn.SET_OPERATOR_ACCESS, new SetOperatorAccess());
        this.commands.put(ProcessIn.SET_OPERATOR_INFO, new SetOperatorInfo());
        this.commands.put(ProcessIn.SET_READY, new SetReady());
        this.commands.put(ProcessIn.SET_RECENT_HISTORY, new SetRecentHistory());
        this.commands.put(ProcessIn.SET_STATE_SAVE, new SetStateSave());
        this.commands.put(ProcessIn.SET_STEP_TEST_END_RESULT, new SetStepTestEndResult());
        this.commands.put(ProcessIn.VALID_CAMPAIGN, new ValidCampaign());

    }

    /**
     * Main function that dispatch the process called
     * @param id_command : the id of the command called
     * @param params : the params of the command
     * @return the bus -> called the activity concerned
     */
    public BusResponse dispatch (String id_command, JSONObject params) {
        return commands.get(id_command).execute(params);
    }
}
