/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public class CommandOffline extends RequestTelegram {
  public static int requiredId = 0x1F;
  public static int requiredSize = 1;
   
  public CommandOffline(byte[] byteArray) throws TelegramException {
    super(byteArray);
  }

  public CommandOffline() throws TelegramException {
    super(requiredId, requiredSize);
  }
  
  public static Class waitsForReply() {
    return null;
  }
}