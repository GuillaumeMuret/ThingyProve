/**
 * @file Brain.java
 * @brief The screen of the card history
 * @version 1.0
 * @date 06/05/2016
 * @author Guillaume Muret
 * @copyright Copyright (c) 2016, PRØVE
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <p/>
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of PRØVE, Angers nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY PRØVE AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL PRØVE AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package fr.prove.thingy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import fr.prove.thingy.adapter.history.ExpandableHistoryAdapter;
import fr.prove.thingy.adapter.test.ExpandableTestAdapter;
import fr.prove.thingy.bus.BusProtocol;
import fr.prove.thingy.constants.Fonts;
import fr.prove.thingy.constants.Number;
import fr.prove.thingy.bus.BusResponse;
import fr.prove.thingy.model.CardHistoryItem;
import fr.prove.thingy.model.DataStore;
import fr.prove.thingy.model.History;
import fr.prove.thingy.utils.DateUtils;

public class CardHistoryActivity extends AppCompatActivity {

    //State for the screen Card history :
    /**
     * Wait an answer from the raspberry
     */
    private static final int MODE_HISTORY_WAIT_CARD_HISTORY = 0;

    /**
     * Error in the history
     */
    private static final int MODE_HISTORY_ERROR = 1;

    /**
     * History with no details
     */
    private static final int MODE_HISTORY_MAIN = 2;

    /**
     * History with details
     */
    private static final int MODE_HISTORY_DETAIL = 3;


    // Views
    /**
     * The list to display on the activity
     */
    private RecyclerView recyList;

    /**
     * The Title of the screen
     */
    private TextView tvAppName;

    /**
     * The subtitle of the screen
     */
    private TextView tvAppDesc;

    /**
     * The left arrow on the screen
     */
    private ImageView toolArrowLeftIco;

    /**
     * The progress bar when the rasp search the card history
     */
    private ProgressBar toolSearchProgress;

    /**
     * The card history mode
     */
    private Integer cardHistoryMode;

    /**
     * The context of the activity
     */
    private Context context;

    /**
     * The fonts of the activity
     */
    private Typeface fontThin;

    /**
     * The card history
     */
    private History cardHistory;

    /**
     * The detail of the card history
     */
    private CardHistoryItem detailHistory;

    /**
     * Adapter use for the card history
     */
    private ExpandableHistoryAdapter adapterHistory;

    /**
     * Adapter use for the detail of the card history
     */
    private ExpandableTestAdapter adapterDetails;

    /**
     * Dialog when network problem
     */
    private MaterialDialog netErrorDialog;

    /**
     * The bus event (initialisation)
     */
    private EventBus bus = EventBus.getDefault();

    /**
     * Flag used to check if we have to unregister from BusEvent before destroying an activity
     */
    private boolean orientationHasChanged;

    /**
     * Called when the activity is starting
     * @param savedInstanceState : the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set device orientation (depends if its a tablet or not)
        //ScreenUtils.setDeviceOrientation(this);

        // Set orientation flag
        if (savedInstanceState == null) {
            orientationHasChanged = true;
        }

        // Set Layout
        setContentView(R.layout.activity_card_history);

        // Get context
        context = CardHistoryActivity.this;

        // Init toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar toolbar = (ActionBar) getSupportActionBar();
        LinearLayout actionBarLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_toolbar_card_history, null);
        toolbar.setDisplayShowHomeEnabled(false);
        toolbar.setDisplayShowTitleEnabled(false);
        toolbar.setDisplayShowCustomEnabled(true);
        toolbar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        toolbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.LEFT);
        toolbar.setCustomView(actionBarLayout, params);

        // Get Views
        tvAppName = (TextView) actionBarLayout.findViewById(R.id.tvToolbarTitle);
        tvAppDesc = (TextView) actionBarLayout.findViewById(R.id.tvToolbarDesc);
        recyList = (RecyclerView) findViewById(R.id.homeRecyList);
        toolArrowLeftIco = (ImageView) actionBarLayout.findViewById(R.id.toolArrowLeft);
        toolSearchProgress = (ProgressBar) actionBarLayout.findViewById(R.id.toolProgressRefresh);

        // Get fonts
        fontThin = Typeface.createFromAsset(getAssets(), Fonts.ROBOTO_LIGHT);

        // Init Views
        tvAppName.setTypeface(fontThin);
        tvAppDesc.setTypeface(fontThin);
        tvAppName.setText(DataStore.getInstance().getHistoryCardID());
        tvAppDesc.setText(R.string.history_subtitle_wait);

        // Init Views on the toolbar
        toolSearchProgress.setVisibility(View.VISIBLE);
        toolArrowLeftIco.setVisibility(View.VISIBLE);

        // Init listeners
        initToolbarListeners();

        // Init Card history
        cardHistory = new History();

        if (savedInstanceState == null) {
            // Register to events
            bus.register(this);
        }

        cardHistoryMode = MODE_HISTORY_WAIT_CARD_HISTORY;
    }

    /**
     * Process called when a bus event (response) arrive
     * @param response : the response of the event bus
     */
    @Subscribe
    public void onEvent(BusResponse response) {
        toolSearchProgress.setVisibility(View.INVISIBLE);

        if (BuildConfig.DEBUG) Log.d("TACT", " <-- " + response.getRequestType());

        switch (response.getRequestType()) {

            // no history found
            case ERROR_CARD_HISTORY:
                cardHistoryMode = MODE_HISTORY_ERROR;
                tvAppDesc.setText(R.string.history_subtitle_error);
                break;

            // History Found !
            case DISPLAY_CARD_HISTORY:
                cardHistoryMode = MODE_HISTORY_MAIN;
                cardHistory.setHistory(context, response.getData().getString(BusProtocol.BUS_DATA_UI_CARDHISTORY), History.TYPE_CARD_HISTORY);
                initCardHistory();
                break;

            // Display the details
            case SHOW_DETAILS:
                cardHistoryMode = MODE_HISTORY_DETAIL;
                detailHistory = searchDetails(response.getData().getInt(BusProtocol.BUS_DATA_UI_CARDHISTORY_DETAILS));
                if (detailHistory != null) {
                    initCardDetails();
                } else {
                    tvAppDesc.setText(R.string.cardhistory_text_no_detail);
                }
                break;

            // a network problem occur
            case ALARM_UI:
                networkErrorDialog();
                break;

        }
    }

    /**
     * Search the card history item selected in order to display the details of this item.
     * @param numTest : the number of the test
     * @return the card history item
     */
    private CardHistoryItem searchDetails(int numTest) {
        // Search on all the items
        for (int i = 0; i < cardHistory.getHistoryGroupers().size(); i++) {
            for (int j = 0; j < cardHistory.getHistoryGroupers().get(i).getCardHistoryItems().size(); j++) {

                // If the num are the same : That's the item !!
                if (numTest == cardHistory.getHistoryGroupers().get(i).getCardHistoryItems().get(j).getNumTest()) {
                    return cardHistory.getHistoryGroupers().get(i).getCardHistoryItems().get(j);
                }
            }
        }
        // If the item has not been found
        return null;
    }

    /**
     * initialisation of the view of the card history details
     */
    private void initCardDetails() {
        // Init adapter
        adapterDetails = new ExpandableTestAdapter(context,
                ExpandableTestAdapter.convertModel(detailHistory.getDetailCardHistory(),
                        adapterDetails, context));

        // set adapter
        recyList.swapAdapter(adapterDetails, true);

        // Init recycler view
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyList.setAdapter(adapterDetails);
        recyList.setHasFixedSize(true);
        recyList.setLayoutManager(llm);

        // The subtitle
        tvAppDesc.setText(String.format(context.getResources().getString(R.string.cardhistory_subtitle_details),
                detailHistory.getNumTest(),
                DateUtils.parseDateToFrenchString(detailHistory.getDate()),
                detailHistory.getNameTester()));
    }

    /**
     * Initialisation of the view of the card history
     */
    private void initCardHistory() {

        // Init adapter
        adapterHistory = new ExpandableHistoryAdapter(context,
                ExpandableHistoryAdapter.convertModel(cardHistory.getHistoryGroupers(),
                        adapterHistory, context));

        // set the adapter
        recyList.swapAdapter(adapterHistory, true);

        // Init recycler view
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyList.setAdapter(adapterHistory);
        recyList.setHasFixedSize(true);
        recyList.setLayoutManager(llm);

        // See all the items
        adapterHistory.expandAllParents();

        // The subtitle
        tvAppDesc.setText(String.format(context.getResources().getString(R.string.cardhistory_subtitle_before_details),
                DateUtils.parseDateToFrenchString(cardHistory.getDateCreation())));
    }

    /**
     * Defines toolbar icon action & listeners
     */
    private void initToolbarListeners() {

        // Edit icon : go into edition mode
        toolArrowLeftIco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Process called when the user tap on the button back of his android
     */
    @Override
    public void onBackPressed() {
        if (cardHistoryMode == MODE_HISTORY_ERROR || cardHistoryMode == MODE_HISTORY_WAIT_CARD_HISTORY || cardHistoryMode == MODE_HISTORY_MAIN) {
            finishWithResult(Number.FINISH_CARD_HISTORY);
        } else {
            cardHistoryMode = MODE_HISTORY_MAIN;
            initCardHistory();
        }
    }

    /**
     * Display network error dialog
     */
    private void networkErrorDialog() {
        netErrorDialog = new MaterialDialog.Builder(context)
                .title(R.string.dialog_title_error)
                .content(R.string.dialog_error_network)
                .cancelable(false)
                .negativeText(R.string.dialog_cancel)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finishWithResult(Number.FINISH_ACTIVITY_ALARM_UI);
                    }
                })
                .show();
    }

    /**
     * Process called to finish an activity
     * @param number : the number of the finish activity
     */
    private void finishWithResult(int number) {
        Intent data = new Intent();
        //set the data to pass back
        data.setData(Uri.parse(Integer.valueOf(number).toString()));
        setResult(RESULT_OK, data);
        //close the activity
        if (netErrorDialog != null)
            if (netErrorDialog.isShowing())
                netErrorDialog.dismiss();
        finish();
    }

    /**
     * Process called when the activity is destroyed
     */
    @Override
    protected void onDestroy() {
        /*if (orientationHasChanged) {
            orientationHasChanged = false;
        } else {
            bus.unregister(this);
        }*/
        bus.unregister(this);
        super.onDestroy();
    }
}
