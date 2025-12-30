package A00;

import Y61.k;
import androidx.compose.runtime.internal.P;
import androidx.recyclerview.widget.C23424o;
import com.avito.android.mortgage.person_form.list.items.PersonFormItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.jvm.internal.s0;

/* compiled from: PersonFormDiffCallback.kt */
@P
@s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"}, d2 = {"LA00/a;", "Landroidx/recyclerview/widget/o$f;", "Lcom/avito/android/mortgage/person_form/list/items/PersonFormItem;", "_avito_mortgage_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes15.dex */
public final class a extends C23424o.f<PersonFormItem> {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final Set<c> f33a;

    @Inject
    public a(@k Set<c> set) {
        this.f33a = set;
    }

    @Override // androidx.recyclerview.widget.C23424o.f
    public final boolean a(PersonFormItem personFormItem, PersonFormItem personFormItem2) {
        return personFormItem.equals(personFormItem2);
    }

    @Override // androidx.recyclerview.widget.C23424o.f
    public final boolean b(PersonFormItem personFormItem, PersonFormItem personFormItem2) {
        return personFormItem.getF287615b() == personFormItem2.getF287615b();
    }

    @Override // androidx.recyclerview.widget.C23424o.f
    public final Object c(PersonFormItem personFormItem, PersonFormItem personFormItem2) {
        PersonFormItem personFormItem3 = personFormItem;
        PersonFormItem personFormItem4 = personFormItem2;
        Set<c> set = this.f33a;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            Object objA = ((c) it.next()).a(personFormItem3, personFormItem4);
            if (objA != null) {
                arrayList.add(objA);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }
}
