package androidx.camera.core.impl.utils.executor;

import j.B;
import j.X;
import java.util.ArrayDeque;
import java.util.concurrent.Executor;

/* compiled from: SequentialExecutor.java */
@X
/* loaded from: classes.dex */
final class i implements Executor {

    /* renamed from: c, reason: collision with root package name */
    public final Executor f24230c;

    /* renamed from: b, reason: collision with root package name */
    @B
    public final ArrayDeque f24229b = new ArrayDeque();

    /* renamed from: d, reason: collision with root package name */
    public final b f24231d = new b();

    /* renamed from: e, reason: collision with root package name */
    @B
    public c f24232e = c.f24236b;

    /* renamed from: f, reason: collision with root package name */
    @B
    public long f24233f = 0;

    /* compiled from: SequentialExecutor.java */
    public class a implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Runnable f24234b;

        public a(Runnable runnable) {
            this.f24234b = runnable;
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.f24234b.run();
        }
    }

    /* compiled from: SequentialExecutor.java */
    public final class b implements Runnable {
        public b() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x0044, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x004a, code lost:
        
            r1 = r1 | java.lang.Thread.interrupted();
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x004b, code lost:
        
            r3.run();
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0051, code lost:
        
            r3.toString();
            androidx.camera.core.C20140q0.c("SequentialExecutor");
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:?, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final void a() {
            /*
                r9 = this;
                r0 = 0
                r1 = r0
            L2:
                androidx.camera.core.impl.utils.executor.i r2 = androidx.camera.core.impl.utils.executor.i.this     // Catch: java.lang.Throwable -> L4f
                java.util.ArrayDeque r2 = r2.f24229b     // Catch: java.lang.Throwable -> L4f
                monitor-enter(r2)     // Catch: java.lang.Throwable -> L4f
                if (r0 != 0) goto L28
                androidx.camera.core.impl.utils.executor.i r0 = androidx.camera.core.impl.utils.executor.i.this     // Catch: java.lang.Throwable -> L1c
                androidx.camera.core.impl.utils.executor.i$c r3 = r0.f24232e     // Catch: java.lang.Throwable -> L1c
                androidx.camera.core.impl.utils.executor.i$c r4 = androidx.camera.core.impl.utils.executor.i.c.f24239e     // Catch: java.lang.Throwable -> L1c
                if (r3 != r4) goto L1e
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L1c
                if (r1 == 0) goto L1b
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                r0.interrupt()
            L1b:
                return
            L1c:
                r0 = move-exception
                goto L5a
            L1e:
                long r5 = r0.f24233f     // Catch: java.lang.Throwable -> L1c
                r7 = 1
                long r5 = r5 + r7
                r0.f24233f = r5     // Catch: java.lang.Throwable -> L1c
                r0.f24232e = r4     // Catch: java.lang.Throwable -> L1c
                r0 = 1
            L28:
                androidx.camera.core.impl.utils.executor.i r3 = androidx.camera.core.impl.utils.executor.i.this     // Catch: java.lang.Throwable -> L1c
                java.util.ArrayDeque r3 = r3.f24229b     // Catch: java.lang.Throwable -> L1c
                java.lang.Object r3 = r3.poll()     // Catch: java.lang.Throwable -> L1c
                java.lang.Runnable r3 = (java.lang.Runnable) r3     // Catch: java.lang.Throwable -> L1c
                if (r3 != 0) goto L45
                androidx.camera.core.impl.utils.executor.i r0 = androidx.camera.core.impl.utils.executor.i.this     // Catch: java.lang.Throwable -> L1c
                androidx.camera.core.impl.utils.executor.i$c r3 = androidx.camera.core.impl.utils.executor.i.c.f24236b     // Catch: java.lang.Throwable -> L1c
                r0.f24232e = r3     // Catch: java.lang.Throwable -> L1c
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L1c
                if (r1 == 0) goto L44
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                r0.interrupt()
            L44:
                return
            L45:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L1c
                boolean r2 = java.lang.Thread.interrupted()     // Catch: java.lang.Throwable -> L4f
                r1 = r1 | r2
                r3.run()     // Catch: java.lang.Throwable -> L4f java.lang.RuntimeException -> L51
                goto L2
            L4f:
                r0 = move-exception
                goto L5c
            L51:
                java.lang.String r2 = "SequentialExecutor"
                r3.toString()     // Catch: java.lang.Throwable -> L4f
                androidx.camera.core.C20140q0.c(r2)     // Catch: java.lang.Throwable -> L4f
                goto L2
            L5a:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L1c
                throw r0     // Catch: java.lang.Throwable -> L4f
            L5c:
                if (r1 == 0) goto L65
                java.lang.Thread r1 = java.lang.Thread.currentThread()
                r1.interrupt()
            L65:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.utils.executor.i.b.a():void");
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                a();
            } catch (Error e12) {
                synchronized (i.this.f24229b) {
                    i.this.f24232e = c.f24236b;
                    throw e12;
                }
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: SequentialExecutor.java */
    public static final class c {

        /* renamed from: b, reason: collision with root package name */
        public static final c f24236b;

        /* renamed from: c, reason: collision with root package name */
        public static final c f24237c;

        /* renamed from: d, reason: collision with root package name */
        public static final c f24238d;

        /* renamed from: e, reason: collision with root package name */
        public static final c f24239e;

        /* renamed from: f, reason: collision with root package name */
        public static final /* synthetic */ c[] f24240f;

        static {
            c cVar = new c("IDLE", 0);
            f24236b = cVar;
            c cVar2 = new c("QUEUING", 1);
            f24237c = cVar2;
            c cVar3 = new c("QUEUED", 2);
            f24238d = cVar3;
            c cVar4 = new c("RUNNING", 3);
            f24239e = cVar4;
            f24240f = new c[]{cVar, cVar2, cVar3, cVar4};
        }

        public c() {
            throw null;
        }

        public static c valueOf(String str) {
            return (c) Enum.valueOf(c.class, str);
        }

        public static c[] values() {
            return (c[]) f24240f.clone();
        }
    }

    public i(Executor executor) {
        executor.getClass();
        this.f24230c = executor;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0061  */
    @Override // java.util.concurrent.Executor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void execute(java.lang.Runnable r8) {
        /*
            r7 = this;
            r8.getClass()
            java.util.ArrayDeque r0 = r7.f24229b
            monitor-enter(r0)
            androidx.camera.core.impl.utils.executor.i$c r1 = r7.f24232e     // Catch: java.lang.Throwable -> L6d
            androidx.camera.core.impl.utils.executor.i$c r2 = androidx.camera.core.impl.utils.executor.i.c.f24239e     // Catch: java.lang.Throwable -> L6d
            if (r1 == r2) goto L6f
            androidx.camera.core.impl.utils.executor.i$c r2 = androidx.camera.core.impl.utils.executor.i.c.f24238d     // Catch: java.lang.Throwable -> L6d
            if (r1 != r2) goto L11
            goto L6f
        L11:
            long r3 = r7.f24233f     // Catch: java.lang.Throwable -> L6d
            androidx.camera.core.impl.utils.executor.i$a r1 = new androidx.camera.core.impl.utils.executor.i$a     // Catch: java.lang.Throwable -> L6d
            r1.<init>(r8)     // Catch: java.lang.Throwable -> L6d
            java.util.ArrayDeque r8 = r7.f24229b     // Catch: java.lang.Throwable -> L6d
            r8.add(r1)     // Catch: java.lang.Throwable -> L6d
            androidx.camera.core.impl.utils.executor.i$c r8 = androidx.camera.core.impl.utils.executor.i.c.f24237c     // Catch: java.lang.Throwable -> L6d
            r7.f24232e = r8     // Catch: java.lang.Throwable -> L6d
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6d
            java.util.concurrent.Executor r0 = r7.f24230c     // Catch: java.lang.Error -> L44 java.lang.RuntimeException -> L46
            androidx.camera.core.impl.utils.executor.i$b r5 = r7.f24231d     // Catch: java.lang.Error -> L44 java.lang.RuntimeException -> L46
            r0.execute(r5)     // Catch: java.lang.Error -> L44 java.lang.RuntimeException -> L46
            androidx.camera.core.impl.utils.executor.i$c r0 = r7.f24232e
            if (r0 == r8) goto L2e
            return
        L2e:
            java.util.ArrayDeque r0 = r7.f24229b
            monitor-enter(r0)
            long r5 = r7.f24233f     // Catch: java.lang.Throwable -> L3e
            int r1 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r1 != 0) goto L40
            androidx.camera.core.impl.utils.executor.i$c r1 = r7.f24232e     // Catch: java.lang.Throwable -> L3e
            if (r1 != r8) goto L40
            r7.f24232e = r2     // Catch: java.lang.Throwable -> L3e
            goto L40
        L3e:
            r8 = move-exception
            goto L42
        L40:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3e
            return
        L42:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3e
            throw r8
        L44:
            r8 = move-exception
            goto L47
        L46:
            r8 = move-exception
        L47:
            java.util.ArrayDeque r2 = r7.f24229b
            monitor-enter(r2)
            androidx.camera.core.impl.utils.executor.i$c r0 = r7.f24232e     // Catch: java.lang.Throwable -> L55
            androidx.camera.core.impl.utils.executor.i$c r3 = androidx.camera.core.impl.utils.executor.i.c.f24236b     // Catch: java.lang.Throwable -> L55
            if (r0 == r3) goto L57
            androidx.camera.core.impl.utils.executor.i$c r3 = androidx.camera.core.impl.utils.executor.i.c.f24237c     // Catch: java.lang.Throwable -> L55
            if (r0 != r3) goto L61
            goto L57
        L55:
            r8 = move-exception
            goto L6b
        L57:
            java.util.ArrayDeque r0 = r7.f24229b     // Catch: java.lang.Throwable -> L55
            boolean r0 = r0.removeLastOccurrence(r1)     // Catch: java.lang.Throwable -> L55
            if (r0 == 0) goto L61
            r0 = 1
            goto L62
        L61:
            r0 = 0
        L62:
            boolean r1 = r8 instanceof java.util.concurrent.RejectedExecutionException     // Catch: java.lang.Throwable -> L55
            if (r1 == 0) goto L6a
            if (r0 != 0) goto L6a
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L55
            return
        L6a:
            throw r8     // Catch: java.lang.Throwable -> L55
        L6b:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L55
            throw r8
        L6d:
            r8 = move-exception
            goto L76
        L6f:
            java.util.ArrayDeque r1 = r7.f24229b     // Catch: java.lang.Throwable -> L6d
            r1.add(r8)     // Catch: java.lang.Throwable -> L6d
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6d
            return
        L76:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6d
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.utils.executor.i.execute(java.lang.Runnable):void");
    }
}
