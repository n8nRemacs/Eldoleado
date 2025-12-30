package A91;

import android.content.Context;
import kotlin.jvm.internal.N;

/* loaded from: classes9.dex */
public final class n extends N implements Y41.a {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ ru.mts.biometry.sdk.base.p f152l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public n(ru.mts.biometry.sdk.base.p pVar) {
        super(0);
        this.f152l = pVar;
    }

    @Override // Y41.a
    public final Object invoke() {
        ru.mts.biometry.sdk.base.p pVar = this.f152l;
        int i12 = pVar.requireArguments().getInt("arg_title");
        Context context = pVar.getContext();
        String string = context != null ? context.getString(i12) : null;
        return string == null ? "" : string;
    }
}
