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
public class CommandSetStatusTest {
  private static byte[] commandSetStatusTestTelegram = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x05,  // LEN2
    (byte) 0x17,  // TEL
    (byte) 0x00,  // FKN
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x1F}; // CHK2
  
  private static byte[] commandSetStatusTestTelegramWithInvalidId = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x05,  // LEN2
    (byte) 0x16,  // TEL
    (byte) 0x00,  // FKN
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x1E}; // CHK2
    
  private static byte[] commandSetStatusTestTelegramWithInvalidSize = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x05,  // LEN2
    (byte) 0x17,  // TEL
    (byte) 0x09,  // FKN
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x28}; // CHK2
 
  private static byte[] commandSetStatusTestTelegramWithInvalidSize2 = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x05,  // LEN2
    (byte) 0x17,  // TEL
    (byte) 0x05,  // FKN
    (byte) 0x05,  // MOD
    (byte) 0x10,  // CDN
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x39}; // CHK2
  
  private static byte[] commandSetStatusTestTelegramMotorOn = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x05,  // LEN2
    (byte) 0x17,  // TEL
    (byte) 0x01,  // FKN
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x20}; // CHK2
  
  private static byte[] commandSetStatusTestTelegramMotorOff = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x05,  // LEN2
    (byte) 0x17,  // TEL
    (byte) 0x02,  // FKN
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x21}; // CHK2
  
  private static byte[] commandSetStatusTestTelegramVibratorOn = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x05,  // LEN2
    (byte) 0x17,  // TEL
    (byte) 0x05,  // FKN
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x24}; // CHK2

  private static byte[] commandSetStatusTestTelegramVibratorOff = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x05,  // LEN2
    (byte) 0x17,  // TEL
    (byte) 0x06,  // FKN
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x25}; // CHK2
  
  private static byte[] commandSetStatusTestTelegramMode5 = {
    (byte) 0x02,  // STX
    (byte) 0x00,  // LEN1 (DAT1 - CHK2)
    (byte) 0x07,  // LEN2
    (byte) 0x17,  // TEL
    (byte) 0x09,  // FKN
    (byte) 0x05,  // MOD
    (byte) 0x10,  // CDN
    (byte) 0x03,  // ETX
    (byte) 0x00,  // CHK1 (LEN1 - ETX)
    (byte) 0x3F}; // CHK2
  
  @Test(expected = TelegramException.class)
  public void testCreationOfTelegramWithInvalidId() throws TelegramException {
    CommandSetStatus testTelegram = new CommandSetStatus(commandSetStatusTestTelegramWithInvalidId);
  }

  @Test(expected = TelegramException.class)
  public void testCreationOfTelegramWithInvalidSize() throws TelegramException {
    CommandSetStatus testTelegram = new CommandSetStatus(commandSetStatusTestTelegramWithInvalidSize);
  }
  
  @Test(expected = TelegramException.class)
  public void testCreationOfTelegramWithInvalidSize2() throws TelegramException {
    CommandSetStatus testTelegram = new CommandSetStatus(commandSetStatusTestTelegramWithInvalidSize2);
  }
  
  @Test
  public void testCreationOfBareTelegram() throws TelegramException {
    CommandSetStatus testTelegram = new CommandSetStatus();
    assertTrue("Erstellung Bare Motor Off: " + testTelegram.toString(),
            Arrays.equals(testTelegram.toByteArray(), commandSetStatusTestTelegramMotorOff));
    testTelegram.setMotor(true);
    assertTrue("Schalte Motor ein: " + testTelegram.toString(),
            Arrays.equals(testTelegram.toByteArray(), commandSetStatusTestTelegramMotorOn));
    testTelegram.setMotor(false);
    assertTrue("Schalte Motor aus: " + testTelegram.toString(),
            Arrays.equals(testTelegram.toByteArray(), commandSetStatusTestTelegramMotorOff));
    testTelegram.setVibrator(true);
    assertTrue("Schalte vibrator ein: " + testTelegram.toString(),
            Arrays.equals(testTelegram.toByteArray(), commandSetStatusTestTelegramVibratorOn));
    testTelegram.setVibrator(false);
    assertTrue("Schalte Vibrator aus: " + testTelegram.toString(),
            Arrays.equals(testTelegram.toByteArray(), commandSetStatusTestTelegramVibratorOff));
    testTelegram.selectMode(5);
    assertTrue("Setze Modus 5: " + testTelegram.toString(),
            Arrays.equals(testTelegram.toByteArray(), commandSetStatusTestTelegramMode5));
  }
  
  @Test
  public void testCreationOfByteStreamTelegram() throws TelegramException {
    CommandSetStatus testTelegram = new CommandSetStatus(commandSetStatusTestTelegramMotorOff);
    assertTrue(Arrays.equals(testTelegram.toByteArray(), commandSetStatusTestTelegramMotorOff));
  }

  @Test
  public void testWaitFor() throws TelegramException {
    assertEquals(CommandSetStatus.waitsForReply(), null);
  }
}
