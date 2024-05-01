package network.layer;

import org.junit.jupiter.api.Test;

public class ConvolutionLayerTest {

    @Test
    public void testCreateConvLayer() {
        Dimension imageDimension = new Dimension(3, 32, 32);

        ConvolutionLayer layer1 = new ConvolutionLayer(50, 4, 2, imageDimension);

//        System.out.println("---------BEFORE CONNECT LAYER 1--------");
//        System.out.println(layer1.getSize());

        ConvolutionLayer layer2 = new ConvolutionLayer(100, 3, 1);
        layer2.setPrevious(layer1);

        ConvolutionLayer layer3 = new ConvolutionLayer(200, 3, 2);
        layer3.setPrevious(layer2);

        ConvolutionLayer layer4 = new ConvolutionLayer(400, 3, 1);
        layer4.setPrevious(layer3);


        System.out.println("---------AFTER CONNECT FULL--------");
        layer1.getSize();
        layer2.getSize();
        layer3.getSize();
        layer4.getSize();
    }
}
