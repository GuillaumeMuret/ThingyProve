/**
 * @file HomeActivity.java
 * @brief Activity used to show recent history (last tested products)
 *
 * @version 1.0
 * @date 29/03/16
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
package fr.prove.thingy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.melnykov.fab.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import fr.prove.thingy.adapter.history.ExpandableHistoryAdapter;
import fr.prove.thingy.bus.BusProtocol;
import fr.prove.thingy.constants.Fonts;
import fr.prove.thingy.constants.Number;
import fr.prove.thingy.bus.BusRequest;
import fr.prove.thingy.bus.BusResponse;
import fr.prove.thingy.bus.BusType;
import fr.prove.thingy.model.DataStore;
import fr.prove.thingy.model.History;
import fr.prove.thingy.model.TestList;

public class HomeActivity extends AppCompatActivity {

    /**
     * Recycler view
     */
    private RecyclerView recyList;

    /**
     * Title of the screen
     */
    private TextView tvAppName;

    /**
     * Subtitle of the screen
     */
    private TextView tvAppDesc;

    /**
     * Button "+" on the bottom right of the screen
     */
    private FloatingActionButton fabNew;

    /**
     * Dialog opened when the user click on the "fabNew"
     */
    private MaterialDialog testDialog;

    /**
     * Dialog opened when the user valid the "testDialog"
     */
    private MaterialDialog mdProgress;

    /**
     * Dialog displayed when a network problem occur
     */
    private MaterialDialog netErrorDialog;

    /**
     * Dialog opened when the user click on "action_search" (menu)
     */
    private MaterialDialog historyDialog;

    /**
     * Custom view of the test dialog
     */
    private View vTestDial;

    /**
     * Custom view of the history dialog
     */
    private View vHistoryDial;

    /**
     * Check box to create a new card or not
     */
    private CheckBox checkTest;

    /**
     * The cardID from the testDialog
     */
    private EditText etTest;

    /**
     * The cardID from the historyDialog
     */
    private EditText etHistoryCard;

    /**
     * the activity context
     */
    private Context context;

    /**
     * The font
     */
    private Typeface fontThin;

    /**
     * The recent history
     */
    private History recentHistory;

    /**
     * The history adapter used with the "recyList"
     */
    private ExpandableHistoryAdapter adapter;

    /**
     * The event bus
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
        setContentView(R.layout.activity_home);

        // Get context
        context = HomeActivity.this;

        // Init toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar toolbar = (ActionBar) getSupportActionBar();
        LinearLayout actionBarLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.layout_toolbar_two, null);
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
        fabNew = (FloatingActionButton) findViewById(R.id.homeFab);

        // Get fonts
        fontThin = Typeface.createFromAsset(getAssets(), Fonts.ROBOTO_LIGHT);

        // Init Views
        tvAppName.setTypeface(fontThin);
        tvAppDesc.setTypeface(fontThin);
        tvAppName.setText(DataStore.getInstance().getOperator().getName());
        tvAppDesc.setText(DataStore.getInstance().getOperator().getFrenchDate());
        fabNew.attachToRecyclerView(recyList);

        // Init model
        recentHistory = DataStore.getInstance().getRecentHistory();

        if (savedInstanceState == null) {

            // Register to event
            bus.register(this);

            // expand the first recent history
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    // Open first parent to show last tests
                    if (recentHistory.getHistoryGroupers().size() > 0) {
                        adapter.expandParent(0);
                    }
                }
            });
        }

        // Init adapter
        adapter = new ExpandableHistoryAdapter(context, ExpandableHistoryAdapter.convertModel(recentHistory.getHistoryGroupers(), adapter, context));

        // Init recycler view
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyList.setAdapter(adapter);
        recyList.setHasFixedSize(true);
        recyList.setLayoutManager(llm);

        // Pre-Build dialogs
        initTestDialog();
        initHistoryDialog();

        // Dialog click / management
        vTestDial = testDialog.getCustomView();
        vHistoryDial = historyDialog.getCustomView();
        checkTest = (CheckBox) vTestDial.findViewById(R.id.checkDialogTestNew);
        etHistoryCard = (EditText) vHistoryDial.findViewById(R.id.etDialogCardID);
        etTest = (EditText) vTestDial.findViewById(R.id.etDialogTestID);

        // init the listeners
        initListeners();
    }

    /**
     * init the listeners of the activity
     */
    private void initListeners(){
        checkTest.setChecked(true);
        etTest.setEnabled(false);
        checkTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    etTest.setEnabled(false);
                } else {
                    etTest.setEnabled(true);
                }
            }
        });

        // Set on click listeners
        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDialog.show();
            }
        });
    }

    /**
     * Init the research card dialog
     */
    private void initHistoryDialog(){
        historyDialog = new MaterialDialog.Builder(context)
                .title(R.string.dialog_title_history)
                .customView(R.layout.dialog_history_create, true)
                .cancelable(true)
                .theme(Theme.DARK)
                .negativeText(R.string.dialog_cancel)
                .positiveText(R.string.dialog_validate)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        testDialog.cancel();
                        dialog.cancel();
                        // Check the format of the card XXJJMMAAHHMM
                        if(checkCardFormat(etHistoryCard.getText().toString())){
                            goToCardHistoryActivity();
                        }else{
                            errorFormatCardID();
                        }

                    }
                })
                .build();
    }

    /**
     * init the test dialog
     */
    private void initTestDialog(){
        testDialog = new MaterialDialog.Builder(context)
                .title(R.string.dialog_title_new_test)
                .customView(R.layout.dialog_test_create, true)
                .cancelable(true)
                .theme(Theme.DARK)
                .negativeText(R.string.dialog_cancel)
                .positiveText(R.string.dialog_validate)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        onValidTestDialog();
                    }
                })
                .build();
    }

    /**
     * Action the user click on "valid" button on test dialog
     */
    private void onValidTestDialog(){
        // Show progress dialog
        mdProgress = new MaterialDialog.Builder(context)
                .title(R.string.dialog_title_new_test)
                .content(R.string.dialog_progress_new_test)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .cancelable(false)
                .show();

        // Check the format of the card XXJJMMAAHHMM
        if(checkTest.isChecked()||(!checkTest.isChecked()&&checkCardFormat(etTest.getText().toString()))){

            // Send request to main service
            if(!checkTest.isChecked()) {
                BusRequest request = new BusRequest(BusType.ASK_EXISTANT_CARD);
                request.getData().putString(BusProtocol.BUS_DATA_CARDGUARD_CARDID, etTest.getText().toString());
                DataStore.getInstance().setTestCardID(etTest.getText().toString());
                bus.post(request);
            }else{
                // ask the rasp to create a new card
                bus.post(new BusRequest(BusType.NEW_CARD));
            }
        }else{
            mdProgress.dismiss();
            errorFormatCardID();
        }
    }

    /**
     * Process called when the card format is good
     */
    private void goToCardHistoryActivity(){
        BusRequest request = new BusRequest(BusType.ASK_CARD_HISTORY);
        request.getData().putString(BusProtocol.BUS_DATA_HISTORIAN_IDRESEARCHCARD,etHistoryCard.getText().toString());
        bus.post(request);
        DataStore.getInstance().setHistoryCardID(etHistoryCard.getText().toString());
        bus.unregister(this); // Unregister to events
        startActivityForResult(new Intent(context, CardHistoryActivity.class),Number.REQUEST_CODE_ACTIVITY_CARD_HISTORY);

    }

    /**
     * Check the format of the card (XXJJMMAAHHMM)
     * @param strCardID the card id
     * @return if the format is respected or not
     */
    public static Boolean checkCardFormat(String strCardID){
        // exactly 8 character and write lower-case alphabetic character [a-z]
        return strCardID.matches("[A-Z]{2}?"+"\\d{10}?");
    }

    /**
     * Dialog when the format of the card is bad
     */
    private void errorFormatCardID(){
        new MaterialDialog.Builder(context)
            .title(R.string.dialog_title_error)
            .content(R.string.dialog_error_format_content)
            .negativeText(R.string.dialog_cancel)
            .cancelable(true)
            .show();
    }

    /**
     * Dialog when the card id is not present on the data base
     */
    private void errorSearchCardID(){
        mdProgress.dismiss();
        new MaterialDialog.Builder(context)
            .title(R.string.dialog_title_error)
            .content(R.string.dialog_error_id_content)
            .negativeText(R.string.dialog_cancel)
            .cancelable(true)
            .show();
    }

    /**
     * Init the menu bar
     * @param menu the menu of the activity
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // stats, search, logout
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }


    /**
     * Menu action listener
     * @param item the menu item
     * @return the boolean of the super constructor
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // Click on the search button
            case R.id.action_search:
                historyDialog.show();
                return true;

            // Click on the stats button
            case R.id.action_stats:
                bus.unregister(this); // Unregister to events
                Intent i = new Intent(context, StatsActivity.class);
                startActivityForResult(i,Number.REQUEST_CODE_ACTIVITY_STAT);
                return true;

            // Click on the logout button
            case R.id.action_logout:
                quitHomeConfirmation(true);
                return true;

            // Click on the exit button
            case R.id.action_exit:
                quitHomeConfirmation(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Quit message confirmation
     * @param shouldShowLoginScreen : boolean if ui should show the login screen
     */
    private void quitHomeConfirmation (final boolean shouldShowLoginScreen) {
        new MaterialDialog.Builder(context)
                .title(shouldShowLoginScreen ? R.string.dialog_home_disconnect_title : R.string.dialog_home_quit_title)
                .content(shouldShowLoginScreen ? R.string.dialog_home_disconnect_content : R.string.dialog_home_quit_content)
                .cancelable(true)
                .negativeText(R.string.dialog_cancel)
                .positiveText(R.string.dialog_confirm)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (shouldShowLoginScreen) {
                            finishWithResult(Number.FINISH_HOME_AND_START_LOGIN);
                        }else {
                            finishWithResult(Number.FINISH_ACTIVITY_EXIT);
                        }

                    }
                })
                .show();
    }

    /**
     * Dialog to display when a network problem occur
     */
    private void networkErrorDialog () {
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
     * Process called to close all the dialogs
     */
    private void closeAllDialogs(){
        if(mdProgress!=null)
            if(mdProgress.isShowing())
                mdProgress.dismiss();

        if(testDialog!=null)
            if(testDialog.isShowing())
                testDialog.dismiss();

        if(historyDialog!=null)
            if(historyDialog.isShowing())
                historyDialog.dismiss();
    }

    /**
     * Process called when the user tap on the back button
     */
    @Override
    public void onBackPressed() {
        quitHomeConfirmation(true);
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

    /**
     * Process called when a bus response arrive
     * @param response : response of the event bus
     */
    @Subscribe
    public void onEvent(BusResponse response) {

        if (BuildConfig.DEBUG) Log.d("HACT", " <-- " + response.getRequestType());

        switch (response.getRequestType()) {

            // When the recent history has been received
            case SET_RECENT_HISTORY:
                History recentHistory = DataStore.getInstance().getRecentHistory();
                recentHistory.setHistory(context, response.getData().getString(BusProtocol.BUS_DATA_UI_RECENT_HISTORY),History.TYPE_RECENT_HISTORY);
                break;

            // When the card exist on the data base
            case EXISTANT_CARD:
                bus.post(new BusRequest(BusType.ASK_NOMINAL_LIST_TEST));
                break;

            // When the card doesn't exist on the data base
            case NO_CARD:
                errorSearchCardID();
                break;

            // When the nominal list test has been received
            case SET_NOMINAL_LIST_TEST:
                if(mdProgress!=null)
                    mdProgress.dismiss();

                if (BuildConfig.DEBUG) Log.d("DATA", response.getData().getString(BusProtocol.BUS_DATA_UI_NOMINAL_LIST_TEST));
                TestList testList = new TestList();
                testList.setTestList(response.getData().getString(BusProtocol.BUS_DATA_UI_NOMINAL_LIST_TEST));
                DataStore.getInstance().setTestList(testList);
                // Unregister to events
                bus.unregister(this);
                startActivityForResult(new Intent(context, TestActivity.class), Number.REQUEST_CODE_ACTIVITY_TEST);
                break;

            //When the card has been created
            case SET_CARD_ID:
                String testCardID = response.getData().getString(BusProtocol.BUS_DATA_UI_CARDID);
                DataStore.getInstance().setTestCardID(testCardID);
                bus.post(new BusRequest(BusType.ASK_NOMINAL_LIST_TEST));
                break;

            // When a network problem occur
            case ALARM_UI:
                networkErrorDialog();
                break;
        }
    }

    /**
     * Process called when an activity launched is finished
     * @param requestCode the request code of the finished activity
     * @param resultCode the result code of the finished activity
     * @param data the data code of the finished activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            switch (Integer.parseInt(data.getData().toString())) {

                // When the CardHistoryActivity has finished
                case Number.FINISH_CARD_HISTORY:
                    bus.register(this);
                    break;

                // When the TestActivity has finished
                case Number.FINISH_TEST:
                    bus.register(this);
                    break;

                // when the StatsActivity has finished
                case Number.FINISH_STAT:
                    bus.register(this);
                    break;

                // When a network problem occur from a launched activity
                case Number.FINISH_ACTIVITY_ALARM_UI:
                    finishWithResult(Number.FINISH_ACTIVITY_ALARM_UI);
                    break;

            }
        }catch(NullPointerException npe){
            npe.printStackTrace();
        }
    }

    /**
     * Process called to finish an activity
     * @param number : the number to send at the SplashActivity
     */
    private void finishWithResult(int number) {
        Intent data = new Intent();

        //set the data to pass back
        data.setData(Uri.parse(Integer.valueOf(number).toString()));
        setResult(RESULT_OK, data);

        //close the activity
        closeAllDialogs();
        if(netErrorDialog!=null)
            if(netErrorDialog.isShowing())
                netErrorDialog.dismiss();
        Log.d("Finish HOME","FINISH HOME");
        finish();
    }
}
