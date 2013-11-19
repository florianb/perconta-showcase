/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public class UnknownTelegramException extends TelegramException {
  UnknownTelegramException() {
  }
  
  UnknownTelegramException(String message) {
    super(message);
  }
  
  UnknownTelegramException(String message, Throwable cause) {
    super(message, cause);
  }
}
