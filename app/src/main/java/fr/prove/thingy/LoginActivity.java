/**
 * @file LoginActivity.java
 * @brief Activity used to perform operator connection
 *
 * @version 1.0
 * @date 25/03/16
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
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import fr.prove.thingy.bus.BusProtocol;
import fr.prove.thingy.constants.Fonts;
import fr.prove.thingy.constants.Number;
import fr.prove.thingy.bus.BusRequest;
import fr.prove.thingy.bus.BusResponse;
import fr.prove.thingy.bus.BusType;
import fr.prove.thingy.model.DataStore;
import fr.prove.thingy.model.History;
import fr.prove.thingy.model.Operator;

public class LoginActivity extends AppCompatActivity {

    /**
     * Views name of the app
     */
    private TextView tvName;

    /**
     * Instruction on the screen
     */
    private TextView tvInstructions;

    /**
     * Text "connect" of the button bpAuth
     */
    private TextView tvAuth;

    /**
     * Error message on the screen
     */
    private TextView tvLogError;

    /**
     * Username
     */
    private EditText etUsername;

    /**
     * Password
     */
    private EditText etPassword;

    /**
     * progress bar after the text "connexion..."
     */
    private ProgressBar pcAuth;

    /**
     * Button "connect" on the screen
     */
    private Button bpAuth;

    /**
     * Activity context
     */
    private Context context;

    /**
     * The font
     */
    private Typeface fontThin;

    /**
     * Dialog to display when a network problem occur
     */
    private MaterialDialog netErrorDialog;

    /**
     * Event bus
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
        Log.d("Start LOGIN","Start LOGIN");

        // Set device orientation (depends if its a tablet or not)
        //ScreenUtils.setDeviceOrientation(this);

        // Set orientation flag
        if (savedInstanceState == null) {
            orientationHasChanged = true;
        }

        // Set Layout
        setContentView(R.layout.activity_login);

        // Get context
        context = LoginActivity.this;

        // Get Views
        tvName = (TextView) findViewById(R.id.tvLoginName);
        tvInstructions = (TextView) findViewById(R.id.tvLoginInstruction);
        etUsername = (EditText) findViewById(R.id.etLoginUsername);
        etPassword = (EditText) findViewById(R.id.etLoginPassword);
        tvAuth = (TextView) findViewById(R.id.tvLoginAuth);
        tvLogError = (TextView) findViewById(R.id.tvError);
        pcAuth = (ProgressBar) findViewById(R.id.pcLoginAuth);
        bpAuth = (Button) findViewById(R.id.bpLoginAction);

        // Get fonts
        fontThin = Typeface.createFromAsset(getAssets(), Fonts.ROBOTO_LIGHT);

        if (savedInstanceState == null) {
            bus.register(this);
        }

        // Init Views
        tvName.setTypeface(fontThin);
        tvInstructions.setTypeface(fontThin);
        pcAuth.getIndeterminateDrawable().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

        // init listeners
        initListeners();
    }

    /**
     * Initialisation of the listeners
     */
    private void initListeners(){
        bpAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Appui sur CONNECT","Appui sur CONNECT");
                bpAuth.setEnabled(false);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(etUsername.getWindowToken(), 0);
                actionConnect();
            }
        });
    }

    /**
     * Process called when a user click on the button "bpAuth"
     */
    private void actionConnect(){

        // Check the username format and password format
        if(checkOperatorFormat(etUsername.getText().toString(),etPassword.getText().toString())){
            tvLogError.setVisibility(View.INVISIBLE);
            tvAuth.setVisibility(View.VISIBLE);
            pcAuth.setVisibility(View.VISIBLE);
            // Send request to main service
            BusRequest request = new BusRequest(BusType.CONNECT_OPERATOR);
            request.getData().putString(BusProtocol.BUS_DATA_TESTERGUARD_USERID, etUsername.getText().toString());
            request.getData().putString(BusProtocol.BUS_DATA_TESTERGUARD_USERPASS, etPassword.getText().toString());
            bus.post(request);
        // if format isn't good
        }else{
            // display a login error
            errorLogin();
            bpAuth.setEnabled(true);
        }
    }

    /**
     * Display the information when a login error occur
     */
    private void errorLogin(){
        tvAuth.setVisibility(View.INVISIBLE);
        pcAuth.setVisibility(View.INVISIBLE);
        tvLogError.setVisibility(View.VISIBLE);
    }

    /**
     * Check the operator format
     * @param strUsername : the username
     * @param strPassword : the password
     * @return if good format or bad. true -> Right format / false -> Wrong format
     */
    public static Boolean checkOperatorFormat(String strUsername,String strPassword) {
        // login -> exactly 2 characters and write [A-Z]. Password -> 5 characters or more
        return strUsername.matches("[A-Z]{2}?") && strPassword.length() >= Number.PASSWORD_NB_CHAR;
    }

    /**
     * Process called when a bus response arrive
     * @param response : the response of the bus event
     */
    @Subscribe
    public void onEvent (BusResponse response) {

        switch (response.getRequestType()) {

            // When username and password are good
            case ALLOW_OPERATOR_ACCESS:
                bus.post(new BusRequest(BusType.START_TIMER_TIMEOUT_LOGIN));
                bpAuth.setEnabled(false);
                if (BuildConfig.DEBUG) Log.d("GOOD_OPERATOR","debug");
                break;

            // When the access of the operator is refused
            case BAN_OPERATOR_ACCESS:
                bpAuth.setEnabled(true);
                errorLogin();
                break;

            // When operator info has been received
            case SET_OPERATOR_INFO:
                Log.d("SET_OPERATOR_INFO_","SET_OPERATOR_INFO_");
                DataStore.getInstance().setOperator(new Operator(response.getData().getString(BusProtocol.BUS_DATA_UI_TESTERINFO)));
                bus.post(new BusRequest(BusType.ASK_RECENT_HISTORY));
                break;

            // When the recent history has been received
            case SET_RECENT_HISTORY:
                History recentHistory = new History();
                recentHistory.setHistory(context, response.getData().getString(BusProtocol.BUS_DATA_UI_RECENT_HISTORY),History.TYPE_RECENT_HISTORY);
                DataStore.getInstance().setRecentHistory(recentHistory);

                finishWithResult(Number.FINISH_LOGIN_AND_START_HOME);
                break;

            // When a network problem occur
            case ALARM_UI:
                networkErrorDialog();
                break;
        }
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
     * Process called when the user tap on the back button
     */
    @Override
    public void onBackPressed(){
        quitHomeConfirmation();
    }

    /**
     * Quit message confirmation
     */
    private void quitHomeConfirmation () {
        new MaterialDialog.Builder(context)
                .title(R.string.dialog_home_quit_title)
                .content(R.string.dialog_home_quit_content)
                .cancelable(true)
                .negativeText(R.string.dialog_cancel)
                .positiveText(R.string.dialog_confirm)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finishWithResult(Number.FINISH_ACTIVITY_EXIT);
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
     * Process called to finish an activity
     * @param number the number to send at the SplashActivity
     */
    private void finishWithResult(int number)
    {
        Intent data = new Intent();
        //set the data to pass back
        data.setData(Uri.parse(Integer.valueOf(number).toString()));
        setResult(RESULT_OK, data);

        // close the opened dialog
        if(netErrorDialog!=null)
            if(netErrorDialog.isShowing())
                netErrorDialog.dismiss();

        //finish the activity
        finish();
    }
}
