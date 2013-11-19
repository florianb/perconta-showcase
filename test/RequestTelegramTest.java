/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author florianbreisch
 */
public class RequestTelegramTest {
  
  private static byte[] testReplyWithId80 = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x04,  // LEN2
    (byte) 0x80,  // TEL - DAT1
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x87}; // CHK2
  private static byte[] testReplyWithId7F = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x04,  // LEN2
    (byte) 0x7F,  // TEL - DAT1
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x86}; // CHK2
    
  @Test
  public void testWaitsForReply() throws TelegramException {
    assertEquals(RequestTelegram.waitsForReply(), ReplyTelegram.class);
  }
  
  @Test
  public void testSetStatusForId7F() throws TelegramException {
    RequestTelegram testTelegramWithId7F = new RequestTelegram(testReplyWithId7F);
    
    assertFalse(testTelegramWithId7F.isSent());
    assertFalse(testTelegramWithId7F.isAcknowledged());
    
    testTelegramWithId7F.markAsSent();

    assertTrue(testTelegramWithId7F.isSent());
    assertFalse(testTelegramWithId7F.isAcknowledged());
    
    testTelegramWithId7F.markAsAcknowledged();

    assertTrue(testTelegramWithId7F.isSent());
    assertTrue(testTelegramWithId7F.isAcknowledged());
    
    testTelegramWithId7F.markAsUnsent();

    assertFalse(testTelegramWithId7F.isSent());
    assertFalse(testTelegramWithId7F.isAcknowledged());
  }
  
  @Test
  public void testSetStatusForId80() throws TelegramException {
    RequestTelegram testTelegramWithId80 = new RequestTelegram(testReplyWithId80);
    
    assertFalse(testTelegramWithId80.isSent());
    assertFalse(testTelegramWithId80.isAcknowledged());
    
    testTelegramWithId80.markAsSent();

    assertTrue(testTelegramWithId80.isSent());
    assertTrue(testTelegramWithId80.isAcknowledged());
    
    testTelegramWithId80.markAsAcknowledged();

    assertTrue(testTelegramWithId80.isSent());
    assertTrue(testTelegramWithId80.isAcknowledged());
    
    testTelegramWithId80.markAsUnsent();

    assertFalse(testTelegramWithId80.isSent());
    assertFalse(testTelegramWithId80.isAcknowledged());
  }
}
