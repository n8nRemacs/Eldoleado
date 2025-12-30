package androidx.camera.core.impl;

import androidx.camera.core.impl.q0;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: StateObservable.java */
@j.X
/* loaded from: classes.dex */
public abstract class F0<T> implements q0<T> {

    /* renamed from: b, reason: collision with root package name */
    public final AtomicReference<Object> f23951b;

    /* renamed from: a, reason: collision with root package name */
    public final Object f23950a = new Object();

    /* renamed from: c, reason: collision with root package name */
    @j.B
    public int f23952c = 0;

    /* renamed from: d, reason: collision with root package name */
    @j.B
    public boolean f23953d = false;

    /* renamed from: e, reason: collision with root package name */
    @j.B
    public final HashMap f23954e = new HashMap();

    /* renamed from: f, reason: collision with root package name */
    @j.B
    public final CopyOnWriteArraySet<b<T>> f23955f = new CopyOnWriteArraySet<>();

    /* compiled from: StateObservable.java */
    @VY0.c
    public static abstract class a {
        @j.N
        public abstract Throwable a();
    }

    /* compiled from: StateObservable.java */
    public static final class b<T> implements Runnable {

        /* renamed from: i, reason: collision with root package name */
        public static final Object f23956i = new Object();

        /* renamed from: b, reason: collision with root package name */
        public final Executor f23957b;

        /* renamed from: c, reason: collision with root package name */
        public final q0.a<? super T> f23958c;

        /* renamed from: e, reason: collision with root package name */
        public final AtomicReference<Object> f23960e;

        /* renamed from: d, reason: collision with root package name */
        public final AtomicBoolean f23959d = new AtomicBoolean(true);

        /* renamed from: f, reason: collision with root package name */
        public Object f23961f = f23956i;

        /* renamed from: g, reason: collision with root package name */
        @j.B
        public int f23962g = -1;

        /* renamed from: h, reason: collision with root package name */
        @j.B
        public boolean f23963h = false;

        public b(@j.N AtomicReference<Object> atomicReference, @j.N Executor executor, @j.N q0.a<? super T> aVar) {
            this.f23960e = atomicReference;
            this.f23957b = executor;
            this.f23958c = aVar;
        }

        public final void a(int i12) {
            synchronized (this) {
                try {
                    if (!this.f23959d.get()) {
                        return;
                    }
                    if (i12 <= this.f23962g) {
                        return;
                    }
                    this.f23962g = i12;
                    if (this.f23963h) {
                        return;
                    }
                    this.f23963h = true;
                    try {
                        this.f23957b.execute(this);
                    } finally {
                        synchronized (this) {
                        }
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            synchronized (this) {
                try {
                    if (!this.f23959d.get()) {
                        this.f23963h = false;
                        return;
                    }
                    Object obj = this.f23960e.get();
                    int i12 = this.f23962g;
                    while (true) {
                        if (!Objects.equals(this.f23961f, obj)) {
                            this.f23961f = obj;
                            if (obj instanceof a) {
                                this.f23958c.onError(((a) obj).a());
                            } else {
                                this.f23958c.a(obj);
                            }
                        }
                        synchronized (this) {
                            try {
                                if (i12 == this.f23962g || !this.f23959d.get()) {
                                    break;
                                }
                                obj = this.f23960e.get();
                                i12 = this.f23962g;
                            } finally {
                            }
                        }
                    }
                    this.f23963h = false;
                } finally {
                }
            }
        }
    }

    public F0(@j.P Object obj) {
        this.f23951b = new AtomicReference<>(obj);
    }

    @Override // androidx.camera.core.impl.q0
    public final void b(@j.N q0.a<? super T> aVar) {
        synchronized (this.f23950a) {
            b bVar = (b) this.f23954e.remove(aVar);
            if (bVar != null) {
                bVar.f23959d.set(false);
                this.f23955f.remove(bVar);
            }
        }
    }

    @Override // androidx.camera.core.impl.q0
    @j.N
    public final com.google.common.util.concurrent.D0<T> c() {
        Object obj = this.f23951b.get();
        return obj instanceof a ? androidx.camera.core.impl.utils.futures.f.e(((a) obj).a()) : androidx.camera.core.impl.utils.futures.f.g(obj);
    }

    @Override // androidx.camera.core.impl.q0
    public final void d(@j.N Executor executor, @j.N q0.a<? super T> aVar) {
        b<T> bVar;
        synchronized (this.f23950a) {
            b bVar2 = (b) this.f23954e.remove(aVar);
            if (bVar2 != null) {
                bVar2.f23959d.set(false);
                this.f23955f.remove(bVar2);
            }
            bVar = new b<>(this.f23951b, executor, aVar);
            this.f23954e.put(aVar, bVar);
            this.f23955f.add(bVar);
        }
        bVar.a(0);
    }
}
