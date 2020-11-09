#ifndef OTHER_HPP
#define OTHER_HPP

#include <string>

class Other {
	private:
		std::string name;
		int dummyMethod(int);
	public:
		int number;
		std::string getName();
		Other(int, std::string, float);
	protected:
		float sum;
		void calculateSum(float, float);
};

Other::Other(int param1, std::string param2, float param3) {

}

int Other::dummyMethod(int param1)  {
	return 0;
}

std::string Other::getName()  {
	return "";
}

void Other::calculateSum(float param1, float param2)  {

}

#endif
