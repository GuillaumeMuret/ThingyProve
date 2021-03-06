/**
 * @file Historian.java
 * @brief proxy of the Historian.
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

package fr.prove.thingy.model.proxy;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import fr.prove.thingy.bus.BusProtocol;
import fr.prove.thingy.communication.Postman;
import fr.prove.thingy.communication.protocole.ProcessOut;
import fr.prove.thingy.communication.protocole.ProtocoleVocabulary;
import fr.prove.thingy.communication.protocole.NameProcessOut;

public class Historian extends ProxyModel {

    /**
     * Constructor of the Historian
     * @param postman : the postman
     */
    public Historian(Postman postman) {
        super(postman);
    }


    /**
     * encryption of the owner, function, params to create a Json String.
     * @param procedureOut : the process to call
     * @param data : the data
     * @return the json object convert into string
     */
    @Override
    protected String toJSONString(ProcessOut procedureOut, Bundle data) {
        JSONObject mainObj = new JSONObject();
        JSONObject params = new JSONObject();
        try {
            mainObj.put(ProtocoleVocabulary.JSON_OWNER, procedureOut.owner);
            mainObj.put(ProtocoleVocabulary.JSON_NUMOWNER, procedureOut.numOwner);
            mainObj.put(ProtocoleVocabulary.JSON_PROCESS, procedureOut.process);
            switch (procedureOut.process) {
                case NameProcessOut.HISTORIAN_ASK_CARD_HISTORY:
                    params.put(ProtocoleVocabulary.JSON_PARAMS_CARD_ID, data.getString(BusProtocol.BUS_DATA_HISTORIAN_IDRESEARCHCARD));
                    break;

                case NameProcessOut.HISTORIAN_ASK_VALID_SAVE:
                    params.put(ProtocoleVocabulary.JSON_PARAMS_VALIDATION,data.getInt(BusProtocol.BUS_DATA_HISTORIAN_VALIDATION));
                    break;
            }
            mainObj.put(ProtocoleVocabulary.JSON_PARAMS, params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();
    }
}