package androidx.camera.video;

import android.view.Surface;
import androidx.camera.core.C20140q0;
import androidx.camera.core.K0;
import androidx.camera.video.internal.encoder.InterfaceC20169k;
import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: VideoEncoderSession.java */
@j.X
/* loaded from: classes.dex */
final class l0 {

    /* renamed from: a, reason: collision with root package name */
    public final Executor f25290a;

    /* renamed from: b, reason: collision with root package name */
    public final Executor f25291b;

    /* renamed from: c, reason: collision with root package name */
    public final androidx.appcompat.app.r f25292c;

    /* renamed from: d, reason: collision with root package name */
    public InterfaceC20169k f25293d = null;

    /* renamed from: e, reason: collision with root package name */
    public Surface f25294e = null;

    /* renamed from: f, reason: collision with root package name */
    public K0 f25295f = null;

    /* renamed from: g, reason: collision with root package name */
    public Executor f25296g = null;

    /* renamed from: h, reason: collision with root package name */
    public F f25297h = null;

    /* renamed from: i, reason: collision with root package name */
    public b f25298i = b.f25304b;

    /* renamed from: j, reason: collision with root package name */
    public D0<Void> f25299j = androidx.camera.core.impl.utils.futures.f.e(new IllegalStateException("Cannot close the encoder before configuring."));

    /* renamed from: k, reason: collision with root package name */
    public b.a<Void> f25300k = null;

    /* renamed from: l, reason: collision with root package name */
    public D0<InterfaceC20169k> f25301l = androidx.camera.core.impl.utils.futures.f.e(new IllegalStateException("Cannot close the encoder before configuring."));

    /* renamed from: m, reason: collision with root package name */
    public b.a<InterfaceC20169k> f25302m = null;

    /* compiled from: VideoEncoderSession.java */
    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f25303a;

        static {
            int[] iArr = new int[b.values().length];
            f25303a = iArr;
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f25303a[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f25303a[3] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f25303a[2] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f25303a[4] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: VideoEncoderSession.java */
    public static final class b {

        /* renamed from: b, reason: collision with root package name */
        public static final b f25304b;

        /* renamed from: c, reason: collision with root package name */
        public static final b f25305c;

        /* renamed from: d, reason: collision with root package name */
        public static final b f25306d;

        /* renamed from: e, reason: collision with root package name */
        public static final b f25307e;

        /* renamed from: f, reason: collision with root package name */
        public static final b f25308f;

        /* renamed from: g, reason: collision with root package name */
        public static final /* synthetic */ b[] f25309g;

        static {
            b bVar = new b("NOT_INITIALIZED", 0);
            f25304b = bVar;
            b bVar2 = new b("INITIALIZING", 1);
            f25305c = bVar2;
            b bVar3 = new b("PENDING_RELEASE", 2);
            f25306d = bVar3;
            b bVar4 = new b("READY", 3);
            f25307e = bVar4;
            b bVar5 = new b("RELEASED", 4);
            f25308f = bVar5;
            f25309g = new b[]{bVar, bVar2, bVar3, bVar4, bVar5};
        }

        public b() {
            throw null;
        }

        public static b valueOf(String str) {
            return (b) Enum.valueOf(b.class, str);
        }

        public static b[] values() {
            return (b[]) f25309g.clone();
        }
    }

    public l0(@j.N androidx.appcompat.app.r rVar, @j.N Executor executor, @j.N Executor executor2) {
        this.f25290a = executor2;
        this.f25291b = executor;
        this.f25292c = rVar;
    }

    public final void a() {
        int iOrdinal = this.f25298i.ordinal();
        if (iOrdinal == 0 || iOrdinal == 1) {
            b();
            return;
        }
        if (iOrdinal == 2 || iOrdinal == 3) {
            Objects.toString(this.f25298i);
            C20140q0.d(3, "VideoEncoderSession");
            this.f25298i = b.f25306d;
        } else {
            if (iOrdinal == 4) {
                C20140q0.d(3, "VideoEncoderSession");
                return;
            }
            throw new IllegalStateException("State " + this.f25298i + " is not handled");
        }
    }

    public final void b() {
        int iOrdinal = this.f25298i.ordinal();
        b bVar = b.f25308f;
        if (iOrdinal == 0) {
            this.f25298i = bVar;
            return;
        }
        if (iOrdinal != 1 && iOrdinal != 2 && iOrdinal != 3) {
            if (iOrdinal == 4) {
                Objects.toString(this.f25298i);
                C20140q0.d(3, "VideoEncoderSession");
                return;
            } else {
                throw new IllegalStateException("State " + this.f25298i + " is not handled");
            }
        }
        this.f25298i = bVar;
        this.f25302m.b(this.f25293d);
        this.f25295f = null;
        InterfaceC20169k interfaceC20169k = this.f25293d;
        if (interfaceC20169k == null) {
            C20140q0.d(5, "VideoEncoderSession");
            this.f25300k.b(null);
            return;
        }
        Objects.toString(interfaceC20169k);
        C20140q0.d(3, "VideoEncoderSession");
        this.f25293d.release();
        this.f25293d.b().N(new f0(this, 0), this.f25291b);
        this.f25293d = null;
    }

    @j.N
    public final String toString() {
        return "VideoEncoderSession@" + hashCode() + " for " + Objects.toString(this.f25295f, "SURFACE_REQUEST_NOT_CONFIGURED");
    }
}
