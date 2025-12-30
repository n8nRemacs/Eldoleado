package androidx.camera.core.internal.compat.workaround;

import Y41.l;
import android.content.ComponentName;
import android.media.MediaCodec;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.v0;
import com.vk.id.internal.ipc.IPCClientBaseProvider;
import java.util.Comparator;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class e implements Comparator {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24344b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f24345c;

    public /* synthetic */ e(Object obj, int i12) {
        this.f24344b = i12;
        this.f24345c = obj;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.f24344b) {
            case 0:
                SessionConfig.e eVar = (SessionConfig.e) obj2;
                ((f) this.f24345c).getClass();
                Class<?> cls = ((SessionConfig.e) obj).e().f23938j;
                int i12 = 1;
                int i13 = cls == MediaCodec.class ? 2 : cls == v0.class ? 0 : 1;
                Class<?> cls2 = eVar.e().f23938j;
                if (cls2 == MediaCodec.class) {
                    i12 = 2;
                } else if (cls2 == v0.class) {
                    i12 = 0;
                }
                return i13 - i12;
            case 1:
                return IPCClientBaseProvider.componentComparator$lambda$1((IPCClientBaseProvider) this.f24345c, (ComponentName) obj, (ComponentName) obj2);
            default:
                for (l lVar : (l[]) this.f24345c) {
                    int iB2 = kotlin.comparisons.a.b((Comparable) lVar.invoke(obj), (Comparable) lVar.invoke(obj2));
                    if (iB2 != 0) {
                        return iB2;
                    }
                }
                return 0;
        }
    }
}
