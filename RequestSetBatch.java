/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public class RequestSetBatch extends RequestTelegram {
  public static int requiredId = 0x31;
  public static int requiredSize = 6;
  
  public RequestSetBatch(byte[] byteArray) throws TelegramException {
    super(byteArray);
  }

  public RequestSetBatch() throws TelegramException {
    super(requiredId, requiredSize);
  }
  
  public void setCoinQuantity(int coin, int quantity) {
    coin = Math.max(0x00, Math.min(coin, 0x0F));
    quantity = Math.max(0x01, Math.min(quantity, 0xFFFFFE));
    data = TelegramHelper.setByteByLong(data, 1, 1, (long) coin);
    data = TelegramHelper.setByteByLong(data, 2, 4, (long) quantity);
  }
  
  public static Class waitsForReply() {
    return ReplyConfirm.class;
  }
}
