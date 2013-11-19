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
public class RequestSetBatchTest {
  private static byte[] requestSetEmptyBatchTelegram = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x09,  // LEN2
    (byte) 0x31,  // TEL - DAT1
    (byte) 0x00,  // CDN
    (byte) 0x00,  // BTCH1
    (byte) 0x00,  // BTCH2
    (byte) 0x00,  // BTCH3
    (byte) 0x00,  // BTCH4
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x3D}; // CHK2
  private static byte[] requestSetEmptyBatchTelegramWithInvalidId = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x09,  // LEN2
    (byte) 0x30,  // TEL - DAT1
    (byte) 0x00,  // CDN
    (byte) 0x00,  // BTCH1
    (byte) 0x00,  // BTCH2
    (byte) 0x00,  // BTCH3
    (byte) 0x00,  // BTCH4
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x36}; // CHK2
  private static byte[] requestSetEmptyBatchTelegramWithInvalidSize = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x08,  // LEN2
    (byte) 0x31,  // TEL - DAT1
    (byte) 0x00,  // CDN
    (byte) 0x00,  // BTCH1
    (byte) 0x00,  // BTCH2
    (byte) 0x00,  // BTCH3
    (byte) 0x00,  // BTCH4
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x37}; // CHK2
  private static byte[] requestSetFullBatchTelegram = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x09,  // LEN2
    (byte) 0x31,  // TEL - DAT1
    (byte) 0x07,  // CDN
    (byte) 0x00,  // BTCH1: 12442486
    (byte) 0xBD,  // BTCH2
    (byte) 0xDB,  // BTCH3
    (byte) 0x76,  // BTCH4
    (byte) 0x03,  // ETX
    (byte) 0x02,  // CHK1 (LEN1 - ETX)
    (byte) 0x52}; // CHK2
  
  @Test(expected = TelegramException.class)
  public void testCreationOfInvalidSetBatchTelegramWithInvalidId() throws TelegramException {
    Telegram testTelegram = new RequestSetBatch(requestSetEmptyBatchTelegramWithInvalidId);
  }
  
  @Test(expected = TelegramException.class)
  public void testCreationOfInvalidSetBatchTelegramWithInvalidLength() throws TelegramException {
    Telegram testTelegram = new RequestSetBatch(requestSetEmptyBatchTelegramWithInvalidSize);
  }
  
  @Test
  public void testCreate() throws TelegramException {
    RequestSetBatch testTelegram = new RequestSetBatch();
    assertTrue("Bar erstelltes SetBatch-Telegramm entspricht nicht den Vorgaben. (" +
            testTelegram.toString() + ")",
            Arrays.equals(requestSetEmptyBatchTelegram, testTelegram.toByteArray()));
    testTelegram.setCoinQuantity(7, 12442486);
    assertTrue("Angepasstes SetBatch-Telegram enthält falsche Nutzdaten. (" +
            testTelegram.toString() + ")",
            Arrays.equals(requestSetFullBatchTelegram, testTelegram.toByteArray()));
  }
  
  @Test
  public void testWaitsforReply() throws TelegramException {
    assertEquals(RequestSetBatch.waitsForReply(), ReplyConfirm.class);
  }
}
