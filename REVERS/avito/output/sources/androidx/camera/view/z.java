package androidx.camera.view;

import android.content.Context;
import android.view.OrientationEventListener;
import j.N;
import j.X;
import j.k0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: RotationProvider.java */
@X
/* loaded from: classes.dex */
public final class z {

    /* renamed from: b, reason: collision with root package name */
    @j.B
    @N
    @k0
    public final OrientationEventListener f25483b;

    /* renamed from: a, reason: collision with root package name */
    public final Object f25482a = new Object();

    /* renamed from: c, reason: collision with root package name */
    @j.B
    @N
    public final HashMap f25484c = new HashMap();

    /* compiled from: RotationProvider.java */
    public class a extends OrientationEventListener {

        /* renamed from: a, reason: collision with root package name */
        public int f25485a;

        public a(Context context) {
            super(context);
            this.f25485a = -1;
        }

        @Override // android.view.OrientationEventListener
        public final void onOrientationChanged(int i12) {
            ArrayList arrayList;
            if (i12 == -1) {
                return;
            }
            int i13 = (i12 >= 315 || i12 < 45) ? 0 : i12 >= 225 ? 1 : i12 >= 135 ? 2 : 3;
            if (this.f25485a != i13) {
                this.f25485a = i13;
                synchronized (z.this.f25482a) {
                    arrayList = new ArrayList(z.this.f25484c.values());
                }
                if (arrayList.isEmpty()) {
                    return;
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    c cVar = (c) it.next();
                    cVar.getClass();
                    cVar.f25488b.execute(new A(i13, 0, cVar));
                }
            }
        }
    }

    /* compiled from: RotationProvider.java */
    public interface b {
    }

    /* compiled from: RotationProvider.java */
    public static class c {

        /* renamed from: a, reason: collision with root package name */
        public final m f25487a;

        /* renamed from: b, reason: collision with root package name */
        public final Executor f25488b;

        /* renamed from: c, reason: collision with root package name */
        public final AtomicBoolean f25489c = new AtomicBoolean(true);

        public c(m mVar, Executor executor) {
            this.f25487a = mVar;
            this.f25488b = executor;
        }
    }

    public z(@N Context context) {
        this.f25483b = new a(context);
    }
}
