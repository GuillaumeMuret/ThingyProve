/**
 * @file HistoryItemHolder.java
 * @brief Model of History Item Holder (child)
 *
 * @version 1.0
 * @date 06/05/16
 * @author François LEPAROUX
 * @collab Guillaume MURET
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
package fr.prove.thingy.adapter.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import fr.prove.thingy.R;
import fr.prove.thingy.adapter.holder.ItemHolder;
import fr.prove.thingy.bus.BusProtocol;
import fr.prove.thingy.bus.BusResponse;
import fr.prove.thingy.bus.BusType;
import fr.prove.thingy.model.CardHistoryItem;
import fr.prove.thingy.model.History;
import fr.prove.thingy.model.RecentHistoryItem;


public class HistoryItemHolder extends ItemHolder {

    /**
     * Define the type of history (recent or card history)
     */
    private int typeOfHistory;

    /**
     * The refered object for the recent history
     */
    private RecentHistoryItem recentHistoryItem;

    /**
     * The refered object for the card history
     */
    private CardHistoryItem cardHistoryItem;

    /**
     * The remote adapter's context we can use
     */
    private Context context;


    /**
     * The bus event (for the communication with CardHistoryActivity)
     */
    private EventBus bus = EventBus.getDefault();

    /**
     * Constructor of the Items from the recent history model with the adapter
     * @param recentHistoryItem the recent history items
     * @param adapter the adapter
     * @param context the activity context
     */
    public HistoryItemHolder(RecentHistoryItem recentHistoryItem, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, Context context) {
        super(
                String.format(context.getResources().getString(R.string.recenthistory_name),
                        recentHistoryItem.getCardType(),
                        recentHistoryItem.getCardID()),
                String.format(context.getResources().getString(R.string.recenthistory_details),
                        (recentHistoryItem.isValidated()) ?
                                context.getResources().getString(R.string.recenthistory_text_succed) :
                                context.getResources().getString(R.string.recenthistory_text_failed),
                        recentHistoryItem.getMinTime(),
                        recentHistoryItem.getNameTester()),
                adapter);
        this.typeOfHistory= History.TYPE_RECENT_HISTORY;
        this.recentHistoryItem = recentHistoryItem;
        this.context = context;
    }

    /**
     * Constructor of the Items from the card history model with the adapter
     * @param cardHistoryItem the card history item
     * @param adapter the adapter
     * @param context the activity context
     */
    public HistoryItemHolder(CardHistoryItem cardHistoryItem, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, Context context) {
        super(
                String.format(context.getResources().getString(R.string.cardhistory_headeritem),
                        cardHistoryItem.getNumTest(),
                        (cardHistoryItem.isValidated()) ?
                                context.getResources().getString(R.string.cardhistory_text_valid) :
                                context.getResources().getString(R.string.cardhistory_text_invalid)),
                String.format(context.getResources().getString(R.string.cardhistory_details),
                        cardHistoryItem.getMinTime(),
                        cardHistoryItem.getNameTester()),
                adapter);
        this.typeOfHistory=History.TYPE_CARD_HISTORY;
        this.cardHistoryItem = cardHistoryItem;
        this.context = context;
    }

    /**
     * Process called when the adapter is create
     * @param vh the view holder
     */
    @Override
    public void bind(ViewHolder vh) {
        super.bind(vh);
        if (typeOfHistory == History.TYPE_RECENT_HISTORY) {
            if (recentHistoryItem.isValidated()) {
                vh.getImgIco().setImageResource(R.drawable.ic_valid);
                vh.getVwCircle().setBackgroundResource(R.drawable.circle_valid);
            } else {
                vh.getImgIco().setImageResource(R.drawable.ic_delete);
                vh.getVwCircle().setBackgroundResource(R.drawable.circle_error);
            }
        } else {
            vh.getImgIco().setImageResource(R.drawable.ic_delete);
            if (cardHistoryItem.isValidated()) {
                vh.getImgIco().setImageResource(R.drawable.ic_valid);
                vh.getVwCircle().setBackgroundResource(R.drawable.circle_valid);
            } else {
                vh.getImgIco().setImageResource(R.drawable.ic_delete);
                vh.getVwCircle().setBackgroundResource(R.drawable.circle_error);
            }
            // Create listener
            vh.getBackViewListener().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BusResponse response = new BusResponse(BusType.SHOW_DETAILS);
                    response.getData().putInt(BusProtocol.BUS_DATA_UI_CARDHISTORY_DETAILS,cardHistoryItem.getNumTest());
                    bus.post(response);
                }
            });
        }
    }
}
