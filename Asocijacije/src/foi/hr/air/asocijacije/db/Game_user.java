package foi.hr.air.asocijacije.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Game_user")
public class Game_user extends Model {

	@Column(name = "id_entry")
	public long id_entry;
	@Column(name = "id_user")
	public long id_user;
	@Column(name = "user")
	public User user;
	@Column(name = "id_game")
	public long id_game;
	@Column(name = "game")
	public Game game;

	public Game_user() {
		super();
	}

	public Game_user(long id_entry, long id_user, long id_game) {
		super();
		this.id_entry = id_entry;
		this.id_user = id_user;
		this.id_game = id_game;
	}

}
