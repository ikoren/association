package foi.hr.air.asocijacije.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import foi.hr.air.asocijacije.R;
import foi.hr.air.asocijacije.db.SQLiteHandler;
import foi.hr.air.asocijacije.main.AppConfig;
import foi.hr.air.asocijacije.main.AppController;
import foi.hr.air.asocijacije.main.MainActivity;

/**
 * Created by Josip on 14.3.2015..
 */
public class RegisterActivity extends Activity {
	
	private static final String TAG = RegisterActivity.class.getSimpleName();
	private Button btnRegister;
	private Button btnLinkToLogin;
	private EditText inputName;
	private EditText inputSurname;
	private EditText inputEmail;
	private EditText inputPassword;
	private EditText inputPasswordRepeated;
	private ProgressDialog pDialog;
	private SessionManager session;
	private SQLiteHandler db;

    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.register);
        
        inputName = (EditText) findViewById(R.id.name);
        inputSurname = (EditText) findViewById(R.id.surname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputPasswordRepeated = (EditText) findViewById(R.id.passwordCheck);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        
        session = new SessionManager(getApplicationContext());
        
        db = new SQLiteHandler(getApplicationContext());
        
        if (session.isLoggedIn()) {
        	Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        	startActivity(intent);
        	finish();
        }
        
        btnRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = inputName.getText().toString();
				String surname = inputSurname.getText().toString();
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				String passwordCheck = inputPasswordRepeated.getText().toString();
				
				if(name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty() || passwordCheck.isEmpty()) {
					Toast.makeText(getApplicationContext(), "Please enter your details", Toast.LENGTH_LONG).show();
				}
				else {
					if (password.equals(passwordCheck)){
						registerUser(name, surname, email, password);
					}
					else {
						Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_LONG).show();
					}
				}
			}
		});
        
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }
    
    private void registerUser(final String name, final String surname, final String email, final String password) {
    	
    	String tag_string_req = "req_register";
    	
    	pDialog.setMessage("Registering...");
    	showDialog();
    	
    	StringRequest strReq = new StringRequest(Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {
    		@Override
    		public void onResponse(String response) {
    			// TODO Auto-generated method stub
    			Log.d(TAG, "Register response: " + response.toString());
    			hideDialog();
    			
    			try {
    				JSONObject jObj = new JSONObject(response);
    				boolean error = jObj.getBoolean("error");
    				if(!error) {
    					String uid = jObj.getString("uid");
    					JSONObject user = jObj.getJSONObject("user");
    					String name = user.getString("name");
    					String surname = user.getString("surname");
    					String email = user.getString("email");
    					String created_at = user.getString("created_at");
    					
    					db.addUser(uid, name, surname, email, created_at);
    					
    					Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
    					startActivity(intent);
    					finish();
    				}
    				else {
    					String errorMsg = jObj.getString("error_msg");
    					Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
    				}
    			}
    			catch (JSONException e) {
    				e.printStackTrace();
    			}
    		}
    	}, new Response.ErrorListener() {
    		@Override
    		public void onErrorResponse(VolleyError error) {
    			// TODO Auto-generated method stub
    			Log.e(TAG, "Registration error: " + error.getMessage());
    			Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
    			hideDialog();
    		}
    	}) {
    		@Override
    		protected Map<String, String> getParams() {
    			// TODO Auto-generated method stub
    			Map<String, String> params = new HashMap<String, String>();
    			params.put("tag", "register");
    			params.put("name", name);
    			params.put("surname", surname);
    			params.put("email", email);
    			params.put("password", password);

    			return params;
    		}
    	};
    	AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    
    private void showDialog() {
    	if (!pDialog.isShowing()) {
    		pDialog.show();
    	}
    }
    
    private void hideDialog() {
    	if(pDialog.isShowing()) {
    		pDialog.dismiss();
    	}
    }
}