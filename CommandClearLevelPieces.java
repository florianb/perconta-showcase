/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public class CommandClearLevelPieces extends RequestTelegram {
  public static int requiredId = 0x14;
  public static int requiredSize = 2;
   
  public CommandClearLevelPieces(byte[] byteArray) throws TelegramException {
    super(byteArray);
  }

  public CommandClearLevelPieces() throws TelegramException {
    super(requiredId, requiredSize);
  }
  
  public void setMode(int newMode) {
    newMode = Math.max(0, Math.min(newMode, 5)); // Nur Modes von 0 bis 5 erlauben
    data = TelegramHelper.setByteByLong(data, 1, 1, (long) newMode);
  }
  
  public static Class waitsForReply() {
    return null;
  }
}