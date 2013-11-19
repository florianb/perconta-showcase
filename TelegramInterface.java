/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public interface TelegramInterface {
  
  /**
   * Get the value of telegramId
   *
   * @return the value of telegramId
   */
  public int getTelegramId();
  
  /**
   * Rückgabe der Byte-Repräsentation des Telegramms
   * 
   * @return 
   */
  public byte[] toByteArray();
  /**
   * Get the String-representation of this telegram
   * 
   * @return the String representation of the telegram
   */
  @Override
  public String toString();
  public String toString(boolean verbose);
  
  public boolean requiresAck();
  
  public static final byte T_ACK = 0x06;
  public static final byte T_NAK = 0x15;
  public static final byte T_STX = 0x02;
  public static final byte T_ETX = 0x03;
  
  public static final int M_CHECK   = 0;
  public static final int M_FRAUD   = 1;
  public static final int M_PRESET  = 2;
  public static final int M_BATCH   = 3;
  public static final int M_MAIN    = 4;
  public static final int M_TOTAL   = 5;
}
