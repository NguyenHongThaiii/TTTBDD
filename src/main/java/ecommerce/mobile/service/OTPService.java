package ecommerce.mobile.service;

public interface OTPService {
	String generateAndStoreOtp(String email);

	String getOtpByEmail(String email);

	String generateAndStoreAnotherData(String email);

	String getOtpBySession(String email);

	void clearCache(String name,String key);
}
