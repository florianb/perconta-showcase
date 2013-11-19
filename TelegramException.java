/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public class TelegramException extends Exception{
  TelegramException() {
  }
  
  TelegramException(String message) {
    super(message);
  }
  
  TelegramException(String message, Throwable cause) {
    super(message, cause);
  }
}
