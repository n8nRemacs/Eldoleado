package A91;

import android.os.Bundle;
import kotlin.jvm.internal.N;

/* loaded from: classes9.dex */
public final class d extends N implements Y41.a {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ ru.mts.biometry.sdk.base.f f139l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public d(ru.mts.biometry.sdk.base.f fVar) {
        super(0);
        this.f139l = fVar;
    }

    @Override // Y41.a
    public final Object invoke() {
        Bundle arguments = this.f139l.getArguments();
        if (arguments != null) {
            return arguments.getString("arg_title");
        }
        return null;
    }
}
