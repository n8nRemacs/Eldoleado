package androidx.appcompat.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import androidx.annotation.RestrictTo;

/* compiled from: TintContextWrapper.java */
@RestrictTo
/* loaded from: classes.dex */
public class A0 extends ContextWrapper {

    /* renamed from: a, reason: collision with root package name */
    public static final Object f21940a = null;

    public static void a(@j.N Context context) {
        if ((context instanceof A0) || (context.getResources() instanceof C0) || (context.getResources() instanceof N0)) {
            return;
        }
        int i12 = N0.f22223a;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final AssetManager getAssets() {
        throw null;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Resources getResources() {
        return null;
    }
}
