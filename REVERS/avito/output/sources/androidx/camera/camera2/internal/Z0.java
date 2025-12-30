package androidx.camera.camera2.internal;

import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.os.Looper;
import android.util.Range;
import androidx.camera.camera2.impl.b;
import androidx.camera.camera2.internal.C20026p;
import androidx.camera.core.C20140q0;
import androidx.camera.core.CameraControl;
import androidx.concurrent.futures.b;
import androidx.view.C23038g0;
import java.util.concurrent.Executor;

/* compiled from: ZoomControl.java */
@j.X
/* loaded from: classes.dex */
final class Z0 {

    /* renamed from: a, reason: collision with root package name */
    public final C20026p f23094a;

    /* renamed from: b, reason: collision with root package name */
    public final Executor f23095b;

    /* renamed from: c, reason: collision with root package name */
    @j.B
    public final a1 f23096c;

    /* renamed from: d, reason: collision with root package name */
    public final C23038g0<androidx.camera.core.Q0> f23097d;

    /* renamed from: e, reason: collision with root package name */
    @j.N
    public final b f23098e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f23099f = false;

    /* renamed from: g, reason: collision with root package name */
    public final C20026p.c f23100g;

    /* compiled from: ZoomControl.java */
    public class a implements C20026p.c {
        public a() {
        }

        @Override // androidx.camera.camera2.internal.C20026p.c
        public final boolean a(@j.N TotalCaptureResult totalCaptureResult) {
            Z0.this.f23098e.a(totalCaptureResult);
            return false;
        }
    }

    /* compiled from: ZoomControl.java */
    public interface b {
        void a(@j.N TotalCaptureResult totalCaptureResult);

        float getMaxZoom();

        float getMinZoom();

        void l0();

        void m0(float f12, @j.N b.a<Void> aVar);

        @j.N
        Rect n0();

        void o0(@j.N b.a aVar);
    }

    public Z0(@j.N C20026p c20026p, @j.N androidx.camera.camera2.internal.compat.n nVar, @j.N Executor executor) {
        a aVar = new a();
        this.f23094a = c20026p;
        this.f23095b = executor;
        b bVarA = a(nVar);
        this.f23098e = bVarA;
        a1 a1Var = new a1(bVarA.getMaxZoom(), bVarA.getMinZoom());
        this.f23096c = a1Var;
        a1Var.f(1.0f);
        this.f23097d = new C23038g0<>(androidx.camera.core.internal.e.f(a1Var));
        c20026p.m(aVar);
    }

    public static b a(@j.N androidx.camera.camera2.internal.compat.n nVar) {
        Range range;
        if (Build.VERSION.SDK_INT >= 30) {
            try {
                range = (Range) nVar.a(CameraCharacteristics.CONTROL_ZOOM_RATIO_RANGE);
            } catch (AssertionError unused) {
                C20140q0.d(5, "ZoomControl");
                range = null;
            }
            if (range != null) {
                return new C19981b(nVar);
            }
        }
        return new C20011h0(nVar);
    }

    public final void b(@j.N b.a<Void> aVar, @j.N androidx.camera.core.Q0 q02) {
        androidx.camera.core.Q0 q0F;
        if (this.f23099f) {
            this.f23098e.m0(q02.b(), aVar);
            this.f23094a.w();
            return;
        }
        synchronized (this.f23096c) {
            this.f23096c.f(1.0f);
            q0F = androidx.camera.core.internal.e.f(this.f23096c);
        }
        c(q0F);
        aVar.d(new CameraControl.OperationCanceledException("Camera is not active."));
    }

    public final void c(androidx.camera.core.Q0 q02) {
        Looper looperMyLooper = Looper.myLooper();
        Looper mainLooper = Looper.getMainLooper();
        C23038g0<androidx.camera.core.Q0> c23038g0 = this.f23097d;
        if (looperMyLooper == mainLooper) {
            c23038g0.setValue(q02);
        } else {
            c23038g0.postValue(q02);
        }
    }
}
