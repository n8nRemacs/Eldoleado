package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.os.Build;
import androidx.annotation.RestrictTo;
import androidx.camera.camera2.internal.compat.params.h;
import androidx.camera.camera2.internal.compat.params.i;
import j.N;
import j.P;
import j.X;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: SessionConfigurationCompat.java */
@X
/* loaded from: classes.dex */
public final class l {

    /* renamed from: a, reason: collision with root package name */
    public final c f23200a;

    /* compiled from: SessionConfigurationCompat.java */
    @X
    public static final class a implements c {

        /* renamed from: a, reason: collision with root package name */
        public final SessionConfiguration f23201a;

        /* renamed from: b, reason: collision with root package name */
        public final List<f> f23202b;

        public a(@N ArrayList arrayList, @N Executor executor, @N CameraCaptureSession.StateCallback stateCallback) {
            f fVar;
            SessionConfiguration sessionConfiguration = new SessionConfiguration(0, l.h(arrayList), executor, stateCallback);
            this.f23201a = sessionConfiguration;
            List<OutputConfiguration> outputConfigurations = sessionConfiguration.getOutputConfigurations();
            ArrayList arrayList2 = new ArrayList(outputConfigurations.size());
            for (OutputConfiguration outputConfiguration : outputConfigurations) {
                if (outputConfiguration == null) {
                    fVar = null;
                } else {
                    int i12 = Build.VERSION.SDK_INT;
                    fVar = new f(i12 >= 33 ? new j(outputConfiguration) : i12 >= 28 ? new i(new i.a(outputConfiguration)) : new h(new h.a(outputConfiguration)));
                }
                arrayList2.add(fVar);
            }
            this.f23202b = Collections.unmodifiableList(arrayList2);
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        public final e a() {
            return e.b(this.f23201a.getInputConfiguration());
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        @N
        public final List<f> b() {
            return this.f23202b;
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        @N
        public final CameraCaptureSession.StateCallback c() {
            return this.f23201a.getStateCallback();
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        public final void d(@N e eVar) {
            this.f23201a.setInputConfiguration(eVar.f23186a.f23187a);
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        @N
        public final Executor e() {
            return this.f23201a.getExecutor();
        }

        public final boolean equals(@P Object obj) {
            if (obj instanceof a) {
                return Objects.equals(this.f23201a, ((a) obj).f23201a);
            }
            return false;
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        @P
        public final Object f() {
            return this.f23201a;
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        public final int g() {
            return this.f23201a.getSessionType();
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        public final void h(@N CaptureRequest captureRequest) {
            this.f23201a.setSessionParameters(captureRequest);
        }

        public final int hashCode() {
            return this.f23201a.hashCode();
        }
    }

    /* compiled from: SessionConfigurationCompat.java */
    public interface c {
        @P
        e a();

        @N
        List<f> b();

        @N
        CameraCaptureSession.StateCallback c();

        void d(@N e eVar);

        @N
        Executor e();

        @P
        Object f();

        int g();

        void h(@N CaptureRequest captureRequest);
    }

    /* compiled from: SessionConfigurationCompat.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface d {
    }

    public l(@N ArrayList arrayList, @N Executor executor, @N CameraCaptureSession.StateCallback stateCallback) {
        if (Build.VERSION.SDK_INT < 28) {
            this.f23200a = new b(arrayList, executor, stateCallback);
        } else {
            this.f23200a = new a(arrayList, executor, stateCallback);
        }
    }

    @N
    @RestrictTo
    @X
    public static ArrayList h(@N List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add((OutputConfiguration) ((f) it.next()).f23188a.g());
        }
        return arrayList;
    }

    @N
    public final Executor a() {
        return this.f23200a.e();
    }

    @P
    public final e b() {
        return this.f23200a.a();
    }

    @N
    public final List<f> c() {
        return this.f23200a.b();
    }

    public final int d() {
        return this.f23200a.g();
    }

    @N
    public final CameraCaptureSession.StateCallback e() {
        return this.f23200a.c();
    }

    public final boolean equals(@P Object obj) {
        if (!(obj instanceof l)) {
            return false;
        }
        return this.f23200a.equals(((l) obj).f23200a);
    }

    public final void f(@N e eVar) {
        this.f23200a.d(eVar);
    }

    public final void g(@N CaptureRequest captureRequest) {
        this.f23200a.h(captureRequest);
    }

    public final int hashCode() {
        return this.f23200a.hashCode();
    }

    @P
    public final Object i() {
        return this.f23200a.f();
    }

    /* compiled from: SessionConfigurationCompat.java */
    @X
    public static final class b implements c {

        /* renamed from: a, reason: collision with root package name */
        public final List<f> f23203a;

        /* renamed from: b, reason: collision with root package name */
        public final CameraCaptureSession.StateCallback f23204b;

        /* renamed from: c, reason: collision with root package name */
        public final Executor f23205c;

        /* renamed from: d, reason: collision with root package name */
        public e f23206d = null;

        public b(@N ArrayList arrayList, @N Executor executor, @N CameraCaptureSession.StateCallback stateCallback) {
            this.f23203a = Collections.unmodifiableList(new ArrayList(arrayList));
            this.f23204b = stateCallback;
            this.f23205c = executor;
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        @P
        public final e a() {
            return this.f23206d;
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        @N
        public final List<f> b() {
            return this.f23203a;
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        @N
        public final CameraCaptureSession.StateCallback c() {
            return this.f23204b;
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        public final void d(@N e eVar) {
            this.f23206d = eVar;
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        @N
        public final Executor e() {
            return this.f23205c;
        }

        public final boolean equals(@P Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof b) {
                b bVar = (b) obj;
                if (Objects.equals(this.f23206d, bVar.f23206d)) {
                    List<f> list = this.f23203a;
                    int size = list.size();
                    List<f> list2 = bVar.f23203a;
                    if (size == list2.size()) {
                        for (int i12 = 0; i12 < list.size(); i12++) {
                            if (!list.get(i12).equals(list2.get(i12))) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
            return false;
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        @P
        public final Object f() {
            return null;
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        public final int g() {
            return 0;
        }

        public final int hashCode() {
            int iHashCode = this.f23203a.hashCode() ^ 31;
            int i12 = (iHashCode << 5) - iHashCode;
            e eVar = this.f23206d;
            int iHashCode2 = (eVar == null ? 0 : eVar.f23186a.hashCode()) ^ i12;
            return (iHashCode2 << 5) - iHashCode2;
        }

        @Override // androidx.camera.camera2.internal.compat.params.l.c
        public final void h(@N CaptureRequest captureRequest) {
        }
    }
}
