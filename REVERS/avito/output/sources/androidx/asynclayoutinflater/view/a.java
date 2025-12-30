package androidx.asynclayoutinflater.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.util.y;
import j.I;
import j.N;
import j.P;
import j.j0;
import java.util.concurrent.ArrayBlockingQueue;

/* compiled from: AsyncLayoutInflater.java */
/* loaded from: classes.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    public final LayoutInflater f22714a;

    /* renamed from: b, reason: collision with root package name */
    public final Handler f22715b;

    /* renamed from: c, reason: collision with root package name */
    public final d f22716c;

    /* compiled from: AsyncLayoutInflater.java */
    /* renamed from: androidx.asynclayoutinflater.view.a$a, reason: collision with other inner class name */
    public class C1521a implements Handler.Callback {
        public C1521a() {
        }

        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            c cVar = (c) message.obj;
            View view = cVar.f22722d;
            a aVar = a.this;
            if (view == null) {
                cVar.f22722d = aVar.f22714a.inflate(cVar.f22721c, cVar.f22720b, false);
            }
            cVar.f22723e.g(cVar.f22722d, cVar.f22720b);
            d dVar = aVar.f22716c;
            dVar.getClass();
            cVar.f22723e = null;
            cVar.f22719a = null;
            cVar.f22720b = null;
            cVar.f22721c = 0;
            cVar.f22722d = null;
            dVar.f22726c.a(cVar);
            return true;
        }
    }

    /* compiled from: AsyncLayoutInflater.java */
    public static class b extends LayoutInflater {

        /* renamed from: a, reason: collision with root package name */
        public static final String[] f22718a = {"android.widget.", "android.webkit.", "android.app."};

        public b(Context context) {
            super(context);
        }

        @Override // android.view.LayoutInflater
        public final LayoutInflater cloneInContext(Context context) {
            return new b(context);
        }

        @Override // android.view.LayoutInflater
        public final View onCreateView(String str, AttributeSet attributeSet) throws InflateException, ClassNotFoundException {
            View viewCreateView;
            String[] strArr = f22718a;
            for (int i12 = 0; i12 < 3; i12++) {
                try {
                    viewCreateView = createView(str, strArr[i12], attributeSet);
                } catch (ClassNotFoundException unused) {
                }
                if (viewCreateView != null) {
                    return viewCreateView;
                }
            }
            return super.onCreateView(str, attributeSet);
        }
    }

    /* compiled from: AsyncLayoutInflater.java */
    public static class c {

        /* renamed from: a, reason: collision with root package name */
        public a f22719a;

        /* renamed from: b, reason: collision with root package name */
        public ViewGroup f22720b;

        /* renamed from: c, reason: collision with root package name */
        public int f22721c;

        /* renamed from: d, reason: collision with root package name */
        public View f22722d;

        /* renamed from: e, reason: collision with root package name */
        public e f22723e;
    }

    /* compiled from: AsyncLayoutInflater.java */
    public static class d extends Thread {

        /* renamed from: d, reason: collision with root package name */
        public static final d f22724d;

        /* renamed from: b, reason: collision with root package name */
        public final ArrayBlockingQueue<c> f22725b = new ArrayBlockingQueue<>(10);

        /* renamed from: c, reason: collision with root package name */
        public final y.c<c> f22726c = new y.c<>(10);

        static {
            d dVar = new d();
            f22724d = dVar;
            dVar.start();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() throws InterruptedException {
            while (true) {
                try {
                    c cVarTake = this.f22725b.take();
                    try {
                        cVarTake.f22722d = cVarTake.f22719a.f22714a.inflate(cVarTake.f22721c, cVarTake.f22720b, false);
                    } catch (RuntimeException unused) {
                    }
                    Message.obtain(cVarTake.f22719a.f22715b, 0, cVarTake).sendToTarget();
                } catch (InterruptedException unused2) {
                }
            }
        }
    }

    /* compiled from: AsyncLayoutInflater.java */
    public interface e {
        void g(@N View view, @P ViewGroup viewGroup);
    }

    public a(@N Context context) {
        C1521a c1521a = new C1521a();
        this.f22714a = new b(context);
        this.f22715b = new Handler(c1521a);
        this.f22716c = d.f22724d;
    }

    @j0
    public final void a(@I int i12, @P ViewGroup viewGroup, @N e eVar) throws InterruptedException {
        d dVar = this.f22716c;
        c cVarB = dVar.f22726c.b();
        if (cVarB == null) {
            cVarB = new c();
        }
        cVarB.f22719a = this;
        cVarB.f22721c = i12;
        cVarB.f22720b = viewGroup;
        cVarB.f22723e = eVar;
        try {
            dVar.f22725b.put(cVarB);
        } catch (InterruptedException e12) {
            throw new RuntimeException("Failed to enqueue async inflate request", e12);
        }
    }
}
