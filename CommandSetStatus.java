/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public class CommandSetStatus extends RequestTelegram {
  public static int requiredId = 0x17;
  public static int requiredSize = 2;
   
  public CommandSetStatus(byte[] byteArray) throws TelegramException {
    super(byteArray);
  }

  public CommandSetStatus() throws TelegramException {
    super(requiredId, requiredSize);
    setMotor(false);
  }
  
  public final void setMotor(boolean state) {
    initData(true);
    if (state) {
      data[1] = 0x01;
    } else {
      data[1] = 0x02;
    }
  }

  public final void setVibrator(boolean state) {
    initData(true);
    if (state) {
      data[1] = 0x05;
    } else {
      data[1] = 0x06;
    }   
  }
  
  public final void selectMode(int mode) {
    selectMode(mode, 0x10);
  }

  
  public final void selectMode(int mode, int coin) {
    initData(false);
    mode = Math.max(0, Math.min(mode, 5));
    coin = Math.max(0x00, Math.min(coin, 0x10));
    data[1] = 0x09;
    data[2] = (byte) mode;
    data[3] = (byte) coin;
  }

  
  public static Class waitsForReply() {
    return null;
  }
  
  @Override
  protected boolean hasValidSize() {
    if ((int) data[1] == 0x09) {
      return data.length == 4;
    }
    else {
      return data.length == 2;
    }
  }
  
  private void initData(boolean forFunction) {
    if (forFunction) {
      data = new byte[2];
    } else {
      data = new byte[4];
    }
    data[0] = (byte) requiredId;
  }
}