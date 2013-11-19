/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public class ReplyConfirm extends ReplyTelegram {
  public static int requiredId = 0x2B;
  public static int requiredSize = 3;
   
  public ReplyConfirm(byte[] byteArray) throws TelegramException {
    super(byteArray);
  }

  public ReplyConfirm() throws TelegramException {
    super(requiredId, requiredSize);
  }
  
  public static Class answersRequest() {
    return RequestSetBatch.class;
  }
}