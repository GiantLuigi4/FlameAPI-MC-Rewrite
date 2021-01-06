package commons;

import java.util.ArrayList;

public class CSV {
	public String[][] file;
	
	public CSV(String[][] file) {
		this.file = file;
	}
	
	public CSV(String text) {
		ArrayList<String[]> sheet = new ArrayList<>();
		for (String row : text.split("\n")) {
			ArrayList<String> rowArray = new ArrayList<>();
			for (String column : row.split(","))
				rowArray.add(column.trim());
			sheet.add(rowArray.toArray(new String[0]));
		}
		file = sheet.toArray(new String[0][0]);
	}
	
	public String get(int row, int column) {
		return file[row][column];
	}
	
	public int sizeOf(int row) {
		return file[row].length;
	}
	
	public int size() {
		return file.length;
	}
}
