package androidx.appcompat.view;

import android.view.View;
import android.view.animation.BaseInterpolator;
import androidx.annotation.RestrictTo;
import androidx.core.view.A0;
import androidx.core.view.C0;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ViewPropertyAnimatorCompatSet.java */
@RestrictTo
/* loaded from: classes.dex */
public class h {

    /* renamed from: c, reason: collision with root package name */
    public BaseInterpolator f21716c;

    /* renamed from: d, reason: collision with root package name */
    public C0 f21717d;

    /* renamed from: e, reason: collision with root package name */
    public boolean f21718e;

    /* renamed from: b, reason: collision with root package name */
    public long f21715b = -1;

    /* renamed from: f, reason: collision with root package name */
    public final C0 f21719f = new a();

    /* renamed from: a, reason: collision with root package name */
    public final ArrayList<A0> f21714a = new ArrayList<>();

    /* compiled from: ViewPropertyAnimatorCompatSet.java */
    public class a extends C0 {

        /* renamed from: a, reason: collision with root package name */
        public boolean f21720a = false;

        /* renamed from: b, reason: collision with root package name */
        public int f21721b = 0;

        public a() {
        }

        @Override // androidx.core.view.C0, androidx.core.view.B0
        public final void a(View view) {
            int i12 = this.f21721b + 1;
            this.f21721b = i12;
            h hVar = h.this;
            if (i12 == hVar.f21714a.size()) {
                C0 c02 = hVar.f21717d;
                if (c02 != null) {
                    c02.a(null);
                }
                this.f21721b = 0;
                this.f21720a = false;
                hVar.f21718e = false;
            }
        }

        @Override // androidx.core.view.C0, androidx.core.view.B0
        public final void b(View view) {
            if (this.f21720a) {
                return;
            }
            this.f21720a = true;
            C0 c02 = h.this.f21717d;
            if (c02 != null) {
                c02.b(null);
            }
        }
    }

    public final void a() {
        if (this.f21718e) {
            Iterator<A0> it = this.f21714a.iterator();
            while (it.hasNext()) {
                it.next().b();
            }
            this.f21718e = false;
        }
    }

    public final void b() {
        if (this.f21718e) {
            return;
        }
        Iterator<A0> it = this.f21714a.iterator();
        while (it.hasNext()) {
            A0 next = it.next();
            long j12 = this.f21715b;
            if (j12 >= 0) {
                next.c(j12);
            }
            BaseInterpolator baseInterpolator = this.f21716c;
            if (baseInterpolator != null) {
                next.d(baseInterpolator);
            }
            if (this.f21717d != null) {
                next.e(this.f21719f);
            }
            next.f();
        }
        this.f21718e = true;
    }
}
