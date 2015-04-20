package foi.hr.air.asocijacije.main;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import foi.hr.air.asocijacije.R;
import foi.hr.air.asocijacije.db.User;
import foi.hr.air.asocijacije.ws.WsToDb;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActiveAndroid.initialize(this);

		WsToDb wtdb = new WsToDb(this);
		List<User> users = new Select().from(User.class).execute();
		Toast.makeText(this, users.get(0).name, Toast.LENGTH_LONG).show();
		Toast.makeText(this, users.get(0).surname, Toast.LENGTH_LONG).show();


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
