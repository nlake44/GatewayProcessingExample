import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Provides an abstraction layer for different gateway processors.
 */

public abstract class PaymentGateway implements Runnable {
	// Have a unique identifier for each instance.
	protected UUID id;
        // The current state of the gateway.
        protected GatewayState state;
	// Message describing the current state.
	protected String message;
        // Required attributes upon initialization.
        protected HashMap<String, Attribute> attributeMap;
        // Attributes filled into parameters for payment authorization.
        protected HashMap<String, Object> parameters;

        // Constructor for new instances of a gateway.
        public PaymentGateway() {
		this.id = UUID.randomUUID();
		this.state = GatewayState.INIT;
		this.message = "Gateway initializing.";
        }

	/* Loads a gateway information from the DB for an ID. */
	abstract public void loadID(UUID id) throws GatewayIDNotFoundException;

	/* Get attributes required for the payment gateway. */
        abstract HashMap<String, Attribute> getAttributes();

        /* Gets all attributes and stores the instance information in the DB. */
	abstract void initialize(HashMap<String, Object> parameters) throws ParametersNotMetException;

        /* Cancel the current transaction if possible. */
        abstract void cancel();

	/* Check if the parameters given match the required attributes. 
         * Returns true of the parameters pass, false otherwise.
         */
	protected boolean checkParameters() {
		// compare all required attributes with given parameters.
                for (Map.Entry<String, Attribute> entry : this.attributeMap.entrySet()) {
                        String key = entry.getKey();
                        Attribute attr = entry.getValue();
                        Object value = this.parameters.get(key);
                        if (value == null && attr.isRequired()) {
                                return false;
                        }
                }
                return true;

	}
}
