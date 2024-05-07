package network.json;

import com.google.gson.*;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.lang.reflect.Type;

public class RealMatrixDeserializer implements JsonDeserializer<RealMatrix>
{
    @Override
    public RealMatrix deserialize(JsonElement jsonElement,
                                  Type type,
                                  JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        int numRows = jsonArray.size();
        int numCols = jsonArray.get(0).getAsJsonArray().size();

        double[][] doubleArray = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            JsonArray row = jsonArray.get(i).getAsJsonArray();
            if (row.size() != numCols) {
                throw new IllegalArgumentException("All rows must have the same number of columns");
            }
            for (int j = 0; j < numCols; j++) {
                doubleArray[i][j] = row.get(j).getAsDouble();
            }
        }

        RealMatrix realMatrix = new Array2DRowRealMatrix(doubleArray);
        return realMatrix;
    }
}
