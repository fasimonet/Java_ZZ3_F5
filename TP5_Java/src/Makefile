make-java: 
	javac ClassToConvert.java FileWriter.java JavaToCppConverter.java TP5.java

run-java: make-java
	java TP5 ClassToConvert ConvertedClassInCpp --stdout

make-cpp: run-java
	g++ -o classToConvert.hpp 

clean: 
	rm -rf *.o
	rm -rf *.class
