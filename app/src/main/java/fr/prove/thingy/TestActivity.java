/**
 * @file TestActivity.java
 * @brief Activity used to show and manage tests
 * @version 1.0
 * @date 30/03/16
 * @author François LEPAROUX
 * @collab Guillaume MURET
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
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.prove.thingy.adapter.test.ExpandableTestAdapter;
import fr.prove.thingy.bus.BusProtocol;
import fr.prove.thingy.communication.protocole.ProtocoleVocabulary;
import fr.prove.thingy.constants.Fonts;
import fr.prove.thingy.constants.Number;
import fr.prove.thingy.bus.BusRequest;
import fr.prove.thingy.bus.BusResponse;
import fr.prove.thingy.bus.BusType;
import fr.prove.thingy.model.DataStore;
import fr.prove.thingy.model.History;
import fr.prove.thingy.model.TestGrouper;
import fr.prove.thingy.model.TestItem;
import fr.prove.thingy.utils.JsonUtils;

public class TestActivity extends AppCompatActivity {

    // State machine for test management
    /**
     * waiting for operator clics on "play"
     */
    private static final int MODE_TEST_PENDING = 0;

    /**
     * test is running
     */
    private static final int MODE_TEST_RUNNING = 1;

    /**
     * test is done
     */
    private static final int MODE_TEST_END = 2;

    /**
     * test is in edition mode
     */
    private static final int MODE_TEST_EDIT = 3;

    /**
     * test is in replaying mode (prepare new test)
     */
    private static final int MODE_TEST_REPLAYING = 4;

    /**
     * test campaign is saving (request sent, waiting for response)
     */
    private static final int MODE_CAMPAIGN_SAVE = 5;

    /**
     * test has just been done
     */
    private static final int STATE_TEST_IDLE = 0;

    /**
     * test has been validated by operator
     */
    private static final int STATE_TEST_VALIDATED = 1;

    /**
     * test has been invalidated by operator
     */
    private static final int STATE_TEST_INVALIDATED = 2;

    /**
     * present state of the state machine
     */
    private int testMode;

    /**
     * sum of the errors during the test
     */
    private int nbErrors;

    /**
     * State after save method
     */
    private int testSaveState;

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
     * Refresh icon on the toolbar
     */
    private ImageView toolRefreshIco;

    /**
     * Edit icon on the toolbar
     */
    private ImageView toolEditIco;

    /**
     * End edit icon on the toolbar
     */
    private ImageView toolEndEditIco;

    /**
     * Quit icon on the toolbar
     */
    private ImageView toolQuitIco;

    /**
     * Progress bar on the toolbar
     */
    private ProgressBar progressTest;

    /**
     * Progress icon when the user tap on the "toolRefreshIco"
     */
    private ProgressBar toolRefreshProgress;

    /**
     * Progress icon when the user tap on the "fabSaveEdition" (when saving)
     */
    private ProgressBar relativeSaveProgress;

    /**
     * button play on the screen
     */
    private FloatingActionButton fabPlay;

    /**
     * button stop on the screen
     */
    private FloatingActionButton fabStop;

    /**
     * button valid test on the screen
     */
    private FloatingActionButton fabValidTest;

    /**
     * button invalid test on the screen
     */
    private FloatingActionButton fabInvalidTest;

    /**
     * button save campaign on the screen
     */
    private FloatingActionButton fabSaveEdition;

    /**
     * Background view of the "relativeSaveProgress"
     */
    private RelativeLayout relativeSaveView;

    /**
     * Quit dialog on the screen
     */
    private MaterialDialog quitDialog;

    /**
     * Progress dialog on the screen (when saving)
     */
    private MaterialDialog quitProgressDialog;

    /**
     * Progress dialog on screen when the user doesn't valid or invalid the result of the test
     */
    private MaterialDialog errorValidInvalidDialog;

    /**
     * Progress dialog on screen when the user doesn't valid or invalid the result of the test
     */
    private MaterialDialog errorEdition;

    /**
     * Dialog when the user want to interrupt the test
     */
    private MaterialDialog interruptDialog;

    /**
     * Network error dialog
     */
    private MaterialDialog netErrorDialog;

    /**
     * Context activity
     */
    private Context context;

    /**
     * Resource of the activity
     */
    private Resources resources;

    /**
     * Animation of the screen
     */
    private Animation fadeAnim, fadeInAnim, fadeOutAnim;

    /**
     * Event bus
     */
    private EventBus bus = EventBus.getDefault();

    /**
     * The fonts
     */
    private Typeface fontThin;

    /**
     * test group -> refer a test category and has the subtest
     */
    private ArrayList<TestGrouper> testGroupers;

    /**
     * test group -> refer a test category and has the subtest
     */
    private ArrayList<TestGrouper> nominalList;

    /**
     * The adapter of the activity
     */
    private ExpandableTestAdapter adapter;

    /**
     * Flag used to check if we have to unregister from BusEvent before destroying an activity
     */
    private boolean orientationHasChanged;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("testmode", testMode);
        super.onSaveInstanceState(outState);
    }

    /**
     * Called when the activity is starting
     * @param savedInstanceState : the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set device orientation (depends if its a tablet or not)
        //ScreenUtils.setDeviceOrientation(this);

        Log.d("SCR", "onCreate, testMode = " + testMode);

        // Set orientation flag
        if (savedInstanceState == null) {
            orientationHasChanged = true;
        } else {
            testMode = savedInstanceState.getInt("testmode");
        }

        // Set Layout
        setContentView(R.layout.activity_test);

        // Get context
        context = TestActivity.this;
        resources = getResources();

        // Init animation
        fadeAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_fade);
        fadeInAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_fade_in);
        fadeOutAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_fade_out);

        // init the toolbar and get the views
        if (savedInstanceState == null) initActivityView();

        // Get fonts
        fontThin = Typeface.createFromAsset(getAssets(), Fonts.ROBOTO_LIGHT);

        // Init Views
        // --> Toolbar
        tvAppName.setTypeface(fontThin);
        tvAppDesc.setTypeface(fontThin);
        tvAppDesc.setAnimation(fadeAnim);
        tvAppName.setText(DataStore.getInstance().getTestCardID());
        toolRefreshProgress.getIndeterminateDrawable().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

        // --> Layout
        relativeSaveProgress.getIndeterminateDrawable().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

        // Init model
        testGroupers = DataStore.getInstance().getTestList().getTestList();

        // Init adapter
        adapter = new ExpandableTestAdapter(context, ExpandableTestAdapter.convertModel(testGroupers, adapter, context));

        // Init recycler view
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyList.setAdapter(adapter);
        recyList.setHasFixedSize(true);
        recyList.setLayoutManager(llm);

        // init the listeners
        if (savedInstanceState == null) {
            initFabListeners();
            initToolbarListeners();
        }

        // Register to events
        bus.register(this);

        if (savedInstanceState == null) {

            // Init tests and view
            initStepTestList(false);
        }
    }

    /**
     * init the views of the activity (toolbar, widget)
     */
    private void initActivityView() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar toolbar = (ActionBar) getSupportActionBar();
        LinearLayout actionBarLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_toolbar_progress, null);
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
        // --> Toolbar
        tvAppName = (TextView) actionBarLayout.findViewById(R.id.tvToolbarTitle);
        tvAppDesc = (TextView) actionBarLayout.findViewById(R.id.tvToolbarDesc);
        progressTest = (ProgressBar) actionBarLayout.findViewById(R.id.progressTest);
        toolRefreshIco = (ImageView) actionBarLayout.findViewById(R.id.toolIconRefresh);
        toolRefreshProgress = (ProgressBar) actionBarLayout.findViewById(R.id.toolProgressRefresh);
        toolEditIco = (ImageView) actionBarLayout.findViewById(R.id.toolIconEdit);
        toolEndEditIco = (ImageView) actionBarLayout.findViewById(R.id.toolIconEndEdit);
        toolQuitIco = (ImageView) actionBarLayout.findViewById(R.id.toolIconQuit);

        // --> Layout
        recyList = (RecyclerView) findViewById(R.id.testRecyList);
        fabPlay = (FloatingActionButton) findViewById(R.id.testPlayFab);
        fabStop = (FloatingActionButton) findViewById(R.id.testStopFab);

        fabValidTest = (FloatingActionButton) findViewById(R.id.validTestFab);
        fabInvalidTest = (FloatingActionButton) findViewById(R.id.invalidTestFab);

        fabSaveEdition = (FloatingActionButton) findViewById(R.id.testSaveFab);
        relativeSaveView = (RelativeLayout) findViewById(R.id.relativeSaveView);
        relativeSaveProgress = (ProgressBar) findViewById(R.id.relativeSaveProgress);

        testMode = MODE_TEST_PENDING;

        updateViewMode();
    }

    /**
     * Defines toolbar icon action & listeners
     */
    private void initToolbarListeners() {

        // Edit icon : go into edition mode
        toolEditIco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testMode = MODE_TEST_EDIT;
                updateEditMode();
            }
        });

        // End edition icon : go into test mode, but save campaign before
        toolEndEditIco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCampaign();
            }
        });

        // Replay icon : start a new test
        toolRefreshIco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartTestAction();
            }
        });

        // Quit icon : quit activity if operators validate following dialog
        toolQuitIco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitEndTestConfirmation();
            }
        });
    }

    /**
     * initialisation of the fab listener
     */
    private void initFabListeners() {
        // Floating action button (new test) listener
        fabPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mise à jour du menu
                testMode = MODE_TEST_RUNNING;
                startStepTestList();
            }
        });

        // Floating action button (stop current test) listener
        fabStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Floating action button (validate tests) listener
        fabValidTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testSaveState = STATE_TEST_VALIDATED;
                updateTestValidInvalidFab();
            }
        });

        // Floating action button (invalidate tests) listener
        fabInvalidTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testSaveState = STATE_TEST_INVALIDATED;
                updateTestValidInvalidFab();
            }
        });

        // Floating action button (save tests) listener
        fabSaveEdition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCampaign();
            }
        });
    }

    /**
     * Called when the user want to quit and test is over
     */
    private void quitEndTestConfirmation() {
        if (testSaveState == STATE_TEST_IDLE) {
            errorValidInvalid();
        } else {
            quitTestConfirmation();
        }
    }

    /**
     * Called when the user has forgotten to valid or invalid the test
     */
    public void errorValidInvalid() {
        errorValidInvalidDialog = new MaterialDialog.Builder(context)
                .title(R.string.dialog_test_error_title)
                .content(R.string.dialog_test_error_content)
                .cancelable(false)
                .negativeText(R.string.dialog_close)
                .show();
    }

    /**
     * init the edit mode
     */
    private void updateEditMode() {

        for (TestGrouper grouper : testGroupers) {
            grouper.makeAsEdit();
        }
        // collapse the test list
        adapter.collapseAllParents();
        adapter.notifyDataSetChanged();

        updateViewMode();
    }

    /**
     * update the title on the toolbar function of the state machine
     */
    private void updateStatusTextView() {

        switch (testMode) {

            case MODE_TEST_EDIT:
                tvAppDesc.setText(R.string.test_status_edition);
                break;

            case MODE_CAMPAIGN_SAVE:
                tvAppDesc.setText(R.string.test_status_saving);
                break;

            case MODE_TEST_PENDING:
                tvAppDesc.setText(R.string.test_status_waiting);
                break;
        }

        // begin the animation
        tvAppDesc.startAnimation(fadeAnim);
    }

    /**
     * Process called when the user tap on the back button (depends of the state)
     */
    @Override
    public void onBackPressed() {
        switch (testMode) {
            case MODE_TEST_END:
                quitEndTestConfirmation();
                break;

            case MODE_TEST_PENDING:
                quitTestConfirmation();
                break;

            case MODE_TEST_RUNNING:
                quitTestConfirmation();
                break;

            case MODE_TEST_EDIT:
                initStepTestList(true);
                break;

        }
    }

    /**
     * Called when the user valid his intent to quit
     */
    private void displayQuitProgressDialog() {
        // Display a progress dialog waiting for save end
        quitProgressDialog = new MaterialDialog.Builder(context)
                .title(R.string.dialog_test_quit_title)
                .content(R.string.dialog_test_quit_save_content)
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    /**
     * Called when the user want to quit
     */
    private void displayQuitDialog() {
        quitDialog = new MaterialDialog.Builder(context)
                .title(R.string.dialog_test_quit_title)
                .content(R.string.dialog_test_quit_content)
                .cancelable(true)
                .negativeText(R.string.dialog_cancel)
                .positiveText(R.string.dialog_confirm)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (testMode == MODE_TEST_END) {
                            // Send "save" event to service
                            busSendSaveAction();
                            displayQuitProgressDialog();
                        } else {
                            bus.post(new BusRequest(BusType.ASK_RECENT_HISTORY));
                            finishWithResult(Number.FINISH_TEST);
                        }
                    }
                })
                .show();
    }

    /**
     * Called when the user want to interrupt the test
     */
    private void displayInterruptDialog() {
        interruptDialog = new MaterialDialog.Builder(context)
                .title(R.string.dialog_test_interrupt_title)
                .content(R.string.dialog_test_interrupt_content)
                .cancelable(true)
                .negativeText(R.string.dialog_cancel)
                .positiveText(R.string.dialog_confirm)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        BusRequest request = new BusRequest(BusType.SET_STOP);
                        request.getData().putInt(BusProtocol.BUS_DATA_TEST_MANAGER_STOP, 0);
                        bus.post(request);
                        initStepTestList(true);
                    }
                })
                .show();
    }

    /**
     * called when the user want to quit
     */
    private void quitTestConfirmation() {
        if (testMode == MODE_TEST_PENDING || testMode == MODE_TEST_END) {
            displayQuitDialog();
        } else if (testMode == MODE_TEST_RUNNING) {
            displayInterruptDialog();
        }
    }

    /**
     * Called when the user tap on the "toolRefreshIco"
     */
    private void restartTestAction() {

        if (testSaveState == STATE_TEST_IDLE) {
            errorValidInvalid();
        } else {
            // Replay mode -> show progress circle
            testMode = MODE_TEST_REPLAYING;
            updateViewMode();

            // Send "save" event to service
            busSendSaveAction();
        }
    }

    /**
     * Sends bus save request
     */
    private void busSendSaveAction() {
        BusRequest saveRequest = new BusRequest(BusType.ASK_SAVE_VALIDATION_RESULT);
        // Valid -> 1, Invalid -> 0
        saveRequest.getData().putInt(BusProtocol.BUS_DATA_HISTORIAN_VALIDATION, testSaveState == STATE_TEST_VALIDATED ? 1 : 0);
        bus.post(saveRequest);
    }

    /**
     * Send save campaign action
     */
    private void saveCampaign() {
        testMode = MODE_CAMPAIGN_SAVE;
        updateViewMode();

        // Items into list
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<Integer> run = new ArrayList<>();

        // Loop for each of the test groupers (scenarios) to decide which one we should run (or not)
        for (int i = 0; i < testGroupers.size(); i++) {
            ids.add(testGroupers.get(i).getIDscenario());
            run.add(testGroupers.get(i).getStatus());
        }

        BusRequest saveRequest = new BusRequest(BusType.ASK_SAVE_CAMPAIGN);
        saveRequest.getData().putStringArrayList(BusProtocol.BUS_DATA_TEST_MANAGER_ID_SCENARIOS, ids);
        saveRequest.getData().putIntegerArrayList(BusProtocol.BUS_DATA_TEST_MANAGER_SHOULD_RUN, run);
        bus.post(saveRequest);

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
     * @param response : the response of the bus event
     */
    @Subscribe
    public void onEvent(final BusResponse response) {

        if (BuildConfig.DEBUG) Log.d("TACT", " <-- " + response.getRequestType());

        switch (response.getRequestType()) {

            // "Save validation done" response
            case VALID_SAVE_VALIDATION_RESULT:
                if (testMode == MODE_TEST_REPLAYING) {
                    // If we've just asked for a new test
                    initStepTestList(true);
                } else {
                    // If the action to do is to go back to the Recent view
                    bus.post(new BusRequest(BusType.ASK_RECENT_HISTORY));
                }
                break;

            // "Save campaign done" response
            case VALID_CAMPAIGN:
                testMode = MODE_TEST_PENDING;
                initStepTestList(false);
                break;

            // "Test done" response
            case UPDATE_TEST:
                Log.d(" THIS ", response.getData().toString() );
                if (testMode == MODE_TEST_RUNNING) {
                    updateStepTestResult(response.getData());
                }
                break;

            // "Test sequence done" response
            case END_TEST:
                if (testMode == MODE_TEST_RUNNING) {
                    endsStepTestList();
                }
                break;

            // "Go to home and display recent history" response
            case SET_RECENT_HISTORY:
                quitProgressDialog.dismiss();
                History recentHistory = new History();
                recentHistory.setHistory(context, response.getData().getString(BusProtocol.BUS_DATA_UI_RECENT_HISTORY), History.TYPE_RECENT_HISTORY);
                DataStore.getInstance().setRecentHistory(recentHistory);
                finishWithResult(Number.FINISH_TEST);
                break;

            // network problem
            case ALARM_UI:
                networkErrorDialog();
                break;
        }
    }

    /**
     * Update UI test results with data from Raspberry's response
     *
     * @param data : the data
     */
    public void updateStepTestResult(Bundle data) {
        String IDsc = data.getString(BusProtocol.BUS_DATA_UI_ID_SCENARIO);
        String IDstep = data.getString(BusProtocol.BUS_DATA_UI_ID_STEP_TEST);
        boolean testResultIsOk = data.getBoolean(BusProtocol.BUS_DATA_UI_TEST_RESULT);

        // Values used to know which item we should update in the adapter
        int posParent;
        int posChild;

        // Search for test grouper with ID <=> IDsc
        if ((posParent = searchParentID(IDsc)) != -1) {
            if ((posChild = searchChildID(testGroupers.get(posParent), IDstep)) != -1) {
                TestItem item = testGroupers.get(posParent).getTestItems().get(posChild);
                if (BuildConfig.DEBUG) Log.d("DBG", "Test item found : " + item.toString());
                manageItemFound(item, testResultIsOk, posChild, posParent);
            }
        }
    }

    /**
     * Update test results of the item with the data
     * @param item the test item
     * @param testResultIsOk the test result
     * @param posChild the child position (in the list)
     * @param posParent the parent position (in the list)
     */
    private void manageItemFound(TestItem item, boolean testResultIsOk, int posChild, int posParent) {
        // Is test result successful ?
        if (testResultIsOk) {
            item.makeAsSuccess();
        } else {
            item.makeAsFailed();
            // increments error counter
            nbErrors++;
        }

        manageExpandOrCollapseParent(posChild, posParent);

        // manage the scroll position
        recyList.smoothScrollToPosition(posParent + 1 + posChild + 2);

        // update progress bar view
        progressTest.incrementProgressBy(1);
        updateProgressTextView();

        Log.d(" posParent "+posParent," posChild "+posChild);

        // Notify adapter
        adapter.notifyChildItemChanged(posParent, posChild);
    }

    /**
     * Manage the view of the list test
     * @param posChild the child position (in the list)
     * @param posParent the parent position (in the list)
     */
    private void manageExpandOrCollapseParent(int posChild, int posParent) {
        if (posChild == 0) {
            // We're at the start of a parent test, we expand it
            adapter.expandParent(posParent);
            if(posParent>0){
                adapter.collapseParent(posParent-1);
            }

        } else if (posChild == testGroupers.get(posParent).getTestItems().size() - 1) {
            // We're at the end of a parent test, we collapse it
            //adapter.collapseParent(posParent);

        }
    }

    /**
     * Search the position of the parent (in the testGroupers)
     * @param IDsc : the id of the scenario
     * @return the parent position found. Else -1
     */
    private int searchParentID(String IDsc) {
        // Search for test grouper with ID <=> IDsc
        for (int posParent = 0; posParent < testGroupers.size(); posParent++) {
            TestGrouper grouper = testGroupers.get(posParent);
            if (grouper.hasSameID(IDsc)) {
                return posParent;
            }
        }
        return -1;
    }

    /**
     * Search the position of the child (in the group found)
     * @param grouper : the test group
     * @param IDstep : the id of the scenario
     * @return the child position found. Else -1
     */
    private int searchChildID(TestGrouper grouper, String IDstep) {
        // Search for test item with ID <=> IDstep
        for (int posChild = 0; posChild < grouper.getTestItems().size(); posChild++) {
            TestItem item = grouper.getTestItems().get(posChild);
            if (item.hasSameID(IDstep)) {
                return posChild;
            }
        }
        return -1;
    }

    /**
     * Update the view of the floating action button (depend of the current state)
     */
    private void updateFabViewMode() {
        switch (testMode) {
            case MODE_TEST_PENDING:
                fabPlay.setVisibility(View.VISIBLE);
                fabPlay.startAnimation(fadeInAnim);
                fabStop.setVisibility(View.INVISIBLE);
                fabSaveEdition.setVisibility(View.INVISIBLE);
                break;

            case MODE_TEST_RUNNING:
                fabPlay.setVisibility(View.INVISIBLE);
                fabStop.setVisibility(View.VISIBLE);
                break;

            case MODE_TEST_EDIT:
                fabPlay.startAnimation(fadeOutAnim);
                fabStop.setVisibility(View.VISIBLE);
                fabSaveEdition.setVisibility(View.VISIBLE);
                break;

            case MODE_CAMPAIGN_SAVE:
                fabSaveEdition.setVisibility(View.INVISIBLE);
                break;

            case MODE_TEST_END:
                fabStop.setVisibility(View.INVISIBLE);
                break;
        }
    }

    /**
     * Called when a state change : change the view of the screen
     */
    private void updateViewMode() {

        // Texte / progress bar remis à zéro
        updateStatusTextView();

        // Update the floating action button view
        updateFabViewMode();

        // Fab confirmation, invisibles
        updateTestValidInvalidFab();

        // Mise à jour du menu
        updateToolbar();

        switch (testMode) {
            case MODE_TEST_PENDING:
                relativeSaveView.setVisibility(View.INVISIBLE);
                break;

            case MODE_TEST_RUNNING:
                relativeSaveView.setVisibility(View.INVISIBLE);
                break;

            case MODE_TEST_EDIT:
                relativeSaveView.setVisibility(View.INVISIBLE);
                break;

            case MODE_CAMPAIGN_SAVE:
                relativeSaveView.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * process called when a error edition occur
     */
    private void displayErrorEdition() {
        errorEdition = new MaterialDialog.Builder(context)
                .title(R.string.dialog_test_edition_error_title)
                .content(R.string.dialog_test_edition_error_content)
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    /**
     * change the view of the test list
     * @param annulInterruptReplay : boolean of state
     */
    private void initViewList(boolean annulInterruptReplay) {

        for (int i = 0; i < testGroupers.size(); i++) {
            if (testGroupers.get(i).isRequired() && testGroupers.get(i).getStatus() == TestGrouper.TEST_STATUS_EDIT_SKIP) {
                // Never displayed..
                displayErrorEdition();
            } else {
                for (int j = 0; j < testGroupers.get(i).getTestItems().size(); j++) {
                    if (annulInterruptReplay) {
                        if (testGroupers.get(i).getTestItems().get(j).getStatus() == TestItem.TEST_STATUS_SKIP) {
                            testGroupers.get(i).getTestItems().get(j).makeAsSkip();
                        } else {
                            testGroupers.get(i).getTestItems().get(j).makeAsTestWaiting();
                        }
                    } else {
                        if (testGroupers.get(i).getStatus() == TestGrouper.TEST_STATUS_EDIT_SKIP) {
                            testGroupers.get(i).getTestItems().get(j).makeAsSkip();
                        } else {
                            testGroupers.get(i).getTestItems().get(j).makeAsTestWaiting();
                        }
                    }
                }
                testGroupers.get(i).makeAsOther();
            }
        }
    }

    /**
     * Init the view and the test list
     * @param annulInterruptReplay : boolean of state
     */
    public void initStepTestList(boolean annulInterruptReplay) {

        // mode default
        testMode = MODE_TEST_PENDING;

        // No save mode (idle)
        testSaveState = STATE_TEST_IDLE;

        // Mise à jour de la vue de la liste des tests
        initViewList(annulInterruptReplay);

        // Mise à jour de la vue
        updateViewMode();

        //init progress bar
        progressTest.setProgress(0);
        progressTest.setMax(countTestItems(testGroupers));

        // On notifie l'adapter que les valeurs ont changé
        adapter.notifyDataSetChanged();

        // Aucun parent étendu
        adapter.collapseAllParents();
    }

    /**
     * Fake test management : starts test
     */
    private void startStepTestList() {

        updateViewMode();

        // Mise à jour du texte de l'app bar
        updateProgressTextView();

        // Reset des tests
        nbErrors = 0;

        // On ouvre le premier parent
        adapter.expandParent(0);

        // On démarre la séquence de test
        bus.post(new BusRequest(BusType.RUN_TESTS));
    }


    /**
     * Sets the UI in end test mode
     */
    private void endsStepTestList() {
        closeAllDialogs();
        if (nbErrors == 0)
            tvAppDesc.setText(R.string.test_status_done);
        else
            tvAppDesc.setText(resources.getQuantityString(R.plurals.test_status_done_errors, nbErrors, nbErrors));

        // State : end of the test
        testMode = MODE_TEST_END;

        testSaveState = STATE_TEST_IDLE;

        updateViewMode();

        adapter.collapseParent(testGroupers.size() - 1);
        adapter.notifyParentItemChanged(testGroupers.size() - 1);
    }

    /**
     * Manage progress text view (during running test)
     */
    private void updateProgressTextView() {
        int percent = progressTest.getProgress() * 100 / progressTest.getMax();
        if (percent == 100)
            endsStepTestList();
        else
            tvAppDesc.setText(String.format(getResources().getString(R.string.test_status_running), percent));
    }

    /**
     * Count test item that will be executed
     */
    private int countTestItems(ArrayList<TestGrouper> testGroupers) {

        int count = 0;

        for (TestGrouper grouper : testGroupers) {
            for (int i = 0; i < grouper.getTestItems().size(); i++) {
                if (grouper.getTestItems().get(i).getStatus() != TestItem.TEST_STATUS_SKIP)
                    count += 1;
            }
        }

        return count;
    }

    /**
     * Update toolbar views (depends on the current status)
     */
    private void updateToolbar() {

        switch (testMode) {

            // Pending : edit icon visible only
            case MODE_TEST_PENDING:
                toolEditIco.setVisibility(View.VISIBLE);
                toolEndEditIco.setVisibility(View.GONE);
                toolRefreshIco.setVisibility(View.GONE);
                toolRefreshProgress.setVisibility(View.GONE);
                toolQuitIco.setVisibility(View.GONE);
                break;

            // Edition : end edit icon visible only
            case MODE_TEST_EDIT:
                toolEditIco.setVisibility(View.GONE);
                toolEndEditIco.setVisibility(View.VISIBLE);
                toolEndEditIco.setEnabled(true);
                toolRefreshIco.setVisibility(View.GONE);
                toolRefreshProgress.setVisibility(View.GONE);
                toolQuitIco.setVisibility(View.GONE);
                break;

            // Running : no icon visible
            case MODE_TEST_RUNNING:
                toolEditIco.setVisibility(View.GONE);
                toolEndEditIco.setVisibility(View.GONE);
                toolRefreshIco.setVisibility(View.GONE);
                toolRefreshProgress.setVisibility(View.GONE);
                toolQuitIco.setVisibility(View.GONE);
                break;

            // Test has finished : icon refresh + quit visible
            case MODE_TEST_END:
                toolEditIco.setVisibility(View.GONE);
                toolEndEditIco.setVisibility(View.GONE);
                toolRefreshIco.setVisibility(View.VISIBLE);
                toolRefreshProgress.setVisibility(View.GONE);
                toolQuitIco.setVisibility(View.VISIBLE);
                break;

            // Creating a new test (refresh) : progress refresh (indeterminate circle) + quit visible
            case MODE_TEST_REPLAYING:
                toolEditIco.setVisibility(View.GONE);
                toolEndEditIco.setVisibility(View.GONE);
                toolRefreshIco.setVisibility(View.GONE);
                toolRefreshProgress.setVisibility(View.VISIBLE);
                toolQuitIco.setVisibility(View.VISIBLE);
                break;

            // Save the campaign
            case MODE_CAMPAIGN_SAVE:
                toolEndEditIco.setEnabled(false);
                break;

        }
    }

    /**
     * Changes floating action buttons (test validation / invalidation) colors
     */
    private void updateTestValidInvalidFab() {

        if (testMode != MODE_TEST_END) {
            fabInvalidTest.setVisibility(View.INVISIBLE);
            fabValidTest.setVisibility(View.INVISIBLE);
        } else {
            fabInvalidTest.setVisibility(View.VISIBLE);
            fabValidTest.setVisibility(View.VISIBLE);
        }

        switch (testSaveState) {

            case STATE_TEST_IDLE:
                fabValidTest.setColorNormalResId(R.color.colorCircleIdle);
                fabValidTest.setColorPressedResId(R.color.colorCircleIdlePressed);
                fabValidTest.setColorRippleResId(R.color.colorCircleIdleRipple);

                fabInvalidTest.setColorNormalResId(R.color.colorCircleIdle);
                fabInvalidTest.setColorPressedResId(R.color.colorCircleIdlePressed);
                fabInvalidTest.setColorRippleResId(R.color.colorCircleIdleRipple);
                break;

            case STATE_TEST_VALIDATED:
                fabValidTest.setColorNormalResId(R.color.colorCircleNew);
                fabValidTest.setColorPressedResId(R.color.colorCircleNewPressed);
                fabValidTest.setColorRippleResId(R.color.colorCircleNewRipple);

                fabInvalidTest.setColorNormalResId(R.color.colorCircleIdle);
                fabInvalidTest.setColorPressedResId(R.color.colorCircleIdlePressed);
                fabInvalidTest.setColorRippleResId(R.color.colorCircleIdleRipple);
                break;

            case STATE_TEST_INVALIDATED:
                fabValidTest.setColorNormalResId(R.color.colorCircleIdle);
                fabValidTest.setColorPressedResId(R.color.colorCircleIdlePressed);
                fabValidTest.setColorRippleResId(R.color.colorCircleIdleRipple);

                fabInvalidTest.setColorNormalResId(R.color.colorCircleStop);
                fabInvalidTest.setColorPressedResId(R.color.colorCircleStopPressed);
                fabInvalidTest.setColorRippleResId(R.color.colorCircleStopRipple);
                break;
        }
    }

    /**
     * Close all the material dialog
     */
    private void closeAllDialogs() {
        if (quitDialog != null)
            if (quitDialog.isShowing())
                quitDialog.dismiss();

        if (quitProgressDialog != null)
            if (quitProgressDialog.isShowing())
                quitProgressDialog.dismiss();

        if (errorValidInvalidDialog != null)
            if (errorValidInvalidDialog.isShowing())
                errorValidInvalidDialog.dismiss();

        if (interruptDialog != null)
            if (interruptDialog.isShowing())
                interruptDialog.dismiss();

        if (errorEdition != null)
            if (errorEdition.isShowing())
                errorEdition.dismiss();

    }

    /**
     * Called when a network problem occur
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
     * Process called to finish this activity
     * @param number the number of the finish activity
     */
    private void finishWithResult(int number) {
        Intent data = new Intent();

        //set the data to pass back
        data.setData(Uri.parse(Integer.valueOf(number).toString()));
        setResult(RESULT_OK, data);

        closeAllDialogs();
        if (netErrorDialog != null)
            if (netErrorDialog.isShowing())
                netErrorDialog.dismiss();
        //close the activity
        finish();
    }
}
