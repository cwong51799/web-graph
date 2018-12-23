
public class ExtraExceptions extends Exception{
	public class IllegalArgumentException extends Exception{
		IllegalArgumentException(){
			System.out.println("null car");
		}
	}
	public class InputMismatchException extends Exception{
		InputMismatchException(){
			
		}
	}
}
