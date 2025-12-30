package androidx.appcompat.widget;

import Ba1.C13127e;
import android.R;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;
import java.util.HashSet;
import java.util.Set;
import java.util.function.IntFunction;

/* compiled from: LinearLayoutCompat$InspectionCompanion.java */
@RestrictTo
@j.X
/* renamed from: androidx.appcompat.widget.a0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class InspectionCompanionC19938a0 implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22468a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22469b;

    /* renamed from: c, reason: collision with root package name */
    public int f22470c;

    /* renamed from: d, reason: collision with root package name */
    public int f22471d;

    /* renamed from: e, reason: collision with root package name */
    public int f22472e;

    /* renamed from: f, reason: collision with root package name */
    public int f22473f;

    /* renamed from: g, reason: collision with root package name */
    public int f22474g;

    /* renamed from: h, reason: collision with root package name */
    public int f22475h;

    /* renamed from: i, reason: collision with root package name */
    public int f22476i;

    /* renamed from: j, reason: collision with root package name */
    public int f22477j;

    /* compiled from: LinearLayoutCompat$InspectionCompanion.java */
    /* renamed from: androidx.appcompat.widget.a0$a */
    public class a implements IntFunction<String> {
        @Override // java.util.function.IntFunction
        public final String apply(int i12) {
            return i12 != 0 ? i12 != 1 ? String.valueOf(i12) : "vertical" : "horizontal";
        }
    }

    /* compiled from: LinearLayoutCompat$InspectionCompanion.java */
    /* renamed from: androidx.appcompat.widget.a0$b */
    public class b implements IntFunction<Set<String>> {
        @Override // java.util.function.IntFunction
        public final Set<String> apply(int i12) {
            HashSet hashSet = new HashSet();
            if (i12 == 0) {
                hashSet.add("none");
            }
            if (i12 == 1) {
                hashSet.add("beginning");
            }
            if (i12 == 2) {
                hashSet.add("middle");
            }
            if (i12 == 4) {
                hashSet.add("end");
            }
            return hashSet;
        }
    }

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22469b = propertyMapper.mapBoolean("baselineAligned", R.attr.baselineAligned);
        this.f22470c = propertyMapper.mapInt("baselineAlignedChildIndex", R.attr.baselineAlignedChildIndex);
        this.f22471d = propertyMapper.mapGravity("gravity", R.attr.gravity);
        this.f22472e = propertyMapper.mapIntEnum("orientation", R.attr.orientation, new a());
        this.f22473f = propertyMapper.mapFloat("weightSum", R.attr.weightSum);
        this.f22474g = propertyMapper.mapObject("divider", com.avito.android.R.attr.divider);
        this.f22475h = propertyMapper.mapInt("dividerPadding", com.avito.android.R.attr.dividerPadding);
        this.f22476i = propertyMapper.mapBoolean("measureWithLargestChild", com.avito.android.R.attr.measureWithLargestChild);
        this.f22477j = propertyMapper.mapIntFlag("showDividers", com.avito.android.R.attr.showDividers, new b());
        this.f22468a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        LinearLayoutCompat linearLayoutCompat = (LinearLayoutCompat) obj;
        if (!this.f22468a) {
            throw C13127e.o();
        }
        propertyReader.readBoolean(this.f22469b, linearLayoutCompat.f22188b);
        propertyReader.readInt(this.f22470c, linearLayoutCompat.getBaselineAlignedChildIndex());
        propertyReader.readGravity(this.f22471d, linearLayoutCompat.getGravity());
        propertyReader.readIntEnum(this.f22472e, linearLayoutCompat.getOrientation());
        propertyReader.readFloat(this.f22473f, linearLayoutCompat.getWeightSum());
        propertyReader.readObject(this.f22474g, linearLayoutCompat.getDividerDrawable());
        propertyReader.readInt(this.f22475h, linearLayoutCompat.getDividerPadding());
        propertyReader.readBoolean(this.f22476i, linearLayoutCompat.f22195i);
        propertyReader.readIntFlag(this.f22477j, linearLayoutCompat.getShowDividers());
    }
}
