package com.sun.springboot.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordService;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordService implements PasswordService {

	/**
	 *
	 * @param token
	 * @return
	 */
	public String getUnencryptPassword(UsernamePasswordToken token) {
		return token.getUsername() + new String(token.getPassword()) + "my_salt";
	}

	/**
	 * 
	 * @param token
	 * @return
	 * @throws IllegalArgumentException
	 */
	public String encryptPassword(UsernamePasswordToken token) throws IllegalArgumentException {
		return encryptPassword(getUnencryptPassword(token));
	}

	@Override
	public String encryptPassword(Object plaintextPassword) throws IllegalArgumentException {
		final String str;
		if (plaintextPassword instanceof char[]) {
			str = new String((char[]) plaintextPassword);
		} else if (plaintextPassword instanceof String) {
			str = (String) plaintextPassword;
		} else {
			throw new SecurityException("Unsupported password type: " + plaintextPassword.getClass().getName());
		}
		return BCrypt.hashpw(str, BCrypt.gensalt());
	}

	@Override
	public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
		String submittedString = null;
		if (submittedPlaintext instanceof char[]) {
			submittedString = new String((char[]) submittedPlaintext);
		} else if (submittedPlaintext instanceof String) {
			submittedString = (String) submittedPlaintext;
		}
		return BCrypt.checkpw(submittedString, encrypted);
	}

	public static void main(String[] args) {
		BCryptPasswordService s = new BCryptPasswordService();
		String s1 = s.encryptPassword(s.getUnencryptPassword(new UsernamePasswordToken("1", "2")));
		System.out.println(s1);

		boolean b = s.passwordsMatch(s.getUnencryptPassword(new UsernamePasswordToken("1", "2")), s1);
		System.out.println(b);
	}

}