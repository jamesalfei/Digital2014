/**
 * 
 */
package networkUtilities;


/**
 * Provides feedback to UI classes for background tasks such as network calls or
 * computations
 * 
 * @author James Alfei
 */
public interface NetworkCommsFeedback {
	public void onLoginComplete(boolean success, boolean honbuUser, String token);

	public void onLogoutComplete(boolean success);
}
