/*
 * RequestTelegram
 * 
 * Superklasse zur Erstellung eines Request-Telegrammes. Es werden zwei
 * Möglichkeiten der Erstellung angeboten, die Instanziierung mittels
 * der TelegramId und der Anzahl der Datenbytes.
 * Bei der Erstellung wird dann das Daten-Byte-Array mit der gewünschten Länge
 * angelegt und das erste Byte mit der Telegram-ID initialisiert.
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public class RequestTelegram extends Telegram {
  private byte status = 0;
  
  public RequestTelegram(byte[] byteArray) throws TelegramException {
    super(byteArray);
    
    refreshMemberVariables();
  }

  public RequestTelegram(int telegramId, int countOfDataBytes) throws TelegramException {
    super(telegramId, countOfDataBytes);
  }
  
  public void markAsUnsent() {
    status = 0;
  }  
  
  public void markAsSent() {
    if (requiresAck()) {
      status = 1;
    } else {
      status = 2;
    }
  }
  
  public void markAsAcknowledged() {
    status = 2;
  }

  public boolean isSent() {
    return (status > 0);
  }
  
  public boolean isAcknowledged() {
    return (status > 1);
  }
  
  public static Class waitsForReply() {
    return ReplyTelegram.class;
  }
  
  @Override
  protected void refreshMemberVariables() {
    super.refreshMemberVariables();
  }
}
