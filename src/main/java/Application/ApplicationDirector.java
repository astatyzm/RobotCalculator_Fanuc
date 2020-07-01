package Application;

public class ApplicationDirector {
	// kierownik - logika inicjalizacji obiektu
	public void ConstructApplication(ApplicationBuilder builder) {

		builder.LoadSetImage();
		builder.BuildButtonsLayout();
		builder.LoadSetIcon();
		builder.BuildMenu();
		builder.BuildCheckBoxesLayout();
		builder.BuildAuthorLabel();
		builder.BuildLicense();
		builder.BuildFrame();

	}

}
