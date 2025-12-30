package androidx.appcompat.widget;

import android.content.ComponentName;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: ActivityChooserModel.java */
/* renamed from: androidx.appcompat.widget.d, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19943d extends DataSetObservable {

    /* renamed from: h, reason: collision with root package name */
    public static final Object f22490h = new Object();

    /* renamed from: i, reason: collision with root package name */
    public static final HashMap f22491i = new HashMap();

    /* renamed from: a, reason: collision with root package name */
    public final Object f22492a = new Object();

    /* renamed from: b, reason: collision with root package name */
    public final ArrayList f22493b = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    public final ArrayList f22494c = new ArrayList();

    /* renamed from: d, reason: collision with root package name */
    public final c f22495d;

    /* renamed from: e, reason: collision with root package name */
    public final int f22496e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f22497f;

    /* renamed from: g, reason: collision with root package name */
    public boolean f22498g;

    /* compiled from: ActivityChooserModel.java */
    /* renamed from: androidx.appcompat.widget.d$a */
    public interface a {
    }

    /* compiled from: ActivityChooserModel.java */
    /* renamed from: androidx.appcompat.widget.d$b */
    public static final class b implements Comparable<b> {
        @Override // java.lang.Comparable
        public final int compareTo(b bVar) {
            bVar.getClass();
            return Float.floatToIntBits(0.0f) - Float.floatToIntBits(0.0f);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || b.class != obj.getClass()) {
                return false;
            }
            return Float.floatToIntBits(0.0f) == Float.floatToIntBits(0.0f);
        }

        public final int hashCode() {
            return Float.floatToIntBits(0.0f) + 31;
        }

        public final String toString() {
            throw null;
        }
    }

    /* compiled from: ActivityChooserModel.java */
    /* renamed from: androidx.appcompat.widget.d$c */
    public interface c {
    }

    /* compiled from: ActivityChooserModel.java */
    /* renamed from: androidx.appcompat.widget.d$d, reason: collision with other inner class name */
    public static final class C1519d implements c {
        public C1519d() {
            new HashMap();
        }
    }

    /* compiled from: ActivityChooserModel.java */
    /* renamed from: androidx.appcompat.widget.d$e */
    public static final class e {

        /* renamed from: a, reason: collision with root package name */
        public final ComponentName f22499a;

        /* renamed from: b, reason: collision with root package name */
        public final long f22500b;

        /* renamed from: c, reason: collision with root package name */
        public final float f22501c;

        public e(ComponentName componentName, long j12, float f12) {
            this.f22499a = componentName;
            this.f22500b = j12;
            this.f22501c = f12;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || e.class != obj.getClass()) {
                return false;
            }
            e eVar = (e) obj;
            ComponentName componentName = eVar.f22499a;
            ComponentName componentName2 = this.f22499a;
            if (componentName2 == null) {
                if (componentName != null) {
                    return false;
                }
            } else if (!componentName2.equals(componentName)) {
                return false;
            }
            return this.f22500b == eVar.f22500b && Float.floatToIntBits(this.f22501c) == Float.floatToIntBits(eVar.f22501c);
        }

        public final int hashCode() {
            ComponentName componentName = this.f22499a;
            int iHashCode = componentName == null ? 0 : componentName.hashCode();
            long j12 = this.f22500b;
            return Float.floatToIntBits(this.f22501c) + ((((iHashCode + 31) * 31) + ((int) (j12 ^ (j12 >>> 32)))) * 31);
        }

        public final String toString() {
            return "[; activity:" + this.f22499a + "; time:" + this.f22500b + "; weight:" + new BigDecimal(this.f22501c) + "]";
        }
    }

    /* compiled from: ActivityChooserModel.java */
    /* renamed from: androidx.appcompat.widget.d$f */
    public interface f {
    }

    /* compiled from: ActivityChooserModel.java */
    /* renamed from: androidx.appcompat.widget.d$g */
    public final class g extends AsyncTask<Object, Void, Void> {
        @Override // android.os.AsyncTask
        public final Void doInBackground(Object[] objArr) {
            throw null;
        }
    }

    public C19943d() {
        new C1519d();
        this.f22496e = 50;
        this.f22497f = true;
        this.f22498g = true;
        throw null;
    }

    public static C19943d b() {
        C19943d c19943d;
        synchronized (f22490h) {
            try {
                c19943d = (C19943d) f22491i.get(null);
                if (c19943d == null) {
                    new C19943d();
                    throw null;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return c19943d;
    }

    public final void a() {
        if (this.f22497f && this.f22498g && !TextUtils.isEmpty(null)) {
            this.f22497f = false;
            throw null;
        }
        ArrayList arrayList = this.f22494c;
        int size = arrayList.size() - this.f22496e;
        if (size <= 0) {
            return;
        }
        this.f22498g = true;
        for (int i12 = 0; i12 < size; i12++) {
        }
    }

    public final ResolveInfo c() {
        synchronized (this.f22492a) {
            try {
                a();
                if (!this.f22493b.isEmpty()) {
                    throw null;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return null;
    }
}
