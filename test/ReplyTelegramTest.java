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
public class ReplyTelegramTest {
  private static byte[] testReplyWithId80 = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x04,  // LEN2
    (byte) 0x80,  // TEL - DAT1
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x87}; // CHK2

  @Test
  public void testAnswersRequest() throws TelegramException {    
    assertEquals(ReplyTelegram.answersRequest(), RequestTelegram.class);
  }
}
