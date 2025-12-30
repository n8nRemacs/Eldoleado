package androidx.camera.core.impl.utils;

import android.opengl.Matrix;
import j.N;

/* compiled from: MatrixExt.java */
/* loaded from: classes.dex */
public final class p {
    public static void a(@N float[] fArr, float f12) {
        Matrix.translateM(fArr, 0, 0.5f, 0.5f, 0.0f);
        Matrix.rotateM(fArr, 0, f12, 0.0f, 0.0f, 1.0f);
        Matrix.translateM(fArr, 0, -0.5f, -0.5f, 0.0f);
    }

    public static void b(@N float[] fArr) {
        Matrix.translateM(fArr, 0, 0.0f, 0.5f, 0.0f);
        Matrix.scaleM(fArr, 0, 1.0f, -1.0f, 1.0f);
        Matrix.translateM(fArr, 0, -0.0f, -0.5f, 0.0f);
    }
}
