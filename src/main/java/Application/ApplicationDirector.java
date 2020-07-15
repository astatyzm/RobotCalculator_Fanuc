package Application;

public class ApplicationDirector {
	// manager - object initialization logic
	public void ConstructApplication(ApplicationBuilder builder) {

		builder.loadSetImage();
		builder.buildButtonsLayout();
		builder.loadSetIcon();
		builder.buildMenu();
		builder.buildCheckBoxesLayout();
		builder.buildAuthorLabel();
		builder.buildLicense();
		builder.buildFrame();

	}
}
 