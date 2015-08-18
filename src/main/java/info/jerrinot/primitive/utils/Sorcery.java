package info.jerrinot.primitive.utils;

public class Sorcery {

    public static int returnOneIfEqualsZeroOtherwise(int x, int y) {
        //from http://stackoverflow.com/questions/4161656/replacing-with-bitwise-operators
        int t = (x - y) | (y - x); // <0 iff x != y, 0 otherwise
        t >>= 31; // -1 iff x != y, 0 otherwise
        return 1 + t;
    }
}
