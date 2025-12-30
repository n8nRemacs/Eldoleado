package androidx.camera.core.impl.utils;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Size;
import androidx.core.util.z;
import j.N;
import j.X;
import java.util.Locale;

/* compiled from: TransformUtils.java */
@X
/* loaded from: classes.dex */
public class u {

    /* renamed from: a, reason: collision with root package name */
    public static final RectF f24303a = new RectF(-1.0f, -1.0f, 1.0f, 1.0f);

    @N
    public static Matrix a(@N RectF rectF, @N RectF rectF2, int i12, boolean z12) {
        Matrix matrix = new Matrix();
        RectF rectF3 = f24303a;
        Matrix.ScaleToFit scaleToFit = Matrix.ScaleToFit.FILL;
        matrix.setRectToRect(rectF, rectF3, scaleToFit);
        matrix.postRotate(i12);
        if (z12) {
            matrix.postScale(-1.0f, 1.0f);
        }
        Matrix matrix2 = new Matrix();
        matrix2.setRectToRect(rectF3, rectF2, scaleToFit);
        matrix.postConcat(matrix2);
        return matrix;
    }

    public static boolean b(@N Rect rect, @N Size size) {
        return (rect.left == 0 && rect.top == 0 && rect.width() == size.getWidth() && rect.height() == size.getHeight()) ? false : true;
    }

    public static boolean c(int i12) {
        if (i12 == 90 || i12 == 270) {
            return true;
        }
        if (i12 == 0 || i12 == 180) {
            return false;
        }
        throw new IllegalArgumentException(AK.c.g(i12, "Invalid rotation degrees: "));
    }

    public static boolean d(@N Size size, boolean z12, @N Size size2) {
        float width;
        float width2;
        if (z12) {
            width = size.getWidth() / size.getHeight();
            width2 = width;
        } else {
            width = (size.getWidth() + 1.0f) / (size.getHeight() - 1.0f);
            width2 = (size.getWidth() - 1.0f) / (size.getHeight() + 1.0f);
        }
        return width >= (((float) size2.getWidth()) - 1.0f) / (((float) size2.getHeight()) + 1.0f) && (((float) size2.getWidth()) + 1.0f) / (((float) size2.getHeight()) - 1.0f) >= width2;
    }

    @N
    public static String e(@N Rect rect) {
        Locale locale = Locale.US;
        return rect + "(" + rect.width() + "x" + rect.height() + ")";
    }

    @N
    public static Size f(@N Size size, int i12) {
        z.a("Invalid rotation degrees: " + i12, i12 % 90 == 0);
        return c(((i12 % 360) + 360) % 360) ? new Size(size.getHeight(), size.getWidth()) : size;
    }
}
