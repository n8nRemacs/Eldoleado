package androidx.camera.core.internal;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Rational;
import androidx.camera.core.N0;
import androidx.camera.core.impl.G0;
import androidx.camera.core.internal.utils.ImageUtil;
import androidx.core.util.z;
import j.F;
import j.N;
import j.X;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ViewPorts.java */
@X
/* loaded from: classes.dex */
public class l {
    @N
    public static HashMap a(@N Rect rect, boolean z12, @N Rational rational, @F int i12, int i13, int i14, @N HashMap map) {
        RectF rectF;
        boolean z13 = false;
        z.a("Cannot compute viewport crop rects zero sized sensor rect.", rect.width() > 0 && rect.height() > 0);
        RectF rectF2 = new RectF(rect);
        HashMap map2 = new HashMap();
        RectF rectF3 = new RectF(rect);
        for (Map.Entry entry : map.entrySet()) {
            Matrix matrix = new Matrix();
            RectF rectF4 = new RectF(0.0f, 0.0f, ((G0) entry.getValue()).e().getWidth(), ((G0) entry.getValue()).e().getHeight());
            matrix.setRectToRect(rectF4, rectF2, Matrix.ScaleToFit.CENTER);
            map2.put((N0) entry.getKey(), matrix);
            RectF rectF5 = new RectF();
            matrix.mapRect(rectF5, rectF4);
            rectF3.intersect(rectF5);
        }
        Rational rationalA = ImageUtil.a(i12, rational);
        if (i13 != 3) {
            Matrix matrix2 = new Matrix();
            RectF rectF6 = new RectF(0.0f, 0.0f, rationalA.getNumerator(), rationalA.getDenominator());
            if (i13 == 0) {
                matrix2.setRectToRect(rectF6, rectF3, Matrix.ScaleToFit.START);
            } else if (i13 == 1) {
                matrix2.setRectToRect(rectF6, rectF3, Matrix.ScaleToFit.CENTER);
            } else {
                if (i13 != 2) {
                    throw new IllegalStateException(AK.c.g(i13, "Unexpected scale type: "));
                }
                matrix2.setRectToRect(rectF6, rectF3, Matrix.ScaleToFit.END);
            }
            RectF rectF7 = new RectF();
            matrix2.mapRect(rectF7, rectF6);
            boolean z14 = (i14 == 1) ^ z12;
            boolean z15 = i12 == 0 && !z14;
            boolean z16 = i12 == 90 && z14;
            if (z15 || z16) {
                rectF3 = rectF7;
            } else {
                boolean z17 = i12 == 0 && z14;
                boolean z18 = i12 == 270 && !z14;
                if (z17 || z18) {
                    float fCenterX = rectF3.centerX();
                    float f12 = fCenterX + fCenterX;
                    rectF = new RectF(f12 - rectF7.right, rectF7.top, f12 - rectF7.left, rectF7.bottom);
                } else {
                    boolean z19 = i12 == 90 && !z14;
                    boolean z22 = i12 == 180 && z14;
                    if (z19 || z22) {
                        float fCenterY = rectF3.centerY();
                        float f13 = fCenterY + fCenterY;
                        rectF = new RectF(rectF7.left, f13 - rectF7.bottom, rectF7.right, f13 - rectF7.top);
                    } else {
                        boolean z23 = i12 == 180 && !z14;
                        if (i12 == 270 && z14) {
                            z13 = true;
                        }
                        if (!z23 && !z13) {
                            throw new IllegalArgumentException("Invalid argument: mirrored " + z14 + " rotation " + i12);
                        }
                        float fCenterY2 = rectF3.centerY();
                        float f14 = fCenterY2 + fCenterY2;
                        RectF rectF8 = new RectF(rectF7.left, f14 - rectF7.bottom, rectF7.right, f14 - rectF7.top);
                        float fCenterX2 = rectF3.centerX();
                        float f15 = fCenterX2 + fCenterX2;
                        rectF3 = new RectF(f15 - rectF8.right, rectF8.top, f15 - rectF8.left, rectF8.bottom);
                    }
                }
                rectF3 = rectF;
            }
        }
        HashMap map3 = new HashMap();
        RectF rectF9 = new RectF();
        Matrix matrix3 = new Matrix();
        for (Map.Entry entry2 : map2.entrySet()) {
            ((Matrix) entry2.getValue()).invert(matrix3);
            matrix3.mapRect(rectF9, rectF3);
            Rect rect2 = new Rect();
            rectF9.round(rect2);
            map3.put((N0) entry2.getKey(), rect2);
        }
        return map3;
    }
}
