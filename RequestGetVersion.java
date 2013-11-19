/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public class RequestGetVersion extends RequestTelegram {
  public static int requiredId = 0x29;
  public static int requiredSize = 1;
  
  public RequestGetVersion(byte[] byteArray) throws TelegramException {
    super(byteArray);
  }
  
  public RequestGetVersion() throws TelegramException {
    super(requiredId, requiredSize);
  }
  
  public static Class waitsForReply() {
    return ReplyGetVersion.class;
  }
}
