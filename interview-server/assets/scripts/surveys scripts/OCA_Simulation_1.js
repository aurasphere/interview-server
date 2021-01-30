// Oracle Certificate Associate Java 8 Programmer I (1Z0-808) Simulation. 
// 70 questions in 150 minutes.

db.survey.insertOne({
	"_id":"OCA Simulation 1",
	"_class":"co.aurasphere.interview.server.model.Survey",
	"questions":[
		// 1
		{ 
			"questionText":"Given the following: \u003cpre class=\"prettyprint linenums lang-java\"\u003epublic class Java8_I {\n\tpublic static void main(String[] args) {\n\t\tdouble[][][] matrix = new double[5][5][5];\n\t\tfor (int x = 0; x < matrix.length; x++) {\n\t\t\tfor (int y = 0; y < matrix[x].length; y++) {\n\t\t\t\tfor (int z = 0; z < matrix[x][y].length; z++) {\n\t\t\t\t\t// insert code here\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t}\n}\u003c/pre\u003e Which line of code, when inserted independently at line 7, will set each element in the matrix array to the product of its dimension indices?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003ematrix{x y z} = x * y * z;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003ematrix{x,y,z} = x * y * z;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003ematrix[x][y][z] = x * y * z;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003ematrix[[x][y][z]] = x * y * z;\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[2]
		},
		// 2
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epackage games.card;\n\npublic class Poker {\n\tpublic void call() {System.out.println(\"Call!\");}\n\tpublic void raise(double amt) {System.out.println(\"Raise by \" + amt);}\n\tpublic void fold() {System.out.println(\"Fold!\");}\n}\u003c/pre\u003e and \u003cpre class=\"prettyprint lang-java\"\u003epackage java8.app;\n\n//Insert code here\n\npublic class GameApp {\n\t public static void main(String[] args) {\n\t\t Poker pokerGame = new Poker();\n\t\t if (args.length > 0) {\n\t\t\t if (args[0].equalsIgnoreCase(\"raise\")) {\n\t\t\t\t pokerGame.raise(Double.parseDouble(args[1]));\n\t\t\t } else pokerGame.call();\n\t\t } else pokerGame.fold();\n\t }\n}\u003c/pre\u003e Which two import statements, when inserted independently in the second source file, would enable the code to compile and run?",
			"answers":
				[
					"\u003cpre class=\"prettyprint lang-java\"\u003eimport java8;\u003c/pre\u003e",
					"\u003cpre class=\"prettyprint lang-java\"\u003eimport java8.*;\u003c/pre\u003e", "\u003cpre class=\"prettyprint lang-java\"\u003eimport java8.app.*;\u003c/pre\u003e",
					"\u003cpre class=\"prettyprint lang-java\"\u003eimport java8.app.GameApp;\u003c/pre\u003e",
					"\u003cpre class=\"prettyprint lang-java\"\u003eimport games;\u003c/pre\u003e",
					"\u003cpre class=\"prettyprint lang-java\"\u003eimport games.*;\u003c/pre\u003e",
					"\u003cpre class=\"prettyprint lang-java\"\u003eimport games.card.*;\u003c/pre\u003e",
					"\u003cpre class=\"prettyprint lang-java\"\u003eimport games.card.Poker;\u003c/pre\u003e"
				],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[6,7]
		},
		// 3
		{
			"questionText":"Which three statements will cause a runtime or compiler error?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003eLocalDate.of(2015, 5, 31);\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eLocalDate.of(2015, Month.MAY, 50);\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eLocalDate date = new LocalDate(2015,5,31);\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eLocalDate date = new LocalDate();\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eLocalDate.now();\u003c/pre\u003e"
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[1,2,3]
		},
		// 4
		{
			"questionText":"Given: \u003cpre class=\"prettyprint lang-java\"\u003epublic class Pedometer {\n\t private String units;\n\t private double stride;\n\t public Pedometer(String units) {\n\t\t this.units = units;\n\t }\n}\u003c/pre\u003e Which code fragment correctly overloads the constructor?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic Pedometer (double stride) {\n\t this(\"inches\");\n\t this.stride = 25;\n}\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic Pedometer (String units, double stride) {\n\t super(\"inches\");\n\t super.stride = 25;\n}\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic static Pedometer init() {\n\t Pedometer ped = new Pedometer(\"inches\");\n\t ped.stride = 25;\n\t return ped;\n}\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic static Pedometer init(String units, double stride) {\n\t Pedometer ped = new Pedometer(units);\n\t ped.stride = stride;\n\t return ped;\n}\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 5
		{
			"questionText":"Given: \u003cpre class=\"prettyprint lang-java\"\u003eString str1 = \"salt\";\nString str2 = \"sAlT\";\u003c/pre\u003e Which two code fragments will output str1 and str2 are equal?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003eif (str1 == str2 )\n\t System.out.println(\"str1 and str2 are equal\");\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eif (str1.equals(str2) ) \n\t System.out.println(\"str1 and str2 are equal\");\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eif (str1.== str2.toLowerCase() ) \n\t System.out.println(\"str1 and str2 are equal\");\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eif (str1.equals(str2.toLowerCase()) ) \n\t System.out.println(\"str1 and str2 are equal\");\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eif (str1.equalsIgnoreCase(str2) ) \n\t System.out.println(\"str1 and str2 are equal\");\u003c/pre\u003e"
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[3,4]
		},
		// 6
		{
			"questionText":"Given: \u003cpre class=\"prettyprint lang-java\"\u003epublic class Circle {\n\t public double getCircumference(double radius ) {\n\t\t return java.lang.Math.PI * 2 * radius;\n\t }\n\t public static double getArea(double radius) {\n\t\t return java.lang.Math.PI * radius * radius;\n\t }\n}\u003c/pre\u003e Which two code fragments will fail compilation?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003eCircle.getCircumference(10.5);\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003enew Circle().getCircumference(10.5);\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003edouble c = Circle.getCircumference(10.5);\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eCircle.getArea(5.5);\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003enew Circle().getArea(5.5);\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003edouble a = new Circle().getArea(5.5);\u003c/pre\u003e"
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[0,2]
		},
		// 7
		{
			"questionText":"\u003cpre class=\"prettyprint lang-java\"\u003epublic class JavaSETest {\n\t public static void main(String[] args) {\n\t\t List<Integer> weights = new ArrayList<>();\n\t\t weights.add(0);\n\t\t weights.add(5);\n\t\t weights.add(10);\n\t\t weights.add(15);\n\t\t weights.add(20);\n\t\t weights.add(25);\n\t\t weights.remove(5);\n\t\t System.out.println(\"Weights are \"+ weights);\n\t }\n}\u003c/pre\u003e What is the output of the preceding code?",
			"answers":[
				"Weights are null", 
				"Weights are [0, 0, 10, 1, 20]",
				"Weights are [0, 5, 10, 15, 20]", 
				"Weights are [0, 10, 15, 20, 25]"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[2]
		},
		// 8
		{
			"questionText":"Given: \u003cpre class=\"prettyprint lang-java\"\u003epublic interface StringInterface {\n\t public String toString();\n}\u003c/pre\u003e \u003cpre class=\"prettyprint lang-java\"\u003epublic class SuperString implements StringInterface {\n\t public String toString() {\n\t\t return \"Super String\";\n\t }\n}\u003c/pre\u003e \u003cpre class=\"prettyprint lang-java\"\u003eclass SubString extends SuperString {\n\t public String toString() {\n\t\t return \"Sub String\";\n\t }\n\t public static void main(String[] args) {\n\t\t //insert code here\n\t\t System.out.println(str);\n\t }\n}\u003c/pre\u003e Which three statements, when inserted in the code, will generate the output Super String?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003eObject str = new SubString();\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eObject str = new SuperString();\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eSubString str = new SubString();\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eSuperString str = new SuperString();\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eStringInterface str = new SubString();\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eStringInterface str = new SuperString();\u003c/pre\u003e"
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[1,3,5]
		},
		// 9
		{
			"questionText":"Given the code fragment: \u003cpre class=\"prettyprint lang-java\"\u003echar[] charArray = { '8', '9', '0', 'e', 's', 'p', 'r', 'e', 's', 's', 'o'};\nint i = 48; //Start range for digits\ndo {\n\t for(char c : charArray) {\n\t\t if ((char) i == c) {\n\t\t\t System.out.println(c + \" found!\");\n\t\t\t //insert skip statement\n\t\t }\n\t }\n} while (i++ < 57); //End range for digits\u003c/pre\u003e The skip statement should terminate only the inner for block if a numerical character is found. Which skip statement should be inserted in the code?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003ebreak;\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003ebreak for;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003econtinue;\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003ereturn;\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 10
		{
			"questionText":"Which two method declarations will compile if their implementation code explicitly throws a BadStringOperationException?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003evoid trySomething (OperationType op)\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003evoid trySomething (OperationType op) throws Exception\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003evoid trySomething (OperationType op) throw UnsupportedOperationException\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003evoid trySomething (OperationType op) throw BadStringOperationException\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003evoid trySomething (OperationType op) throws BadStringOperationException\u003c/pre\u003e"
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[1,4]
		},
		// 11
		{
			"questionText":"Given: \u003cpre class=\"prettyprint lang-java\"\u003epublic class ExceptionSelection {\n\t private Exception ex;\n\t public ExceptionSelection(Exception ex) {\n\t\t this.ex = ex;\n\t }\n\t public void throwException() throws Exception {\n\t\t System.out.println(\"Method started...\");\n\t\t throw ex;\n\t }\n\t public static void main (String[] args) throws Exception {\n\t\t ExceptionSelection exObj = \n\t\t   new ExceptionSelection(new UnsupportedOperationException());\n\t\t exObj.throwException();\n\t\t System.out.println(\"Method ended.\");\n\t }\n}\u003c/pre\u003e What is the most likely output?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-bash\"\u003eException in thread \"main\" java.lang.UnsupportedOperationException \n  at java8_ii.ExceptionSelection.main(ExceptionSelection.java:21)\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-bash\"\u003eMethod started... \nException in thread \"main\" java.lang.UnsupportedOperationException \n  at java8_ii.ExceptionSelection.main(ExceptionSelection.java:21)\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-bash\"\u003eException in thread \"main\" java.lang.UnsupportedOperationException \n  at java8_ii.ExceptionSelection.main(ExceptionSelection.java:21) \nMethod ended.\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-bash\"\u003eMethod started... \nException in thread \"main\" java.lang.UnsupportedOperationException \n  at java8_ii.ExceptionSelection.main(ExceptionSelection.java:21) \nMethod ended.\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[1]
		},
		// 12
		{
			"questionText":"Which statement is true about reference and object types?",
			"answers":[
				"The reference type corresponds to the instantiated class.", 
				"The object type corresponds to the type in the variable declaration.",
				"The reference type determines which implementation is executed for overloaded methods.", 
				"The object type determines which implementation is executed for overridden methods."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 13
		{
			"questionText":"Given: \u003cpre class=\"prettyprint lang-java\"\u003epublic class Java8 {\n\t static int modify (int[] i) {\n\t\t i[0] += 10;\n\t\t return i[0] + 10;\n\t }\n\t public static void main(String[] args) {\n\t\t int[] i = {10};\n\t\t //insert code here\n\t }\n}\u003c/pre\u003e Which statement(s) should be inserted in the code to output 35?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003eSystem.out.println(modify(i));\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eSystem.out.println(modify(i)+ 15);\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003emodify(i); System.out.println(i[0]);\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003emodify(i); System.out.println(i[0] + 15);\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 14
		{
			"questionText":"Which statement is true about abstract classes?",
			"answers":[
				"An abstract class may contain only abstract methods.", 
				"An abstract class may contain both class and instance members.",
				"Abstract methods may be declared with the final modifier.", 
				"Abstract methods may be declared with the private modifier."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[1]
		},
		// 15
		{
			"questionText":"Which statement is true about if statements nested in if and else statements?",
			"answers":[
				"The inner if statement(s) are evaluated only if the outer if statement is true.", 
				"The inner if statement(s) are evaluated only if the outer if statement is evaluated.",
				"The outer else statement is evaluated only if the inner if statement(s) are true.", 
				"The outer else statement is evaluated only if the inner if statement(s) are evaluated."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 16
		{
			"questionText":"Which of the following is a valid definition for the main method in a Java application?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic void Main(String[] args) {\n\t if (args.length > 0)\n\t\t System.out.println(\"You provided some arguments!\");\n\t else\n\t\t System.out.println(\"No arguments provided.\");\n}\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic static int main(String[] args) {\n\t if (args.length > 0)\n\t\t System.out.println(\"You provided some arguments!\");\n\t else\n\t\t System.out.println(\"No arguments provided.\");\n\t return 0;\n}\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic int Main(String[] args) {\n\t if (args.length > 0)\n\t\t System.out.println(\"You provided some arguments!\");\n\t else\n\t\t System.out.println(\"No arguments provided.\");\n\t return 0;\n}\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic static void main(String[] cmdArgs) {\n\t if (cmdArgs.length > 0)\n\t\t System.out.println(\"You provided some arguments!\");\n\t else\n\t\t System.out.println(\"No arguments provided.\");\n}\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 17
		{
			"questionText":"Given: \u003cpre class=\"prettyprint lang-java\"\u003eArrayList<String> names = new ArrayList<>(2);\nnames.add(\"Alice\");\nnames.add(\"Ann\");\nnames.add(\"James\");\u003c/pre\u003e Which two expressions will evaluate to 3?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003enames.size()\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003enames.length\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003enames.length()\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003enames.get(1).size()\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003enames.get(1).length\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003enames.get(1).length()\u003c/pre\u003e"
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[0,5]
		},
		// 18
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epublic class VarScope {\n\t int var;\n\t public static void main (String[] args) {\n\t\t int var = 10;\n\t\t VarScope scope = new VarScope();\n\t\t scope.var = var + 2;\n\t\t scope.adjustVar(scope.var + 2);\n\t\t System.out.println(\"var = \" + var);\n\t }\n\t private void adjustVar(int var) {\n\t\t var += 2;\n\t }\n}\u003c/pre\u003e What is the result?",
			"answers":[
				"var = 10", 
				"var = 12",
				"var = 14", 
				"var = 16"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 19
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epackage java8;\n\npublic class MyBasicClass {}\u003c/pre\u003e Which statement is true about packages in MyBasicClass?",
			"answers":[
				"MyBasicClass can access members using simple names in the java8 package without an implicit import statement.", 
				"MyBasicClass can access all members using simple names in the same application without an implicit import statement.",
				"MyBasicClass can only access members using simple names in the java.lang package with an explicit import statement.", 
				"MyBasicClass can only access members using simple names in the same application with an explicit import statement."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 20
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epublic class SmartPhone {\n\t float screenResolution, width, height;\n\t public static void main (String[] args) {\n\t\t SmartPhone phone;\n\t\t phone.height = 112.2f;\n\t\t phone.width = 56.8f;\n\t\t System.out.format(\"%.0f dpi - %.1f X %.1f\", \n\t\t phone.screenResolution, phone.height, phone.width);\n\t }\n}\u003c/pre\u003e What is the result?",
			"answers":[
				"null dpi - 112.2 X 56.8", 
				"0 dpi - 112.2 X 56.8",
				"A runtime error is produced.", 
				"A compile error is produced."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 21
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epublic class WrapperTest {\n\t public static void main(String[] args) {\n\t\t System.out.println(Integer.valueOf(\"777.77\"));\n\t }\n}\u003c/pre\u003e What would be the output of this code fragment?",
			"answers":[
				"77", 
				"777",
				"777.77", 
				"NumberFormatException"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 22
		{
			"questionText":"Given: \u003cpre class=\"prettyprint lang-java\"\u003epublic class CardDeck {\n\t public CardDeck() {/*Implementation omitted*/}\n\t public CardDeck (int suits) {/*Implementation omitted*/}\n\t public CardDeck (int suits, boolean includeJokers) {/*Implementation omitted*/}\n}\u003c/pre\u003e Which constructor is the default constructor?",
			"answers":[
				"CardDeck()", 
				"CardDeck (int)",
				"CardDeck (int, boolean)", 
				"Not provided."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 23
		{
			"questionText":"Given: \u003cpre class=\"prettyprint lang-java\"\u003epublic interface StringInterface {\n\t public String toString();\n}\u003c/pre\u003e  \u003cpre class=\"prettyprint lang-java\"\u003epublic class SuperString implements StringInterface {\n\t public String toString() {\n\t\t return \"Super String 1\";\n\t }\n\t public Object toString(String str) {\n\t\t return \"Super String 2\";\n\t }\n}\n\nclass SubString extends SuperString {\n\t public String toString() {\n\t\t return \"Sub String 1\";\n\t }\n\t public String toString(String str) {\n\t\t return \"Sub String 2\";\n\t }\n\t public static void main(String[] args) {\n\t\t StringInterface string = new SubString();\n\t\t System.out.println(string.toString(\"test\"));\n\t }\n}\u003c/pre\u003e What is the result?",
			"answers":[
				"Sub String 1", 
				"Sub String 2",
				"Super String 1", 
				"Super String 2",
				"Compilation fails.",
				"An exception is thrown at run time."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[4]
		},
		// 24
		{
			"questionText":"Given: \u003cpre class=\"prettyprint lang-java\"\u003epublic abstract class Writer {\n\t public static void write() {System.out.println(\"Writing...\");}\n}\u003c/pre\u003e \u003cpre class=\"prettyprint lang-java\"\u003epublic class Author extends Writer {\n\t public static void write() {System.out.println(\"Writing book\");}\n}\u003c/pre\u003e \u003cpre class=\"prettyprint lang-java\"\u003epublic class Programmer extends Writer {\n\t public static void write() {System.out.println(\"Writing code\");}\n\t public static void main(String[] args) {\n\t\t Writer w = new Programmer();\n\t\t w.write();\n\t }\n}\u003c/pre\u003e What is the result?",
			"answers":[
				"Writing...", 
				"Writing book",
				"Writing code", 
				"Compilation fails.",
				"An exception is thrown at run time."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 25
		{
			"questionText":"Given: \u003cpre class=\"prettyprint lang-java\"\u003eStringBuilder sb = new StringBuilder(\"Test\");\u003c/pre\u003e What is the initial capacity of the StringBuilder object?",
			"answers":[
				"4", 
				"12",
				"16", 
				"20"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 26
		{
			"questionText":"Given the following code fragment: \u003cpre class=\"prettyprint lang-java\"\u003epublic class TestArrayList {\n\t public static void main (String[] args) {\n\t\t ArrayList<String> names = new ArrayList<>(\n\t\t   Arrays.asList(\"Amy\",\"Anne\",\"Brian\",\"George\",\"Ruth\",\"Todd\"));\n\t\t names.add(\"Jason\");\n\t\t System.out.println(names[6]);\n\t }\n}\u003c/pre\u003e What is the result?",
			"answers":[
				"Code compilation fails.", 
				"Code throws a runtime exception.",
				"Ruth", 
				"Todd",
				"Jason"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 27
		{
			"questionText":"Which two statements are true about the contents of a class?",
			"answers":[
				"A class can include only non-static members.", 
				"A class cannot include package or import statements.",
				"A class can include nested enumerations and classes.", 
				"A class cannot include constructors."
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[1,2]
		},
		// 28
		{
			"questionText":"Which statement is true about constructor overloading?",
			"answers":[
				"A default constructor can be overloaded in the same class.", 
				"A default constructor can be overloaded in a subclass.",
				"The constructor must use a different name.", 
				"The constructor must use the this keyword."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[1]
		},
		// 29
		{
			"questionText":"Given: \u003cpre class=\"prettyprint lang-java\"\u003epublic class RuntimeExceptionTests {\n\t public static char performOperation(String str) {\n\t\t return str.charAt(0);\n\t }\n\t public static void main (String[] args) {\n\t\t performOperation(\"\");\n\t }\n}\u003c/pre\u003e Which exception is thrown by running the given code?",
			"answers":[
				"NullPointerException", 
				"IndexOutOfBoundsException",
				"ArrayIndexOutOfBoundsException", 
				"StringIndexOutOfBoundsException"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 30
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epublic class MyBasicClass {\n\t static int field; \t\t //line 1\n\t int getField() {} \t\t //line 2\n\t static void Main(String[] args) { \t //line 3\n\t\t System.out.println(field); \t\t //line 4\n\t }\n}\u003c/pre\u003e Which line causes a compilation error?",
			"answers":[
				"line 1", 
				"line 2",
				"line 3", 
				"line 4"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[1]
		},
		// 31
		{
			"questionText":"Which two declaration statements correctly initialize their variables?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003eboolean b1 = (6 < 4);\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eboolean b2 = 1;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eint i1 = 40.4;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eint i2 = -6,000;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003efloat f1 = 10.01;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003efloat f2 = 10.01E2f;\u003c/pre\u003e" 
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[0,5]
		},
		// 32
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003eString s1 = \"espresso\";\nString s2 = \"java\";\nint i = 1;\nboolean b = true;\u003c/pre\u003e Which two expressions are valid in a while statement?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003es1.equals(s2)\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003es1.compareTo(s2)\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003ei = 2\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003ei == 2\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eb != \"false\"\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003ei <= b\u003c/pre\u003e" 
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[0,3]
		},
		// 33
		{
			"questionText":"Which of the following examples best illustrates the concept of Java inheritance? (Choose all that apply.)",
			"answers":[
				"A method in one class can reuse methods in another class.", 
				"A method in one class can reuse methods in multiple other classes.",
				"A method is invoked based on the instantiated object.",
				"A method is invoked based on the method signature.",
				"A method is invoked based on the object reference."
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[0,2]
		},
		// 34
		{
			"questionText":"Which exception indicates that an index is invalid when using a vector, array, or string?",
			"answers":[
				"RuntimeException", 
				"IndexOutOfBoundsException",
				"ArrayIndexOutOfBoundsException",
				"StringIndexOutOfBoundsException"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[1]
		},
		// 35
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epublic class VarScope {\n\t static int var;\n\t public static void main (String[] args) {\n\t\t int var = 9;\n\t\t printVar();\n\t }\n\t public static void printVar() {\n\t\t int var = 10;\n\t\t System.out.print(\"var = \" + var++);\n\t }\n}\u003c/pre\u003e What is the result?",
			"answers":[
				"var = 0", 
				"var = 9",
				"var = 10",
				"var = 11"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[2]
		},
		// 36
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epublic class MyBasicClass {\n\t //Insert code here\n}\u003c/pre\u003e Which three lines of code can be included in the class?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003epackage basicPackage;\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eimport java.text.*;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eenum ClassType {basic, advanced}\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003evoid BasicMethod() {}\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003estatic final int VAL=1000;\u003c/pre\u003e"
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[2,3,4]
		},
		// 37
		{
			"questionText":"Which statement is true about declaring members in a concrete class?",
			"answers":[
				"All methods and constructors must contain bodies.", 
				"All fields must be initialized explicitly.",
				"Only instance fields and methods are supported.",
				"Only static fields and methods are supported."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 38
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epublic class CommandLineClass {\n\t public static void main(String[] args) {\n\t\t System.out.print(\"Argument: \" + args[1]);\n\t }\n}\u003c/pre\u003e Which command will run the code without error?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-bash\"\u003ejava CommandLineClass arg1\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-bash\"\u003ejava CommandLineClass arg1 arg2\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-bash\"\u003ejavac CommandLineClass arg1\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-bash\"\u003ejavac CommandLineClass arg1 arg2\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[1]
		},
		// 39
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epublic class CommandLineClass {\n\t public static void main(String[] args) {\n\t\t System.out.print(\"Argument: \" + args[2]);\n\t }\n}\u003c/pre\u003e and \u003cpre class=\"prettyprint lang-bash\"\u003ejavac CommandLineClass.java \njava CommandLineClass arg1,arg2 arg3\u003c/pre\u003e What is the result?",
			"answers":[
				"arg1", 
				"arg2",
				"arg3",
				"arg1,arg2",
				"Exception in thread \"main\" java.lang.ArrayIndexOutOfBoundsException"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[4]
		},
		// 40
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epublic class Circle {\n\t static double getCircumference(double radius ) {\n\t\t return PI * 2 * radius;\n\t }\n\t public static double getArea(double radius) {\n\t\t return PI * radius * radius;\n\t }\n}\u003c/pre\u003e Which import statement will enable the code to compile and run?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003eimport java.lang.*;\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eimport java.lang.Math;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eimport java.lang.Math.*;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eimport static java.lang.Math.PI;\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 41
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java linenums\"\u003epublic class Java8_I {\n\t public static void main (String[] args) {\n\t\t //Code goes here\n\t\t System.out.println(\"Value: \" + b);\n\t }\n}\u003c/pre\u003e Which declaration line, when inserted at line 3, enables the code to compile and run?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003eboolean b = 0;\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eboolean b = null;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003ebyte b = 1101;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003ebyte b = 0b1101;\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 42
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epublic class Java8{\n\t public static void main (String[] args) {\n\t\t int x = 1;\n\t\t int y = x;\n\t\t int z = y;\n\t\t z = 10;\n\t\t System.out.format(\"x,y,z: %d,%d,%d\", x, y, z);\n\t }\n}\u003c/pre\u003e What is the result when this program is executed?",
			"answers":[
				"x,y,z: 10,10,10", 
				"x,y,z: 1,10,10",
				"x,y,z: 1,1,10",
				"x,y,z: 1,1,1"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[2]
		},
		// 43
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epublic class Java8_I {\n\t static class RefType {\n\t\t int val;\n\t\t RefType(int val) {this.val = val;}\n\t }\n\n\t public static void main (String[] args) {\n\t\t RefType x = new RefType(1);\n\t\t RefType y = x;\n\t\t RefType z = y;\n\t\t z.val = 10;\n\t\t System.out.format(\"x,y,z: %d,%d,%d\", x.val, y.val, z.val);\n\t }\n}\u003c/pre\u003e What is the result when this program is executed?",
			"answers":[
				"x,y,z: 10,10,10", 
				"x,y,z: 1,10,10",
				"x,y,z: 1,1,10",
				"x,y,z: 1,1,1"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 44
		{
			"questionText":"Given the following class: \u003cpre class=\"prettyprint lang-java linenums\"\u003epublic class Machine {\n\t float OSVersion;\n\t public static void main (String[] args) {\n\t\t //Insert code here\n\t }\n}\u003c/pre\u003e Which code fragment correctly assigns a value to the OSVersion field at line 4?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003eOSVersion = 10.1f;\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eMachine.OSVersion = 10.1f;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eMachine myMachine = new Machine(); \nmyMachine.OSVersion = 10;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eMachine myMachine = new Machine(); \nMachine.OSVersion = 10;\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[2]
		},
		// 45
		{
			"questionText":"Given the following class: \u003cpre class=\"prettyprint lang-java\"\u003epublic class JavaSETest {\n\t public static void main(String[] args) {\n\t\t List&lt;Integer&gt; sizes = new ArrayList<>();\n\t\t sizes.add(null);\n\t\t int firstSize = sizes.get(0);\n\t\t System.out.println(firstSize);\n\t }\n}\u003c/pre\u003e What would be the output of this code fragment?",
			"answers":[
				"null", 
				"0",
				"1",
				"NullPointerException"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 46
		{
			"questionText":"Which code line increments the variable x after the variable y is assigned to the expression?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003eint y = (x + 1) * 25;\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eint y = x + 1 * 25;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eint y = x++ * 25;\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eint y = ++x * 25;\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[2]
		},
		// 47
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003eint x = 5;\u003c/pre\u003e Which two expressions evaluate to 5?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003ex-- + 10\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003e--x + 10\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003e-x + 10\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003e10 - --x\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003e10 - x--\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003e10 - -x\u003c/pre\u003e"
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[2,4]
		},
		// 48
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epublic class Java8_I {\n\t public static void main (String[] args) {\n\t\t String s1 = \"salty\"; \n\t\t String s2 = new String(\"salty\");\n\t\t String s3 = s2;\n\t\t if (s1.equals(s2) && s2.equals(s3)) \n\t\t\t System.out.println(\"We are equal!\");\n\t\t if (s1 == s2 && s2 == s3)\n\t\t\t System.out.println(\"We are really equal!\");\n\t }\n}\u003c/pre\u003e What is the result when this program is executed?",
			"answers":[
				"We are equal!", 
				"We are really equal!",
				"We are equal! \nWe are really equal!",
				"Nothing prints."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 49
		{
			"questionText":"Given the following code fragment: \u003cpre class=\"prettyprint lang-java\"\u003epublic class JavaSETest {\n\t public static void main(String[] args) {\n\t\t int index = 0;\n\t\t int number = (index<5)? 10 : \"No\";\n\t\t System.out.println(number);\n\t }\n}\u003c/pre\u003e What is the output?",
			"answers":[
				"0", 
				"10",
				"No",
				"Compiler error"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 50
		{
			"questionText":"Given the following code: \u003cpre class=\"prettyprint lang-java\"\u003epublic class JavaSETest {\n\t public static void main(String[] args) {\n\t\t int i=0, j=1;\n\t\t int a = j<5? j++ : i++;\n\t\t System.out.println(\"i=\" + i+ \" j=\" + j);\n\t }\n}\u003c/pre\u003e What will be the result?",
			"answers":[
				"i=0 j=1", 
				"i=0 j=2",
				"i=1 j=1",
				"i=1 j=2"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[1]
		},
		// 51
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003eswitch(cardVal) {\n\t case 4: case 5: case 6:\n\t case 7: case 8:\n\t\t System.out.println(\"Hit\");\n\t\t break;\n\t case 9: case 10: case 11:\n\t\t System.out.println(\"Double\");\n\t\t break;\n\t case 15: case 16:\n\t\t System.out.println(\"Surrender\");\n\t\t break;\n\t default:\n\t\t System.out.println(\"Stand\");\n}\u003c/pre\u003e Which two values for the variable cardVal will output Stand?",
			"answers":[
				"6", 
				"10",
				"14",
				"16",
				"18"
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[2,4]
		},
		// 52
		{
			"questionText":"Examine the code fragment: \u003cpre class=\"prettyprint lang-java\"\u003epublic static String getGradeFB (String grade) {\n\t String comment = null;\n\t switch (grade) {\n\t\t case \"A\":\n\t\t case \"B\":\n\t\t\t comment = \"Excellent job!\";\n\t\t\t break;\n\t\t case \"C\":\n\t\t case \"D\":\n\t\t\t comment = \"You should try again.\";\n\t\t\t break;\n\t\t case \"F\":\n\t\t\t comment = \"You should study more.\";\n\t\t default:\n\t\t\t throw new RuntimeException();\n\t\t }\n\t\t return comment;\n}\u003c/pre\u003e Which three code statements will throw an exception?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003egetGradeFB(\"A\");\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003egetGradeFB(\"B\");\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003egetGradeFB(\"C\");\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003egetGradeFB(\"d\");\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003egetGradeFB(\"F\");\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003egetGradeFB(null);\u003c/pre\u003e"
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[3,4,5]
		},
		// 53
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epublic class Java8_I {\n\t public static void main (String[] args) {\n\t\t int[10] intArray; //line 1\n\t\t intArray[0] = 10; //line 2\n\t\t intArray[10] = 0; //line 3\n\t\t System.out.print(intArray.length); //line 4\n\t }\n}\u003c/pre\u003e Which line causes a compilation error?",
			"answers":[
				"Line 1", 
				"Line 2",
				"Line 3", 
				"Line 4"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 54
		{
			"questionText":"Which statement is true about array declarations?",
			"answers":[
				"An array's dimension can be set without using the new keyword.", 
				"Curly braces can contain a comma-delimited list of dimensions.",
				"Square brackets can contain a comma-delimited list of values.", 
				"Square brackets can be placed after the data type or variable name."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 55
		{
			"questionText":"Given the code fragment: \u003cpre class=\"prettyprint lang-java\"\u003echar [][] charArray2D = {{'c','u','p'},{'j','a','v','a'}}; \nSystem.out.println(charArray2D[1][2]);\u003c/pre\u003e What is the result?",
			"answers":[
				"u", 
				"v",
				"cup", 
				"java"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[1]
		},
		// 56
		{
			"questionText":"Given the code fragment: \u003cpre class=\"prettyprint lang-java\"\u003eint i = 0;\nwhile (i < 10 ? 1 : 0) {\n\t i++;\n}\nSystem.out.println(i);\u003c/pre\u003e What is the result?",
			"answers":[
				"Code compilation fails.", 
				"Code throws a runtime exception.",
				"0", 
				"9",
				"10"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 57
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003eint x = 0;\u003c/pre\u003e Which code fragment increments x to 10?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003ewhile (x < 10) { x++; }\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003ewhile (x < 11) { x++; }\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003ewhile (x < 10 ? 1 : 0) { x++; }\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003ewhile (x < 11 ? 1 : 0) { x++; }\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 58
		{
			"questionText":"Given the code fragment: \u003cpre class=\"prettyprint lang-java\"\u003eint[] grades = {73,82,97,91,67};\u003c/pre\u003e Which two sets of expressions are valid in a for statement?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003e;; i++\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003e;i < 5; i++\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eint i = 0;i < 5; i++\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eint i : grades\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003egrades : int i\u003c/pre\u003e"
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[2,3]
		},
		// 59
		{
			"questionText":"Given the code fragment: \u003cpre class=\"prettyprint lang-java\"\u003eint i = 0;\nfor (;;) {\n\t i++;\n}\nSystem.out.println(i);\u003c/pre\u003e What is the result?",
			"answers":[
				"Code compilation fails.", 
				"Code throws a runtime exception.",
				"0", 
				"2,147,483,647",
				"-2,147,483,648"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 60
		{
			"questionText":"Given the following code fragment: \u003cpre class=\"prettyprint lang-java\"\u003eint i = 6;\ndo {\n\t System.out.print(--i + \" \"); \n} while (i > 0);\nSystem.out.print(\"...BLAST OFF!\");\u003c/pre\u003e What is the result?",
			"answers":[
				"...BLAST OFF!", 
				"4 3 2 1 0 ...BLAST OFF!",
				"5 4 3 2 1 0 ...BLAST OFF!", 
				"5 4 3 2 1 ...BLAST OFF!",
				"6 5 4 3 2 1 ...BLAST OFF!"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[2]
		},
		// 61
		{
			"questionText":"Which statement is true about the while statement in a do-while block?",
			"answers":[
				"The while statement requires an expression that must evaluate to an int.", 
				"The while statement requires an expression that must evaluate to a boolean.",
				"Statements within a while block will execute at least once.", 
				"Statements within a do-while block may never execute."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[1]
		},
		// 62
		{
			"questionText":"Given the following: \u003cpre class=\"prettyprint lang-java\"\u003epublic class Java8_Looping {\n\t public static void main(String[] args) {\n\t\t char[] charArray = { 'e', 's', 'p', 'r', 'e', 's', 's', 'o', '8', '9', '0'};\n\t\t int i = 48; //Start range for digits\n\t\t do {\n\t\t\t for(char c : charArray) \n\t\t\t\t if ((char) i == c)\n\t\t\t\t\t System.out.println(c + \" found!\");\n\t\t } while (i++ < 57); //End range for digits\n\t }\n}\u003c/pre\u003e How many times is found! printed?",
			"answers":[
				"None", 
				"Once",
				"Twice", 
				"Thrice"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 63
		{
			"questionText":"Given the following code fragment: \u003cpre class=\"prettyprint lang-java\"\u003epublic class StandardMethods { \n\t public static double getSurfaceArea(double width, double height, double length) { \n\t\t return 2 * (width * length + height * length + height * width); \n\t } \n}\u003c/pre\u003e Assuming the given code is in the same package, which two code lines will compile? ",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003eStandardMethods.getSurfaceArea(8.5);\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eStandardMethods.getSurfaceArea(8.5,11);\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eStandardMethods.getSurfaceArea(8.5,11, 1.5);\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003edouble surfaceArea =StandardMethods.getSurfaceArea(8.5);\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003edouble surfaceArea =StandardMethods.getSurfaceArea(8.5,11);\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003edouble surfaceArea =StandardMethods.getSurfaceArea(8.5,11, 1.5);\u003c/pre\u003e"
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[2,5]
		},
		// 64
		{
			"questionText":"Given the following code fragment: \u003cpre class=\"prettyprint lang-java\"\u003epublic class StandardMethods {\n\t public static void printPerimeter(double... sides) {\n\t\t double result = 0;\n\t\t for (double side: sides) {\n\t\t\t result += side;\n\t\t }\n\t\t System.out.println(\"Perimeter is \" + result);\n\t }\n}\u003c/pre\u003e Assuming the given code is in the same package, which two code lines will compile?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003eStandardMethods.printPerimeter();\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eStandardMethods.printPerimeter(7.5, 9.8, 11);\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003edouble perimeter = StandardMethods.printPerimeter();\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003edouble perimeter = StandardMethods.printPerimeter(7.5, 9.8, 11);\u003c/pre\u003e"
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[0,1]
		},
		// 65
		{
			"questionText":"Which statement is true about varargs when specifying method parameters?",
			"answers":[
				"Arguments are accessible as enumeration values.", 
				"Arguments can consist of different data types.",
				"Valid invocation requires at least one argument value.", 
				"Valid invocation can have zero arguments."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[3]
		},
		// 66
		{
			"questionText":"Which code fragment is a valid method declaration for an arbitrary number of values?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic Result performOperation (OperationType type, Object... args) {\n\t //implementation omitted\n}\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic Result performOperation (OperationType type, Object varargs) {\n\t //implementation omitted\n}\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic Result performOperation (Object... args, OperationType type) {\n\t //implementation omitted\n}\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic Result performOperation (Object varargs, OperationType type) {\n\t //implementation omitted\n}\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[0]
		},
		// 67
		{
			"questionText":"Given: \u003cpre class=\"prettyprint lang-java\"\u003epublic class VarScope {\n\t static int var;\n\t public static void main (String[] args) {\n\t\t int var = 9;\n\t\t printVar();\n\t }\n\t public static void printVar() {\n\t\t int var = 10;\n\t\t //insert code here\n\t }\n}\u003c/pre\u003e Which statement should be inserted in the code to output 0?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003eSystem.out.print(var);\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eSystem.out.print(VarScope.var);\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eSystem.out.print(main.var);\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eSystem.out.print(main().var);\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[1]
		},
		// 68
		{
			"questionText":"Given the code: \u003cpre class=\"prettyprint lang-java\"\u003epublic class VarScope {\n\t public int i1;\n\t public static int i2;\n}\u003c/pre\u003e Given the output: \u003cpre class=\"prettyprint lang-bash\"\u003e10 + 5 = 15\u003c/pre\u003e Which two code fragments will generate the required output?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003eVarScope v1 = new VarScope();\nVarScope v2 = new VarScope();\nv1.i1 = 10; v2.i1 = 5;\nSystem.out.format(\"%d + %d = %d\", v1.i1, v2.i1, v1.i1 + v2.i1);\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003eVarScope v1 = new VarScope();\nVarScope v2 = new VarScope();\nv1.i2 = 10; v2.i2 = 5;\nSystem.out.format(\"%d + %d = %d\", v1.i2, v2.i2, v1.i2 + v2.i2);\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eVarScope v1 = new VarScope();\nVarScope v2 = new VarScope();\nv1.i1 = 10; v2.i2 = 5;\nSystem.out.format(\"%d + %d = %d\", v1.i1, v1.i2, v1.i1 + v1.i2);\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003eVarScope v1 = new VarScope();\nVarScope v2 = new VarScope();\nv1.i1 = 10; v2.i1 = 5;\nSystem.out.format(\"%d + %d = %d\", v1.i2, v1.i2, v1.i2 + v1.i2)\u003c/pre\u003e"
			],
			"type":"MULTIPLE_ANSWERS",
			"correctAnswers":[0,2]
		},
		// 69
		{
			"questionText":"Given: \u003cpre class=\"prettyprint lang-java\"\u003epublic class SuperEmptyClass {\n\t public SuperEmptyClass (Object obj) { /*Implementation omitted*/ }\n}\u003c/pre\u003e \u003cpre class=\"prettyprint lang-java\"\u003epublic class EmptyClass extends SuperEmptyClass { }\u003c/pre\u003e Which constructor is provided to EmptyClass by the compiler?",
			"answers":[
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic EmptyClass() {}\u003c/pre\u003e", 
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic EmptyClass() { super();}\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic EmptyClass(Object obj) {}\u003c/pre\u003e",
				"\u003cpre class=\"prettyprint lang-java\"\u003epublic EmptyClass(Object obj) { super(obj);}\u003c/pre\u003e"
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[1]
		},
		// 70
		{
			"questionText":"Which statement is true about default constructors?",
			"answers":[
				"Default constructors include only a single parameter.", 
				"Default constructors include an invocation of the superclass.",
				"All classes are provided a default constructor by the compiler.",
				"Superclass constructors automatically invoke default constructors of subclasses."
			],
			"type":"SINGLE_ANSWER",
			"correctAnswers":[1]
		}	
	],
	"technology":"Java",
	"time":9000
});