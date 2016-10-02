/**
 * @file ProxyModel.java
 * @brief which define the model of all the proxy
 *
 * @version 1.0
 * @date 10/05/2016
 * @author Guillaume Muret
 * @collab François LEPAROUX
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
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import fr.prove.thingy.BuildConfig;
import fr.prove.thingy.communication.Postman;
import fr.prove.thingy.communication.protocole.ProcessOut;
import fr.prove.thingy.communication.protocole.ProtocoleVocabulary;

public abstract class ProxyModel {

    /**
     * postman use to send and receive the message
     */
    public Postman postman;

    /**
     * Constructor of all the proxys
     * @param postman : the postman
     */
    public ProxyModel(Postman postman) {
        this.postman = postman;
    }

    /**
     * transform the params and the process into JSONObject and call the postman to send the message
     * @param procedureOut : the process to call
     */
    public void encode(ProcessOut procedureOut, Bundle data) {
        if (BuildConfig.DEBUG) Log.d("Encodage","debug");

        // Encyption
        String jsonString=this.toJSONString(procedureOut,data);

        // Tell handler to Send the Message
        this.postman.writeMessage(jsonString);
    }

    /**
     * encryption of the owner, function, params to create a Json String.
     * @param procedureOut : the process to call
     * @param data : the data
     * @return the json object convert into string
     */
    protected String toJSONString(ProcessOut procedureOut, Bundle data) {
        JSONObject mainObj = new JSONObject();
        JSONObject params = new JSONObject();

        try {
            mainObj.put(ProtocoleVocabulary.JSON_OWNER, procedureOut.owner);
            mainObj.put(ProtocoleVocabulary.JSON_NUMOWNER, procedureOut.numOwner);
            mainObj.put(ProtocoleVocabulary.JSON_PROCESS, procedureOut.process);
            mainObj.put(ProtocoleVocabulary.JSON_PARAMS, params);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();
    }

    /**
     * Getter of the postman
     * @return the postman
     */
    public Postman getPostman() {
        return postman;
    }
}
