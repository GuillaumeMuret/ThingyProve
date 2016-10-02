/**
 * @file SetCardID.java
 * @brief The command setCardID send by the raspberry
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


package fr.prove.thingy.communication.distribution;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import fr.prove.thingy.BuildConfig;
import fr.prove.thingy.bus.BusProtocol;
import fr.prove.thingy.bus.BusResponse;
import fr.prove.thingy.bus.BusType;
import fr.prove.thingy.communication.protocole.ProtocoleVocabulary;

public class SetCardID implements ICommand {

    /**
     * Process from the interface ICommand
     * @param params : the params of the process
     * @return the bus response for the activity concerned
     */
    @Override
    public BusResponse execute(JSONObject params) {
        if (BuildConfig.DEBUG) Log.d("> setCardID", "debug");
        BusResponse response = new BusResponse(BusType.SET_CARD_ID);
        try {
            response.getData().putString(BusProtocol.BUS_DATA_UI_CARDID, params.getString(ProtocoleVocabulary.JSON_PARAMS_CARD_ID));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
}
