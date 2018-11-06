import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;




public class Bot extends TelegramLongPollingBot {

	
	@Override
	public void onUpdateReceived(Update update) {
		// TODO Auto-generated method stub
		System.out.println(update.getMessage().getFrom().getFirstName() + ": " + update.getMessage().getText());
	
		//send message
		SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
		sendMessage.setText("Hello: " + update.getMessage().getFrom().getFirstName() + "\n" + update.getMessage().getText());
		
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "Stujects";
	}

	

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "532222914:AAFC8z9yo9qFibSQgCxJTLhP5un8prrzyLw";
	}

}
