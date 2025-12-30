package aa1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import androidx.view.C23038g0;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import org.tensorflow.lite.InterpreterApi;
import xa1.C49907a;
import xa1.C49908b;
import xa1.C49909c;
import ya1.C50206b;
import ya1.C50207c;

/* loaded from: classes9.dex */
public final class t {

    /* renamed from: a, reason: collision with root package name */
    public final C50207c f20990a;

    /* renamed from: b, reason: collision with root package name */
    public final r f20991b;

    /* renamed from: c, reason: collision with root package name */
    public final C49907a f20992c;

    /* renamed from: d, reason: collision with root package name */
    public final C49908b f20993d;

    /* renamed from: e, reason: collision with root package name */
    public final C49909c f20994e;

    /* renamed from: f, reason: collision with root package name */
    public final InterpreterApi f20995f;

    /* renamed from: g, reason: collision with root package name */
    public final float[] f20996g;

    /* renamed from: h, reason: collision with root package name */
    public final FloatBuffer f20997h;

    public t(Context context) {
        Rect rectA = H91.b.a(context);
        this.f20990a = new C50207c(context);
        this.f20991b = new r(new wa1.c(), new wa1.d());
        this.f20992c = new C49907a(rectA.width(), rectA.height());
        this.f20993d = new C49908b(rectA.width());
        this.f20994e = new C49909c(rectA.height());
        this.f20995f = wa1.q.a(context, "t234_tflite_fixed_input.tflite");
        this.f20996g = new float[359424];
        this.f20997h = FloatBuffer.allocate(2808);
        new C23038g0(null);
    }

    public static s a(t tVar, Bitmap bitmap) {
        tVar.getClass();
        long jCurrentTimeMillis = System.currentTimeMillis();
        C50206b c50206bF = tVar.f20990a.f(bitmap, 288, 416, 64, null);
        FloatBuffer floatBuffer = tVar.f20997h;
        floatBuffer.clear();
        tVar.f20990a.getClass();
        Bitmap bitmap2 = c50206bF.f443193a;
        float[] fArr = tVar.f20996g;
        C50207c.g(bitmap2, fArr);
        tVar.f20995f.run(FloatBuffer.wrap(fArr), floatBuffer);
        q qVarA = tVar.f20991b.a(c50206bF.f443197e, c50206bF.f443198f, c50206bF.f443199g, c50206bF.f443200h, floatBuffer);
        int iCurrentTimeMillis = (int) (1000 / (System.currentTimeMillis() - jCurrentTimeMillis));
        ArrayList arrayList = qVarA.f20977b;
        return new s(arrayList, qVarA.f20976a, tVar.f20992c.a(arrayList), tVar.f20994e.b(arrayList, qVarA.f20978c, qVarA.f20979d, iCurrentTimeMillis), tVar.f20993d.a(arrayList));
    }
}
