/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public class RequestOnline extends RequestTelegram {
  public static int requiredId = 0x1E;
  public static int requiredSize = 1;
  
  public RequestOnline(byte[] byteArray) throws TelegramException {
    super(byteArray);
  }

  public RequestOnline() throws TelegramException {
    super(requiredId, requiredSize);
  }
  
  public static Class waitsForReply() {
    return ReplyOnline.class;
  }
}
