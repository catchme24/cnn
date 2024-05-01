package network.layer;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Dimension {

    private int channel;
    private int heightTens;
    private int widthTens;
    private int stride;
    private int heightKernel;
    private int widthKernel;



    public Dimension(int channel, int heightTens, int widthTens, int stride, int heightKernel, int widthKernel) {
        this.channel = channel;
        this.heightTens = heightTens;
        this.widthTens = widthTens;
        this.stride = stride;
        this.heightKernel = heightKernel;
        this.widthKernel = widthKernel;
    }

    public Dimension(int channel, int heightTens, int widthTens) {
        this.channel = channel;
        this.heightTens = heightTens;
        this.widthTens = widthTens;
    }
}
