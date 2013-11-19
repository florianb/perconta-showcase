/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

import java.util.HashMap;

/**
 *
 * @author florianbreisch
 */
public class TelegramHelper {
  /**
   * Wandelt die ersten acht Bit einer Long-Zahl in ein Byte
   * 
   * @param givenNumber Umzuwandelnde Long-Zahl
   * @return Erstes Byte der Long Zahl
   */
  public static byte getByteOfLong(long givenNumber) {
    return getByteOfLong(0, givenNumber);
  }
  
  /**
   * Extrahiert aus einer Long-Zahl ein beliebiges Byte
   * 
   * @param byteOffset Offset der Byte-Zahl (0-7), ist das Offset ungültig
   * wird das erste Byte extrahiert.
   * @param givenNumber Long-Zahl aus der das Byte extrahiert werden soll.
   * @return 
   */
  public static byte getByteOfLong(int byteOffset, long givenNumber) {
    switch (byteOffset) {
      case 7:
        return (byte) ((givenNumber >> 56) & 0xFF);
      case 6:
        return (byte) ((givenNumber >> 48) & 0xFF);
      case 5:
        return (byte) ((givenNumber >> 40) & 0xFF);
      case 4:
        return (byte) ((givenNumber >> 32) & 0xFF);
      case 3:
        return (byte) ((givenNumber >> 24) & 0xFF);
      case 2:
        return (byte) ((givenNumber >> 16) & 0xFF);
      case 1:
        return (byte) ((givenNumber >> 8) & 0xFF);
      case 0:
        return (byte) (givenNumber & 0xFF);
      default:
        return (byte) 0x00;
    }
  }
  
  
  /**
   * 
   * @param givenArray
   * @param byteOffset
   * @param length
   * @param givenNumber
   * @return
   */
  public static byte[] setByteByLong(byte[] givenArray, int byteOffset, int length, long givenNumber) {
    length = Math.min(length, 8);
    for (int i = 0; i < length; i++) {
      givenArray[i + byteOffset] = getByteOfLong(length - i - 1, givenNumber);
    }
    return givenArray;
  }
  
  /**
   * Wandelt die ersten 8 Byte eines Byte-Arrays in eine Long-Zahl
   * 
   * @param byteArray Umzuwandelndes Byte-Array
   * @return Extrahierte Long-Zahl
   */
  public static long getLongOfBytes(byte[] byteArray) {
    return getLongOfBytes(byteArray, 0, byteArray.length);
  }
  
  /**
   * Wandelt eine beliebige Anzahl an Bytes von einem beliebigen Offset eines
   * Byte-Arrays in eine Long-Zahl
   * 
   * @param byteArray Byte-Array aus dem die Long-Zahl extrahiert wird.
   * @param offset Byte-Offset bei dem die Umwandlung beginnen soll.
   * @param length Anzahl an Bytes, die extrahiert werden sollen.
   * @return Extrahierte Long-Zahl
   */
  public static long getLongOfBytes(byte[] byteArray, int offset, int length) {
    long result = 0;
    
    for (int i = 0; i < length; i++) {
      result += ((long) (byteArray[i + offset] & 0xFF)) << ((length - i - 1) * 8);
    }
    return result;
  }
  
  /**
   * 
   * @param telegram
   * @return
   */
  public static String byteArrayToPrettyString(byte[] telegram) {
    int blocksPerLine = 4;
    int separateAfterBlocks = 4;
    
    String output = "";
    
    for (int i = 0; i < telegram.length; i++) {
      if (i % blocksPerLine == 0) {
        if (!output.isEmpty())
          output += "\n";
        output += String.format("%03d..", i);
      } else if (i % separateAfterBlocks == 0) {
        output += "..";
      }
      output += String.format(".%02X", telegram[i]);
    }
    
    return output;
  }
  
  
  public static Object extractNextObject(byte[] givenStream) throws Exception {
    Object returnObject;
    
    if (givenStream.length < 1)
      return null;
    
    switch (givenStream[0]) {
      case Telegram.T_ACK:
        returnObject = Telegram.T_ACK;
        break;
        
      case Telegram.T_NAK:
        returnObject = Telegram.T_NAK;
        break;
      
      case Telegram.T_STX:
        if (givenStream.length >= 7) {
          int size = (int) TelegramHelper.getLongOfBytes(givenStream, 1, 2);
          if (givenStream.length >= size + 3 &&
              givenStream[size] == Telegram.T_ETX) {
            returnObject = telegramFactory(givenStream);
            break;
          }
        }
      default:
        returnObject = null;
    }
    return returnObject;
  }
  
  public static Telegram telegramFactory(byte[] telegramStream) throws Exception {
    HashMap <Integer, Class> availableTelegrams = new HashMap();
    availableTelegrams.put(CommandClearLevelPieces.requiredId, CommandClearLevelPieces.class);
    availableTelegrams.put(CommandOffline.requiredId, CommandOffline.class);
    availableTelegrams.put(CommandSetStatus.requiredId, CommandSetStatus.class);
    availableTelegrams.put(ReplyConfirm.requiredId, ReplyConfirm.class);
    availableTelegrams.put(ReplyGetVersion.requiredId, ReplyGetVersion.class);
    availableTelegrams.put(ReplyOnline.requiredId, ReplyOnline.class);
    availableTelegrams.put(RequestGetVersion.requiredId, RequestGetVersion.class);
    availableTelegrams.put(RequestOnline.requiredId, RequestOnline.class);
    availableTelegrams.put(RequestSetBatch.requiredId, RequestSetBatch.class);
    
    int lastIndex = telegramStream.length - 1;
    
    if (! (lastIndex >= 7 &&
          telegramStream[0] == Telegram.T_STX &&
          telegramStream[lastIndex - 2] == Telegram.T_ETX) &&
          (int) getLongOfBytes(telegramStream, 1, 2) == lastIndex - 2) {
      
      throw new InvalidTelegramException("One or more formal criterias weren't met by the bytestream.");
    }
    
    int givenId = (int) getLongOfBytes(telegramStream, 3, 1);
    
    if (!availableTelegrams.containsKey(givenId)) {
      throw new UnknownTelegramException("There is no Telegram with ID " +
              givenId + " registered.");
    }
    
    return (Telegram) availableTelegrams.get(givenId).getConstructor(byte[].class).newInstance(telegramStream);
  }
}
