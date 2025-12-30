package a81;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.introspect.C36464c;
import com.fasterxml.jackson.databind.introspect.C36472k;
import e81.InterfaceC39968b;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
/* renamed from: a81.d, reason: case insensitive filesystem */
/* loaded from: classes7.dex */
public final /* synthetic */ class C19699d implements Predicate {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f20748a;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.f20748a) {
            case 0:
                return !Y71.i.a(((InterfaceC39968b) obj).getMethod());
            default:
                C36464c c36464c = (C36464c) obj;
                if (((C36472k) c36464c.f342004a).w().length == 1 && ((C36472k) c36464c.f342004a).v(0) == String.class) {
                    return c36464c.f342005b == JsonCreator.Mode.f340924d;
                }
                return true;
        }
    }
}
