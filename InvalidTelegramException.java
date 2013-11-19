/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moneycount.perconta.telegram;

/**
 *
 * @author florianbreisch
 */
public class InvalidTelegramException extends TelegramException {
  InvalidTelegramException() {
  }
  
  InvalidTelegramException(String message) {
    super(message);
  }
  
  InvalidTelegramException(String message, Throwable cause) {
    super(message, cause);
  }
}
