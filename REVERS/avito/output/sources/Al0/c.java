package aL0;

import Ag.CallableC13028a;
import Y61.k;
import com.squareup.anvil.annotations.ContributesBinding;
import io.reactivex.rxjava3.core.z;
import io.reactivex.rxjava3.internal.operators.observable.C41948f0;
import javax.inject.Inject;
import javax.inject.Singleton;
import kotlin.Metadata;

/* compiled from: PublishWithNativeVideoUxFeedbackDelegate.kt */
@Singleton
@ContributesBinding
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaL0/c;", "LaL0/b;", "_avito_player-core_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class c implements InterfaceC19789b {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final SK0.b f20869a;

    @Inject
    public c(@k SK0.b bVar) {
        this.f20869a = bVar;
    }

    @Override // aL0.InterfaceC19789b
    @k
    public final C41948f0 a() {
        return z.W(new CallableC13028a(5, this, new C19788a()));
    }
}
