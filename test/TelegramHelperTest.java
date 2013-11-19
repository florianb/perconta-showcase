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
public class TelegramHelperTest {           //1 2 3 4 5 6 7 8
  private static long testLongSigned =      0xFFFFFFFFFFFFFFFFL;
  private static long testLongSignedDeced = 0x7FFFFFFFFFFFFFFFL;
  private static long testLongRightEnd =    0x0000000000000001L;
  private static long testLongEnd1 =        0x8000000000000001L;  
                                            
  private static byte[] testByteArraySigned = {
    (byte) 0xFF,
    (byte) 0xFF,
    (byte) 0xFF,
    (byte) 0xFF,
    (byte) 0xFF,
    (byte) 0xFF,
    (byte) 0xFF,
    (byte) 0xFF};
  
  private static byte[] testByteArrayMaxDeced = {
    (byte) 0x7F,
    (byte) 0xFF,
    (byte) 0xFF,
    (byte) 0xFF,
    (byte) 0xFF,
    (byte) 0xFF,
    (byte) 0xFF,
    (byte) 0xFF};
  
  private static byte[] testByteArrayRightEnd = {
    (byte) 0x00,
    (byte) 0x00,
    (byte) 0x00,
    (byte) 0x00,
    (byte) 0x00,
    (byte) 0x00,
    (byte) 0x00,
    (byte) 0x01};
    
  private static byte[] testByteArrayEnd1 = {
    (byte) 0x80,
    (byte) 0x00,
    (byte) 0x00,
    (byte) 0x00,
    (byte) 0x00,
    (byte) 0x00,
    (byte) 0x00,
    (byte) 0x01};
  
    private static byte[] testSetBytes = {
    (byte) 0x80,
    (byte) 0x00,
    (byte) 0x00,
    (byte) 0x88,
    (byte) 0xCC,
    (byte) 0xAA,
    (byte) 0x00,
    (byte) 0x01};
  
  @Test
  public void testGetByteOfLong_long() {
    assertEquals(TelegramHelper.getByteOfLong(testLongSigned), (byte) 0xFF);
    assertEquals(TelegramHelper.getByteOfLong(testLongRightEnd), (byte) 0x01);
  }

  @Test
  public void testGetByteOfLong_int_long() {
    assertEquals(TelegramHelper.getByteOfLong(0, testLongEnd1), (byte) 0x01);
    assertEquals(TelegramHelper.getByteOfLong(7, testLongEnd1), (byte) 0x80);
    assertEquals(TelegramHelper.getByteOfLong(8, testLongSigned), (byte) 0x00);
    assertEquals(TelegramHelper.getByteOfLong(testLongEnd1), (byte) 0x01);
  }

  @Test
  public void testGetLongOfBytes_byteArr() {
    assertEquals(TelegramHelper.getLongOfBytes(testByteArrayMaxDeced), testLongSignedDeced);
    assertEquals(TelegramHelper.getLongOfBytes(testByteArraySigned), testLongSigned);
    assertEquals(TelegramHelper.getLongOfBytes(testByteArrayRightEnd), testLongRightEnd);
    assertEquals(TelegramHelper.getLongOfBytes(testByteArrayEnd1), testLongEnd1);
    assertEquals(TelegramHelper.getLongOfBytes(testByteArrayRightEnd), (long) 1);
    assertEquals(TelegramHelper.getLongOfBytes(testByteArraySigned), (long) -1);    
  }

  @Test
  public void testGetLongOfBytes_3args() {
    assertEquals(TelegramHelper.getLongOfBytes(testByteArrayMaxDeced, 0, 2), (long) 32767);
    assertEquals(TelegramHelper.getLongOfBytes(testByteArrayEnd1, 1, 7), (long) 1);   
  }
  
  @Test
  public void testSetByteArray() {
    //System.out.println(TelegramHelper.byteArrayToPrettyString(testSetBytes));
    //System.out.println(TelegramHelper.byteArrayToPrettyString(TelegramHelper.setByteByLong(testByteArrayEnd1, 3, 3, 8965290L)));
    assertTrue(Arrays.equals(TelegramHelper.setByteByLong(testByteArrayEnd1, 3, 3, 8965290L), testSetBytes));
    assertFalse(Arrays.equals(TelegramHelper.setByteByLong(testByteArrayEnd1, 3, 5, 8965290L), testSetBytes));
  }
}
