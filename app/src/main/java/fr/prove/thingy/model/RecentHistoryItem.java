/**
 * @file RecentHistoryItem.java
 * @brief The recent history item of a special group
 *
 * @version 1.0
 * @date 12/05/16
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

package fr.prove.thingy.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import fr.prove.thingy.BuildConfig;
import fr.prove.thingy.communication.protocole.ProtocoleVocabulary;
import fr.prove.thingy.constants.Number;
import fr.prove.thingy.utils.DateUtils;
import fr.prove.thingy.utils.JsonUtils;

public class RecentHistoryItem {
    /**
     * The type of card
     */
    private String cardType;

    /**
     * The id of the card
     */
    private String cardID;

    /**
     * The name of the operator
     */
    private String nameTester;

    /**
     * the date of the test
     */
    private Date date;

    /**
     * The validation of the test
     */
    private boolean validation;

    /**
     * The elapsed time to make the test
     */
    private int minTime;

    /**
     * Constructor of the item of the recent history group
     * @param strJSON : the string of the JSON object -> recent history
     */
    public RecentHistoryItem(String strJSON) {
        try {
            if (BuildConfig.DEBUG) Log.d("historyItem",strJSON);
            JSONObject obj = JsonUtils.strToJson(strJSON);
            this.date = DateUtils.parseStringToDate(obj.getString(ProtocoleVocabulary.JSON_PARAMS_DATE));
            this.cardType = obj.getString(ProtocoleVocabulary.JSON_PARAMS_CARD_TYPE);
            this.cardID = obj.getString(ProtocoleVocabulary.JSON_PARAMS_CARD_ID);
            this.validation = obj.getInt(ProtocoleVocabulary.JSON_PARAMS_VALIDATION) == Number.STATUS_VALIDATION;
            this.minTime = obj.getInt(ProtocoleVocabulary.JSON_PARAMS_MIN_TIME);
            this.nameTester = obj.getString(ProtocoleVocabulary.JSON_PARAMS_NAME_TESTER);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter of the card id
     * @return cardID
     */
    public String getCardID() {
        return cardID;
    }

    /**
     * Getter of the operator id
     * @return nameTester
     */
    public String getNameTester() {
        return nameTester;
    }

    /**
     * Getter of the date of the test
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter of the validation
     * @return validation
     */
    public boolean isValidated() {
        return validation;
    }

    /**
     * Getter of the elapsed time
     * @return minTime
     */
    public int getMinTime() {
        return minTime;
    }

    /**
     * Getter of the card type
     * @return cardType
     */
    public String getCardType() {
        return cardType;
    }
}
