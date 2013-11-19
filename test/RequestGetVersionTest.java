/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
/**
 *
 * @author florianbreisch
 */
public class RequestGetVersionTest {
  private static byte[] requestGetVersionTelegram = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x04,  // LEN2
    (byte) 0x29,  // TEL - DAT1
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x30}; // CHK2
  private static byte[] requestGetVersionTelegramWithInvalidID = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x04,  // LEN2
    (byte) 0x28,  // TEL - DAT1
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x30}; // CHK2
  private static byte[] requestGetVersionTelegramWithInvalidLength = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x03,  // LEN2
    (byte) 0x29,  // TEL - DAT1
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x30}; // CHK2
    
  @Test(expected = TelegramException.class)
  public void testCreationOfInvalidGetVersionTelegramWithInvalidId() throws TelegramException {
    Telegram testTelegram = new RequestGetVersion(requestGetVersionTelegramWithInvalidID);
  }
  
  @Test(expected = TelegramException.class)
  public void testCreationOfInvalidGetVersionTelegramWithInvalidLength() throws TelegramException {
    Telegram testTelegram = new RequestGetVersion(requestGetVersionTelegramWithInvalidLength);
  }
  
  @Test
  public void testCreate() throws Exception {
    RequestGetVersion testTelegram = new RequestGetVersion();
    assertTrue(Arrays.equals(requestGetVersionTelegram, testTelegram.toByteArray()));
  }
  
  @Test
  public void testWaitsforReply() throws TelegramException {
    assertEquals(RequestGetVersion.waitsForReply(), ReplyGetVersion.class);
  }
}
