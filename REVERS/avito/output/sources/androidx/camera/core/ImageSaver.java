package androidx.camera.core;

import androidx.camera.core.internal.utils.ImageUtil;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;

@j.X
/* loaded from: classes.dex */
final class ImageSaver implements Runnable {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class SaveError {

        /* renamed from: b, reason: collision with root package name */
        public static final /* synthetic */ SaveError[] f23583b = {new SaveError("FILE_IO_FAILED", 0), new SaveError("ENCODE_FAILED", 1), new SaveError("CROP_FAILED", 2), new SaveError(GrsBaseInfo.CountryCodeSource.UNKNOWN, 3)};

        /* JADX INFO: Fake field, exist only in values array */
        SaveError EF5;

        public SaveError() {
            throw null;
        }

        public static SaveError valueOf(String str) {
            return (SaveError) Enum.valueOf(SaveError.class, str);
        }

        public static SaveError[] values() {
            return (SaveError[]) f23583b.clone();
        }
    }

    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f23584a;

        static {
            int[] iArr = new int[ImageUtil.CodecFailedException.FailureType.values().length];
            f23584a = iArr;
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f23584a[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f23584a[2] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public interface b {
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:?, code lost:
    
        throw null;
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void run() {
        /*
            r1 = this;
            r0 = 0
            throw r0     // Catch: java.io.IOException -> L2
        L2:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.ImageSaver.run():void");
    }
}
