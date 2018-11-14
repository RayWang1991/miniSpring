package labTest.typeConversion;

import org.junit.Test;

public class TypeConvertionTest {
    private <T> T func(Class<T> type, Object obj) {
        return (T) type;
    }

    @Test
    public void test(){
        Object obj = func(null, "string");
        System.out.println(obj);
    }
}
