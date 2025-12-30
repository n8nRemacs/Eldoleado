package androidx.camera.camera2.internal;

import androidx.camera.core.CameraState;
import androidx.camera.core.impl.CameraInternal;
import androidx.view.C23038g0;

/* compiled from: CameraStateMachine.java */
@j.X
/* loaded from: classes.dex */
class W {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final androidx.camera.core.impl.E f23075a;

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public final C23038g0<CameraState> f23076b;

    /* compiled from: CameraStateMachine.java */
    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f23077a;

        static {
            int[] iArr = new int[CameraInternal.State.values().length];
            f23077a = iArr;
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f23077a[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f23077a[2] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f23077a[3] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f23077a[4] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f23077a[6] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f23077a[5] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f23077a[7] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public W(@j.N androidx.camera.core.impl.E e12) {
        this.f23075a = e12;
        C23038g0<CameraState> c23038g0 = new C23038g0<>();
        this.f23076b = c23038g0;
        c23038g0.postValue(CameraState.a(CameraState.Type.f23555f));
    }
}
