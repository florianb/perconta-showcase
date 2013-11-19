/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public class ReplyOnline extends ReplyTelegram {
  public static int requiredId = 0x82;
  public static int requiredSize = 5;
  
  public ReplyOnline(byte[] byteArray) throws TelegramException {
    super(byteArray);
    refreshMemberVariables();
  }

  public ReplyOnline() throws TelegramException {
    super(requiredId, requiredSize);
    refreshMemberVariables();
  }
  
  public static Class answersRequest() {
    return RequestOnline.class;
  }
  
  public long getCounted() {
    return counted;
  }
  
  //////////////////////////////////////////////////////////////////////////////
  
  private long counted;
  
  @Override
  protected final void refreshMemberVariables() {
    super.refreshMemberVariables();
    
    counted = (long) TelegramHelper.getLongOfBytes(data, 1, 4);
  }
}
