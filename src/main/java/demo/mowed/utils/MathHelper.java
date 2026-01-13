package demo.mowed.utils;

public class MathHelper {
    public static Float truncate(Float value, int digits) {
        if (value == null) {
            return null;
        }

        float factor = (float) Math.pow(10, digits + 1);
        return (float) ((int) (value * factor)) / factor;
    }
}
