package androidx.camera.video.internal.workaround;

import androidx.camera.core.impl.Timebase;
import androidx.camera.video.internal.encoder.H;
import j.N;
import j.P;
import j.X;

/* compiled from: VideoTimebaseConverter.java */
@X
/* loaded from: classes.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    public final H f25278a;

    /* renamed from: b, reason: collision with root package name */
    public long f25279b = -1;

    /* renamed from: c, reason: collision with root package name */
    public Timebase f25280c;

    /* compiled from: VideoTimebaseConverter.java */
    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f25281a;

        static {
            int[] iArr = new int[Timebase.values().length];
            f25281a = iArr;
            try {
                iArr[1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f25281a[0] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public e(@N H h12, @P Timebase timebase) {
        this.f25278a = h12;
        this.f25280c = timebase;
    }
}
