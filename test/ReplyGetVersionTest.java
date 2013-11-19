/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author florianbreisch
 */
public class ReplyGetVersionTest {
  private static byte[] validTelegramWithChecksum256 = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x04,  // LEN2
    (byte) 0xF9,  // TEL - DAT1
    (byte) 0x03,  // ETX
    (byte) 0x01,  // CHK1 (LEN1 - ETX)
    (byte) 0x00}; // CHK2
  
  private static byte[] validReply = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x19,  // LEN2
    (byte) 0x2A,  // TEL - DAT1
    (byte) 0x42,  // HW1: 4358436
    (byte) 0x81,  // HW2
    (byte) 0x24,  // HW3
    (byte) 0xBC,  // SER1: 207367836959012
    (byte) 0x99,  // SER2
    (byte) 0x96,  // SER3
    (byte) 0x42,  // SER4
    (byte) 0x81,  // SER5
    (byte) 0x24,  // SER6
    (byte) 0x96,  // SW1: 2529702211
    (byte) 0xC8,  // SW2
    (byte) 0x31,  // SW3
    (byte) 0x43,  // SW4
    (byte) 0x97,  // SW5: 2547594579
    (byte) 0xD9,  // SW6
    (byte) 0x35,  // SW7
    (byte) 0x53,  // SW8
    (byte) 0x96,  // SW9: 2529702211
    (byte) 0xC8,  // SW10
    (byte) 0x31,  // SW11
    (byte) 0x43,  // SW12
    (byte) 0x03,  // ETX
    (byte) 0x09,  // CHK1 (LEN1 - ETX)
    (byte) 0x9B}; // CHK2
  
  private static byte[] invalidIDReply = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x19,  // LEN2
    (byte) 0x2B,  // TEL - DAT1
    (byte) 0x42,  // HW1: 4358436
    (byte) 0x81,  // HW2
    (byte) 0x24,  // HW3
    (byte) 0xBC,  // SER1: 207367836959012
    (byte) 0x99,  // SER2
    (byte) 0x96,  // SER3
    (byte) 0x42,  // SER4
    (byte) 0x81,  // SER5
    (byte) 0x24,  // SER6
    (byte) 0x96,  // SW1: 2529702211
    (byte) 0xC8,  // SW2
    (byte) 0x31,  // SW3
    (byte) 0x43,  // SW4
    (byte) 0x97,  // SW5: 2547594579
    (byte) 0xD9,  // SW6
    (byte) 0x35,  // SW7
    (byte) 0x53,  // SW8
    (byte) 0x96,  // SW9: 2529702211
    (byte) 0xC8,  // SW10
    (byte) 0x31,  // SW11
    (byte) 0x43,  // SW12
    (byte) 0x03,  // ETX
    (byte) 0x09,  // CHK1 (LEN1 - ETX)
    (byte) 0x9C}; // CHK2
  
    private static byte[] invalidLengthReply = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x20,  // LEN2
    (byte) 0x2B,  // TEL - DAT1
    (byte) 0x42,  // HW1: 4358436
    (byte) 0x81,  // HW2
    (byte) 0x24,  // HW3
    (byte) 0xBC,  // SER1: 207367836959012
    (byte) 0x99,  // SER2
    (byte) 0x96,  // SER3
    (byte) 0x42,  // SER4
    (byte) 0x81,  // SER5
    (byte) 0x24,  // SER6
    (byte) 0x96,  // SW1: 2529702211
    (byte) 0xC8,  // SW2
    (byte) 0x31,  // SW3
    (byte) 0x43,  // SW4
    (byte) 0x97,  // SW5: 2547594579
    (byte) 0xD9,  // SW6
    (byte) 0x35,  // SW7
    (byte) 0x53,  // SW8
    (byte) 0x96,  // SW9: 2529702211
    (byte) 0xC8,  // SW10
    (byte) 0x31,  // SW11
    (byte) 0x43,  // SW12
    (byte) 0x03,  // ETX
    (byte) 0x09,  // CHK1 (LEN1 - ETX)
    (byte) 0x9C}; // CHK2
  
  @Test(expected = TelegramException.class)
  public void testCreationOfInvalidReplyGetVersionTelegramWithInvalidId() throws TelegramException {
    ReplyGetVersion testTelegram = new ReplyGetVersion(invalidIDReply);
  }
  
  @Test(expected = TelegramException.class)
  public void testCreationOfInvalidReplyGetVersionTelegramWithInvalidLength() throws TelegramException {
    ReplyGetVersion testTelegram = new ReplyGetVersion(invalidLengthReply);
  }

  @Test
  public void testCreationOfDummyReplyGetVersionTelegram() throws TelegramException {
    ReplyGetVersion testTelegram = new ReplyGetVersion();
  }
  
  @Test
  public void testCreationOfValidReplyGetVersionTelegram() throws TelegramException {
    ReplyGetVersion testTelegram = new ReplyGetVersion(validReply);
    
    junit.framework.Assert.assertEquals("HardwareIdentifier is wrong!",
            testTelegram.getHardwareIdentifier(),
            4358436);
    
    junit.framework.Assert.assertEquals("SerialNumber is wrong!",
            testTelegram.getSerialNumber(),
            "207367836959012");

    junit.framework.Assert.assertEquals("FirmwareVersion is wrong!",
            testTelegram.getFirmwareVersion(),
            "2529702211.2547594579.2529702211");
  }
  
  @Test
  public void testAnswersRequest() throws TelegramException {
    assertEquals(ReplyGetVersion.answersRequest(), RequestGetVersion.class);
  }
}
