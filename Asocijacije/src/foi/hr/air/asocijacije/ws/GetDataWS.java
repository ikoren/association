package foi.hr.air.asocijacije.ws;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import foi.hr.air.asocijacije.db.User;

public class GetDataWS {
	
	public static ArrayList<User> getUsers (String result) {
		JSONArray users = new JSONArray();
		ArrayList<User> userList = new ArrayList<User>();
		String tag_users = "users";
		if (result != null) {

			try {

				JSONObject jsonObject = new JSONObject(result);
				users = jsonObject.getJSONArray(tag_users);
				for (int i = 0; i < users.length(); i++) {

					JSONObject jsn = users.getJSONObject(i);
					User user = new User(jsn.getInt("id_user"),
							jsn.getString("name"), jsn.getString("surname"),
							jsn.getString("username"),
							jsn.getString("password"),
							jsn.getString("email"),
							jsn.getString("image"),
							jsn.getInt("result"), jsn.getInt("rating"));

					userList.add(user);
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return userList;
	}
}
