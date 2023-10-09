package ecommerce.mobile.constant;

public enum RoleField {

	ROLE_ADMIN("ROLE_ADMIN"), ROLE_MOD("ROLE_MOD"), ROLE_USER("ROLE_USER"), ROLE_GUEST("ROLE_GUEST");

	private final String value;

	RoleField(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
