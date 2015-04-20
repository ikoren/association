package foi.hr.air.asocijacije.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Sentence")
public class Sentence extends Model {

	@Column(name = "id_sentence")
	public long id_sentence;
	@Column(name = "description")
	public String description;

	public Sentence() {
		super();
	}

	public Sentence(long id_sentence, String description) {
		super();
		this.id_sentence = id_sentence;
		this.description = description;
	}

}
