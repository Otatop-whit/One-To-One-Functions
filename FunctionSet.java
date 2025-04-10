import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;

public class FunctionSet {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Coordinate> given = new ArrayList<>();
		char choice = choices(input);
		while (choice != 'F') {
			switch (choice) {
			case 'A':
				addPoint(given, input);
				break;
			case 'V':
				viewPoints(given);
				break;
			case 'F':
				break;
			default:
				System.out.println("Wrong Input! Please enter the one of the following choices:");
			}
			choice = choices(input);
		}
		oneToOne(given);
	}

	// Lists of choices to manage the function values
	public static char choices(Scanner input) {
		System.out.println("A - Add a Coordinate Point");
		System.out.println("V - View Coordinate set");
		System.out.println("F - Finish the Function set");
		return input.nextLine().toUpperCase().charAt(0);
	}
	public static ArrayList<Coordinate> addPoint(ArrayList<Coordinate> given, Scanner input) {
		try {
			System.out.println("Enter a value for x");
			int x = Integer.parseInt(input.nextLine());
			System.out.println("Enter a value for y");
			int y = Integer.parseInt(input.nextLine());
			Coordinate coordinate = new Coordinate(x, y);
			String point = "(" + coordinate.getX() + "," + coordinate.getY() + ")";
			given.add(coordinate);
			if(nonFunction(given) == false) {
				System.out.println("Added Coordinate: " + point + " to the Function");
			}
			else {
				given.remove(given.size()-1);
				throw new ArithmeticException("New Coordinate Point: " + point + "conflicts the defintion of a function.");
			}
		} catch (Exception e) {
			System.err.println("An error occured! Possible reasonings:\n  1.The input was not an integer. \n  2.The input value for x was already entered in the function");
		}
		return given;
	}
	public static void viewPoints(ArrayList<Coordinate> given) {
		if(given.size() == 0) {
			System.out.println(given);
		}
		else {
			System.out.print("[");
			for(int i = 0; i < given.size(); i++) {
				if(i == given.size()-1) {
					System.out.print("(" + given.get(i).getX() + "," + given.get(i).getY() +")");
				}
				else {
					System.out.print("(" + given.get(i).getX() + "," + given.get(i).getY() +")" + ", ");
				}
			}
			System.out.println("]");
		}
	}
	public static void oneToOne(ArrayList<Coordinate> given) {
		ArrayList<Coordinate> valueCounter = new ArrayList<>();
		HashSet<Integer> coDomain = new HashSet<>();
		int lastDigit = 0;
		for(Coordinate y : given) {
			if (y.getY() >= 10) {
				String lastDigitString = "" + y.getY();
				lastDigitString = "" + lastDigitString.charAt(lastDigitString.length() - 1);
				lastDigit = Integer.parseInt(lastDigitString);
			}
			else {
				lastDigit = y.getY();
			}
			coDomain.add(lastDigit);
		}
		for(int n: coDomain) {
			Coordinate counter = new Coordinate(0,0);
			counter.setX(n);
			valueCounter.add(counter);
		}
		if(yDuplicates(given,valueCounter) == true || sameImage(given) == true) {
			System.out.println("The function f is not one to one");
		}
		else{
			System.out.println("The function f is one to one");
		}
		
	}
	// Condition Methods for One to One
	public static boolean yDuplicates(ArrayList<Coordinate> given, ArrayList<Coordinate> valueCounter) {
		for(int i = 0; i < given.size(); i++) {
			int scanner = given.get(i).getY();
			for(int j = 0; j < valueCounter.size(); j++) {
				if(scanner == valueCounter.get(j).getX()) {
					valueCounter.get(j).setY(valueCounter.get(j).getY() + 1);
					if(valueCounter.get(j).getY() >= 2) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public static boolean sameImage(ArrayList<Coordinate> given) {
		for(int i = 0; i < given.size(); i++) {
			if(given.get(i).getX() == given.get(i).getY()) {
				return true;
			}
		}
		return false;
	}
	public static boolean nonFunction(ArrayList<Coordinate> given) {
		ArrayList<Coordinate> valueCounter = new ArrayList<>();
		HashSet<Integer> domain = new HashSet<>();
		for(Coordinate x: given) {
			domain.add(x.getX());
		}
		for(int n : domain) {
			Coordinate storedValue = new Coordinate(n,0);
			valueCounter.add(storedValue);
		}
		
		for(int i = 0; i < given.size(); i++) {
			int scanner = given.get(i).getX();
			for(int j = 0; j < valueCounter.size(); j++) {
				if(scanner == valueCounter.get(j).getX()) {
					valueCounter.get(j).setY(valueCounter.get(j).getY() + 1);
					if(valueCounter.get(j).getY() >= 2) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
