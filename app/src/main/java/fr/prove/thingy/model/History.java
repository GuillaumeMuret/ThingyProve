/**
 * @file History.java
 * @brief The history displayed on the Activity (CardHistoryActivity or HomeActivity)
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

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import fr.prove.thingy.communication.protocole.ProtocoleVocabulary;
import fr.prove.thingy.utils.DateUtils;

public class History {

    /**
     * The different type of history
     */
    public static final int TYPE_RECENT_HISTORY=1;
    public static final int TYPE_CARD_HISTORY=2;

    /**
     * the history (recent or card)
     */
    private ArrayList<HistoryGrouper> historyGroupers;

    /**
     * The date of the creation of the card (for card history)
     */
    private Date dateCreation;

    /**
     * type of history (recent or card)
     */
    private int typeOfHistory;

    /**
     * The recent history groupers by date
     */
    public History(){
        historyGroupers = new ArrayList<>();
    }

    /**
     * Setter of the group of history by date
     * @param context : context of the activity
     * @param strHistory : the string of the history (recent or card)
     */
    public void setHistory(Context context, String strHistory,int typeOfHistory){
        // define the type of history
        this.typeOfHistory=typeOfHistory;

        // clear array before willing it
        historyGroupers.clear();
        try {
            // Create the Json object and Json array object
            JSONObject obj = new JSONObject(strHistory);
            JSONArray history = obj.getJSONArray(ProtocoleVocabulary.JSON_PARAMS_HISTORY);

            if(typeOfHistory==History.TYPE_CARD_HISTORY) {
                dateCreation = DateUtils.parseStringToDate(
                        obj.getJSONObject(ProtocoleVocabulary.JSON_PARAMS_CREATION).getString(ProtocoleVocabulary.JSON_PARAMS_DATE));
            }
            // Create the HistoryGrouper by date
            createHistoryGrouper(history,context);
            if(typeOfHistory==TYPE_RECENT_HISTORY) {
                // sort the recent History by date
                sortByDate();
            }else if (typeOfHistory==TYPE_CARD_HISTORY){
                // sort the card History by date and items
                sortCardHistory();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the history grouper
     * @param history : the json history
     * @param context : the activity context
     * @throws JSONException
     */
    private void createHistoryGrouper(JSONArray history, Context context) throws JSONException{
        // Array that refer which history has been done
        boolean[] historyDone = new boolean[history.length()];

        // init the array of history done (true -> the history item has been treated. Else -> false
        for(int i=0;i<history.length();i++) {
            historyDone[i]=false;
        }
        // Create all the history grouper
        for(int iteratorHistory=0;iteratorHistory<history.length();iteratorHistory++) {

            if(!historyDone[iteratorHistory]) {
                // Create the group I
                HistoryGrouper historyGrouperI = new HistoryGrouper(history.getJSONObject(iteratorHistory).getString(ProtocoleVocabulary.JSON_PARAMS_DATE),
                        history.getJSONObject(iteratorHistory).toString(),typeOfHistory);
                historyGrouperI.parseDateWithContext(context);

                historyGroupers.add(historyGrouperI);

                historyDone = createHistoryGrouperByDate(history,historyGrouperI,historyDone,iteratorHistory);

            }
        }
    }

    /**
     * called to create the history grouper sort by date
     * @param history : the json history
     * @param historyGrouperI : the history grouper of the iterator iteratorHistory
     * @param historyDone : the array that refer the history done
     * @param iteratorHistory : position of the history item
     * @return the history done changed
     * @throws JSONException
     */
    private boolean[] createHistoryGrouperByDate(JSONArray history,HistoryGrouper historyGrouperI, boolean [] historyDone,int iteratorHistory) throws JSONException{

        for (int j = iteratorHistory + 1; j < history.length(); j++) {
            Date dateJ = DateUtils.parseStringToDate(
                    history.getJSONObject(j).getString(ProtocoleVocabulary.JSON_PARAMS_DATE));

            // If dates are equals, we add the item to the group I
            if (historyGrouperI.getDateAsDate().compareTo(dateJ) == 0) {
                if(typeOfHistory==History.TYPE_RECENT_HISTORY) {
                    historyGrouperI.getRecentHistoryItems().add(new RecentHistoryItem(history.getJSONObject(j).toString()));
                }
                if(typeOfHistory==History.TYPE_CARD_HISTORY) {
                    historyGrouperI.getCardHistoryItems().add(new CardHistoryItem(history.getJSONObject(j).toString()));
                }
                historyDone[j] = true;
            }
        }
        return historyDone;
    }

    /**
     * Sort the history groupers by date
     */
    private void sortByDate(){
        for(int i=0;i<historyGroupers.size();i++) {
            for (int j = i+1; j < historyGroupers.size(); j++) {
                if(historyGroupers.get(i).getDateAsDate().compareTo(historyGroupers.get(j).getDateAsDate())<0){
                    // Exchange the two dates compared
                    HistoryGrouper tmp = historyGroupers.get(i);
                    historyGroupers.set(i,historyGroupers.get(j));
                    historyGroupers.set(j,tmp);
                }
            }
        }
    }

    /**
     * Sort the history groupers by date and by num of test
     */
    private void sortCardHistory(){
        //sort by date
        sortByDate();

        //sort by num of item
        for(int i=0;i<historyGroupers.size();i++) {
            if (historyGroupers.get(i).getCardHistoryItems().size() > 1){
                for (int j = 0; j < historyGroupers.get(i).getCardHistoryItems().size(); j++) {
                    for (int k = j+1; k <historyGroupers.get(i).getCardHistoryItems().size(); k++){
                        if(historyGroupers.get(i).getCardHistoryItems().get(j).getNumTest()<historyGroupers.get(i).getCardHistoryItems().get(k).getNumTest()){
                            // Exchange the two items compared
                            CardHistoryItem tmp = historyGroupers.get(i).getCardHistoryItems().get(j);
                            historyGroupers.get(i).getCardHistoryItems().set(j,historyGroupers.get(i).getCardHistoryItems().get(k));
                            historyGroupers.get(i).getCardHistoryItems().set(k,tmp);
                        }
                    }
                }
            }
        }
    }

    /**
     * Getter of the HistoryGroupers
     * @return : historyGroupers
     */
    public ArrayList<HistoryGrouper> getHistoryGroupers() {
        return historyGroupers;
    }

    /**
     * Getter of the type of history (recent or card)
     * @return : typeOfHistory
     */
    public int getTypeOfHistory() {
        return typeOfHistory;
    }

    /**
     * Getter of the date of the creation of the card
     * @return : dateCreation
     */
    public Date getDateCreation() {
        return dateCreation;
    }
}
