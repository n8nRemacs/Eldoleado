package a2;

import Y61.k;
import Y61.l;
import androidx.annotation.RestrictTo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.s0;

/* compiled from: ProcessLock.kt */
@s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"La2/a;", "", "a", "sqlite-framework_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@RestrictTo
/* renamed from: a2.a, reason: case insensitive filesystem */
/* loaded from: classes10.dex */
public final class C19668a {

    /* renamed from: e, reason: collision with root package name */
    @k
    public static final C1480a f20703e = new C1480a(null);

    /* renamed from: f, reason: collision with root package name */
    @k
    public static final HashMap f20704f = new HashMap();

    /* renamed from: a, reason: collision with root package name */
    public final boolean f20705a;

    /* renamed from: b, reason: collision with root package name */
    @l
    public final File f20706b;

    /* renamed from: c, reason: collision with root package name */
    @k
    public final Lock f20707c;

    /* renamed from: d, reason: collision with root package name */
    @l
    public FileChannel f20708d;

    /* compiled from: ProcessLock.kt */
    @s0
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0014\u0010\u0005\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0005\u0010\u0006R \u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\b0\u00078\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"La2/a$a;", "", "<init>", "()V", "", "TAG", "Ljava/lang/String;", "", "Ljava/util/concurrent/locks/Lock;", "threadLocksMap", "Ljava/util/Map;", "sqlite-framework_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: a2.a$a, reason: collision with other inner class name */
    public static final class C1480a {
        public /* synthetic */ C1480a(C42822w c42822w) {
            this();
        }

        public C1480a() {
        }
    }

    public C19668a(@k String str, @l File file, boolean z12) {
        Lock lock;
        this.f20705a = z12;
        this.f20706b = file != null ? new File(file, str.concat(".lck")) : null;
        f20703e.getClass();
        HashMap map = f20704f;
        synchronized (map) {
            try {
                Object reentrantLock = map.get(str);
                if (reentrantLock == null) {
                    reentrantLock = new ReentrantLock();
                    map.put(str, reentrantLock);
                }
                lock = (Lock) reentrantLock;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        this.f20707c = lock;
    }

    public final void a(boolean z12) throws IOException {
        this.f20707c.lock();
        if (z12) {
            File file = this.f20706b;
            try {
                if (file == null) {
                    throw new IOException("No lock directory was provided.");
                }
                File parentFile = file.getParentFile();
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
                FileChannel channel = new FileOutputStream(file).getChannel();
                channel.lock();
                this.f20708d = channel;
            } catch (IOException unused) {
                this.f20708d = null;
            }
        }
    }

    public final void b() throws IOException {
        try {
            FileChannel fileChannel = this.f20708d;
            if (fileChannel != null) {
                fileChannel.close();
            }
        } catch (IOException unused) {
        }
        this.f20707c.unlock();
    }
}
