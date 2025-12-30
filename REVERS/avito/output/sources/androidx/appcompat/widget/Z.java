package androidx.appcompat.widget;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import androidx.annotation.RestrictTo;

/* compiled from: ForwardingListener.java */
@RestrictTo
/* loaded from: classes.dex */
public abstract class Z implements View.OnTouchListener, View.OnAttachStateChangeListener {

    /* renamed from: b, reason: collision with root package name */
    public final float f22457b;

    /* renamed from: c, reason: collision with root package name */
    public final int f22458c;

    /* renamed from: d, reason: collision with root package name */
    public final int f22459d;

    /* renamed from: e, reason: collision with root package name */
    public final View f22460e;

    /* renamed from: f, reason: collision with root package name */
    public Runnable f22461f;

    /* renamed from: g, reason: collision with root package name */
    public Runnable f22462g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f22463h;

    /* renamed from: i, reason: collision with root package name */
    public int f22464i;

    /* renamed from: j, reason: collision with root package name */
    public final int[] f22465j = new int[2];

    /* compiled from: ForwardingListener.java */
    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            ViewParent parent = Z.this.f22460e.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    /* compiled from: ForwardingListener.java */
    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            Z z12 = Z.this;
            z12.b();
            View view = z12.f22460e;
            if (view.isEnabled() && !view.isLongClickable() && z12.d()) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                long jUptimeMillis = SystemClock.uptimeMillis();
                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                view.onTouchEvent(motionEventObtain);
                motionEventObtain.recycle();
                z12.f22463h = true;
            }
        }
    }

    public Z(View view) {
        this.f22460e = view;
        view.setLongClickable(true);
        view.addOnAttachStateChangeListener(this);
        this.f22457b = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        int tapTimeout = ViewConfiguration.getTapTimeout();
        this.f22458c = tapTimeout;
        this.f22459d = (ViewConfiguration.getLongPressTimeout() + tapTimeout) / 2;
    }

    public final void b() {
        Runnable runnable = this.f22462g;
        View view = this.f22460e;
        if (runnable != null) {
            view.removeCallbacks(runnable);
        }
        Runnable runnable2 = this.f22461f;
        if (runnable2 != null) {
            view.removeCallbacks(runnable2);
        }
    }

    public abstract androidx.appcompat.view.menu.r c();

    public boolean d() {
        androidx.appcompat.view.menu.r rVarC = c();
        if (rVarC == null || rVarC.a()) {
            return true;
        }
        rVarC.show();
        return true;
    }

    public boolean e() {
        androidx.appcompat.view.menu.r rVarC = c();
        if (rVarC == null || !rVarC.a()) {
            return true;
        }
        rVarC.dismiss();
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0100  */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onTouch(android.view.View r13, android.view.MotionEvent r14) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instructions count: 284
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.Z.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        this.f22463h = false;
        this.f22464i = -1;
        Runnable runnable = this.f22461f;
        if (runnable != null) {
            this.f22460e.removeCallbacks(runnable);
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
    }
}
