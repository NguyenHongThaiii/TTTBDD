package ecommerce.mobile.constant;

public enum SortField {
	NAME("name"), NAMEDESC("nameDesc"), ID("id"), IDDESC("idDesc"), CREATEDAT("createdAt"),
	CREATEDATDESC("createdAtDesc"), UPDATEDAT("updatedAt"), UPDATEDATDESC("updatedAtDesc");

	private final String value;

	SortField(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
