/* A simple class to hold metadata about gateway attributes. */
public class Attribute {
	// True if this field required for processing.
	private boolean isRequired;
	// True if this field is a number.
	private boolean isNumber;
	// Maximum field length.
        private short fieldLength;
	// A short description explaining what this field is.
        private String description;

	public Attribute(boolean isRequired, boolean isNumber,
		short fieldLength, String description) {
		this.isRequired = isRequired;
		this.isNumber = isNumber;
		this.fieldLength = fieldLength;
		this.description = description;
	}

	/* The methods are simple get/set implementations. */
	
	public void setIsRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	
	public boolean getIsRequired() {
		return this.isRequired;
	}

	public void setIsNumber(boolean isNumber) {
		this.isNumber = isNumber;
	}

	public boolean getIsNumber() {
		return this.isNumber;
	}

	public void setFieldLength(short fieldLength) {
		this.fieldLength = fieldLength;
	}

	public short  getFieldLength() {
		return this.fieldLength;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}
}
