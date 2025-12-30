package androidx.camera.video;

import android.util.Size;
import androidx.annotation.RestrictTo;
import java.util.ArrayList;

/* compiled from: VideoCapabilities.java */
@j.X
/* loaded from: classes.dex */
public interface X {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    @RestrictTo
    public static final X f24847a = new a();

    /* compiled from: VideoCapabilities.java */
    public class a implements X {
        @Override // androidx.camera.video.X
        @j.N
        public final ArrayList a(@j.N androidx.camera.core.F f12) {
            return new ArrayList();
        }
    }

    @j.N
    ArrayList a(@j.N androidx.camera.core.F f12);

    @RestrictTo
    @j.P
    default androidx.camera.video.internal.f b(@j.N A a12, @j.N androidx.camera.core.F f12) {
        return null;
    }

    @RestrictTo
    @j.P
    default androidx.camera.video.internal.f c(@j.N Size size, @j.N androidx.camera.core.F f12) {
        return null;
    }
}
