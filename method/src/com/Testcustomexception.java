package com;
class InvalideageException extends Exception {
InvalideageException(String s)
{
	super();
}
}

public class Testcustomexception {

	static void validate(int age) throws InvalideageException {

		if (age < 18)
			throw new InvalideageException("not valid");
		else
			System.out.println("welcome to vote");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			validate(13);
		} catch (Exception x) {
			System.out.println("Exception occured:" + x);
		}
		System.out.println("rest the code:");
	}

}
