package A91;

import android.os.Bundle;
import kotlin.jvm.internal.N;

/* loaded from: classes9.dex */
public final class l extends N implements Y41.a {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ ru.mts.biometry.sdk.base.p f150l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public l(ru.mts.biometry.sdk.base.p pVar) {
        super(0);
        this.f150l = pVar;
    }

    @Override // Y41.a
    public final Object invoke() {
        Bundle arguments = this.f150l.getArguments();
        return Boolean.valueOf(arguments != null ? arguments.getBoolean("arg_show_button") : true);
    }
}
