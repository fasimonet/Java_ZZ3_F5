import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class JavaToCppConverter {
    public static void convertJavaToCppClass(String javaClassName, String cppClassName) throws ClassNotFoundException {
        Map<String, ArrayList<String>> attributesMethodsAndConstructorsSortedByVisibility = new HashMap<>();
        Set<String> dependencies = new HashSet<>();
        Class java = Class.forName(javaClassName);

        retrieveAttributes(java, attributesMethodsAndConstructorsSortedByVisibility, dependencies);
        retrieveMethods(java, attributesMethodsAndConstructorsSortedByVisibility, dependencies);
        retrieveConstructors(java, attributesMethodsAndConstructorsSortedByVisibility, dependencies);

        displayGuardiansHeader(cppClassName);
        displayDependencies(dependencies);
        displayClass(cppClassName, attributesMethodsAndConstructorsSortedByVisibility);
        displayGuardiansFooter();
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
    private static void retrieveAttributes(Class javaClassType, Map<String, ArrayList<String>> content, Set<String> dependencies) {
        for (Field attribute : javaClassType.getDeclaredFields()) {
            String splitedAttribute = attribute.toString().split(" ")[0];
            String attributesInCppString = convertJavaStringToCppString(attribute.toString().split(" ")[1], dependencies);

            if (!content.containsKey(splitedAttribute)) {
                content.put(splitedAttribute, new ArrayList<>());
            }
            content.get(splitedAttribute).add(new StringBuilder(attributesInCppString).append(" ").append(attribute.getName()).toString());
        }
    }

    // Retrieve Methods
    private static void retrieveMethods(Class javaClassType, Map<String, ArrayList<String>> content, Set<String> dependencies) {
        for (Method method : javaClassType.getDeclaredMethods()) {
            String splitedMethod = method.toString().split(" ")[0];
            String methodsInCppString = convertJavaStringToCppString(method.toString().split(" ")[1], dependencies);

            List<String> params = new ArrayList<>();
            for (Class c : method.getParameterTypes()) {
                params.add(convertJavaStringToCppString(c.getName(), dependencies));
            }

            if (!content.containsKey(splitedMethod)) {
                content.put(splitedMethod, new ArrayList<>());
            }
            content.get(splitedMethod).add(new StringBuilder(methodsInCppString).append(" ").append(method.getName()).append("(").append(String.join(", ", params)).append(")").toString());
        }
    }

    // Retrieve constructors
    private static void retrieveConstructors(Class javaClass, Map<String, ArrayList<String>> attributesMethodsAndConstructorsSortedByVisibility, Set<String> dependencies) {
        for (Constructor constructor : javaClass.getDeclaredConstructors()) {
            String javaConstructor = constructor.toString().split(" ")[0];

            List<String> params = new ArrayList<>();
            for (Class c : constructor.getParameterTypes()) {
                params.add(convertJavaStringToCppString(c.getName(), dependencies));
            }

            if (!attributesMethodsAndConstructorsSortedByVisibility.containsKey(javaConstructor)) {
                attributesMethodsAndConstructorsSortedByVisibility.put(javaConstructor, new ArrayList<>());
            }
            attributesMethodsAndConstructorsSortedByVisibility.get(javaConstructor).add(new StringBuilder(constructor.getName()).append("(").append(String.join(", ", params)).append(")").toString());
        }
    }

    // Display the guardian header
    public static void displayGuardiansHeader(String className) {
        System.out.println(new StringBuilder("#ifndef ").append(className.toUpperCase()).append("_HPP"));
        System.out.println(new StringBuilder("#define ").append(className.toUpperCase()).append("_HPP"));
        System.out.println();
    }

    // Display the dependencies
    private static void displayDependencies(Set<String> dependencies) {
        for (String dependency : dependencies) {
            System.out.println(new StringBuilder("#include <").append(dependency).append(">"));
        }
        System.out.println();
    }

    // Display the cpp class content
    private static void displayClass(String cppClassName, Map<String, ArrayList<String>> attributesMethodsAndConstructorsSortedByVisibility) {
        System.out.println(new StringBuilder("Class ").append(cppClassName).append(" {"));

        for (Map.Entry<String, ArrayList<String>> elementSorted : attributesMethodsAndConstructorsSortedByVisibility.entrySet()) {
            // Display visibility
            System.out.println(new StringBuilder("\t").append(elementSorted.getKey()).append(":"));
            // Display attributes for this visibility
            for (String method : elementSorted.getValue()) {
                System.out.println(new StringBuilder("\t\t").append(method).append(";"));
            }
        }

        System.out.println("}");
        System.out.println();
    }

    // Display the guardian footer
    public static void displayGuardiansFooter() {
        System.out.println("#endif");
    }
}
