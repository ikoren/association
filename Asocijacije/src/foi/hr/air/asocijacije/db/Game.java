package foi.hr.air.asocijacije.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table (name = "Game")
public class Game extends Model {

	@Column (name = "id_game")
	public long id_game;
	@Column (name = "game_name")
	public String game_name;
	@Column (name = "game_result")
	public int game_result;
	
	public Game (){
		super();
	}

	public Game(long id_game, String game_name, int game_result) {
		super();
		this.id_game = id_game;
		this.game_name = game_name;
		this.game_result = game_result;
	}


}
