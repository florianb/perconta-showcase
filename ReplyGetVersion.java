/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public class ReplyGetVersion extends ReplyTelegram {
  public static int requiredId = 0x2A;
  public static int requiredSize = 22;
  
  public ReplyGetVersion() throws TelegramException {
    super(requiredId, requiredSize);
    refreshMemberVariables();
  }

  public ReplyGetVersion(byte[] byteArray) throws TelegramException {
    super(byteArray);
    refreshMemberVariables();
  }
  
  public static Class answersRequest() {
    return RequestGetVersion.class;
  }
  
  public int getHardwareIdentifier() {
    return hardwareIdentifier;
  }
  
  public String getSerialNumber() {
    return serialNumber;
  }
  
  public String getFirmwareVersion() {
    return firmwareVersion;
  }
  
  //////////////////////////////////////////////////////////////////////////////
  
  private int hardwareIdentifier;
  private String serialNumber;
  private String firmwareVersion;
  
  @Override
  protected final void refreshMemberVariables() {
    super.refreshMemberVariables();
    
    hardwareIdentifier = (int) TelegramHelper.getLongOfBytes(data, 1, 3);
    serialNumber = Long.toString(TelegramHelper.getLongOfBytes(data, 4, 6));
    firmwareVersion = String.format("%d.%d.%d",
            TelegramHelper.getLongOfBytes(data, 10, 4),
            TelegramHelper.getLongOfBytes(data, 14, 4),
            TelegramHelper.getLongOfBytes(data, 18, 4));
  }
}
