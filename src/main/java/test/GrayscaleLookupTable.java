package test;

import java.awt.image.LookupTable;

/**
 * Custom converting to gray scale
 *
 * @author vsizko on 16.02.19.
 */
public class GrayscaleLookupTable extends LookupTable {

    private static final double K = 0.333;

    public GrayscaleLookupTable(){
        super(0, 3);
    }

    @Override
    public int[] lookupPixel(int[] src, int[] dest) {
        if (dest == null){
            dest = new int[3];
        }
        int val = (int)(src[0]*K + src[1]*K + src[2]*K);

        for(int i = 0; i < dest.length; i++){
            dest[i] = val;
        }
        return dest;
    }
}
