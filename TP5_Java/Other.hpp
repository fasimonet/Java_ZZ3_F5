#ifndef OTHER_HPP
#define OTHER_HPP

#include <string>

public class Other {
	private:
		std::string name;
		int dummyMethod(int);
	public:
		int number;
		std::string getName();
		ClassToConvert(int, std::string, float);
	protected:
		float sum;
		void calculateSum(float, float);
}

public Other::ClassToConvert(int param1, std::string param2, float param3) {

}

private int Other::dummyMethod(int param1)  {
	return 0;
}

protected void Other::calculateSum(float param1, float param2)  {

}

public std::string Other::getName()  {
	return "";
}

#endif
