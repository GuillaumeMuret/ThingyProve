/**
 * @file Obsolete -> DistributorUiObsolete.java
 * @brief Obsolete -> decode the received message and distribute the message
 *
 * @version 1.0
 * @date 14/04/2016
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

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import fr.prove.thingy.BuildConfig;
import fr.prove.thingy.bus.BusProtocol;
import fr.prove.thingy.communication.protocole.ProtocoleVocabulary;
import fr.prove.thingy.communication.protocole.ProcessIn;
import fr.prove.thingy.bus.BusResponse;
import fr.prove.thingy.bus.BusType;
import fr.prove.thingy.utils.JsonUtils;


public class DistributorUiObsolete{
    /**
     * decode the params send by the rasp of the process setReady
     * @param params the received params
     * @return the bus type to post
     */
    private BusResponse decodeReady(JSONObject params){
        if (BuildConfig.DEBUG) Log.d("> setReady", "debug");
        try {
            if(params.getInt(ProtocoleVocabulary.JSON_PARAMS_STATE)==0) {
                return new BusResponse(BusType.READY_OK);
            }else{
                return new BusResponse(BusType.NOT_READY_KO);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // ERROR
        return new BusResponse(BusType.NOTHING);
    }

    /**
     * decode the params send by the rasp of the process setOperatorAccess
     * @param params the received params
     * @return the bus type to post
     */
    private BusResponse decodeOperatorAccess(JSONObject params){

        if (BuildConfig.DEBUG) Log.d("> setOperatorAccess", "debug");

        try {
            if(params.getInt(ProtocoleVocabulary.JSON_PARAMS_STATE)==0) {
                // allow access
                return new BusResponse(BusType.ALLOW_OPERATOR_ACCESS);
            }else if(params.getInt(ProtocoleVocabulary.JSON_PARAMS_STATE)==1) {
                // refuse access
                return new BusResponse(BusType.BAN_OPERATOR_ACCESS);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // ERROR
        return new BusResponse(BusType.NOTHING);
    }

    /**
     * decode the params send by the rasp of the process setOperatorInfo
     * @param params the received params
     * @return the bus type to post
     */
    private BusResponse decodeOperatorInfo(JSONObject params){
        if (BuildConfig.DEBUG) Log.d("> setOperatorInfo", "debug");
        BusResponse response;
        JSONObject infoTester = null;
        try {
            infoTester = new JSONObject(params.toString()).getJSONObject(ProtocoleVocabulary.JSON_PARAMS_INFO_TESTER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            response = new BusResponse(BusType.SET_OPERATOR_INFO);
            response.getData().putString(BusProtocol.BUS_DATA_UI_TESTERINFO, infoTester.toString());
            if (BuildConfig.DEBUG) Log.d("JSON", "user info : " + infoTester.toString());
        }catch(NullPointerException npe){
            npe.printStackTrace();
            response = new BusResponse(BusType.ALARM_UI);
        }
        return response;
    }

    /**
     * decode the params send by the rasp of the process setRecentHistory
     * @param params the received params
     * @return the bus type to post
     */
    private BusResponse decodeRecentHistory(JSONObject params){
        if (BuildConfig.DEBUG) Log.d("> setRecentHistory", "debug");
        BusResponse response = new BusResponse(BusType.SET_RECENT_HISTORY);
        response.getData().putString(BusProtocol.BUS_DATA_UI_RECENT_HISTORY,params.toString());
        return response;
    }

    /**
     * decode the params send by the rasp of the process setCardHistory
     * @param params the received params
     * @return the bus type to post
     */
    private BusResponse decodeCardHistory(JSONObject params){
        if (BuildConfig.DEBUG) Log.d("> setCardHistory", "debug");
        if(params.toString().equals(ProtocoleVocabulary.JSON_PARAMS_NOTHING)) {
            return new BusResponse(BusType.ERROR_CARD_HISTORY);
        }else{
            BusResponse response = new BusResponse(BusType.DISPLAY_CARD_HISTORY);
            response.getData().putString(BusProtocol.BUS_DATA_UI_CARDHISTORY, params.toString());
            return response;
        }
    }

    /**
     * decode the params send by the rasp of the process setCardExistance
     * @param params the received params
     * @return the bus type to post
     */
    private BusResponse decodeCardExistance(JSONObject params){
        if (BuildConfig.DEBUG) Log.d("> setCardExistance", "debug");
        try {
            if(params.getInt(ProtocoleVocabulary.JSON_PARAMS_STATE)==0) {
                return new BusResponse(BusType.EXISTANT_CARD);
            }else{
                return new BusResponse(BusType.NO_CARD);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // ERROR
        return new BusResponse(BusType.NOTHING);
    }

    /**
     * decode the params send by the rasp of the process setNominalListTests
     * @param params the received params
     * @return the bus type to post
     */
    private BusResponse decodeNominalListTests(JSONObject params){
        if (BuildConfig.DEBUG) Log.d("> setNominalListTests", "debug");
        BusResponse response = new BusResponse(BusType.SET_NOMINAL_LIST_TEST);
        response.getData().putString(BusProtocol.BUS_DATA_UI_NOMINAL_LIST_TEST, params.toString());
        return response;
    }

    /**
     * decode the params send by the rasp of the process setCardID
     * @param params the received params
     * @return the bus type to post
     */
    private BusResponse decodeCardID(JSONObject params){
        if (BuildConfig.DEBUG) Log.d("> setCardID", "debug");
        BusResponse response = new BusResponse(BusType.SET_CARD_ID);
        try {
            response.getData().putString(BusProtocol.BUS_DATA_UI_CARDID, params.getString(ProtocoleVocabulary.JSON_PARAMS_CARD_ID));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * decode the params send by the rasp of the process setStepTestEndResult
     * @param params the received params
     * @return the bus type to post
     */
    private BusResponse decodeStepTestEndResult(JSONObject params){
        if (BuildConfig.DEBUG) Log.d("> setStepTestEndResult", "debug");
        BusResponse response = new BusResponse(BusType.UPDATE_TEST);
        try {
            response.getData().putString(BusProtocol.BUS_DATA_UI_ID_SCENARIO,params.getString(ProtocoleVocabulary.JSON_PARAMS_ID_SCENARIO));
            response.getData().putString(BusProtocol.BUS_DATA_UI_ID_STEP_TEST,params.getString(ProtocoleVocabulary.JSON_PARAMS_ID_STEP_TEST));
            response.getData().putBoolean(BusProtocol.BUS_DATA_UI_TEST_RESULT,params.getInt(ProtocoleVocabulary.JSON_PARAMS_TEST_RESULT)==0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getData().putString(BusProtocol.BUS_DATA_UI_STEP_TEST_RESULT, params.toString());

        return response;
    }


    /**
     * decode the params send by the rasp of the process setStateSave
     * @param params the received params
     * @return the bus type to post
     */
    private BusResponse decodeStateSave(JSONObject params){
        if (BuildConfig.DEBUG) Log.d("> setStateSave", "debug");
        try {
            if(params.getInt(ProtocoleVocabulary.JSON_PARAMS_STATE)==0) {
                return new BusResponse(BusType.VALID_SAVE_VALIDATION_RESULT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new BusResponse(BusType.ALARM_UI);
    }


    /**
     * decode the params send by the rasp of the process setEndTest
     * @param params the received params
     * @return the bus type to post
     */
    private BusResponse decodeEndTest(JSONObject params){
        if (BuildConfig.DEBUG) Log.d("> setEndTest", "debug");
        return new BusResponse(BusType.END_TEST);
    }

    /**
     * decode the params send by the rasp of the process validCampaign
     * @param params the received params
     * @return the bus type to post
     */
    private BusResponse decodeValidCampaign(JSONObject params){
        if (BuildConfig.DEBUG) Log.d("> validCampaign", "debug");
        return new BusResponse(BusType.VALID_CAMPAIGN);
    }

    /**
     * process which decode the received message and separate the decode with their process
     * @param receivedMessage : the received message
     * @return the bus type to post
     */
    public BusResponse decode(String receivedMessage) {
        if (BuildConfig.DEBUG) Log.d("decodage", "debug");

        // substring with this format --> {messageJSON}
        String recMessSubString = null;
        int posA = receivedMessage.indexOf("{"), posB = receivedMessage.lastIndexOf("}");
        if (posA != -1 && posB != -1 && posA < posB) {
            recMessSubString = receivedMessage.substring(posA, posB + 1);
        }
        if (recMessSubString != null) {

            // Create a JSONObject with the receive Message
            JSONObject mainObj = JsonUtils.strToJson(recMessSubString);
            if (mainObj != null) {
                try {
                    JSONObject params = mainObj.getJSONObject(ProtocoleVocabulary.JSON_PARAMS);
                    Log.d(params.toString(),"debug");
                    //analyse the process of the received message
                    switch (mainObj.getString(ProtocoleVocabulary.JSON_PROCESS)) {

                        case ProcessIn.SET_READY:
                            return decodeReady(params);

                        case ProcessIn.SET_OPERATOR_ACCESS:
                            return decodeOperatorAccess(params);

                        case ProcessIn.SET_OPERATOR_INFO:
                            return decodeOperatorInfo(params);

                        case ProcessIn.SET_RECENT_HISTORY:
                            return decodeRecentHistory(params);

                        case ProcessIn.SET_CARD_HISTORY:
                            return decodeCardHistory(params);

                        case ProcessIn.SET_CARD_EXISTANCE:
                            return decodeCardExistance(params);

                        case ProcessIn.SET_NOMINAL_LIST_TESTS:
                            return decodeNominalListTests(params);

                        case ProcessIn.SET_CARD_ID:
                            return decodeCardID(params);

                        case ProcessIn.SET_STEP_TEST_END_RESULT:
                            return decodeStepTestEndResult(params);

                        case ProcessIn.SET_STATE_SAVE:
                            return decodeStateSave(params);

                        case ProcessIn.SET_END_TEST:
                            return decodeEndTest(params);

                        case ProcessIn.VALID_CAMPAIGN:
                            return decodeValidCampaign(params);

                        case ProcessIn.ALARM_UI:
                            if (BuildConfig.DEBUG) Log.d("> alarmUI", "debug");
                            return new BusResponse(BusType.ALARM_UI);

                        default:
                            if (BuildConfig.DEBUG) Log.d("error process distrib", "debug");
                            return new BusResponse(BusType.NOTHING);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            if (BuildConfig.DEBUG) Log.d("substring ERROR", "debug");
            if (BuildConfig.DEBUG) Log.d(receivedMessage, "debug");
            return new BusResponse(BusType.ALARM_UI);
        }
        return new BusResponse(BusType.NOTHING);
    }

}
