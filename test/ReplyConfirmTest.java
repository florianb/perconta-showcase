/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author florianbreisch
 */
public class ReplyConfirmTest {
  private static byte[] replyConfirmTestTelegram = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x06,  // LEN2
    (byte) 0x2B,  // TEL
    (byte) 0x00,  // NUL
    (byte) 0x00,  // NUL
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x34}; // CHK2
  
  private static byte[] replyConfirmTestTelegramWithInvalidId = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x06,  // LEN2
    (byte) 0x2C,  // TEL
    (byte) 0x00,  // NUL
    (byte) 0x00,  // NUL
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x35}; // CHK2
    
  private static byte[] replyConfirmTestTelegramWithInvalidSize = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x07,  // LEN2
    (byte) 0x2B,  // TEL
    (byte) 0x00,  // NUL
    (byte) 0x00,  // NUL
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x35}; // CHK2
  
  @Test(expected = TelegramException.class)
  public void testCreationOfTelegramWithInvalidId() throws TelegramException {
    ReplyConfirm testTelegram = new ReplyConfirm(replyConfirmTestTelegramWithInvalidId);
  }

  @Test(expected = TelegramException.class)
  public void testCreationOfTelegramWithInvalidSize() throws TelegramException {
    ReplyConfirm testTelegram = new ReplyConfirm(replyConfirmTestTelegramWithInvalidSize);
  }
  
  @Test
  public void testCreationOfBareTelegram() throws TelegramException {
    ReplyConfirm testTelegram = new ReplyConfirm();
    assertTrue(Arrays.equals(testTelegram.toByteArray(), replyConfirmTestTelegram));
  }
  
  @Test
  public void testCreationOfByteStreamTelegram() throws TelegramException {
    ReplyConfirm testTelegram = new ReplyConfirm(replyConfirmTestTelegram);
  }

  @Test
  public void testWaitFor() throws TelegramException {
    assertEquals(ReplyConfirm.answersRequest(), RequestSetBatch.class);
  }
}
