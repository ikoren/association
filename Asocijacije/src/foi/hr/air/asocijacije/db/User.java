package foi.hr.air.asocijacije.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "User")
public class User extends Model {
	@Column(name = "user")
	public long id_user;
	@Column(name = "name")
	public String name;
	@Column(name = "surname")
	public String surname;
	@Column(name = "username")
	public String username;
	@Column(name = "password")
	public String password;
	@Column(name = "email")
	public String email;
	@Column(name = "image")
	public String image;
	@Column(name = "result")
	public int result;
	@Column(name = "rating")
	public int rating;

	public User() {
		super();
	}

	public User(long id_user, String name, String surname, String username,
			String password, String email, String image, int result, int rating) {
		super();
		this.id_user = id_user;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.image = image;
		this.result = result;
		this.rating = rating;
	}



}
