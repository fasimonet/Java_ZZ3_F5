public class ClassToConvert {
    public int number;
    private String name;
    protected float sum;

    public ClassToConvert(int nb, String name, float sum)
    {
        number = nb;
        this.name = name;
        this.sum = sum;
    }

    protected void calculateSum(float a, float b)
    {
        sum = a + b;
    }

    private int dummyMethod(int a)
    {
        return 2 - a;
    }

    public String getName()
    {
        return this.name;
    }
}
