package foi.hr.air.asocijacije.ws;

import java.util.ArrayList;

import android.content.Context;
import foi.hr.air.asocijacije.db.User;

public class WsToDb {

	private ArrayList<User> users;
	private String url = "";

	public WsToDb(Context context) {
		WSExecutor userExecutor = new WSExecutor(context);
		url = "http://10.0.3.2:80/user.php";
		// tu sve zapocinje, poziva se metoda doinbackground
		userExecutor.execute(url, usersHandler);
	}

	// interface
	ResultHandler usersHandler = new ResultHandler() {

		@Override
		public void result(String result) {
			users = GetDataWS.getUsers(result);
			for (User u : users) {
				u.save();
			}

		}
	};

	// s web servera se dohvaca json s podacima (ovisi o url-u koji se odnosi na
	// php skriptu koja se mora pisati za svaku tablicu posebno)
	// interface dobiva rezultat (onPost u wsexecutor), a metoda interfacea
	// dobiveni string parsira i sprema u array listu pa onda u lok bazu

}
