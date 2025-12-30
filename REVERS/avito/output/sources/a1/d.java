package A1;

import Y61.k;
import androidx.view.M0;
import kotlin.Metadata;

/* compiled from: JvmViewModelProviders.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LA1/d;", "", "<init>", "()V", "lifecycle-viewmodel_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class d {

    /* renamed from: a, reason: collision with root package name */
    @k
    public static final d f38a = new d();

    @k
    public static M0 a(@k Class cls) {
        try {
            return (M0) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (IllegalAccessException e12) {
            throw new RuntimeException(androidx.compose.ui.graphics.colorspace.e.k(cls, "Cannot create an instance of "), e12);
        } catch (InstantiationException e13) {
            throw new RuntimeException(androidx.compose.ui.graphics.colorspace.e.k(cls, "Cannot create an instance of "), e13);
        } catch (NoSuchMethodException e14) {
            throw new RuntimeException(androidx.compose.ui.graphics.colorspace.e.k(cls, "Cannot create an instance of "), e14);
        }
    }
}
