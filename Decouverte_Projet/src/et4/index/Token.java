package et4.index;

public class Token {

	private String documentName;
	private int position;
	private String token;

	public Token(String documentName, int position, String token) {
		this.documentName = documentName;
		this.position = position;
		this.token = token;
	}

	@Override
	public String toString() {
		String result = "";

		result += "[" + documentName + ", position :" + position + "]\n";
		result += "[token : " + token + "]";

		return result;
	}

	public String getStringToken() {
		return token;
	}
	
	public int getPosition() {
		return position;
	}

}
