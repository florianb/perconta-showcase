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
public class TelegramTest {
  private static byte[] emptyTelegram = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x03,  // LEN2
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x06}; // CHK2
  private static byte[] validTelegramWithChecksum256 = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x04,  // LEN2
    (byte) 0xF9,  // TEL - DAT1
    (byte) 0x03,  // ETX
    (byte) 0x01,  // CHK1 (LEN1 - ETX)
    (byte) 0x00}; // CHK2
  private static byte[] validRequestOnlineTelegram = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x04,  // LEN2
    (byte) 0x1E,  // TEL - DAT1
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x25}; // CHK2
  private static byte[] invalidRequestOnlineTelegram = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x04,  // LEN2
    (byte) 0x1E,  // TEL - DAT1
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x26}; // CHK2
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
  private static byte[] testReplyWithIdFF = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x04,  // LEN2
    (byte) 0xFF,  // TEL - DAT1
    (byte) 0x03,  // ETX
    (byte) 0x01,  // CHK1 (LEN1 - ETX)
    (byte) 0x06}; // CHK2

  @Test(expected = TelegramException.class)
  public void testEmptyTelegramCreation() throws Exception {
    Telegram testTelegram = new Telegram(emptyTelegram);
  }
  
  @Test
  public void testDiverseTelegramsToString() throws Exception {
    // Dieser Test ist unmöglich, da leere Telegramme nicht mehr valide sind.    
    //Telegram testTelegram = new Telegram(emptyTelegram);
    //assertTrue(testTelegram.toString().equalsIgnoreCase("000...02.00.03.03\n004...00.06"));
        
    Telegram testTelegram = new Telegram(validRequestOnlineTelegram);
    assertTrue(testTelegram.toString().equalsIgnoreCase("000...02.00.04.1E\n004...03.00.25"));
  }
    
  @Test
  public void testValidTelegramCreation() throws Exception { 
    Telegram testTelegram = new Telegram(validRequestOnlineTelegram);
  }
  
  @Test
  public void testInvalidTelegramException() { 
    try {
      Telegram testTelegram = new Telegram(invalidRequestOnlineTelegram);
      fail("Telegram should throw an exception because its checksum or length-value is wrong.");
    } catch (Exception thrownException) {
      assertTrue(thrownException instanceof TelegramException);
    }
  }
  
  @Test
  public void testChecksumOverflow() throws TelegramException {
    Telegram testTelegram = new Telegram(validTelegramWithChecksum256);
  }
  
  @Test
  public void testTelegramId() throws TelegramException {
    Telegram testTelegram = new Telegram(validRequestOnlineTelegram);
    assertEquals(testTelegram.getTelegramId(), (int) 0x1E);
  }
  
  @Test
  public void testTelegramCreationByIdAndSize() throws TelegramException {
    Telegram testTelegram = new Telegram(0xF9, 1);
    assertTrue(Arrays.equals(validTelegramWithChecksum256, testTelegram.toByteArray()));
  }
  
  @Test 
  public void testTelegramCreationByIdAndSizeFailure() {
    try {
      Telegram testTelegram = new Telegram(0xF9, 0);
      fail("Telegram should throw an exception because its datagram size is too small (< 1).");
    } catch (Exception thrownException) {
      assertTrue(thrownException instanceof TelegramException);
    }
  }
  
  @Test
  public void testRequiresAck() throws TelegramException {
    ReplyTelegram testReply = new ReplyTelegram(testReplyWithId80);
    assertFalse(testReply.requiresAck());
    
    testReply = new ReplyTelegram(testReplyWithId7F);
    assertTrue(testReply.requiresAck());
    
    testReply = new ReplyTelegram(testReplyWithIdFF);
    assertFalse(testReply.requiresAck());
  }
}
