package foi.hr.air.asocijacije.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Word")
public class Word extends Model {

	@Column(name = "id_word")
	public long id_word;
	@Column(name = "word_name")
	public String word_name;
	@Column(name = "id_game")
	public long id_game;
	@Column(name = "game")
	public Game game;
	@Column(name = "id_sentence")
	public long id_sentence;
	@Column(name = "sentence")
	public Sentence sentence;

	public Word() {
		super();
	}

	public Word(long id_word, String word_name, long id_game, long id_sentence) {
		super();
		this.id_word = id_word;
		this.word_name = word_name;
		this.id_game = id_game;
		this.id_sentence = id_sentence;
	}

	

}
