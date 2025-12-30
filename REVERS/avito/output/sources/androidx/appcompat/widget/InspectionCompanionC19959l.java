package androidx.appcompat.widget;

import Ba1.C13127e;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;
import com.avito.android.R;
import java.util.function.IntFunction;

/* compiled from: AppCompatButton$InspectionCompanion.java */
@RestrictTo
@j.X
/* renamed from: androidx.appcompat.widget.l, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class InspectionCompanionC19959l implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22555a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22556b;

    /* renamed from: c, reason: collision with root package name */
    public int f22557c;

    /* renamed from: d, reason: collision with root package name */
    public int f22558d;

    /* renamed from: e, reason: collision with root package name */
    public int f22559e;

    /* renamed from: f, reason: collision with root package name */
    public int f22560f;

    /* renamed from: g, reason: collision with root package name */
    public int f22561g;

    /* renamed from: h, reason: collision with root package name */
    public int f22562h;

    /* renamed from: i, reason: collision with root package name */
    public int f22563i;

    /* compiled from: AppCompatButton$InspectionCompanion.java */
    /* renamed from: androidx.appcompat.widget.l$a */
    public class a implements IntFunction<String> {
        @Override // java.util.function.IntFunction
        public final String apply(int i12) {
            return i12 != 0 ? i12 != 1 ? String.valueOf(i12) : "uniform" : "none";
        }
    }

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22556b = propertyMapper.mapInt("autoSizeMaxTextSize", R.attr.autoSizeMaxTextSize);
        this.f22557c = propertyMapper.mapInt("autoSizeMinTextSize", R.attr.autoSizeMinTextSize);
        this.f22558d = propertyMapper.mapInt("autoSizeStepGranularity", R.attr.autoSizeStepGranularity);
        this.f22559e = propertyMapper.mapIntEnum("autoSizeTextType", R.attr.autoSizeTextType, new a());
        this.f22560f = propertyMapper.mapObject("backgroundTint", R.attr.backgroundTint);
        this.f22561g = propertyMapper.mapObject("backgroundTintMode", R.attr.backgroundTintMode);
        this.f22562h = propertyMapper.mapObject("drawableTint", R.attr.drawableTint);
        this.f22563i = propertyMapper.mapObject("drawableTintMode", R.attr.drawableTintMode);
        this.f22555a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        C19961m c19961m = (C19961m) obj;
        if (!this.f22555a) {
            throw C13127e.o();
        }
        propertyReader.readInt(this.f22556b, c19961m.getAutoSizeMaxTextSize());
        propertyReader.readInt(this.f22557c, c19961m.getAutoSizeMinTextSize());
        propertyReader.readInt(this.f22558d, c19961m.getAutoSizeStepGranularity());
        propertyReader.readIntEnum(this.f22559e, c19961m.getAutoSizeTextType());
        propertyReader.readObject(this.f22560f, c19961m.getBackgroundTintList());
        propertyReader.readObject(this.f22561g, c19961m.getBackgroundTintMode());
        propertyReader.readObject(this.f22562h, c19961m.getCompoundDrawableTintList());
        propertyReader.readObject(this.f22563i, c19961m.getCompoundDrawableTintMode());
    }
}
