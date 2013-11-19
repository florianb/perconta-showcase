/*
 * ReplyTelegram
 * 
 * Superklasse zur Erstellung eines Reply-Telegrammes. Es werden zwei
 * Möglichkeiten der Erstellung angeboten, die Instanziierung mittels
 * der TelegramId und der Anzahl der Datenbytes.
 * Bei der Erstellung wird dann das Daten-Byte-Array mit der gewünschten Länge
 * angelegt und das erste Byte mit der Telegram-ID initialisiert.
 * 
 * Das ReplyTelegram weiß zudem ob ein ACK gesendet werden muss (requiresAck)
 * und auf welchen Request sich die Antwort bezieht (answersRequest).
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public class ReplyTelegram extends Telegram {
  
  public ReplyTelegram(byte[] byteArray) throws TelegramException {
    super(byteArray);
    
    refreshMemberVariables();
  }
  
  public ReplyTelegram(int telegramId, int countOfDataBytes) throws TelegramException {
    super(telegramId, countOfDataBytes);
  }

  public static Class answersRequest() {
    return RequestTelegram.class;
  }
  
  @Override
  protected void refreshMemberVariables() {
    super.refreshMemberVariables();
  }
}
