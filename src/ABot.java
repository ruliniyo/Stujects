import com.google.common.annotations.VisibleForTesting;
import com.vdurmont.emoji.EmojiParser;

import org.glassfish.hk2.api.Visibility;
import org.telegram.abilitybots.api.bot.*;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.*;

import java.sql.*;
import java.util.*;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.objects.Flag.MESSAGE;
import static org.telegram.abilitybots.api.objects.Flag.REPLY;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.ADMIN;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;







public class ABot extends AbilityBot{
	
	public void sendCustomKeyboard(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Bienvenido a stujects, selecciona una opcion del menú");

        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("/lista");
        // Add the first row to the keyboard
        keyboard.add(row);
        // Create another keyboard row
        row = new KeyboardRow();
        // Set each button for the second line
        row.add("/nuevo");
        // Add the second row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(keyboardMarkup);

        try {
            // Send the message
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
	
	
	

	public static String BOT_TOKEN = "470857291:AAERwxEJ7fAbvoPLTkEnqxxoeRnhpeb7UHY";
	public static String BOT_USERNAME = "Stujects_bot";
	
	public ABot() {
		super(BOT_TOKEN, BOT_USERNAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int creatorId() {
		// TODO Auto-generated method stub
		return 327149233;
	}
	



public Ability ListaProyectos() {
    return Ability.builder()
        .name("lista")
        .info("Lista los proyectos actuales")
        .privacy(PUBLIC)
        .locality(ALL)
        .input(0)
        .action(ctx -> {
          // db.getMap takes in a string, this must be unique and the same everytime you want to call the exact same map
          // TODO: Using integer as a key in this db map is not recommended, it won't be serialized/deserialized properly if you ever decide to recover/backup db
        	Map<Integer, String> listmap = db.getMap("PROYECTOS");
          int userId = ctx.user().id();

          
          
          
          Iterator it = listmap.entrySet().iterator();
          String answer;
          while(it.hasNext()) {
        	  
        	  Map.Entry e = (Map.Entry)it.next();
          
          // Send formatted will enable markdown
        	  System.out.println("Clave: " + e.getKey() + " -> Valor: " + e.getValue());
        	  
          answer = EmojiParser.parseToUnicode(":diamonds:ID del Proyecto: " + e.getKey() + " \n\n:eight_spoked_asterisk:Descripcion::eight_spoked_asterisk:\n" + e.getValue());
          silent.send(answer, ctx.chatId());
          
          
          }
          sendCustomKeyboard(String.valueOf(ctx.chatId()));
        })

        .build();
}    
    
    
    
    
    
    
public Ability nuevoproyecto() {
	String nameofproj = "Escribe el nombre del proyecto";
	String descproj = "Escribe la descripcion de tu proyecto";
	project proyecto = new project();
	
	
	
    	return Ability.builder()
    			.name("nuevo")
    			.info("nuevo NombreProyecto descripcion")
    			.input(0)
    			.locality(ALL)
    			.privacy(PUBLIC)
    			.action(ctx -> {
    				
    			silent.forceReply(nameofproj, ctx.chatId());
    				
    			})
    			.reply(upd -> {
    			
    			proyecto.userId = Integer.valueOf(upd.getMessage().getFrom().getId());
    			proyecto.user = String.valueOf(upd.getMessage().getFrom().getFirstName()); 		
    			proyecto.nameproj = String.valueOf(upd.getMessage().getText());
    			
    			System.out.println(proyecto.userId+" "+proyecto.user+" "+proyecto.nameproj);
    				
    			silent.forceReply(descproj, upd.getMessage().getChatId());

    				
    			},
    			
    			Flag.MESSAGE,
    			Flag.REPLY,
    			isReplyToBot(),
    			isReplyToMessage(nameofproj)
    			)
    			
    			.reply(upd -> {
    			
    			Map<Integer, String> listmap = db.getMap("PROYECTOS");
    			
    			proyecto.descproj = String.valueOf(upd.getMessage().getText());
    				
    			listmap.put(proyecto.userId, "Proyecto de: "+proyecto.user+". Nombre: "+proyecto.nameproj+". Descripcion: "+proyecto.descproj);
    			
    			silent.send("Proyecto creado con éxito", upd.getMessage().getChatId());
    			sendCustomKeyboard(String.valueOf(upd.getMessage().getChatId()));
    			},
    			Flag.MESSAGE,
    	    	Flag.REPLY,
    	    	isReplyToBot(),
    	    	isReplyToMessage(descproj)
    			)
    			.build();
}
  


	private Predicate<Update> isReplyToBot() {
	      return upd -> upd.getMessage().getReplyToMessage().getFrom().getUserName().equalsIgnoreCase(getBotUsername());
	    }
	
			

private Predicate<Update> isReplyToMessage(String message) {
    return upd -> {
      Message reply = upd.getMessage().getReplyToMessage();
      return reply.hasText() && reply.getText().equalsIgnoreCase(message);
    };
  }
}

