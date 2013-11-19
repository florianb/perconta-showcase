/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author florianbreisch
 */
public class RequestOnlineTest {
  private static byte[] requestOnlineTestTelegram = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x04,  // LEN2
    (byte) 0x1E,  // TEL
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x25}; // CHK2
  
  private static byte[] requestOnlineTestTelegramWithInvalidId = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x04,  // LEN2
    (byte) 0x1F,  // TEL
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x25}; // CHK2
    
  private static byte[] requestOnlineTestTelegramWithInvalidSize = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x54,  // LEN2
    (byte) 0x1E,  // TEL
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x25}; // CHK2
  
  @Test(expected = TelegramException.class)
  public void testCreationOfTelegramWithInvalidId() throws TelegramException {
    RequestOnline testTelegram = new RequestOnline(requestOnlineTestTelegramWithInvalidId);
  }

  @Test(expected = TelegramException.class)
  public void testCreationOfTelegramWithInvalidSize() throws TelegramException {
    RequestOnline testTelegram = new RequestOnline(requestOnlineTestTelegramWithInvalidSize);
  }
  
  @Test
  public void testCreationOfBareTelegram() throws TelegramException {
    RequestOnline testTelegram = new RequestOnline();
  }
  
  @Test
  public void testCreationOfByteStreamTelegram() throws TelegramException {
    RequestOnline testTelegram = new RequestOnline(requestOnlineTestTelegram);
  }

  @Test
  public void testWaitFor() throws TelegramException {
    assertEquals(RequestOnline.waitsForReply(), ReplyOnline.class);
  }
}
