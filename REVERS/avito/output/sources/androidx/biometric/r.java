package androidx.biometric;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.RestrictTo;
import androidx.biometric.b;
import androidx.biometric.p;
import androidx.view.C23038g0;
import androidx.view.M0;
import j.N;
import j.P;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

/* compiled from: BiometricViewModel.java */
@RestrictTo
/* loaded from: classes.dex */
public class r extends M0 {

    /* renamed from: E, reason: collision with root package name */
    @P
    public Executor f22788E;

    /* renamed from: J, reason: collision with root package name */
    @P
    public p.a f22789J;

    /* renamed from: K, reason: collision with root package name */
    @P
    public p.d f22790K;

    /* renamed from: L, reason: collision with root package name */
    @P
    public p.c f22791L;

    /* renamed from: M, reason: collision with root package name */
    @P
    public androidx.biometric.b f22792M;

    /* renamed from: N, reason: collision with root package name */
    @P
    public s f22793N;

    /* renamed from: O, reason: collision with root package name */
    @P
    public DialogInterface.OnClickListener f22794O;

    /* renamed from: P, reason: collision with root package name */
    @P
    public String f22795P;

    /* renamed from: R, reason: collision with root package name */
    public boolean f22797R;

    /* renamed from: S, reason: collision with root package name */
    public boolean f22798S;

    /* renamed from: T, reason: collision with root package name */
    public boolean f22799T;

    /* renamed from: U, reason: collision with root package name */
    public boolean f22800U;

    /* renamed from: V, reason: collision with root package name */
    public boolean f22801V;

    /* renamed from: W, reason: collision with root package name */
    @P
    public C23038g0<p.b> f22802W;

    /* renamed from: X, reason: collision with root package name */
    @P
    public C23038g0<d> f22803X;

    /* renamed from: Y, reason: collision with root package name */
    @P
    public C23038g0<CharSequence> f22804Y;

    /* renamed from: Z, reason: collision with root package name */
    @P
    public C23038g0<Boolean> f22805Z;

    /* renamed from: a0, reason: collision with root package name */
    @P
    public C23038g0<Boolean> f22806a0;

    /* renamed from: c0, reason: collision with root package name */
    @P
    public C23038g0<Boolean> f22808c0;

    /* renamed from: e0, reason: collision with root package name */
    @P
    public C23038g0<Integer> f22810e0;

    /* renamed from: f0, reason: collision with root package name */
    @P
    public C23038g0<CharSequence> f22811f0;

    /* renamed from: Q, reason: collision with root package name */
    public int f22796Q = 0;

    /* renamed from: b0, reason: collision with root package name */
    public boolean f22807b0 = true;

    /* renamed from: d0, reason: collision with root package name */
    public int f22809d0 = 0;

    /* compiled from: BiometricViewModel.java */
    public static final class a extends b.c {

        /* renamed from: a, reason: collision with root package name */
        @N
        public final WeakReference<r> f22812a;

        public a(@P r rVar) {
            this.f22812a = new WeakReference<>(rVar);
        }

        @Override // androidx.biometric.b.c
        public final void a(int i12, @P CharSequence charSequence) {
            WeakReference<r> weakReference = this.f22812a;
            if (weakReference.get() == null || weakReference.get().f22799T || !weakReference.get().f22798S) {
                return;
            }
            weakReference.get().me(new d(i12, charSequence));
        }

        @Override // androidx.biometric.b.c
        public final void b() {
            WeakReference<r> weakReference = this.f22812a;
            if (weakReference.get() == null || !weakReference.get().f22798S) {
                return;
            }
            r rVar = weakReference.get();
            if (rVar.f22805Z == null) {
                rVar.f22805Z = new C23038g0<>();
            }
            r.qe(rVar.f22805Z, Boolean.TRUE);
        }

        @Override // androidx.biometric.b.c
        public final void c(@N p.b bVar) {
            WeakReference<r> weakReference = this.f22812a;
            if (weakReference.get() == null || !weakReference.get().f22798S) {
                return;
            }
            int i12 = -1;
            if (bVar.f22771b == -1) {
                int iKe = weakReference.get().ke();
                if ((iKe & 32767) != 0 && !androidx.biometric.c.b(iKe)) {
                    i12 = 2;
                }
                bVar = new p.b(bVar.f22770a, i12);
            }
            r rVar = weakReference.get();
            if (rVar.f22802W == null) {
                rVar.f22802W = new C23038g0<>();
            }
            r.qe(rVar.f22802W, bVar);
        }
    }

    /* compiled from: BiometricViewModel.java */
    public static class b implements Executor {

        /* renamed from: b, reason: collision with root package name */
        public final Handler f22813b = new Handler(Looper.getMainLooper());

        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            this.f22813b.post(runnable);
        }
    }

    /* compiled from: BiometricViewModel.java */
    public static class c implements DialogInterface.OnClickListener {

        /* renamed from: b, reason: collision with root package name */
        @N
        public final WeakReference<r> f22814b;

        public c(@P r rVar) {
            this.f22814b = new WeakReference<>(rVar);
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i12) {
            WeakReference<r> weakReference = this.f22814b;
            if (weakReference.get() != null) {
                weakReference.get().pe(true);
            }
        }
    }

    public static <T> void qe(C23038g0<T> c23038g0, T t12) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            c23038g0.setValue(t12);
        } else {
            c23038g0.postValue(t12);
        }
    }

    public final int ke() {
        p.d dVar = this.f22790K;
        if (dVar != null) {
            return androidx.biometric.c.a(dVar, this.f22791L);
        }
        return 0;
    }

    @P
    public final CharSequence le() {
        String str = this.f22795P;
        if (str != null) {
            return str;
        }
        p.d dVar = this.f22790K;
        if (dVar == null) {
            return null;
        }
        String str2 = dVar.f22779d;
        return str2 != null ? str2 : "";
    }

    public final void me(@P d dVar) {
        if (this.f22803X == null) {
            this.f22803X = new C23038g0<>();
        }
        qe(this.f22803X, dVar);
    }

    public final void ne(@N CharSequence charSequence) {
        if (this.f22811f0 == null) {
            this.f22811f0 = new C23038g0<>();
        }
        qe(this.f22811f0, charSequence);
    }

    public final void oe(int i12) {
        if (this.f22810e0 == null) {
            this.f22810e0 = new C23038g0<>();
        }
        qe(this.f22810e0, Integer.valueOf(i12));
    }

    public final void pe(boolean z12) {
        if (this.f22806a0 == null) {
            this.f22806a0 = new C23038g0<>();
        }
        qe(this.f22806a0, Boolean.valueOf(z12));
    }
}
