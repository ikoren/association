package foi.hr.air.asocijacije.main;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import foi.hr.air.asocijacije.R;
import foi.hr.air.asocijacije.db.SQLiteHandler;
import foi.hr.air.asocijacije.db.User;
import foi.hr.air.asocijacije.ui.LoginActivity;
import foi.hr.air.asocijacije.ui.SessionManager;
import foi.hr.air.asocijacije.ws.WsToDb;

public class MainActivity extends Activity {
	
	private TextView txtName;
	private TextView txtEmail;
	private Button btnLogout;
	
	private SQLiteHandler db;
	private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtName = (TextView) findViewById(R.id.name);
		txtEmail = (TextView) findViewById(R.id.email);
		btnLogout = (Button) findViewById(R.id.btnLogout);
		
		db = new SQLiteHandler(getApplicationContext());
		session = new SessionManager(getApplicationContext());
		
		if(!session.isLoggedIn()) {
			logoutUser();
		}
		
		HashMap<String, String> user = db.getUserDetails();
		String name = user.get("name");
		String email = user.get("email");
		
		txtName.setText(name);
		txtEmail.setText(email);
		
		btnLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				logoutUser();
			}
		});
		
		ActiveAndroid.initialize(this);

		WsToDb wtdb = new WsToDb(this);
		List<User> users = new Select().from(User.class).execute();
		Toast.makeText(this, users.get(0).name, Toast.LENGTH_LONG).show();
		Toast.makeText(this, users.get(0).surname, Toast.LENGTH_LONG).show();
	}
	
	private void logoutUser() {
		session.setLogin(false);
		
		db.deleteUsers();
		
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
