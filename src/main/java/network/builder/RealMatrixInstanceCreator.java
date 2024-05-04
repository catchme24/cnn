package network.builder;

import com.google.gson.InstanceCreator;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.IOException;
import java.lang.reflect.Type;

public class RealMatrixInstanceCreator implements InstanceCreator<RealMatrix> {

    public RealMatrixInstanceCreator() {}

    @Override
    public RealMatrix createInstance(Type type) {

        Array2DRowRealMatrix instance = new Array2DRowRealMatrix();
        System.out.println("----------------");
        System.out.println(type.getTypeName());
        System.out.println("СОЗДАЮ ИНСТАНС");
        System.out.println(instance.getData().length + "х" + instance.getData()[0].length);
        System.out.println("----------------");
        return instance;
    }
}
