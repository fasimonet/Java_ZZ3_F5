import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static java.lang.System.lineSeparator;

public class JavaToCppConverter {
    public static String convertJavaToCppClass(String javaClassName, String cppClassName) throws ClassNotFoundException {
        StringBuilder cppClassContent = new StringBuilder();

        Map<String, ArrayList<String>> attributesMethodsAndConstructorsSortedByVisibility = new HashMap<>();
        Map<String, ArrayList<String>> methodsSortedByVisibility = new HashMap<>();
        Map<String, ArrayList<String>> constructorsSortedByVisibility = new HashMap<>();
        Set<String> dependencies = new HashSet<>();
        Class javaClass = Class.forName(javaClassName);

        retrieveAttributes(javaClass, attributesMethodsAndConstructorsSortedByVisibility, dependencies);
        retrieveMethodsPrototypes(javaClass, attributesMethodsAndConstructorsSortedByVisibility, methodsSortedByVisibility, dependencies);
        retrieveConstructors(javaClass, attributesMethodsAndConstructorsSortedByVisibility, constructorsSortedByVisibility, dependencies);

        buildGuardiansHeader(cppClassName, cppClassContent);
        buildDependencies(dependencies, cppClassContent);
        buildClass(cppClassName, attributesMethodsAndConstructorsSortedByVisibility, cppClassContent);
        buildClassImplementation(cppClassName, methodsSortedByVisibility, constructorsSortedByVisibility, cppClassContent);
        buildGuardiansFooter(cppClassContent);

        return cppClassContent.toString();
    }

    // Convert Java String type to cpp std::string type
    private static String convertJavaStringToCppString(String javaType, Set<String> dependencies) {
        String typeToReturn;

        if (javaType.equals("java.lang.String")) {
            dependencies.add("string");
            typeToReturn = "std::string";
        } else {
            typeToReturn = javaType;
        }

        return typeToReturn;
    }

    // Retrieve attributes
    private static void retrieveAttributes(Class javaClassType, Map<String, ArrayList<String>> attributesMethodsAndConstructorsSortedByVisibility, Set<String> dependencies) {
        for (Field attribute : javaClassType.getDeclaredFields()) {
            String splitedAttribute = attribute.toString().split(" ")[0];
            String attributesInCppString = convertJavaStringToCppString(attribute.toString().split(" ")[1], dependencies);

            if (!attributesMethodsAndConstructorsSortedByVisibility.containsKey(splitedAttribute)) {
                attributesMethodsAndConstructorsSortedByVisibility.put(splitedAttribute, new ArrayList<>());
            }
            attributesMethodsAndConstructorsSortedByVisibility.get(splitedAttribute).add(new StringBuilder(attributesInCppString).append(" ").append(attribute.getName()).toString());
        }
    }

    // Retrieve Methods
    private static void retrieveMethodsPrototypes(Class javaClassType, Map<String, ArrayList<String>> attributesMethodsAndConstructorsSortedByVisibility, Map<String, ArrayList<String>> methodsSortedByVisibility, Set<String> dependencies) {
        for (Method method : javaClassType.getDeclaredMethods()) {
            String splitedMethod = method.toString().split(" ")[0];
            String methodsInCppString = convertJavaStringToCppString(method.toString().split(" ")[1], dependencies);

            List<String> parametersTypes = new ArrayList<>();
            List<String> parametersTypesAndNames = new ArrayList<>();

            int cpt = 1;

            for (Class c : method.getParameterTypes()) {
                String param = convertJavaStringToCppString(c.getName(), dependencies);

                parametersTypes.add(param);
                parametersTypesAndNames.add(param + " param" + cpt++);
            }

            if (!attributesMethodsAndConstructorsSortedByVisibility.containsKey(splitedMethod)) {
                attributesMethodsAndConstructorsSortedByVisibility.put(splitedMethod, new ArrayList<>());
            }

            if (!methodsSortedByVisibility.containsKey(splitedMethod)) {
                methodsSortedByVisibility.put(splitedMethod, new ArrayList<>());
            }

            attributesMethodsAndConstructorsSortedByVisibility.get(splitedMethod).add(new StringBuilder(methodsInCppString).append(" ").append(method.getName()).append("(").append(String.join(", ", parametersTypes)).append(")").toString());
            methodsSortedByVisibility.get(splitedMethod).add(new StringBuilder(methodsInCppString).append(" ").append(method.getName()).append("(").append(String.join(", ", parametersTypesAndNames)).append(")").toString());
        }
    }

    // Retrieve constructors
    private static void retrieveConstructors(Class javaClass, Map<String, ArrayList<String>> attributesMethodsAndConstructorsSortedByVisibility, Map<String, ArrayList<String>> constructorsSortedByVisibility, Set<String> dependencies) {
        for (Constructor constructor : javaClass.getDeclaredConstructors()) {
            String javaConstructor = constructor.toString().split(" ")[0];

            List<String> parametersTypes = new ArrayList<>();
            List<String> parametersTypesAndNames = new ArrayList<>();

            int cpt = 1;

            for (Class c : constructor.getParameterTypes()) {
                String param = convertJavaStringToCppString(c.getName(), dependencies);

                parametersTypes.add(param);
                parametersTypesAndNames.add(param + " param" + cpt++);
            }

            if (!attributesMethodsAndConstructorsSortedByVisibility.containsKey(javaConstructor)) {
                attributesMethodsAndConstructorsSortedByVisibility.put(javaConstructor, new ArrayList<>());
            }

            if (!constructorsSortedByVisibility.containsKey(javaConstructor)) {
                constructorsSortedByVisibility.put(javaConstructor, new ArrayList<>());
            }

            attributesMethodsAndConstructorsSortedByVisibility.get(javaConstructor).add(new StringBuilder(constructor.getName()).append("(").append(String.join(", ", parametersTypes)).append(")").toString());
            constructorsSortedByVisibility.get(javaConstructor).add(new StringBuilder(constructor.getName()).append("(").append(String.join(", ", parametersTypesAndNames)).append(")").toString());
        }
    }

    // Build the guardian header
    public static void buildGuardiansHeader(String className, StringBuilder cppClassContent) {
        cppClassContent.append("#ifndef ").append(className.toUpperCase()).append("_HPP").append(lineSeparator());
        cppClassContent.append("#define ").append(className.toUpperCase()).append("_HPP").append(lineSeparator()).append(lineSeparator());
    }

    // Build the dependencies to include
    private static void buildDependencies(Set<String> dependencies, StringBuilder cppClassContent) {
        for (String dependency : dependencies) {
            cppClassContent.append("#include <").append(dependency).append(">").append(lineSeparator());
        }
        cppClassContent.append(System.lineSeparator());
    }

    private static void buildClass(String cppClassName, Map<String, ArrayList<String>> attributesMethodsAndConstructorsSortedByVisibility, StringBuilder cppClassContent) {
        cppClassContent.append("class").append(" ").append(cppClassName).append(" {").append(lineSeparator());

        for (Map.Entry<String, ArrayList<String>> elementSorted : attributesMethodsAndConstructorsSortedByVisibility.entrySet()) {
            // Get visibility
            cppClassContent.append("\t").append(elementSorted.getKey()).append(":").append(lineSeparator());

            // Display attributes, methods and constructors for this visibility
            for (String method : elementSorted.getValue()) {
                cppClassContent.append("\t\t").append(method).append(";").append(lineSeparator());
            }
        }

        cppClassContent.append("}").append(lineSeparator());
    }

    private static void buildClassImplementation(String cppClassName, Map<String, ArrayList<String>> methodsSortedByVisibility, Map<String, ArrayList<String>> constructorsSortedByVisibility, StringBuilder cppClassContent) {
        cppClassContent.append(lineSeparator());

        buildConstructorsImplementation(cppClassName, constructorsSortedByVisibility,cppClassContent);
        buildMethodsImplementation(cppClassName, methodsSortedByVisibility, cppClassContent);
    }

    private static void buildMethodsImplementation(String cppClassName, Map<String, ArrayList<String>> methodsSortedByVisibility, StringBuilder cppClassContent) {
        for (Map.Entry<String, ArrayList<String>> elementSorted : methodsSortedByVisibility.entrySet()) {
            for (String method : elementSorted.getValue()) {
                String[] methodPrototype = method.split(" ");

                String returnType = methodPrototype[0];
                StringBuilder methodNameAndParameters = new StringBuilder();

                for (int i=1; i<methodPrototype.length; ++i) {
                    methodNameAndParameters.append(methodPrototype[i]).append(" ");
                }

                cppClassContent.append(returnType).append(" ").append(cppClassName).append("::").append(methodNameAndParameters).append(" {").append(lineSeparator());

                if (returnType.compareTo("int") == 0 || returnType.compareTo("float") == 0 || returnType.compareTo("double") == 0 || returnType.compareTo("long") == 0 || returnType.compareTo("short") == 0 || returnType.compareTo("byte") == 0) {
                    cppClassContent.append("\treturn 0;");
                } else if (returnType.compareTo("boolean") == 0) {
                    cppClassContent.append("\treturn true;");
                } else if (returnType.compareTo("std::string") == 0) {
                    cppClassContent.append("\treturn \"\";");
                }

                cppClassContent.append(lineSeparator()).append("};").append(lineSeparator()).append(lineSeparator());
            }
        }
    }

    private static void buildConstructorsImplementation(String cppClassName, Map<String, ArrayList<String>> constructorsSortedByVisibility, StringBuilder cppClassContent) {
        for (Map.Entry<String, ArrayList<String>> elementSorted : constructorsSortedByVisibility.entrySet()) {
            for (String method : elementSorted.getValue()) {
                cppClassContent.append(cppClassName).append("::").append(method).append(" {").append(lineSeparator());
                cppClassContent.append(lineSeparator()).append("}").append(lineSeparator()).append(lineSeparator());
            }
        }
    }

    private static void buildGuardiansFooter(StringBuilder cppClassContent) {
        cppClassContent.append("#endif").append(lineSeparator());
    }
}
