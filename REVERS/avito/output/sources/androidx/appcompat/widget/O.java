package androidx.appcompat.widget;

import Ba1.C13127e;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;
import com.avito.android.R;
import java.util.function.IntFunction;

/* compiled from: AppCompatTextView$InspectionCompanion.java */
@RestrictTo
@j.X
/* loaded from: classes.dex */
public final class O implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22224a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22225b;

    /* renamed from: c, reason: collision with root package name */
    public int f22226c;

    /* renamed from: d, reason: collision with root package name */
    public int f22227d;

    /* renamed from: e, reason: collision with root package name */
    public int f22228e;

    /* renamed from: f, reason: collision with root package name */
    public int f22229f;

    /* renamed from: g, reason: collision with root package name */
    public int f22230g;

    /* renamed from: h, reason: collision with root package name */
    public int f22231h;

    /* renamed from: i, reason: collision with root package name */
    public int f22232i;

    /* compiled from: AppCompatTextView$InspectionCompanion.java */
    public class a implements IntFunction<String> {
        @Override // java.util.function.IntFunction
        public final String apply(int i12) {
            return i12 != 0 ? i12 != 1 ? String.valueOf(i12) : "uniform" : "none";
        }
    }

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22225b = propertyMapper.mapInt("autoSizeMaxTextSize", R.attr.autoSizeMaxTextSize);
        this.f22226c = propertyMapper.mapInt("autoSizeMinTextSize", R.attr.autoSizeMinTextSize);
        this.f22227d = propertyMapper.mapInt("autoSizeStepGranularity", R.attr.autoSizeStepGranularity);
        this.f22228e = propertyMapper.mapIntEnum("autoSizeTextType", R.attr.autoSizeTextType, new a());
        this.f22229f = propertyMapper.mapObject("backgroundTint", R.attr.backgroundTint);
        this.f22230g = propertyMapper.mapObject("backgroundTintMode", R.attr.backgroundTintMode);
        this.f22231h = propertyMapper.mapObject("drawableTint", R.attr.drawableTint);
        this.f22232i = propertyMapper.mapObject("drawableTintMode", R.attr.drawableTintMode);
        this.f22224a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        AppCompatTextView appCompatTextView = (AppCompatTextView) obj;
        if (!this.f22224a) {
            throw C13127e.o();
        }
        propertyReader.readInt(this.f22225b, appCompatTextView.getAutoSizeMaxTextSize());
        propertyReader.readInt(this.f22226c, appCompatTextView.getAutoSizeMinTextSize());
        propertyReader.readInt(this.f22227d, appCompatTextView.getAutoSizeStepGranularity());
        propertyReader.readIntEnum(this.f22228e, appCompatTextView.getAutoSizeTextType());
        propertyReader.readObject(this.f22229f, appCompatTextView.getBackgroundTintList());
        propertyReader.readObject(this.f22230g, appCompatTextView.getBackgroundTintMode());
        propertyReader.readObject(this.f22231h, appCompatTextView.getCompoundDrawableTintList());
        propertyReader.readObject(this.f22232i, appCompatTextView.getCompoundDrawableTintMode());
    }
}
