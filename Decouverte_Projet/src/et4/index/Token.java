package et4.index;

import java.util.ArrayList;

public class Token {

	private String documentName;
	private ArrayList<Integer> positions = new ArrayList<Integer>();
	private String token;

	public Token(String documentName, int position, String token) {
		this.documentName = documentName;
		this.positions.add(position);
		this.token = token;
	}

	@Override
	public String toString() {
		String result = "";

		result += "[" + documentName + ", positions :" + positions + "]\n";
		result += "[token : " + token + "]";

		return result;
	}

	public String getStringToken() {
		return token;
	}

	public ArrayList<Integer> getPositions() {
		return positions;
	}

}