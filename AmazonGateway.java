import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * An implmentation of a payment gateway for Amazon.
 */

public class AmazonGateway extends PaymentGateway implements Runnable {
	public AmazonGateway() {
		super();
		Attribute attr1 = new Attribute(true, false, (short) 50, 
			"Email account");
		Attribute attr2 = new Attribute(false, true, (short) 3, 
			"Age of person");
	        this.attributeMap = new HashMap<String, Attribute>();
		this.attributeMap.put("email", attr1);
		this.attributeMap.put("age", attr2);

		// Do Amazon API specific initialization here.
		// ...
	}

	/* Tries and loads a UUID from the database. */
	@Override
	public void loadID(UUID id) throws GatewayIDNotFoundException {
		// Try and load the ID from the database.

		// DO STUFF
		// ...

		// If it is not found throw an exception.
		throw new GatewayIDNotFoundException();
	}

	/* Get required and optional attributes that Amazon requires. */
	@Override
	public HashMap<String, Attribute> getAttributes() {
		return this.attributeMap;
	}

	/* 
	 * Initialize the payment gateway with attribute parameters.
	 * Throws an exception if all required fields are not met.
	 */
	@Override
	public void initialize(HashMap<String, Object> parameters) throws ParametersNotMetException {
		this.parameters = parameters;
		this.state = GatewayState.READY;

		// Check to see that all required parameters are met.
		if (checkParameters()) {
			throw new ParametersNotMetException();
		}

		// Store this instance information in the DB for persistance.
		// ...

		return;
	}

	/* Cancel the current payment process. */
	@Override
	public void cancel() {
		this.state = GatewayState.CANCELLED;
	}

	/* Asynchronous process which updates the state of the gateway. */
	public void run() {
		boolean isSuccess = false;
		if (this.state != GatewayState.READY) {
			this.state = GatewayState.FAILED;
			this.message = "Gateway was never initialized.";
			return;
		}

		this.state = GatewayState.RUNNING;

		isSuccess = runTransaction();
	 
		if (isSuccess) {
			this.state = GatewayState.DONE;
			this.message = "Payment was a success";
		}
		else {
			this.state = GatewayState.FAILED;
			this.message = "Payment failed due to...";
		}
	}

	/* Do tranasctional work here calling out to Amazon services. */
	private boolean runTransaction() {
		return true;	
	}
}
