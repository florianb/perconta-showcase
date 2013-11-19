/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

import java.util.Arrays;

/*
 * Telegram
 * 
 * Superklasse zur Erstellung eines Telegrammes. Es werden zwei
 * Möglichkeiten der Erstellung angeboten, die Instanziierung mittels
 * der TelegramId und der Anzahl der Datenbytes.
 * Bei der Erstellung wird dann das Daten-Byte-Array mit der gewünschten Länge
 * angelegt und das erste Byte mit der Telegram-ID initialisiert.
 * 
 * Alternativ kann das Telegramm mittels einem Byte-Array angelegt werden,
 * dazu muss das komplette Telegramm als Byte-Array übergeben werden. Dann
 * werden die Daten-Bytes aus dem Quell-Array extrahiert und genutzt um ein
 * "neues" Telegramm zu erstellen. Das neuerstellte Telegramm wird zur
 * Verifikation mit dem Ursprungsstream abgeglichen, falls die Byte-Folgen
 * gleich sind, wird das neue Telegramm zurückgegeben.
 * 
 * Die Klasse bietet zudem eine Funktion um die Telegramm-ID auszulesen und das
 * Telegramm als String auszugeben.
 * 
 * @author florianbreisch
 */
public class Telegram implements TelegramInterface{

  public Telegram(byte[] byteArray) throws TelegramException {
    data = new byte[byteArray.length - 6];
    for (int i = 6; i < byteArray.length; i++) {
      data[i - 6] = byteArray[i - 3];
    }
    
    validate();
    
    if (!Arrays.equals(toByteArray(), byteArray))
      throw new TelegramException("Invalid Telegram:\n" +
              TelegramHelper.byteArrayToPrettyString(byteArray) + "\nRecreation:\n" +
              TelegramHelper.byteArrayToPrettyString(toByteArray()));
    
    refreshMemberVariables();
  }
  
  public Telegram(int telegramId, int countOfDataBytes) throws TelegramException {
    if (countOfDataBytes < 1)
      throw new TelegramException("Size of datagram is to small (< 1).");
    
    data = new byte[countOfDataBytes];
    
    
    data[0] = TelegramHelper.getByteOfLong((long) telegramId);
    
    validate();
  }
      
  @Override
  public boolean requiresAck() {
    return (getTelegramId() < (int) 0x80);
  }
  
  @Override
  public int getTelegramId() {
    return (int) (data[0] & 0xFF);
  }
  
  @Override
  public final byte[] toByteArray(){
    int checksum = 0;
    int dataLength = data.length;
    int etxIndex = dataLength + 3;
    int telegramLength = dataLength + 3;
    
    byte[] byteArray = new byte[dataLength + 6];
    
    byteArray[0] = T_STX;
    byteArray[1] = (byte) (telegramLength >> 8); // Länge setzen (Hi & Lo)
    byteArray[2] = (byte) telegramLength;
    
    for (int i = 3; i < etxIndex; i++) { // einkopieren der Daten
      byteArray[i] = data[i - 3];
    }
    
    byteArray[etxIndex] = T_ETX;
    
    for (int i = 1; i <= etxIndex; i++) { // Checksumme über Len-Hi bis ETX (inkl.), durch Binary-Add berechnen
      checksum += (int) (byteArray[i] & 0xFF); // Implizite Nichtinterpretation des Vorzeichens!
    }
    
    byteArray[etxIndex + 1] = (byte) Integer.rotateRight(checksum, 8); // Checksumme anfügen
    byteArray[etxIndex + 2] = (byte) checksum;
    
    return byteArray;
  }
  
  @Override
  public String toString() {
    return toString(false);
  }
  
  @Override
  public String toString(boolean verbose) {
    return TelegramHelper.byteArrayToPrettyString(toByteArray());
  }
  
  
  
  //////////////////////////////////////////////////////////////////////////////
  
  protected byte[] data = new byte[0];
  
  protected void refreshMemberVariables() { 
  }

  protected boolean hasValidId() {
    try {
      int givenRequiredId = this.getClass().getDeclaredField("requiredId").getInt(this);
      return getTelegramId() == givenRequiredId;
    } catch (Exception e) {}
    return true;
  }
  
  protected boolean hasValidSize() {
    try {
      int givenRequiredSize = this.getClass().getField("requiredSize").getInt(this);
      return data.length == givenRequiredSize;
    } catch (Exception e) {}
    return data.length >= 1;
  }
  
  //////////////////////////////////////////////////////////////////////////////
  
  private void validate() throws TelegramException {
    if (!hasValidId()) {
      throw new TelegramException("Invalid Telegram-ID " +
              getTelegramId() + ":\n" +
              TelegramHelper.byteArrayToPrettyString(toByteArray()));
    }
    if (!hasValidSize()) {
      throw new TelegramException("Invalid Telegram-Size:\n" +
              TelegramHelper.byteArrayToPrettyString(toByteArray()));
    }
  }
}
