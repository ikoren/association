package foi.hr.air.asocijacije.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import foi.hr.air.asocijacije.R;
import foi.hr.air.asocijacije.ui.SessionManager;
import foi.hr.air.asocijacije.main.AppConfig;
import foi.hr.air.asocijacije.main.AppController;
import foi.hr.air.asocijacije.main.MainActivity;

/**
 * Created by Josip on 14.3.2015..
 */
public class LoginActivity extends Activity {

	private static final String TAG = RegisterActivity.class.getSimpleName();
	private Button btnLogin;
	private Button btnLinkToRegister;
	private EditText inputEmail;
	private EditText inputPassword;
	private ProgressDialog pDialog;
	private SessionManager session;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		inputEmail = (EditText) findViewById(R.id.email);
		inputPassword = (EditText) findViewById(R.id.password);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

		pDialog = new ProgressDialog(this);
		pDialog.setCancelable(false);

		session = new SessionManager(getApplicationContext());

		if (session.isLoggedIn()) {
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}

		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();

				if (email.trim().length() > 0 && password.trim().length() > 0) {
					checkLogin(email, password);
				} else {
					Toast.makeText(getApplicationContext(),
							"Please enter the credentials", Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
			}
		});
	}

	private void checkLogin(final String email, final String password) {
		
		String tag_string_req = "req_login";

		pDialog.setMessage("Logging in...");
		showDialog();

		StringRequest strReq = new StringRequest(Method.POST,
				AppConfig.URL_REGISTER, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.d(TAG, "Login response: " + response.toString());
						hideDialog();

						try {
							JSONObject jObj = new JSONObject(response);
							boolean error = jObj.getBoolean("error");

							if (!error) {
								session.setLogin(true);

								Intent intent = new Intent(LoginActivity.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								String errorMsg = jObj.getString("error_msg");
								Toast.makeText(getApplicationContext(),
										errorMsg, Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e(TAG, "Login Error: " + error.getMessage());
						Toast.makeText(getApplicationContext(),
								error.getMessage(), Toast.LENGTH_LONG).show();
						hideDialog();
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				// TODO Auto-generated method stub
				Map<String, String> params = new HashMap<String, String>();
				params.put("tag", "login");
				params.put("email", email);
				params.put("password", password);
				return params;
			}
		};
		AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
	}
	
	private void showDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}
	
	private void hideDialog() {
		if (pDialog.isShowing())
			pDialog.dismiss();
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			setContentView(R.layout.login);
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			setContentView(R.layout.login);
		}
	}
}