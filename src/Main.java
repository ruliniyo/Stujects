import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.abilitybots.api.util.AbilityUtils;
import java.sql.*;


public class Main {

	public static void main(String args[]) {
		//Conexion con base de datos
		
		
    
		
		
		
		
		
		//Iniciamos Bot
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		ABot bot = new ABot();
		
		try {
			telegramBotsApi.registerBot(bot);
		} catch (TelegramApiRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
