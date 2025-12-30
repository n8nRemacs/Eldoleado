package A;

import android.content.Context;
import androidx.camera.camera2.internal.L;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.InitializationException;
import androidx.camera.core.impl.InterfaceC20116x;
import java.util.Set;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class b implements InterfaceC20116x.a {
    @Override // androidx.camera.core.impl.InterfaceC20116x.a
    public final L a(Context context, Object obj, Set set) throws InitializationException {
        try {
            return new L(context, obj, set);
        } catch (CameraUnavailableException e12) {
            throw new InitializationException(e12);
        }
    }
}
