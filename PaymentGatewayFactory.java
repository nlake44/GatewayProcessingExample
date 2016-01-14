/* A factory class for creating new instances of payment gateways. */
public class PaymentGatewayFactory {
	public static PaymentGateway createPaymentGateway(GatewayType type) throws GatewayNotSupportedException {
		if (type == GatewayType.Amazon){
			return new AmazonGateway();
		}
		else if (type == GatewayType.CoinBase){
			return new CoinbaseGateway();
		}
		else if (type == GatewayType.PayPal){
			return new PaypalGateway();
		}
		throw new GatewayNotSupportedException();
	}
}
