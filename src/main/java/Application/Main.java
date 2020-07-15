package Application;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(Main.class);
		// alt + f5 Mavena !
		ApplicationDirector applicationDirector = new ApplicationDirector();
		ApplicationBuilder builder = new Builder();
		applicationDirector.ConstructApplication(builder);
		logger.info("Main started successfully");
		
	}

} 