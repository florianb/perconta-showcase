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
public class CommandClearLevelPiecesTest {
  private static byte[] CommandClearLevelPiecesTestTelegram = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x05,  // LEN2
    (byte) 0x14,  // TEL
    (byte) 0x00,  // MOD
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x1C}; // CHK2
  
  private static byte[] CommandClearLevelPiecesTestTelegramWithInvalidId = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x05,  // LEN2
    (byte) 0x15,  // TEL
    (byte) 0x00,  // MOD
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x1D}; // CHK2
    
  private static byte[] CommandClearLevelPiecesTestTelegramWithInvalidSize = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x06,  // LEN2
    (byte) 0x14,  // TEL
    (byte) 0x00,  // MOD
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x1D}; // CHK2 
  
  private static byte[] CommandClearLevelPiecesTestTelegramForMode5 = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x05,  // LEN2
    (byte) 0x14,  // TEL
    (byte) 0x05,  // MOD
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x21}; // CHK2 
  
  @Test(expected = TelegramException.class)
  public void testCreationOfTelegramWithInvalidId() throws TelegramException {
    CommandClearLevelPieces testTelegram = new CommandClearLevelPieces(CommandClearLevelPiecesTestTelegramWithInvalidId);
  }

  @Test(expected = TelegramException.class)
  public void testCreationOfTelegramWithInvalidSize() throws TelegramException {
    CommandClearLevelPieces testTelegram = new CommandClearLevelPieces(CommandClearLevelPiecesTestTelegramWithInvalidSize);
  }
  
  @Test
  public void testCreationOfTelegrams() throws TelegramException {
    CommandClearLevelPieces testTelegram = new CommandClearLevelPieces();
    assertTrue(Arrays.equals(testTelegram.toByteArray(), CommandClearLevelPiecesTestTelegram));
    testTelegram.setMode(5);
    assertTrue("Telegramm mit Nutzdaten entspricht nicht der Vorgabe: " + testTelegram.toString(),
            Arrays.equals(testTelegram.toByteArray(), CommandClearLevelPiecesTestTelegramForMode5));
  }
  
  @Test
  public void testCreationOfByteStreamTelegram() throws TelegramException {
    CommandClearLevelPieces testTelegram = new CommandClearLevelPieces(CommandClearLevelPiecesTestTelegram);
  }

  @Test
  public void testWaitFor() throws TelegramException {
    assertEquals(CommandClearLevelPieces.waitsForReply(), null);
  }
}
