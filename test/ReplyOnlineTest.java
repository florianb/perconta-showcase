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
public class ReplyOnlineTest {
  private static byte[] replyOnlineTestTelegram = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x08,  // LEN2
    (byte) 0x82,  // TEL
    (byte) 0xF0,  // VAL1: 4037694887
    (byte) 0xAA,  // VAL2
    (byte) 0x55,  // VAL3
    (byte) 0xA7,  // VAL4
    (byte) 0x03,  // ETX
    (byte) 0x03,  // CHK1 (LEN1 - ETX)
    (byte) 0x23}; // CHK2
  
  private static byte[] replyOnlineTestTelegramWithInvalidId = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x08,  // LEN2
    (byte) 0x81,  // TEL
    (byte) 0xF0,  // VAL1: 4037694887
    (byte) 0xAA,  // VAL2
    (byte) 0x55,  // VAL3
    (byte) 0xA7,  // VAL4
    (byte) 0x03,  // ETX
    (byte) 0x03,  // CHK1 (LEN1 - ETX)
    (byte) 0x23}; // CHK2
    
  private static byte[] replyOnlineTestTelegramWithInvalidSize = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x09,  // LEN2
    (byte) 0x82,  // TEL
    (byte) 0xF0,  // VAL1: 4037694887
    (byte) 0xAA,  // VAL2
    (byte) 0x55,  // VAL3
    (byte) 0xA7,  // VAL4
    (byte) 0x03,  // ETX
    (byte) 0x03,  // CHK1 (LEN1 - ETX)
    (byte) 0x23}; // CHK2
  
  @Test(expected = TelegramException.class)
  public void testCreationOfTelegramWithInvalidId() throws TelegramException {
    ReplyOnline testTelegram = new ReplyOnline(replyOnlineTestTelegramWithInvalidId);
  }

  @Test(expected = TelegramException.class)
  public void testCreationOfTelegramWithInvalidSize() throws TelegramException {
    ReplyOnline testTelegram = new ReplyOnline(replyOnlineTestTelegramWithInvalidSize);
  }
  
  @Test
  public void testCreationOfBareTelegram() throws TelegramException {
    ReplyOnline testTelegram = new ReplyOnline();
  }
  
  @Test
  public void testCreationOfByteStreamTelegram() throws TelegramException {
    ReplyOnline testTelegram = new ReplyOnline(replyOnlineTestTelegram);
  }

  @Test
  public void testWaitFor() throws TelegramException {
    assertEquals(ReplyOnline.answersRequest(), RequestOnline.class);
  }
  
  @Test
  public void testGetCounted() throws TelegramException {
    ReplyOnline testTelegramm = new ReplyOnline(replyOnlineTestTelegram);
    assertEquals("Wrong counted-value!" ,testTelegramm.getCounted(),  4037694887L);
  }
}
