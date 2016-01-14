import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * An implmentation of a payment gateway for Amazon.
 */

public class AmazonGateway extends PaymentGateway implements Runnable {
	private static class AmazonAttributes {
		final static Attribute email = new Attribute(true, false, 
			(short) 50, "Email account"); 
		final static Attribute age = new Attribute(false, true, 
			(short) 3, "Age of person");
		/* and so on ... */
	}

	public AmazonGateway() {
		super();
	        this.attributeMap = new HashMap<String, Attribute>();
		this.attributeMap.put("email", AmazonAttributes.email);
		this.attributeMap.put("age", AmazonAttributes.age);

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
		updateDB();

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

		// Update running status of payment processing in the DB.
		updateDB();

		isSuccess = runTransaction();
	 
		if (isSuccess) {
			this.state = GatewayState.DONE;
			this.message = "Payment was a success";
		}
		else {
			this.state = GatewayState.FAILED;
		}

		// Update failed/done status of payment processing in the DB.
		updateDB();
	}

	/* 
	 * Do transactional work here calling out to Amazon services. 
	 * The billing callouts are done here in a thread and will return 
	 * true on success, and false otherwise. The message class variable
	 * is set if there was a failure.
	 */
	private boolean runTransaction() {
		try {
			// All Amazon transactional work goes here.
			// ...
		}
		// Catch all subclasses of Exception first such as any
		// which are Amazon related.
		// ...
		// Then capture the high level exception.
		catch (Exception e) {
			// Log this exception and if this message is passed 
			// to the user, make sure it is easily readable.
			// Logger.warning(e.getMessage());
			this.message = e.getMessage();
			return false;
		}
		return true;	
	}

	/*
	 * We want to store the current payment status in the DB for three
	 * reasons:
	 * 1. To have a persistant record of the transaction.
	 * 2. To be able to recover if hardware or software failure happens.
	 * 3. Different application servers need to be able to access the 
	 * 	current status of a transaction.
	 */
	private void updateDB(){
		// Store the current GatewayStatus and parameters in the DB.
		//...
	}
}
