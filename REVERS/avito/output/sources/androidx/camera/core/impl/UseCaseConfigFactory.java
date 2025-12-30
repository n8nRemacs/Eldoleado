package androidx.camera.core.impl;

import android.content.Context;

@j.X
/* loaded from: classes.dex */
public interface UseCaseConfigFactory {

    /* renamed from: a, reason: collision with root package name */
    public static final UseCaseConfigFactory f24053a = new a();

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class CaptureType {

        /* renamed from: b, reason: collision with root package name */
        public static final CaptureType f24054b;

        /* renamed from: c, reason: collision with root package name */
        public static final CaptureType f24055c;

        /* renamed from: d, reason: collision with root package name */
        public static final CaptureType f24056d;

        /* renamed from: e, reason: collision with root package name */
        public static final CaptureType f24057e;

        /* renamed from: f, reason: collision with root package name */
        public static final CaptureType f24058f;

        /* renamed from: g, reason: collision with root package name */
        public static final CaptureType f24059g;

        /* renamed from: h, reason: collision with root package name */
        public static final /* synthetic */ CaptureType[] f24060h;

        static {
            CaptureType captureType = new CaptureType("IMAGE_CAPTURE", 0);
            f24054b = captureType;
            CaptureType captureType2 = new CaptureType("PREVIEW", 1);
            f24055c = captureType2;
            CaptureType captureType3 = new CaptureType("IMAGE_ANALYSIS", 2);
            f24056d = captureType3;
            CaptureType captureType4 = new CaptureType("VIDEO_CAPTURE", 3);
            f24057e = captureType4;
            CaptureType captureType5 = new CaptureType("STREAM_SHARING", 4);
            f24058f = captureType5;
            CaptureType captureType6 = new CaptureType("METERING_REPEATING", 5);
            f24059g = captureType6;
            f24060h = new CaptureType[]{captureType, captureType2, captureType3, captureType4, captureType5, captureType6};
        }

        public CaptureType() {
            throw null;
        }

        public static CaptureType valueOf(String str) {
            return (CaptureType) Enum.valueOf(CaptureType.class, str);
        }

        public static CaptureType[] values() {
            return (CaptureType[]) f24060h.clone();
        }
    }

    public class a implements UseCaseConfigFactory {
        @Override // androidx.camera.core.impl.UseCaseConfigFactory
        @j.P
        public final Config a(@j.N CaptureType captureType, int i12) {
            return null;
        }
    }

    public interface b {
        @j.N
        androidx.camera.camera2.internal.P a(@j.N Context context);
    }

    @j.P
    Config a(@j.N CaptureType captureType, int i12);
}
