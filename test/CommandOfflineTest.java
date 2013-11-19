/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

import java.util.Arrays;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author florianbreisch
 */
public class CommandOfflineTest {
  private static byte[] commandOfflineTestTelegram = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x04,  // LEN2
    (byte) 0x1F,  // TEL
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x26}; // CHK2
  
  private static byte[] commandOfflineTestTelegramWithInvalidId = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x04,  // LEN2
    (byte) 0x1E,  // TEL
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x25}; // CHK2
    
  private static byte[] commandOfflineTestTelegramWithInvalidSize = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x05,  // LEN2
    (byte) 0x1F,  // TEL
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x27}; // CHK2    
  
  @Test(expected = TelegramException.class)
  public void testCreationOfTelegramWithInvalidId() throws TelegramException {
    CommandOffline testTelegram = new CommandOffline(commandOfflineTestTelegramWithInvalidId);
  }

  @Test(expected = TelegramException.class)
  public void testCreationOfTelegramWithInvalidSize() throws TelegramException {
    CommandOffline testTelegram = new CommandOffline(commandOfflineTestTelegramWithInvalidSize);
  }
  
  @Test
  public void testCreationOfBareTelegram() throws TelegramException {
    CommandOffline testTelegram = new CommandOffline();
    assertTrue(Arrays.equals(testTelegram.toByteArray(), commandOfflineTestTelegram));
  }
  
  @Test
  public void testCreationOfByteStreamTelegram() throws TelegramException {
    CommandOffline testTelegram = new CommandOffline(commandOfflineTestTelegram);
  }

  @Test
  public void testWaitFor() throws TelegramException {
    assertEquals(CommandOffline.waitsForReply(), null);
  }
}
