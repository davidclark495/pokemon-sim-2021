package m_activities;

/**
 * Represents an option that the player can select.
 * Has a description, an ID, and a result.
 * 
 * Has a setResult method for ease of initialization, 
 * should otherwise not be altered during runtime.
 * 
 * @author davidclark
 * Date: 08/31/21
 */
public class Option {
	private String id;
	private String description;
	private Result result;

	public Option(String id, String description, Result result) {
		this.id = id;
		this.description = description;
		this.result = result;
	}


	///// Accessors /////
	public String getID() {
		return id;
	}
	public String getDescription() {
		return description;
	}
	public Result getResult() {
		return result;
	}

	///// Mutators /////
	public void setResult(Result result) {
		this.result = result;
	}
}