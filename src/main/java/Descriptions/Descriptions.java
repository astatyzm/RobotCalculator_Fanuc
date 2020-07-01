package Descriptions;

public enum Descriptions {
	
	STRING_FRAME_TITLE("Robot Calculator"),
	STRING_LOGO_JPG("logo.jpg"), 
	STRING_FAUCICON_PNG("fanucIcon.png"),
	STRING_PATH_FILE_IN("Path File In"),
	STRING_PATH_FILE_IN_TOOL_TIP("Click here to select LS program to mirror."),
	STRING_FILE_LOAD_SUCCESFULLY("File loaded successfully"),
	STRING_NEW_FOLDER_NAME("ReversedPath"),
	STRING_WRONG_EXTENSION("Wrong file extension."),
	STRING_CONFIG_CHECKBOX("Check Configuration in points"),
	STRING_SPEED_CHECKBOX("Check Speed in points"),
	STRING_ZONE_CHECKBOX("Check Zone in points"),
	STRING_FILELOADED_TOOL_TIP("Path of input file"),
	STRING_FILE_SAVED_SUCCESFULLY("File saved successfully"),
	STRING_EXECUT_REVERSE("Execute & Reverse"),
	STRING_EXECUT_REVERSE_TOOL_TIP("Path mirror and save output"),
	STRING_MESSAGE_WRONG_CONG("Wrong CONFIG in OLP.\n"),
	STRING_MESSAGE_WRONG_SPEED("Wrong Speed in OLP.\n"),
	STRING_MESSAGE_WRONG_ZONE("Wrong Zones-approximations in OLP.\n"),
	STRING_AUTHOR("Author: astatyzm"),
	STRING_SIMPLE_DATE_FORMAT("yyyy-MM-dd"),
	STRING_MESSAGE_EXPIRED("Sorry your product is expired."),
	STRING_HELP("Help"),
	STRING_LICENSE("License"),
	STRING_EXPIRE_DATE_TEXT("Expire date "), 
	STRING_EMPTY_FILE("Selected file is empty.");

	private final String stringValue;

	Descriptions(String string) {
		stringValue = string;
	}

	public String displayString() {
		return stringValue;
	}

}
